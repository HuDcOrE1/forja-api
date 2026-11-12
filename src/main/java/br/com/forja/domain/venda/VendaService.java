package br.com.forja.domain.venda;

import br.com.forja.domain.cliente.Cliente;
import br.com.forja.domain.cliente.ClienteRepository;
import br.com.forja.domain.estoque.MovimentacaoEstoqueService;
import br.com.forja.domain.estoque.dto.EntradaEstoqueRequest;
import br.com.forja.domain.estoque.dto.SaidaEstoqueRequest;
import br.com.forja.domain.produto.Produto;
import br.com.forja.domain.produto.ProdutoRepository;
import br.com.forja.domain.usuario.Usuario;
import br.com.forja.domain.usuario.UsuarioRepository;
import br.com.forja.domain.venda.dto.CancelarVendaRequest;
import br.com.forja.domain.venda.dto.ItemVendaRequest;
import br.com.forja.domain.venda.dto.ItemVendaResponse;
import br.com.forja.domain.venda.dto.PagamentoVendaRequest;
import br.com.forja.domain.venda.dto.PagamentoVendaResponse;
import br.com.forja.domain.venda.dto.VendaRequest;
import br.com.forja.domain.venda.dto.VendaResponse;
import br.com.forja.exception.VendaNaoEncontradaException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {

        private final VendaRepository vendaRepository;
        private final ItemVendaRepository itemVendaRepository;
        private final PagamentoVendaRepository pagamentoVendaRepository;

        private final ProdutoRepository produtoRepository;
        private final ClienteRepository clienteRepository;
        private final UsuarioRepository usuarioRepository;

        private final MovimentacaoEstoqueService movimentacaoEstoqueService;

        public List<VendaResponse> listar() {

                return vendaRepository.findAllByOrderByCriadoEmDesc()
                                .stream()
                                .map(this::toResponse)
                                .toList();
        }

        public VendaResponse buscarPorId(Long id) {

                Venda venda = vendaRepository.findById(id)
                                .orElseThrow(() -> new VendaNaoEncontradaException(id));

                return toResponse(venda);
        }

        @Transactional
        public VendaResponse salvar(VendaRequest request) {

                Cliente cliente = clienteRepository
                                .findById(request.clienteId())
                                .orElseThrow();

                Authentication authentication = SecurityContextHolder
                                .getContext()
                                .getAuthentication();

                String email = authentication.getName();

                Usuario usuario = usuarioRepository
                                .findByEmail(email)
                                .orElseThrow();

                BigDecimal valorDesconto = request.valorDesconto() == null
                                ? BigDecimal.ZERO
                                : request.valorDesconto();

                BigDecimal valorAcrescimo = request.valorAcrescimo() == null
                                ? BigDecimal.ZERO
                                : request.valorAcrescimo();

                if (valorDesconto.compareTo(BigDecimal.ZERO) < 0) {
                        throw new IllegalArgumentException(
                                        "O valor do desconto não pode ser negativo.");
                }

                if (valorAcrescimo.compareTo(BigDecimal.ZERO) < 0) {
                        throw new IllegalArgumentException(
                                        "O valor do acréscimo não pode ser negativo.");
                }

                if (request.pagamentos() == null || request.pagamentos().isEmpty()) {
                        throw new IllegalArgumentException(
                                        "A venda deve possuir ao menos uma forma de pagamento.");
                }

                BigDecimal valorBruto = BigDecimal.ZERO;

                for (ItemVendaRequest itemRequest : request.itens()) {

                        if (itemRequest.quantidade() == null
                                        || itemRequest.quantidade() <= 0) {

                                throw new IllegalArgumentException(
                                                "A quantidade do item deve ser maior que zero.");
                        }

                        Produto produto = produtoRepository
                                        .findById(itemRequest.produtoId())
                                        .orElseThrow();

                        BigDecimal subtotal = produto.getValorVenda()
                                        .multiply(
                                                        BigDecimal.valueOf(
                                                                        itemRequest.quantidade()));

                        valorBruto = valorBruto.add(subtotal);
                }

                BigDecimal valorLiquido = valorBruto
                                .subtract(valorDesconto)
                                .add(valorAcrescimo);

                if (valorLiquido.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new IllegalArgumentException(
                                        "O valor líquido da venda deve ser maior que zero.");
                }

                BigDecimal valorPagamentos = request.pagamentos()
                                .stream()
                                .map(pagamento -> {

                                        if (pagamento.formaPagamento() == null) {
                                                throw new IllegalArgumentException(
                                                                "A forma de pagamento é obrigatória.");
                                        }

                                        if (pagamento.valor() == null
                                                        || pagamento.valor().compareTo(BigDecimal.ZERO) <= 0) {

                                                throw new IllegalArgumentException(
                                                                "O valor do pagamento deve ser maior que zero.");
                                        }

                                        return pagamento.valor();
                                })
                                .reduce(
                                                BigDecimal.ZERO,
                                                BigDecimal::add);

                if (valorPagamentos.compareTo(valorLiquido) != 0) {
                        throw new IllegalArgumentException(
                                        "A soma dos pagamentos deve ser igual ao valor líquido da venda.");
                }

                Venda venda = Venda.builder()
                                .cliente(cliente)
                                .usuario(usuario)
                                .valorBruto(valorBruto)
                                .valorDesconto(valorDesconto)
                                .valorAcrescimo(valorAcrescimo)
                                .valorLiquido(valorLiquido)
                                .status(StatusVenda.FINALIZADA)
                                .build();

                venda = vendaRepository.save(venda);

                for (ItemVendaRequest itemRequest : request.itens()) {

                        Produto produto = produtoRepository
                                        .findById(itemRequest.produtoId())
                                        .orElseThrow();

                        BigDecimal subtotal = produto.getValorVenda()
                                        .multiply(
                                                        BigDecimal.valueOf(
                                                                        itemRequest.quantidade()));

                        ItemVenda item = ItemVenda.builder()
                                        .venda(venda)
                                        .produto(produto)
                                        .quantidade(itemRequest.quantidade())
                                        .valorUnitario(produto.getValorVenda())
                                        .subtotal(subtotal)
                                        .build();

                        itemVendaRepository.save(item);
                }

                for (PagamentoVendaRequest pagamentoRequest : request.pagamentos()) {

                        PagamentoVenda pagamento = PagamentoVenda.builder()
                                        .venda(venda)
                                        .formaPagamento(
                                                        pagamentoRequest.formaPagamento())
                                        .valor(
                                                        pagamentoRequest.valor())
                                        .build();

                        pagamentoVendaRepository.save(pagamento);
                }

                for (ItemVendaRequest itemRequest : request.itens()) {

                        Produto produto = produtoRepository
                                        .findById(itemRequest.produtoId())
                                        .orElseThrow();

                        movimentacaoEstoqueService.saida(
                                        new SaidaEstoqueRequest(
                                                        produto.getId(),
                                                        itemRequest.quantidade(),
                                                        "Venda " + venda.getId()));
                }

                return toResponse(venda);
        }

        @Transactional
        public VendaResponse cancelar(
                        Long id,
                        CancelarVendaRequest request) {

                Venda venda = vendaRepository.findById(id)
                                .orElseThrow(() -> new VendaNaoEncontradaException(id));

                if (venda.getStatus() == StatusVenda.CANCELADA) {
                        throw new IllegalArgumentException(
                                        "A venda já está cancelada.");
                }

                Authentication authentication = SecurityContextHolder
                                .getContext()
                                .getAuthentication();

                String email = authentication.getName();

                Usuario usuario = usuarioRepository
                                .findByEmail(email)
                                .orElseThrow();

                List<ItemVenda> itens = itemVendaRepository
                                .findByVendaId(venda.getId());

                for (ItemVenda item : itens) {

                        movimentacaoEstoqueService.entrada(
                                        new EntradaEstoqueRequest(
                                                        item.getProduto().getId(),
                                                        item.getQuantidade(),
                                                        "Estorno da venda " + venda.getId()));
                }

                venda.setStatus(StatusVenda.CANCELADA);

                venda.setCanceladaEm(
                                java.time.LocalDateTime.now());

                venda.setCanceladaPor(usuario);

                venda.setMotivoCancelamento(
                                request.motivo());

                venda = vendaRepository.save(venda);

                return toResponse(venda);
        }

        private VendaResponse toResponse(Venda venda) {

                List<ItemVendaResponse> itens = itemVendaRepository
                                .findByVendaId(venda.getId())
                                .stream()
                                .map(item -> new ItemVendaResponse(
                                                item.getProduto().getId(),
                                                item.getProduto().getNome(),
                                                item.getQuantidade(),
                                                item.getValorUnitario(),
                                                item.getSubtotal()))
                                .toList();

                List<PagamentoVendaResponse> pagamentos = pagamentoVendaRepository
                                .findByVendaId(venda.getId())
                                .stream()
                                .map(pagamento -> new PagamentoVendaResponse(
                                                pagamento.getFormaPagamento(),
                                                pagamento.getValor()))
                                .toList();

                return new VendaResponse(
                                venda.getId(),
                                venda.getCliente().getId(),
                                venda.getCliente().getNome(),
                                venda.getUsuario().getId(),
                                venda.getUsuario().getNome(),
                                venda.getValorBruto(),
                                venda.getValorDesconto(),
                                venda.getValorAcrescimo(),
                                venda.getValorLiquido(),
                                venda.getStatus().name(),
                                venda.getCriadoEm(),
                                itens,
                                pagamentos);
        }
}