package com.assesment.Dynamic_PDF_Generation.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.assesment.Dynamic_PDF_Generation.model.InvoiceRequest;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfService {

    private static final String PDF_DIRECTORY = "pdf-storage/";

    public String generatePdf(InvoiceRequest invoiceRequest) throws DocumentException, IOException {
        // Unique filename based on request
        String fileName = getFileName(invoiceRequest);
        File file = new File(PDF_DIRECTORY + fileName);

        // Check if file exists
        if (file.exists()) {
            return file.getAbsolutePath();
        }

        // Generate PDF
        Document document = new Document();
        Files.createDirectories(Paths.get(PDF_DIRECTORY));
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        document.add(new Paragraph("Seller: " + invoiceRequest.getSeller()));
        document.add(new Paragraph("GSTIN: " + invoiceRequest.getSellerGstin()));
        document.add(new Paragraph("Address: " + invoiceRequest.getSellerAddress()));
        document.add(new Paragraph("\nBuyer: " + invoiceRequest.getBuyer()));
        document.add(new Paragraph("GSTIN: " + invoiceRequest.getBuyerGstin()));
        document.add(new Paragraph("Address: " + invoiceRequest.getBuyerAddress()));

        // Add table for items
        PdfPTable table = new PdfPTable(4);
        table.addCell("Item");
        table.addCell("Quantity");
        table.addCell("Rate");
        table.addCell("Amount");
        for (InvoiceRequest.Item item : invoiceRequest.getItems()) {
            table.addCell(item.getName());
            table.addCell(item.getQuantity());
            table.addCell(String.valueOf(item.getRate()));
            table.addCell(String.valueOf(item.getAmount()));
        }
        document.add(table);
        document.close();

        return file.getAbsolutePath();
    }

    // Generate a unique filename based on the request
    private String getFileName(InvoiceRequest invoiceRequest) {
        int hashCode = Objects.hash(invoiceRequest.getSeller(), invoiceRequest.getBuyer(), invoiceRequest.getItems());
        return "invoice_" + hashCode + ".pdf";
    }

}
