package br.com.forja.domain.relatorio;

import br.com.forja.domain.relatorio.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService service;
    private final RelatorioExportService exportService;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/estoque")
    public List<RelatorioEstoqueResponse> estoque(

            @RequestParam(required = false)
            Long categoriaId,

            @RequestParam(required = false)
            Integer estoqueMinimo,

            @RequestParam(required = false)
            Boolean somenteAtivos,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "20")
            int size,

            @RequestParam(defaultValue = "produto")
            String sort,

            @RequestParam(defaultValue = "ASC")
            String direction
    ) {

        return service.estoque(
                new RelatorioEstoqueRequest(
                        categoriaId,
                        estoqueMinimo,
                        somenteAtivos
                ),
                page,
                size,
                sort,
                direction
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/vendas")
    public List<RelatorioVendaResponse> vendas(

            @RequestParam(required = false)
            Long clienteId,

            @RequestParam(required = false)
            String formaPagamento,

            @RequestParam(required = false)
            String status,

            @RequestParam(required = false)
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime inicio,

            @RequestParam(required = false)
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime fim,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "20")
            int size,

            @RequestParam(defaultValue = "dataVenda")
            String sort,

            @RequestParam(defaultValue = "DESC")
            String direction
    ) {

        return service.vendas(
                new FiltroVendaRequest(
                        clienteId,
                        formaPagamento,
                        status,
                        inicio,
                        fim
                ),
                page,
                size,
                sort,
                direction
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/clientes")
    public RelatorioClienteResponse clientes() {

        return service.clientes();
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/agendamentos")
    public List<RelatorioAgendamentoResponse> agendamentos(

            @RequestParam(required = false)
            Long clienteId,

            @RequestParam(required = false)
            Long raiaId,

            @RequestParam(required = false)
            String status,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "20")
            int size,

            @RequestParam(defaultValue = "inicio")
            String sort,

            @RequestParam(defaultValue = "ASC")
            String direction
    ) {

        return service.agendamentos(
                new FiltroAgendamentoRequest(
                        clienteId,
                        raiaId,
                        status
                ),
                page,
                size,
                sort,
                direction
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/raias")
    public List<RelatorioRaiaResponse> raias() {

        return service.raias();
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/vendas/export/csv")
    public ResponseEntity<ByteArrayResource> exportarVendasCsv() {

        byte[] arquivo =
                exportService.exportarVendasCsv(
                        service.vendas(
                                null,
                                0,
                                Integer.MAX_VALUE,
                                "dataVenda",
                                "DESC"
                        )
                );

        ByteArrayResource resource =
                new ByteArrayResource(arquivo);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=vendas.csv"
                )
                .contentType(
                        MediaType.parseMediaType(
                                "text/csv"
                        )
                )
                .contentLength(
                        arquivo.length
                )
                .body(resource);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/estoque/export/csv")
    public ResponseEntity<ByteArrayResource> exportarEstoqueCsv() {

        byte[] arquivo =
                exportService.exportarEstoqueCsv(
                        service.estoque(
                                null,
                                0,
                                Integer.MAX_VALUE,
                                "produto",
                                "ASC"
                        )
                );

        ByteArrayResource resource =
                new ByteArrayResource(arquivo);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=estoque.csv"
                )
                .contentType(
                        MediaType.parseMediaType(
                                "text/csv"
                        )
                )
                .contentLength(
                        arquivo.length
                )
                .body(resource);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/vendas/export/xlsx")
    public ResponseEntity<ByteArrayResource>
    exportarVendasExcel() {

        byte[] arquivo =
                exportService.exportarVendasExcel(
                        service.vendas(
                                null,
                                0,
                                Integer.MAX_VALUE,
                                "dataVenda",
                                "DESC"
                        )
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=vendas.xlsx"
                )
                .contentLength(
                        arquivo.length
                )
                .body(
                        new ByteArrayResource(
                                arquivo
                        )
                );
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/estoque/export/xlsx")
    public ResponseEntity<ByteArrayResource>
    exportarEstoqueExcel() {

        byte[] arquivo =
                exportService.exportarEstoqueExcel(
                        service.estoque(
                                null,
                                0,
                                Integer.MAX_VALUE,
                                "produto",
                                "ASC"
                        )
                );

        ByteArrayResource resource =
                new ByteArrayResource(arquivo);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=estoque.xlsx"
                )
                .contentLength(
                        arquivo.length
                )
                .body(resource);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/vendas/export/pdf")
    public ResponseEntity<ByteArrayResource>
    exportarVendasPdf() {

        byte[] arquivo =
                exportService.exportarVendasPdf(
                        service.vendas(
                                null,
                                0,
                                Integer.MAX_VALUE,
                                "dataVenda",
                                "DESC"
                        )
                );

        ByteArrayResource resource =
                new ByteArrayResource(arquivo);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=vendas.pdf"
                )
                .contentType(
                        MediaType.APPLICATION_PDF
                )
                .contentLength(
                        arquivo.length
                )
                .body(resource);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/estoque/export/pdf")
    public ResponseEntity<ByteArrayResource>
    exportarEstoquePdf() {

        byte[] arquivo =
                exportService.exportarEstoquePdf(
                        service.estoque(
                                null,
                                0,
                                Integer.MAX_VALUE,
                                "produto",
                                "ASC"
                        )
                );

        ByteArrayResource resource =
                new ByteArrayResource(arquivo);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=estoque.pdf"
                )
                .contentType(
                        MediaType.APPLICATION_PDF
                )
                .contentLength(
                        arquivo.length
                )
                .body(resource);
    }
}