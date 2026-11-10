package br.com.forja.domain.relatorio;

import br.com.forja.domain.relatorio.dto.RelatorioEstoqueResponse;
import br.com.forja.domain.relatorio.dto.RelatorioVendaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioExportService {

        public byte[] exportarVendasCsv(
                        List<RelatorioVendaResponse> vendas) {

                StringBuilder csv = new StringBuilder();

                csv.append(
                                "VendaId;Cliente;Usuario;ValorTotal;FormaPagamento;Status;DataVenda\n");

                for (RelatorioVendaResponse venda : vendas) {

                        csv.append(
                                        venda.vendaId())
                                        .append(";")

                                        .append(
                                                        venda.cliente())
                                        .append(";")

                                        .append(
                                                        venda.usuario())
                                        .append(";")

                                        .append(
                                                        venda.valorTotal())
                                        .append(";")

                                        .append(
                                                        venda.formaPagamento())
                                        .append(";")

                                        .append(
                                                        venda.status())
                                        .append(";")

                                        .append(
                                                        venda.dataVenda())
                                        .append("\n");
                }

                return csv.toString()
                                .getBytes(
                                                StandardCharsets.UTF_8);
        }

        public byte[] exportarEstoqueCsv(
                        List<RelatorioEstoqueResponse> estoque) {

                StringBuilder csv = new StringBuilder();

                csv.append(
                                "ProdutoId;Codigo;Produto;Estoque;ValorVenda;ValorTotalEstoque\n");

                for (RelatorioEstoqueResponse item : estoque) {

                        csv.append(
                                        item.produtoId())
                                        .append(";")

                                        .append(
                                                        item.codigo())
                                        .append(";")

                                        .append(
                                                        item.produto())
                                        .append(";")

                                        .append(
                                                        item.estoque())
                                        .append(";")

                                        .append(
                                                        item.valorVenda())
                                        .append(";")

                                        .append(
                                                        item.valorTotalEstoque())
                                        .append("\n");
                }

                return csv.toString()
                                .getBytes(
                                                StandardCharsets.UTF_8);
        }

        public byte[] exportarVendasExcel(
                        List<RelatorioVendaResponse> vendas) {

                try (XSSFWorkbook workbook = new XSSFWorkbook();
                                ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                        Sheet sheet = workbook.createSheet(
                                        "Vendas");

                        Row header = sheet.createRow(0);

                        header.createCell(0)
                                        .setCellValue("Venda");

                        header.createCell(1)
                                        .setCellValue("Cliente");

                        header.createCell(2)
                                        .setCellValue("Usuário");

                        header.createCell(3)
                                        .setCellValue("Valor");

                        int rowNum = 1;

                        for (RelatorioVendaResponse venda : vendas) {

                                Row row = sheet.createRow(rowNum++);

                                row.createCell(0)
                                                .setCellValue(venda.vendaId());

                                row.createCell(1)
                                                .setCellValue(venda.cliente());

                                row.createCell(2)
                                                .setCellValue(venda.usuario());

                                row.createCell(3)
                                                .setCellValue(
                                                                venda.valorTotal()
                                                                                .doubleValue());
                        }

                        workbook.write(out);

                        return out.toByteArray();
                } catch (Exception ex) {

                        throw new RuntimeException(
                                        ex.getMessage());
                }
        }

        public byte[] exportarVendasPdf(
                        List<RelatorioVendaResponse> vendas) {

                try {

                        ByteArrayOutputStream out = new ByteArrayOutputStream();

                        Document document = new Document();

                        PdfWriter.getInstance(
                                        document,
                                        out);

                        document.open();

                        document.add(
                                        new Paragraph(
                                                        "Relatório de Vendas"));

                        document.add(
                                        new Paragraph(" "));

                        for (RelatorioVendaResponse venda : vendas) {

                                document.add(
                                                new Paragraph(
                                                                String.format(
                                                                                "Venda %d - Cliente: %s - Valor: %s",
                                                                                venda.vendaId(),
                                                                                venda.cliente(),
                                                                                venda.valorTotal())));
                        }

                        document.close();

                        return out.toByteArray();
                } catch (Exception ex) {

                        throw new RuntimeException(
                                        ex.getMessage());
                }
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

                        Document document = new Document();

                        PdfWriter.getInstance(
                                        document,
                                        out);

                        document.open();

                        document.add(
                                        new Paragraph(
                                                        "FORJA - RELATORIO DE ESTOQUE"));

                        document.add(
                                        new Paragraph(" "));

                        if (estoque.isEmpty()) {

                                document.add(
                                                new Paragraph(
                                                                "Nenhum produto encontrado."));
                        }

                        for (RelatorioEstoqueResponse item : estoque) {

                                document.add(
                                                new Paragraph(
                                                                item.codigo()
                                                                                + " - "
                                                                                + item.produto()));

                                document.add(
                                                new Paragraph(
                                                                "Estoque: "
                                                                                + item.estoque()));

                                document.add(
                                                new Paragraph(
                                                                "Valor de Venda: "
                                                                                + item.valorVenda()));

                                document.add(
                                                new Paragraph(
                                                                "Valor Total Estoque: "
                                                                                + item.valorTotalEstoque()));

                                document.add(
                                                new Paragraph(
                                                                "-----------------------------------------------------------"));
                        }

                        document.close();

                        return out.toByteArray();

                } catch (Exception ex) {

                        throw new RuntimeException(
                                        "Erro ao gerar PDF de estoque.",
                                        ex);
                }
        }
}