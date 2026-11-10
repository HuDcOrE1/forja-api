package br.com.forja.domain.relatorio;

import br.com.forja.domain.agendamento.Agendamento;
import br.com.forja.domain.agendamento.AgendamentoRepository;
import br.com.forja.domain.cliente.ClienteRepository;
import br.com.forja.domain.produto.Produto;
import br.com.forja.domain.produto.ProdutoRepository;
import br.com.forja.domain.raia.RaiaRepository;
import br.com.forja.domain.relatorio.dto.*;
import br.com.forja.domain.venda.Venda;
import br.com.forja.domain.venda.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

        private final ProdutoRepository produtoRepository;
        private final VendaRepository vendaRepository;
        private final ClienteRepository clienteRepository;
        private final AgendamentoRepository agendamentoRepository;
        private final RaiaRepository raiaRepository;

        public List<RelatorioEstoqueResponse> estoque(
                        RelatorioEstoqueRequest filtro,
                        int page,
                        int size,
                        String sort,
                        String direction) {

                List<RelatorioEstoqueResponse> resultado = produtoRepository.findByAtivoTrue()
                                .stream()
                                .filter(produto -> filtro == null
                                                || filtro.estoqueMinimo() == null
                                                || produto.getEstoque() >= filtro.estoqueMinimo())
                                .filter(produto -> filtro == null
                                                || filtro.categoriaId() == null
                                                || produto.getCategoria()
                                                                .getId()
                                                                .equals(filtro.categoriaId()))
                                .map(this::estoqueResponse)
                                .toList();

                Comparator<RelatorioEstoqueResponse> comparator = switch (sort) {

                        case "estoque" ->
                                Comparator.comparing(
                                                RelatorioEstoqueResponse::estoque);

                        case "valorVenda" ->
                                Comparator.comparing(
                                                RelatorioEstoqueResponse::valorVenda);

                        case "valorTotalEstoque" ->
                                Comparator.comparing(
                                                RelatorioEstoqueResponse::valorTotalEstoque);

                        default ->
                                Comparator.comparing(
                                                RelatorioEstoqueResponse::produto);
                };

                if ("DESC".equalsIgnoreCase(direction)) {
                        comparator = comparator.reversed();
                }

                return resultado.stream()
                                .sorted(comparator)
                                .skip((long) page * size)
                                .limit(size)
                                .toList();
        }

        public List<RelatorioVendaResponse> vendas(
                        FiltroVendaRequest filtro,
                        int page,
                        int size,
                        String sort,
                        String direction) {

                List<RelatorioVendaResponse> resultado = vendaRepository.findAllByOrderByCriadoEmDesc()
                                .stream()
                                .filter(venda -> filtro == null
                                                || filtro.clienteId() == null
                                                || (venda.getCliente() != null
                                                                && venda.getCliente()
                                                                                .getId()
                                                                                .equals(filtro.clienteId())))
                                .filter(venda -> filtro == null
                                                || filtro.formaPagamento() == null
                                                || venda.getFormaPagamento()
                                                                .equalsIgnoreCase(
                                                                                filtro.formaPagamento()))
                                .filter(venda -> filtro == null
                                                || filtro.status() == null
                                                || venda.getStatus()
                                                                .equalsIgnoreCase(
                                                                                filtro.status()))
                                .filter(venda -> filtro == null
                                                || filtro.inicio() == null
                                                || !venda.getCriadoEm()
                                                                .isBefore(filtro.inicio()))
                                .filter(venda -> filtro == null
                                                || filtro.fim() == null
                                                || !venda.getCriadoEm()
                                                                .isAfter(filtro.fim()))
                                .map(this::vendaResponse)
                                .toList();

                Comparator<RelatorioVendaResponse> comparator = switch (sort) {

                        case "valorTotal" ->
                                Comparator.comparing(
                                                RelatorioVendaResponse::valorTotal);

                        case "cliente" ->
                                Comparator.comparing(
                                                RelatorioVendaResponse::cliente,
                                                Comparator.nullsLast(String::compareTo));

                        default ->
                                Comparator.comparing(
                                                RelatorioVendaResponse::dataVenda);
                };

                if ("DESC".equalsIgnoreCase(direction)) {
                        comparator = comparator.reversed();
                }

                return resultado.stream()
                                .sorted(comparator)
                                .skip((long) page * size)
                                .limit(size)
                                .toList();
        }

        public RelatorioClienteResponse clientes() {

                long ativos = clienteRepository.findByAtivoTrue()
                                .size();

                long total = clienteRepository.count();

                long inativos = total - ativos;

                return new RelatorioClienteResponse(
                                total,
                                ativos,
                                inativos);
        }

        public List<RelatorioAgendamentoResponse> agendamentos(
                        FiltroAgendamentoRequest filtro,
                        int page,
                        int size,
                        String sort,
                        String direction) {

                List<RelatorioAgendamentoResponse> resultado = agendamentoRepository.findAll()
                                .stream()
                                .filter(a -> filtro == null
                                                || filtro.clienteId() == null
                                                || a.getCliente()
                                                                .getId()
                                                                .equals(filtro.clienteId()))
                                .filter(a -> filtro == null
                                                || filtro.raiaId() == null
                                                || a.getRaia()
                                                                .getId()
                                                                .equals(filtro.raiaId()))
                                .filter(a -> filtro == null
                                                || filtro.status() == null
                                                || a.getStatus()
                                                                .equalsIgnoreCase(
                                                                                filtro.status()))
                                .map(this::agendamentoResponse)
                                .toList();

                Comparator<RelatorioAgendamentoResponse> comparator = Comparator.comparing(
                                RelatorioAgendamentoResponse::inicio);

                if ("DESC".equalsIgnoreCase(direction)) {
                        comparator = comparator.reversed();
                }

                return resultado.stream()
                                .sorted(comparator)
                                .skip((long) page * size)
                                .limit(size)
                                .toList();
        }

        public List<RelatorioRaiaResponse> raias() {

                List<Agendamento> agendamentos = agendamentoRepository.findAll();

                return raiaRepository.findAll()
                                .stream()
                                .map(raia -> {

                                        long total = agendamentos.stream()
                                                        .filter(a -> a.getRaia()
                                                                        .getId()
                                                                        .equals(
                                                                                        raia.getId()))
                                                        .count();

                                        return new RelatorioRaiaResponse(
                                                        raia.getId(),
                                                        raia.getDescricao(),
                                                        total);
                                })
                                .toList();
        }

        private RelatorioEstoqueResponse estoqueResponse(
                        Produto produto) {

                BigDecimal total = produto.getValorVenda()
                                .multiply(
                                                BigDecimal.valueOf(
                                                                produto.getEstoque()));

                return new RelatorioEstoqueResponse(
                                produto.getId(),
                                produto.getCodigo(),
                                produto.getNome(),
                                produto.getEstoque(),
                                produto.getValorVenda(),
                                total);
        }

        private RelatorioVendaResponse vendaResponse(
                        Venda venda) {

                return new RelatorioVendaResponse(
                                venda.getId(),
                                venda.getCliente() != null
                                                ? venda.getCliente().getNome()
                                                : null,
                                venda.getUsuario().getNome(),
                                venda.getValorTotal(),
                                venda.getFormaPagamento(),
                                venda.getStatus(),
                                venda.getCriadoEm());
        }

        private RelatorioAgendamentoResponse agendamentoResponse(
                        Agendamento agendamento) {

                return new RelatorioAgendamentoResponse(
                                agendamento.getId(),
                                agendamento.getCliente().getNome(),
                                agendamento.getRaia().getDescricao(),
                                agendamento.getDataInicio(),
                                agendamento.getDataFim(),
                                agendamento.getStatus());
        }
}