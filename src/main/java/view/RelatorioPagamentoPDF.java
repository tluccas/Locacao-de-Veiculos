package view;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controller.PagamentoController;
import model.Pagamento;
import model.exceptions.JsonCarregamentoException;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioPagamentoPDF {

    private PagamentoController pagamentoController;

    public RelatorioPagamentoPDF() throws JsonCarregamentoException {
        this.pagamentoController = new PagamentoController();
    }

    public void gerarRelatorioMensal(String nomeArquivo) {
        try {
            // Obtém dados do controller
            List<Pagamento> pagamentos = pagamentoController.listarPagamentos();
            double totalMensal = pagamentoController.faturamentoMensal();

            // Configuração do documento PDF
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(nomeArquivo));
            doc.open();

            // Adiciona título
            adicionarTitulo(doc);

            // Adiciona tabela de pagamentos
            adicionarTabelaPagamentos(doc, pagamentos);

            // Adiciona totais
            adicionarTotais(doc, totalMensal);

            doc.close();
            System.out.println("Relatório gerado com sucesso: " + nomeArquivo);

        } catch (DocumentException | FileNotFoundException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarTitulo(Document doc) throws DocumentException {
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph titulo = new Paragraph("Relatório de Pagamentos Mensal", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        doc.add(titulo);

        // Adiciona data de geração
        Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
        Paragraph dataGeracao = new Paragraph("Gerado em: " + java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                fontSubtitulo);
        dataGeracao.setAlignment(Element.ALIGN_CENTER);
        dataGeracao.setSpacingAfter(15);
        doc.add(dataGeracao);
    }

    private void adicionarTabelaPagamentos(Document doc, List<Pagamento> pagamentos) throws DocumentException {
        PdfPTable tabela = new PdfPTable(new float[]{2, 2, 2, 2, 2});
        tabela.setWidthPercentage(100);

        // Cabeçalho
        Font fontCabecalho = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
        adicionarCelulaCabecalho(tabela, "ID Pagamento", fontCabecalho);
        adicionarCelulaCabecalho(tabela, "ID Locação", fontCabecalho);
        adicionarCelulaCabecalho(tabela, "Valor (R$)", fontCabecalho);
        adicionarCelulaCabecalho(tabela, "Data", fontCabecalho);
        adicionarCelulaCabecalho(tabela, "Método", fontCabecalho);

        // Dados
        Font fontDados = new Font(Font.FontFamily.HELVETICA, 9);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Pagamento p : pagamentos) {
            // Lógica para pegarmos apenas os pagamentos do mês atual e não de todos anteriores
            LocalDate diaHoje = LocalDate.now();
            int mesHoje = diaHoje.getMonthValue();
            int anoHoje = diaHoje.getYear();
            LocalDate pagData = p.getDataPagamento();

            if(pagData.getMonthValue() == mesHoje && pagData.getYear() == anoHoje) {
                adicionarCelula(tabela, p.getId(), fontDados);
                adicionarCelula(tabela, p.getLocacaoid(), fontDados);
                adicionarCelula(tabela, String.format("%,.2f", p.getValorPago()), fontDados);
                adicionarCelula(tabela, p.getDataPagamento().format(formatter), fontDados);
                adicionarCelula(tabela, p.getMetodoPagamento().toString(), fontDados);
            }
        }

        doc.add(tabela);
    }

    private void adicionarTotais(Document doc, double totalMensal) throws DocumentException {
        Font fontTotal = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph total = new Paragraph("Total mensal: R$ " + String.format("%,.2f", totalMensal), fontTotal);
        total.setAlignment(Element.ALIGN_RIGHT);
        total.setSpacingBefore(20);
        doc.add(total);
    }

    private void adicionarCelulaCabecalho(PdfPTable tabela, String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBackgroundColor(new BaseColor(70, 130, 180)); // Azul steel
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        tabela.addCell(cell);
    }

    private void adicionarCelula(PdfPTable tabela, String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        tabela.addCell(cell);
    }
}
