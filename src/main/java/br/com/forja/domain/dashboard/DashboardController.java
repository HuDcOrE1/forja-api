package br.com.forja.domain.dashboard;

import br.com.forja.domain.dashboard.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

        private final DashboardService service;

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping
        public DashboardResponse dashboard() {

                return service.dashboard();
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping("/faturamento-diario")
        public List<FaturamentoDiarioResponse> faturamentoDiario(

                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

                return service.faturamentoDiario(
                                inicio,
                                fim);
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping("/faturamento-mensal")
        public List<FaturamentoMensalResponse> faturamentoMensal(

                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

                return service.faturamentoMensal(
                                inicio,
                                fim);
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping("/top-produtos")
        public List<RankingProdutoResponse> topProdutos() {

                return service.topProdutos();
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping("/top-clientes")
        public List<RankingClienteResponse> topClientes() {

                return service.topClientes();
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping("/ocupacao-raias")
        public List<OcupacaoRaiaResponse> ocupacaoRaias() {

                return service.ocupacaoRaias();
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping("/comparativo-faturamento")
        public ComparativoFaturamentoResponse comparativoFaturamento() {

                return service.comparativoFaturamento();
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping("/meta-faturamento")
        public MetaFaturamentoResponse metaFaturamento(

                        @RequestParam java.math.BigDecimal meta) {

                return service.metaFaturamento(meta);
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping("/meta-vendas")
        public MetaVendaResponse metaVendas(

                        @RequestParam Long meta) {

                return service.metaVendas(meta);
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping("/meta-agendamentos")
        public MetaAgendamentoResponse metaAgendamentos(

                        @RequestParam Long meta) {

                return service.metaAgendamentos(meta);
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping("/ticket-medio")
        public TicketMedioPeriodoResponse ticketMedio(

                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,

                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

                return service.ticketMedio(
                                inicio,
                                fim);
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping("/saude-estoque")
        public SaudeEstoqueResponse saudeEstoque() {

                return service.saudeEstoque();
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping("/conversao")
        public ConversaoOperacionalResponse conversaoOperacional() {

                return service.conversaoOperacional();
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping("/receita-forma-pagamento")
        public List<ReceitaFormaPagamentoResponse> receitaFormaPagamento() {

                return service.receitaPorFormaPagamento();
        }

        @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
        @GetMapping("/receita-categoria")
        public List<ReceitaCategoriaResponse> receitaCategoria() {

                return service.receitaPorCategoria();
        }

}