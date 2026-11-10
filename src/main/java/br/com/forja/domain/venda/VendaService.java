package br.com.forja.domain.venda;

import br.com.forja.domain.cliente.Cliente;
import br.com.forja.domain.cliente.ClienteRepository;
import br.com.forja.domain.estoque.MovimentacaoEstoqueService;
import br.com.forja.domain.estoque.dto.SaidaEstoqueRequest;
import br.com.forja.domain.produto.Produto;
import br.com.forja.domain.produto.ProdutoRepository;
import br.com.forja.domain.usuario.Usuario;
import br.com.forja.domain.usuario.UsuarioRepository;
import br.com.forja.domain.venda.dto.ItemVendaRequest;
import br.com.forja.domain.venda.dto.ItemVendaResponse;
import br.com.forja.domain.venda.dto.VendaRequest;
import br.com.forja.domain.venda.dto.VendaResponse;
import br.com.forja.exception.VendaNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {

        private final VendaRepository vendaRepository;
        private final ItemVendaRepository itemVendaRepository;
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
                                .orElseThrow(
                                                () -> new VendaNaoEncontradaException(id));

                return toResponse(venda);
        }

        public VendaResponse salvar(
                        VendaRequest request) {

                Cliente cliente = request.clienteId() == null
                                ? null
                                : clienteRepository
                                                .findById(
                                                                request.clienteId())
                                                .orElseThrow();

                Authentication authentication = SecurityContextHolder
                                .getContext()
                                .getAuthentication();

                String email = authentication.getName();

                Usuario usuario = usuarioRepository
                                .findByEmail(email)
                                .orElseThrow();

                Venda venda = Venda.builder()
                                .cliente(cliente)
                                .usuario(usuario)
                                .formaPagamento(
                                                request.formaPagamento())
                                .status("FINALIZADA")
                                .valorTotal(BigDecimal.ZERO)
                                .build();

                venda = vendaRepository.save(venda);

                BigDecimal valorTotal = BigDecimal.ZERO;

                for (ItemVendaRequest itemRequest : request.itens()) {

                        Produto produto = produtoRepository
                                        .findById(
                                                        itemRequest.produtoId())
                                        .orElseThrow();

                        BigDecimal subtotal = produto.getValorVenda()
                                        .multiply(
                                                        BigDecimal.valueOf(
                                                                        itemRequest.quantidade()));

                        ItemVenda item = ItemVenda.builder()
                                        .venda(venda)
                                        .produto(produto)
                                        .quantidade(
                                                        itemRequest.quantidade())
                                        .valorUnitario(
                                                        produto.getValorVenda())
                                        .subtotal(subtotal)
                                        .build();

                        itemVendaRepository.save(item);

                        valorTotal = valorTotal.add(subtotal);

                        movimentacaoEstoqueService.saida(
                                        new SaidaEstoqueRequest(
                                                        produto.getId(),
                                                        itemRequest.quantidade(),
                                                        "Venda " + venda.getId()));
                }

                venda.setValorTotal(valorTotal);

                venda = vendaRepository.save(venda);

                return toResponse(venda);
        }

        private VendaResponse toResponse(
                        Venda venda) {

                List<ItemVendaResponse> itens = itemVendaRepository
                                .findByVendaId(
                                                venda.getId())
                                .stream()
                                .map(item -> new ItemVendaResponse(
                                                item.getProduto().getId(),
                                                item.getProduto().getNome(),
                                                item.getQuantidade(),
                                                item.getValorUnitario(),
                                                item.getSubtotal()))
                                .toList();

                return new VendaResponse(
                                venda.getId(),
                                venda.getCliente() != null
                                                ? venda.getCliente().getId()
                                                : null,
                                venda.getCliente() != null
                                                ? venda.getCliente().getNome()
                                                : null,
                                venda.getUsuario().getId(),
                                venda.getUsuario().getNome(),
                                venda.getValorTotal(),
                                venda.getFormaPagamento(),
                                venda.getStatus(),
                                venda.getCriadoEm(),
                                itens);
        }
}