package br.com.forja.domain.dashboard;

import br.com.forja.domain.agendamento.Agendamento;
import br.com.forja.domain.agendamento.AgendamentoRepository;
import br.com.forja.domain.cliente.ClienteRepository;
import br.com.forja.domain.dashboard.dto.*;
import br.com.forja.domain.produto.Produto;
import br.com.forja.domain.produto.ProdutoRepository;
import br.com.forja.domain.raia.RaiaRepository;
import br.com.forja.domain.venda.ItemVenda;
import br.com.forja.domain.venda.ItemVendaRepository;
import br.com.forja.domain.venda.PagamentoVenda;
import br.com.forja.domain.venda.Venda;
import br.com.forja.domain.venda.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

        private final ClienteRepository clienteRepository;
        private final ProdutoRepository produtoRepository;
        private final VendaRepository vendaRepository;
        private final ItemVendaRepository itemVendaRepository;
        private final AgendamentoRepository agendamentoRepository;
        private final RaiaRepository raiaRepository;

        public DashboardResponse dashboard() {

                long clientesAtivos = clienteRepository.findByAtivoTrue()
                                .size();

                long produtosAtivos = produtoRepository.findByAtivoTrue()
                                .size();

                List<Venda> vendas = vendaRepository.findAll();

                long totalVendas = vendas.size();

                BigDecimal valorTotalVendas = vendas.stream()
                                .map(v -> v.getValorLiquido() == null
                                                ? BigDecimal.ZERO
                                                : v.getValorLiquido())
                                .reduce(
                                                BigDecimal.ZERO,
                                                BigDecimal::add);

                BigDecimal ticketMedio = totalVendas == 0
                                ? BigDecimal.ZERO
                                : valorTotalVendas.divide(
                                                BigDecimal.valueOf(totalVendas),
                                                2,
                                                RoundingMode.HALF_UP);

                List<ItemVenda> itens = itemVendaRepository.findAll();

                Map<String, Long> produtosVendidos = itens.stream()
                                .collect(
                                                Collectors.groupingBy(
                                                                item -> item.getProduto()
                                                                                .getNome(),
                                                                Collectors.summingLong(
                                                                                ItemVenda::getQuantidade)));

                String produtoMaisVendido = produtosVendidos.entrySet()
                                .stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .orElse("N/A");

                Long quantidadeProdutoMaisVendido = produtosVendidos.entrySet()
                                .stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getValue)
                                .orElse(0L);

                Map<String, Long> clientesCompradores = vendas.stream()
                                .filter(v -> v.getCliente() != null)
                                .collect(
                                                Collectors.groupingBy(
                                                                v -> v.getCliente().getNome(),
                                                                Collectors.counting()));

                String clienteMaisComprador = clientesCompradores.entrySet()
                                .stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .orElse("N/A");

                Long quantidadeComprasCliente = clientesCompradores.entrySet()
                                .stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getValue)
                                .orElse(0L);

                LocalDate hoje = LocalDate.now();

                BigDecimal receitaHoje = vendas.stream()
                                .filter(v -> v.getCriadoEm() != null
                                                &&
                                                v.getCriadoEm()
                                                                .toLocalDate()
                                                                .equals(hoje))
                                .map(Venda::getValorLiquido)
                                .reduce(
                                                BigDecimal.ZERO,
                                                BigDecimal::add);

                YearMonth mesAtual = YearMonth.now();

                BigDecimal receitaMes = vendas.stream()
                                .filter(v -> v.getCriadoEm() != null
                                                &&
                                                YearMonth.from(
                                                                v.getCriadoEm()).equals(mesAtual))
                                .map(Venda::getValorLiquido)
                                .reduce(
                                                BigDecimal.ZERO,
                                                BigDecimal::add);

                List<Agendamento> agendamentos = agendamentoRepository.findAll();

                long agendamentosAtivos = agendamentos.stream()
                                .filter(a -> !"CANCELADO".equals(
                                                a.getStatus()))
                                .count();

                Map<String, Long> utilizacaoRaias = agendamentos.stream()
                                .collect(
                                                Collectors.groupingBy(
                                                                a -> a.getRaia()
                                                                                .getDescricao(),
                                                                Collectors.counting()));

                String raiaMaisUtilizada = utilizacaoRaias.entrySet()
                                .stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .orElse("N/A");

                Long quantidadeAgendamentosRaia = utilizacaoRaias.entrySet()
                                .stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getValue)
                                .orElse(0L);

                long raiasAtivas = raiaRepository.findByAtivaTrue()
                                .size();

                BigDecimal valorTotalEstoque = produtoRepository.findByAtivoTrue()
                                .stream()
                                .map(this::valorEstoqueProduto)
                                .reduce(
                                                BigDecimal.ZERO,
                                                BigDecimal::add);

                return new DashboardResponse(
                                clientesAtivos,
                                produtosAtivos,
                                totalVendas,
                                valorTotalVendas,
                                ticketMedio,
                                produtoMaisVendido,
                                quantidadeProdutoMaisVendido,
                                clienteMaisComprador,
                                quantidadeComprasCliente,
                                receitaHoje,
                                receitaMes,
                                agendamentosAtivos,
                                raiaMaisUtilizada,
                                quantidadeAgendamentosRaia,
                                raiasAtivas,
                                valorTotalEstoque);
        }

        public List<FaturamentoDiarioResponse> faturamentoDiario(
                        LocalDate inicio,
                        LocalDate fim) {

                LocalDate dataInicial = inicio == null
                                ? LocalDate.now().minusDays(30)
                                : inicio;

                LocalDate dataFinal = fim == null
                                ? LocalDate.now()
                                : fim;

                return vendaRepository.findAll()
                                .stream()
                                .filter(v -> v.getCriadoEm() != null)
                                .filter(v -> !v.getCriadoEm().toLocalDate().isBefore(dataInicial))
                                .filter(v -> !v.getCriadoEm().toLocalDate().isAfter(dataFinal))
                                .collect(
                                                Collectors.groupingBy(
                                                                venda -> venda.getCriadoEm().toLocalDate(),
                                                                Collectors.mapping(
                                                                                Venda::getValorLiquido,
                                                                                Collectors.reducing(
                                                                                                BigDecimal.ZERO,
                                                                                                BigDecimal::add))))
                                .entrySet()
                                .stream()
                                .sorted(Map.Entry.comparingByKey())
                                .map(entry -> new FaturamentoDiarioResponse(
                                                entry.getKey(),
                                                entry.getValue()))
                                .toList();
        }

        public List<FaturamentoMensalResponse> faturamentoMensal(
                        LocalDate inicio,
                        LocalDate fim) {

                LocalDate dataInicial = inicio == null
                                ? LocalDate.now().minusMonths(12)
                                : inicio;

                LocalDate dataFinal = fim == null
                                ? LocalDate.now()
                                : fim;

                return vendaRepository.findAll()
                                .stream()
                                .filter(v -> v.getCriadoEm() != null)
                                .filter(v -> !v.getCriadoEm().toLocalDate().isBefore(dataInicial))
                                .filter(v -> !v.getCriadoEm().toLocalDate().isAfter(dataFinal))
                                .collect(
                                                Collectors.groupingBy(
                                                                venda -> YearMonth.from(venda.getCriadoEm()),
                                                                Collectors.mapping(
                                                                                Venda::getValorLiquido,
                                                                                Collectors.reducing(
                                                                                                BigDecimal.ZERO,
                                                                                                BigDecimal::add))))
                                .entrySet()
                                .stream()
                                .sorted(Map.Entry.comparingByKey())
                                .map(entry -> new FaturamentoMensalResponse(
                                                entry.getKey().toString(),
                                                entry.getValue()))
                                .toList();
        }

        private BigDecimal valorEstoqueProduto(
                        Produto produto) {

                return produto.getValorVenda()
                                .multiply(
                                                BigDecimal.valueOf(
                                                                produto.getEstoqueAtual()));
        }

        public List<RankingProdutoResponse> topProdutos() {

                return itemVendaRepository.findAll()
                                .stream()
                                .collect(
                                                Collectors.groupingBy(
                                                                item -> item.getProduto()
                                                                                .getNome(),
                                                                Collectors.summingLong(
                                                                                ItemVenda::getQuantidade)))
                                .entrySet()
                                .stream()
                                .sorted(
                                                Map.Entry.<String, Long>comparingByValue()
                                                                .reversed())
                                .limit(10)
                                .map(entry -> new RankingProdutoResponse(
                                                entry.getKey(),
                                                entry.getValue()))
                                .toList();
        }

        public List<RankingClienteResponse> topClientes() {

                return vendaRepository.findAll()
                                .stream()
                                .filter(
                                                venda -> venda.getCliente() != null)
                                .collect(
                                                Collectors.groupingBy(
                                                                venda -> venda.getCliente()
                                                                                .getNome(),
                                                                Collectors.counting()))
                                .entrySet()
                                .stream()
                                .sorted(
                                                Map.Entry.<String, Long>comparingByValue()
                                                                .reversed())
                                .limit(10)
                                .map(entry -> new RankingClienteResponse(
                                                entry.getKey(),
                                                entry.getValue()))
                                .toList();
        }

        public List<OcupacaoRaiaResponse> ocupacaoRaias() {

                return agendamentoRepository.findAll()
                                .stream()
                                .collect(
                                                Collectors.groupingBy(
                                                                a -> a.getRaia()
                                                                                .getDescricao(),
                                                                Collectors.counting()))
                                .entrySet()
                                .stream()
                                .sorted(
                                                Map.Entry.<String, Long>comparingByValue()
                                                                .reversed())
                                .map(entry -> new OcupacaoRaiaResponse(
                                                entry.getKey(),
                                                entry.getValue()))
                                .toList();
        }

        public ComparativoFaturamentoResponse comparativoFaturamento() {

                YearMonth atual = YearMonth.now();

                YearMonth anterior = atual.minusMonths(1);

                BigDecimal faturamentoAtual = vendaRepository.findAll()
                                .stream()
                                .filter(v -> v.getCriadoEm() != null)
                                .filter(v -> YearMonth.from(
                                                v.getCriadoEm()).equals(atual))
                                .map(Venda::getValorLiquido)
                                .reduce(
                                                BigDecimal.ZERO,
                                                BigDecimal::add);

                BigDecimal faturamentoAnterior = vendaRepository.findAll()
                                .stream()
                                .filter(v -> v.getCriadoEm() != null)
                                .filter(v -> YearMonth.from(
                                                v.getCriadoEm()).equals(anterior))
                                .map(Venda::getValorLiquido)
                                .reduce(
                                                BigDecimal.ZERO,
                                                BigDecimal::add);

                BigDecimal diferenca = faturamentoAtual.subtract(
                                faturamentoAnterior);

                double percentual = 0;

                if (faturamentoAnterior.compareTo(
                                BigDecimal.ZERO) > 0) {

                        percentual = diferenca
                                        .multiply(
                                                        BigDecimal.valueOf(
                                                                        100))
                                        .divide(
                                                        faturamentoAnterior,
                                                        2,
                                                        RoundingMode.HALF_UP)
                                        .doubleValue();
                }

                return new ComparativoFaturamentoResponse(
                                faturamentoAtual,
                                faturamentoAnterior,
                                diferenca,
                                percentual);
        }

        public MetaFaturamentoResponse metaFaturamento(
                        BigDecimal meta) {

                BigDecimal realizado = vendaRepository.findAll()
                                .stream()
                                .map(Venda::getValorLiquido)
                                .reduce(
                                                BigDecimal.ZERO,
                                                BigDecimal::add);

                Double percentual = meta.compareTo(BigDecimal.ZERO) == 0
                                ? 0D
                                : realizado
                                                .multiply(
                                                                BigDecimal.valueOf(100))
                                                .divide(
                                                                meta,
                                                                2,
                                                                RoundingMode.HALF_UP)
                                                .doubleValue();

                return new MetaFaturamentoResponse(
                                meta,
                                realizado,
                                percentual);
        }

        public MetaVendaResponse metaVendas(
                        Long meta) {

                long realizado = vendaRepository.count();

                double percentual = meta == 0
                                ? 0
                                : (realizado * 100.0) / meta;

                return new MetaVendaResponse(
                                meta,
                                realizado,
                                percentual);
        }

        public MetaAgendamentoResponse metaAgendamentos(
                        Long meta) {

                long realizado = agendamentoRepository.count();

                double percentual = meta == 0
                                ? 0
                                : (realizado * 100.0) / meta;

                return new MetaAgendamentoResponse(
                                meta,
                                realizado,
                                percentual);
        }

        public TicketMedioPeriodoResponse ticketMedio(
                        LocalDate inicio,
                        LocalDate fim) {

                List<Venda> vendas = vendaRepository.findAll()
                                .stream()
                                .filter(v -> v.getCriadoEm() != null)
                                .filter(v -> !v.getCriadoEm()
                                                .toLocalDate()
                                                .isBefore(inicio))
                                .filter(v -> !v.getCriadoEm()
                                                .toLocalDate()
                                                .isAfter(fim))
                                .toList();

                BigDecimal total = vendas.stream()
                                .map(Venda::getValorLiquido)
                                .reduce(
                                                BigDecimal.ZERO,
                                                BigDecimal::add);

                BigDecimal ticket = vendas.isEmpty()
                                ? BigDecimal.ZERO
                                : total.divide(
                                                BigDecimal.valueOf(
                                                                vendas.size()),
                                                2,
                                                RoundingMode.HALF_UP);

                return new TicketMedioPeriodoResponse(
                                inicio + " até " + fim,
                                ticket);
        }

        public SaudeEstoqueResponse saudeEstoque() {

                var produtos = produtoRepository.findByAtivoTrue();

                long semEstoque = produtos.stream()
                                .filter(p -> p.getEstoqueAtual() == null
                                                || p.getEstoqueAtual() <= 0)
                                .count();

                long estoqueCritico = produtos.stream()
                                .filter(p -> p.getEstoqueAtual() != null
                                                && p.getEstoqueMinimo() != null
                                                && p.getEstoqueAtual() > 0
                                                && p.getEstoqueAtual() <= p.getEstoqueMinimo())
                                .count();

                long estoqueSaudavel = produtos.stream()
                                .filter(p -> p.getEstoqueAtual() != null
                                                && p.getEstoqueMinimo() != null
                                                && p.getEstoqueAtual() > p.getEstoqueMinimo())
                                .count();

                BigDecimal valorFinanceiroEstoque = produtos.stream()
                                .map(produto -> produto.getValorVenda()
                                                .multiply(
                                                                BigDecimal.valueOf(
                                                                                produto.getEstoqueAtual())))
                                .reduce(
                                                BigDecimal.ZERO,
                                                BigDecimal::add);

                return new SaudeEstoqueResponse(
                                semEstoque,
                                estoqueCritico,
                                estoqueSaudavel,
                                (long) produtos.size(),
                                valorFinanceiroEstoque);
        }

        public ConversaoOperacionalResponse conversaoOperacional() {

                long agendados = agendamentoRepository.findAll()
                                .stream()
                                .filter(a -> "AGENDADO".equalsIgnoreCase(
                                                a.getStatus()))
                                .count();

                long finalizados = agendamentoRepository.findAll()
                                .stream()
                                .filter(a -> "FINALIZADO".equalsIgnoreCase(
                                                a.getStatus()))
                                .count();

                long cancelados = agendamentoRepository.findAll()
                                .stream()
                                .filter(a -> "CANCELADO".equalsIgnoreCase(
                                                a.getStatus()))
                                .count();

                long total = agendados +
                                finalizados +
                                cancelados;

                double taxaConversao = total == 0
                                ? 0
                                : (finalizados * 100.0)
                                                / total;

                double taxaCancelamento = total == 0
                                ? 0
                                : (cancelados * 100.0)
                                                / total;

                return new ConversaoOperacionalResponse(
                                agendados,
                                finalizados,
                                cancelados,
                                taxaConversao,
                                taxaCancelamento);
        }

        public List<ReceitaFormaPagamentoResponse> receitaPorFormaPagamento() {

                return itemVendaRepository.findAll()
                                .stream()
                                .flatMap(item -> item.getVenda()
                                                .getPagamentos()
                                                .stream())
                                .collect(
                                                Collectors.groupingBy(
                                                                pagamento -> pagamento.getFormaPagamento()
                                                                                .name(),
                                                                Collectors.mapping(
                                                                                PagamentoVenda::getValor,
                                                                                Collectors.reducing(
                                                                                                BigDecimal.ZERO,
                                                                                                BigDecimal::add))))
                                .entrySet()
                                .stream()
                                .sorted(
                                                Map.Entry.<String, BigDecimal>comparingByValue()
                                                                .reversed())
                                .map(entry -> new ReceitaFormaPagamentoResponse(
                                                entry.getKey(),
                                                entry.getValue()))
                                .toList();
        }

        public List<ReceitaCategoriaResponse> receitaPorCategoria() {

                return itemVendaRepository.findAll()
                                .stream()
                                .collect(
                                                Collectors.groupingBy(
                                                                item -> item.getProduto()
                                                                                .getCategoria()
                                                                                .getNome(),
                                                                Collectors.mapping(
                                                                                ItemVenda::getSubtotal,
                                                                                Collectors.reducing(
                                                                                                BigDecimal.ZERO,
                                                                                                BigDecimal::add))))
                                .entrySet()
                                .stream()
                                .sorted(
                                                Map.Entry.<String, BigDecimal>comparingByValue()
                                                                .reversed())
                                .map(entry -> new ReceitaCategoriaResponse(
                                                entry.getKey(),
                                                entry.getValue()))
                                .toList();
        }

}