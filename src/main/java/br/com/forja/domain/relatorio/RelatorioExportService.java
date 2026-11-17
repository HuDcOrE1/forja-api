package br.com.forja.domain.relatorio;

import br.com.forja.domain.relatorio.dto.FiltroVendaRequest;
import br.com.forja.domain.relatorio.dto.RelatorioEstoqueResponse;
import br.com.forja.domain.relatorio.dto.RelatorioVendaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lowagie.text.Font;
import com.lowagie.text.Font;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioExportService {

        public byte[] exportarVendasCsv(
                        List<RelatorioVendaResponse> vendas) {

                StringBuilder csv = new StringBuilder();

                csv.append(
                                "VendaId;Cliente;Usuario;ValorTotal;FormaPagamento;Status;DataVenda\n");

                for (RelatorioVendaResponse venda : vendas) {

                        csv.append(venda.vendaId()).append(";")

                                        .append(venda.cliente()).append(";")

                                        .append(venda.usuario()).append(";")

                                        .append(venda.valorTotal()).append(";")

                                        .append(venda.formaPagamento()).append(";")

                                        .append(venda.status()).append(";")

                                        .append(venda.dataVenda()).append("\n");
                }

                return csv.toString()
                                .getBytes(StandardCharsets.UTF_8);
        }

        public byte[] exportarEstoqueCsv(
                        List<RelatorioEstoqueResponse> estoque) {

                StringBuilder csv = new StringBuilder();

                csv.append(
                                "ProdutoId;Codigo;Produto;Estoque;ValorVenda;ValorTotalEstoque\n");

                for (RelatorioEstoqueResponse item : estoque) {

                        csv.append(item.produtoId()).append(";")

                                        .append(item.codigo()).append(";")

                                        .append(item.produto()).append(";")

                                        .append(item.estoque()).append(";")

                                        .append(item.valorVenda()).append(";")

                                        .append(item.valorTotalEstoque()).append("\n");
                }

                return csv.toString()
                                .getBytes(
                                                StandardCharsets.UTF_8);
        }

        public byte[] exportarVendasExcel(
                        List<RelatorioVendaResponse> vendas) {

                try (
                                XSSFWorkbook workbook = new XSSFWorkbook();

                                ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                        Sheet resumo = workbook.createSheet(
                                        "Resumo Executivo");

                        Sheet detalhes = workbook.createSheet(
                                        "Vendas");

                        Sheet financeiro = workbook.createSheet(
                                        "Financeiro");

                        int totalVendas = vendas.size();

                        BigDecimal faturamento = vendas.stream()
                                        .map(RelatorioVendaResponse::valorTotal)
                                        .reduce(
                                                        BigDecimal.ZERO,
                                                        BigDecimal::add);

                        BigDecimal ticketMedio = totalVendas == 0
                                        ? BigDecimal.ZERO
                                        : faturamento.divide(
                                                        BigDecimal.valueOf(
                                                                        totalVendas),
                                                        2,
                                                        RoundingMode.HALF_UP);

                        long canceladas = vendas.stream()
                                        .filter(v -> "CANCELADA"
                                                        .equalsIgnoreCase(
                                                                        v.status()))
                                        .count();

                        long finalizadas = vendas.stream()
                                        .filter(v -> "FINALIZADA"
                                                        .equalsIgnoreCase(
                                                                        v.status()))
                                        .count();

                        Map<String, BigDecimal> resumoPagamento = vendas.stream()
                                        .collect(
                                                        Collectors.groupingBy(
                                                                        RelatorioVendaResponse::formaPagamento,
                                                                        Collectors.reducing(
                                                                                        BigDecimal.ZERO,
                                                                                        RelatorioVendaResponse::valorTotal,
                                                                                        BigDecimal::add)));

                        Row resumoTitulo = resumo.createRow(0);

                        resumoTitulo.createCell(0)
                                        .setCellValue(
                                                        "FORJA - RESUMO EXECUTIVO");

                        resumo.createRow(2)
                                        .createCell(0)
                                        .setCellValue(
                                                        "Total de Vendas");

                        resumo.getRow(2)
                                        .createCell(1)
                                        .setCellValue(
                                                        totalVendas);

                        resumo.createRow(3)
                                        .createCell(0)
                                        .setCellValue(
                                                        "Finalizadas");

                        resumo.getRow(3)
                                        .createCell(1)
                                        .setCellValue(
                                                        finalizadas);

                        resumo.createRow(4)
                                        .createCell(0)
                                        .setCellValue(
                                                        "Canceladas");

                        resumo.getRow(4)
                                        .createCell(1)
                                        .setCellValue(
                                                        canceladas);

                        resumo.createRow(5)
                                        .createCell(0)
                                        .setCellValue(
                                                        "Faturamento");

                        resumo.getRow(5)
                                        .createCell(1)
                                        .setCellValue(
                                                        faturamento.doubleValue());

                        resumo.createRow(6)
                                        .createCell(0)
                                        .setCellValue(
                                                        "Ticket Médio");

                        resumo.getRow(6)
                                        .createCell(1)
                                        .setCellValue(
                                                        ticketMedio.doubleValue());

                        Row financeiroTitulo = financeiro.createRow(0);

                        financeiroTitulo
                                        .createCell(0)
                                        .setCellValue(
                                                        "FORMA DE PAGAMENTO");

                        financeiroTitulo
                                        .createCell(1)
                                        .setCellValue(
                                                        "VALOR");

                        int linhaFinanceiro = 1;

                        for (Map.Entry<String, BigDecimal> entry : resumoPagamento.entrySet()) {

                                Row row = financeiro.createRow(
                                                linhaFinanceiro++);

                                row.createCell(0)
                                                .setCellValue(
                                                                entry.getKey());

                                row.createCell(1)
                                                .setCellValue(
                                                                entry.getValue()
                                                                                .doubleValue());
                        }

                        Row header = detalhes.createRow(0);

                        header.createCell(0)
                                        .setCellValue(
                                                        "Venda");

                        header.createCell(1)
                                        .setCellValue(
                                                        "Data");

                        header.createCell(2)
                                        .setCellValue(
                                                        "Cliente");

                        header.createCell(3)
                                        .setCellValue(
                                                        "Usuário");

                        header.createCell(4)
                                        .setCellValue(
                                                        "Pagamento");

                        header.createCell(5)
                                        .setCellValue(
                                                        "Status");

                        header.createCell(6)
                                        .setCellValue(
                                                        "Valor");

                        int rowNum = 1;

                        for (RelatorioVendaResponse venda : vendas) {

                                Row row = detalhes.createRow(
                                                rowNum++);

                                row.createCell(0)
                                                .setCellValue(
                                                                venda.vendaId());

                                row.createCell(1)
                                                .setCellValue(
                                                                venda.dataVenda() != null
                                                                                ? venda.dataVenda()
                                                                                                .format(
                                                                                                                DateTimeFormatter
                                                                                                                                .ofPattern(
                                                                                                                                                "dd/MM/yyyy HH:mm"))
                                                                                : "");

                                row.createCell(2)
                                                .setCellValue(
                                                                venda.cliente());

                                row.createCell(3)
                                                .setCellValue(
                                                                venda.usuario());

                                row.createCell(4)
                                                .setCellValue(
                                                                venda.formaPagamento());

                                row.createCell(5)
                                                .setCellValue(
                                                                venda.status());

                                row.createCell(6)
                                                .setCellValue(
                                                                venda.valorTotal()
                                                                                .doubleValue());
                        }

                        for (int i = 0; i < 7; i++) {
                                detalhes.autoSizeColumn(i);
                        }

                        resumo.autoSizeColumn(0);
                        resumo.autoSizeColumn(1);
                        financeiro.autoSizeColumn(0);
                        financeiro.autoSizeColumn(1);

                        workbook.write(out);

                        return out.toByteArray();

                } catch (Exception ex) {

                        throw new RuntimeException(
                                        "Erro ao gerar Excel de vendas.",
                                        ex);
                }
        }

        public byte[] exportarVendasPdf(
                        List<RelatorioVendaResponse> vendas,
                        FiltroVendaRequest filtro) {

                try {

                        ByteArrayOutputStream out = new ByteArrayOutputStream();

                        Document document = new Document(
                                        PageSize.A4.rotate(),
                                        20,
                                        20,
                                        20,
                                        20);

                        PdfWriter.getInstance(
                                        document,
                                        out);

                        document.open();

                        Font titulo = FontFactory.getFont(
                                        FontFactory.HELVETICA_BOLD,
                                        18,
                                        Color.BLACK);

                        Font subtitulo = FontFactory.getFont(
                                        FontFactory.HELVETICA_BOLD,
                                        12,
                                        Color.DARK_GRAY);

                        Font normal = FontFactory.getFont(
                                        FontFactory.HELVETICA,
                                        10,
                                        Color.BLACK);

                        Font destaque = FontFactory.getFont(
                                        FontFactory.HELVETICA_BOLD,
                                        14,
                                        new Color(16, 185, 129));

                        Font cabecalhoTabela = FontFactory.getFont(
                                        FontFactory.HELVETICA_BOLD,
                                        10,
                                        Color.WHITE);

                        Paragraph empresa = new Paragraph(
                                        "FORJA",
                                        titulo);

                        empresa.setAlignment(Element.ALIGN_CENTER);

                        document.add(empresa);

                        Paragraph sistema = new Paragraph(
                                        "Sistema de Gestão para Clube de Tiro",
                                        normal);

                        sistema.setAlignment(Element.ALIGN_CENTER);

                        document.add(sistema);

                        document.add(new Paragraph(" "));

                        Paragraph relatorio = new Paragraph(
                                        "RELATÓRIO EXECUTIVO DE VENDAS",
                                        subtitulo);

                        relatorio.setAlignment(Element.ALIGN_CENTER);

                        document.add(relatorio);

                        document.add(new Paragraph(" "));

                        document.add(new Paragraph("Emitido em: " + LocalDateTime.now().format(
                                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), normal));

                        document.add(new Paragraph(" "));

                        document.add(new Paragraph(
                                        "FILTROS APLICADOS",
                                        subtitulo));

                        document.add(new Paragraph(
                                        "Cliente: " + (filtro.clienteId() == null ? "Todos" : filtro.clienteId()),
                                        normal));

                        document.add(new Paragraph(
                                        "Forma de Pagamento: " + (filtro.formaPagamento() == null
                                                        || filtro.formaPagamento().isBlank() ? "Todas"
                                                                        : filtro.formaPagamento()),
                                        normal));

                        document.add(new Paragraph(
                                        "Status: " + (filtro.status() == null || filtro.status().isBlank() ? "Todos"
                                                        : filtro.status()),
                                        normal));

                        document.add(new Paragraph("Período: "
                                        + (filtro.inicio() == null ? "Não informado"
                                                        : filtro.inicio().format(
                                                                        DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                                        + " até " + (filtro.fim() == null ? "Não informado"
                                                        : filtro.fim().format(
                                                                        DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
                                        normal));

                        document.add(new Paragraph(" "));

                        document.add(new Paragraph(" "));

                        long totalVendas = vendas.size();

                        long canceladas = vendas.stream()
                                        .filter(v -> "CANCELADA"
                                                        .equalsIgnoreCase(
                                                                        v.status()))
                                        .count();

                        long finalizadas = vendas.stream()
                                        .filter(v -> "FINALIZADA"
                                                        .equalsIgnoreCase(
                                                                        v.status()))
                                        .count();

                        BigDecimal faturamentoTotal = vendas.stream()
                                        .map(RelatorioVendaResponse::valorTotal)
                                        .reduce(
                                                        BigDecimal.ZERO,
                                                        BigDecimal::add);

                        BigDecimal ticketMedio = totalVendas == 0
                                        ? BigDecimal.ZERO
                                        : faturamentoTotal.divide(
                                                        BigDecimal.valueOf(totalVendas),
                                                        2,
                                                        RoundingMode.HALF_UP);

                        PdfPTable resumo = new PdfPTable(3);

                        resumo.setWidthPercentage(100);

                        resumo.setWidths(new float[] { 1f, 1f, 1f });

                        PdfPCell resumoExecutivo = new PdfPCell();

                        resumoExecutivo.setPadding(10);

                        resumoExecutivo.addElement(new Paragraph(
                                        "RESUMO EXECUTIVO",
                                        subtitulo));

                        resumoExecutivo.addElement(new Paragraph(
                                        "Total de vendas: "
                                                        + totalVendas,
                                        normal));

                        resumoExecutivo.addElement(new Paragraph(
                                        "Vendas finalizadas: "
                                                        + finalizadas,
                                        normal));

                        resumoExecutivo.addElement(new Paragraph(
                                        "Vendas canceladas: "
                                                        + canceladas,
                                        normal));

                        resumoExecutivo.addElement(new Paragraph(
                                        "Faturamento total: "
                                                        + MOEDA.format(faturamentoTotal),
                                        destaque));

                        resumoExecutivo.addElement(new Paragraph(
                                        "Ticket médio: "
                                                        + MOEDA.format(ticketMedio),
                                        destaque));

                        Map<String, BigDecimal> resumoPagamento = vendas.stream()
                                        .collect(Collectors.groupingBy(
                                                        RelatorioVendaResponse::formaPagamento,
                                                        Collectors.reducing(
                                                                        BigDecimal.ZERO,
                                                                        RelatorioVendaResponse::valorTotal,
                                                                        BigDecimal::add)));

                        PdfPCell resumoFinanceiro = new PdfPCell();

                        resumoFinanceiro.setPadding(10);

                        resumoFinanceiro.addElement(
                                        new Paragraph("RESUMO FINANCEIRO",
                                                        subtitulo));

                        for (var entry : resumoPagamento.entrySet()) {

                                resumoFinanceiro.addElement(
                                                new Paragraph(
                                                                entry.getKey()
                                                                                + " ............  "
                                                                                + MOEDA.format(entry.getValue()),
                                                                normal));
                        }

                        PdfPCell resumoOperacional = new PdfPCell();

                        resumoOperacional.setPadding(10);

                        resumoOperacional.addElement(
                                        new Paragraph(
                                                        "RESUMO OPERACIONAL",
                                                        subtitulo));

                        double taxaConversao = totalVendas == 0
                                        ? 0
                                        : (finalizadas * 100.0)
                                                        / totalVendas;

                        resumoOperacional.addElement(
                                        new Paragraph(
                                                        "Finalizadas: "
                                                                        + finalizadas,
                                                        normal));

                        resumoOperacional.addElement(
                                        new Paragraph(
                                                        "Canceladas: "
                                                                        + canceladas,
                                                        normal));

                        resumoOperacional.addElement(
                                        new Paragraph(
                                                        String.format(
                                                                        "Conversão: %.2f%%",
                                                                        taxaConversao),
                                                        destaque));

                        resumo.addCell(resumoExecutivo);

                        resumo.addCell(resumoFinanceiro);

                        resumo.addCell(resumoOperacional);

                        document.add(resumo);

                        document.add(new Paragraph(" "));

                        PdfPTable tabela = new PdfPTable(7);

                        tabela.setWidthPercentage(100);

                        tabela.setWidths(
                                        new float[] {
                                                        1.2f,
                                                        2.0f,
                                                        3.5f,
                                                        3.0f,
                                                        2.0f,
                                                        2.0f,
                                                        2.0f
                                        });

                        adicionarCabecalho(
                                        tabela,
                                        "Venda",
                                        cabecalhoTabela);

                        adicionarCabecalho(
                                        tabela,
                                        "Data",
                                        cabecalhoTabela);

                        adicionarCabecalho(
                                        tabela,
                                        "Cliente",
                                        cabecalhoTabela);

                        adicionarCabecalho(
                                        tabela,
                                        "Usuário",
                                        cabecalhoTabela);

                        adicionarCabecalho(
                                        tabela,
                                        "Pagamento",
                                        cabecalhoTabela);

                        adicionarCabecalho(
                                        tabela,
                                        "Status",
                                        cabecalhoTabela);

                        adicionarCabecalho(
                                        tabela,
                                        "Valor",
                                        cabecalhoTabela);

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                                        "dd/MM/yyyy HH:mm");

                        for (RelatorioVendaResponse venda : vendas) {

                                tabela.addCell(String.valueOf(
                                                venda.vendaId()));

                                tabela.addCell(venda.dataVenda() != null
                                                ? venda.dataVenda()
                                                                .format(
                                                                                formatter)
                                                : "-");

                                tabela.addCell(venda.cliente());

                                tabela.addCell(venda.usuario());

                                tabela.addCell(venda.formaPagamento());

                                PdfPCell status = new PdfPCell(new Phrase(venda.status(),
                                                normal));

                                if ("FINALIZADA".equalsIgnoreCase(venda.status())) {

                                        status.setBackgroundColor(new Color(
                                                        220,
                                                        252,
                                                        231));

                                } else if ("CANCELADA".equalsIgnoreCase(venda.status())) {

                                        status.setBackgroundColor(new Color(
                                                        254,
                                                        226,
                                                        226));
                                }

                                tabela.addCell(status);

                                tabela.addCell(MOEDA.format(venda.valorTotal()));
                        }

                        document.add(tabela);

                        document.add(new Paragraph(" "));

                        document.add(new Paragraph("TOTAL DE REGISTROS: "
                                        + totalVendas,
                                        subtitulo));

                        document.add(new Paragraph(
                                        "FATURAMENTO TOTAL: "
                                                        + MOEDA.format(faturamentoTotal),
                                        destaque));

                        Paragraph rodape = new Paragraph(
                                        "FORJA • Sistema de Gestão para Clube de Tiro",
                                        normal);

                        rodape.setAlignment(Element.ALIGN_CENTER);

                        document.add(rodape);

                        document.close();

                        return out.toByteArray();

                } catch (Exception ex) {

                        throw new RuntimeException(
                                        "Erro ao gerar PDF de vendas.",
                                        ex);

                }

        }

        private void adicionarCabecalho(
                        PdfPTable tabela,
                        String titulo,
                        Font fonte) {

                PdfPCell cell = new PdfPCell(
                                new Phrase(
                                                titulo,
                                                fonte));

                cell.setBackgroundColor(
                                new Color(
                                                31,
                                                41,
                                                55));

                tabela.addCell(
                                cell);
        }

        public byte[] exportarEstoqueExcel(
                        List<RelatorioEstoqueResponse> estoque) {

                try (
                                XSSFWorkbook workbook = new XSSFWorkbook();

                                ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                        Sheet sheet = workbook.createSheet("Estoque");

                        Row header = sheet.createRow(0);

                        header.createCell(0)
                                        .setCellValue("Codigo");

                        header.createCell(1)
                                        .setCellValue("Produto");

                        header.createCell(2)
                                        .setCellValue("Estoque");

                        header.createCell(3)
                                        .setCellValue("Valor Venda");

                        header.createCell(4)
                                        .setCellValue("Valor Total Estoque");

                        int rowNum = 1;

                        for (RelatorioEstoqueResponse item : estoque) {

                                Row row = sheet.createRow(rowNum++);

                                row.createCell(0)
                                                .setCellValue(item.codigo());

                                row.createCell(1)
                                                .setCellValue(item.produto());

                                row.createCell(2)
                                                .setCellValue(item.estoque());

                                row.createCell(3)
                                                .setCellValue(
                                                                item.valorVenda()
                                                                                .doubleValue());

                                row.createCell(4)
                                                .setCellValue(
                                                                item.valorTotalEstoque()
                                                                                .doubleValue());
                        }

                        workbook.write(out);

                        return out.toByteArray();

                } catch (Exception ex) {

                        throw new RuntimeException(
                                        "Erro ao gerar Excel de estoque.",
                                        ex);
                }
        }

        public byte[] exportarEstoquePdf(
                        List<RelatorioEstoqueResponse> estoque) {

                try {

                        ByteArrayOutputStream out = new ByteArrayOutputStream();

                        Document document = new Document(
                                        PageSize.A4.rotate(),
                                        20,
                                        20,
                                        20,
                                        20);

                        PdfWriter.getInstance(
                                        document,
                                        out);

                        document.open();

                        Font titulo = FontFactory.getFont(
                                        FontFactory.HELVETICA_BOLD,
                                        18,
                                        Color.BLACK);

                        Font subtitulo = FontFactory.getFont(
                                        FontFactory.HELVETICA_BOLD,
                                        12,
                                        Color.DARK_GRAY);

                        Font normal = FontFactory.getFont(
                                        FontFactory.HELVETICA,
                                        10,
                                        Color.BLACK);

                        Font destaque = FontFactory.getFont(
                                        FontFactory.HELVETICA_BOLD,
                                        14,
                                        new Color(16, 185, 129));

                        Font cabecalhoTabela = FontFactory.getFont(
                                        FontFactory.HELVETICA_BOLD,
                                        10,
                                        Color.WHITE);

                        Paragraph empresa = new Paragraph(
                                        "FORJA",
                                        titulo);

                        empresa.setAlignment(
                                        Element.ALIGN_CENTER);

                        document.add(
                                        empresa);

                        Paragraph sistema = new Paragraph(
                                        "Sistema de Gestão para Clube de Tiro",
                                        normal);

                        sistema.setAlignment(
                                        Element.ALIGN_CENTER);

                        document.add(
                                        sistema);

                        document.add(
                                        new Paragraph(" "));

                        Paragraph relatorio = new Paragraph(
                                        "RELATÓRIO EXECUTIVO DE ESTOQUE",
                                        subtitulo);

                        relatorio.setAlignment(
                                        Element.ALIGN_CENTER);

                        document.add(
                                        relatorio);

                        document.add(
                                        new Paragraph(" "));

                        document.add(
                                        new Paragraph(
                                                        "Emitido em: "
                                                                        + LocalDateTime.now().format(
                                                                                        DateTimeFormatter.ofPattern(
                                                                                                        "dd/MM/yyyy HH:mm:ss")),
                                                        normal));

                        document.add(
                                        new Paragraph(" "));

                        int totalProdutos = estoque.size();

                        long semEstoque = estoque.stream()
                                        .filter(p -> p.estoque() == 0)
                                        .count();

                        long estoqueCritico = estoque.stream()
                                        .filter(p -> p.estoque() > 0
                                                        && p.estoque() <= 5)
                                        .count();

                        long estoqueSaudavel = estoque.stream()
                                        .filter(p -> p.estoque() > 5)
                                        .count();

                        BigDecimal valorTotalEstoque = estoque.stream()
                                        .map(RelatorioEstoqueResponse::valorTotalEstoque)
                                        .reduce(
                                                        BigDecimal.ZERO,
                                                        BigDecimal::add);

                        BigDecimal valorMedio = totalProdutos == 0
                                        ? BigDecimal.ZERO
                                        : valorTotalEstoque.divide(
                                                        BigDecimal.valueOf(
                                                                        totalProdutos),
                                                        2,
                                                        RoundingMode.HALF_UP);

                        PdfPTable resumo = new PdfPTable(3);

                        resumo.setWidthPercentage(
                                        100);

                        resumo.setWidths(
                                        new float[] { 1f, 1f, 1f });

                        PdfPCell executivo = new PdfPCell();

                        executivo.setPadding(10);

                        executivo.addElement(
                                        new Paragraph(
                                                        "RESUMO EXECUTIVO",
                                                        subtitulo));

                        executivo.addElement(
                                        new Paragraph(
                                                        "Produtos: "
                                                                        + totalProdutos,
                                                        normal));

                        executivo.addElement(
                                        new Paragraph(
                                                        "Sem estoque: "
                                                                        + semEstoque,
                                                        normal));

                        executivo.addElement(
                                        new Paragraph(
                                                        "Críticos: "
                                                                        + estoqueCritico,
                                                        normal));

                        PdfPCell financeiro = new PdfPCell();

                        financeiro.setPadding(10);

                        financeiro.addElement(
                                        new Paragraph(
                                                        "RESUMO FINANCEIRO",
                                                        subtitulo));

                        financeiro.addElement(
                                        new Paragraph(
                                                        "Total estoque:",
                                                        normal));

                        financeiro.addElement(
                                        new Paragraph(
                                                        MOEDA.format(
                                                                        valorTotalEstoque),
                                                        destaque));

                        financeiro.addElement(
                                        new Paragraph(
                                                        "Média por produto:",
                                                        normal));

                        financeiro.addElement(
                                        new Paragraph(
                                                        MOEDA.format(
                                                                        valorMedio),
                                                        destaque));

                        PdfPCell operacional = new PdfPCell();

                        operacional.setPadding(10);

                        operacional.addElement(
                                        new Paragraph(
                                                        "SAÚDE DO ESTOQUE",
                                                        subtitulo));

                        operacional.addElement(
                                        new Paragraph(
                                                        "Saudáveis: "
                                                                        + estoqueSaudavel,
                                                        normal));

                        operacional.addElement(
                                        new Paragraph(
                                                        "Críticos: "
                                                                        + estoqueCritico,
                                                        normal));

                        operacional.addElement(
                                        new Paragraph(
                                                        "Sem estoque: "
                                                                        + semEstoque,
                                                        normal));

                        resumo.addCell(
                                        executivo);

                        resumo.addCell(
                                        financeiro);

                        resumo.addCell(
                                        operacional);

                        document.add(
                                        resumo);

                        document.add(
                                        new Paragraph(" "));

                        PdfPTable tabela = new PdfPTable(5);

                        tabela.setWidthPercentage(
                                        100);

                        tabela.setWidths(
                                        new float[] {
                                                        2f,
                                                        4f,
                                                        2f,
                                                        2f,
                                                        3f
                                        });

                        adicionarCabecalho(
                                        tabela,
                                        "Código",
                                        cabecalhoTabela);

                        adicionarCabecalho(
                                        tabela,
                                        "Produto",
                                        cabecalhoTabela);

                        adicionarCabecalho(
                                        tabela,
                                        "Estoque",
                                        cabecalhoTabela);

                        adicionarCabecalho(
                                        tabela,
                                        "Valor Venda",
                                        cabecalhoTabela);

                        adicionarCabecalho(
                                        tabela,
                                        "Valor Total",
                                        cabecalhoTabela);

                        for (RelatorioEstoqueResponse item : estoque) {

                                tabela.addCell(
                                                item.codigo());

                                tabela.addCell(
                                                item.produto());

                                PdfPCell estoqueCell = new PdfPCell(
                                                new Phrase(
                                                                String.valueOf(
                                                                                item.estoque()),
                                                                normal));

                                if (item.estoque() == 0) {

                                        estoqueCell.setBackgroundColor(
                                                        new Color(
                                                                        254,
                                                                        226,
                                                                        226));

                                } else if (item.estoque() <= 5) {

                                        estoqueCell.setBackgroundColor(
                                                        new Color(
                                                                        254,
                                                                        249,
                                                                        195));
                                }

                                tabela.addCell(
                                                estoqueCell);

                                tabela.addCell(
                                                MOEDA.format(
                                                                item.valorVenda()));

                                tabela.addCell(
                                                MOEDA.format(
                                                                item.valorTotalEstoque()));
                        }

                        document.add(
                                        tabela);

                        document.add(
                                        new Paragraph(" "));

                        Paragraph rodape = new Paragraph(
                                        "FORJA • Sistema de Gestão para Clube de Tiro",
                                        normal);

                        rodape.setAlignment(
                                        Element.ALIGN_CENTER);

                        document.add(
                                        rodape);

                        document.close();

                        return out.toByteArray();

                } catch (Exception ex) {

                        throw new RuntimeException(
                                        "Erro ao gerar PDF de estoque.",
                                        ex);
                }
        }

        private static final NumberFormat MOEDA = NumberFormat.getCurrencyInstance(
                        new Locale("pt", "BR"));
}