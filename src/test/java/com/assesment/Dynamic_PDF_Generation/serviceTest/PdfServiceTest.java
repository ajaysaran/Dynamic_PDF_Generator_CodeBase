package com.assesment.Dynamic_PDF_Generation.serviceTest;

import com.assesment.Dynamic_PDF_Generation.model.InvoiceRequest;
import com.assesment.Dynamic_PDF_Generation.service.PdfService;
import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PdfServiceTest {

    @Autowired
    private PdfService pdfService;

    private InvoiceRequest invoiceRequest;

    @BeforeEach
    public void setUp() {
        // Setup the InvoiceRequest with sample data
        invoiceRequest = new InvoiceRequest();
        invoiceRequest.setSeller("XYZ Pvt. Ltd.");
        invoiceRequest.setSellerGstin("29AABBCCDD121ZD");
        invoiceRequest.setSellerAddress("New Delhi, India");
        invoiceRequest.setBuyer("Vedant Computers");
        invoiceRequest.setBuyerGstin("29AABBCCDD131ZD");
        invoiceRequest.setBuyerAddress("New Delhi, India");

        // Set the item list with one item for testing
        InvoiceRequest.Item item = new InvoiceRequest.Item("Product 1", "12 Nos", 123.00, 1476.00);
        invoiceRequest.setItems(Collections.singletonList(item));
    }

    @Test
    public void testGeneratePdf() throws DocumentException, IOException {
        // Generate PDF
        String pdfPath = pdfService.generatePdf(invoiceRequest);

        // Check if the PDF file was created successfully
        File pdfFile = new File(pdfPath);
        assertTrue(pdfFile.exists());

        // Verify the content of the PDF (you can extend this to read and validate content)
        byte[] fileContent = Files.readAllBytes(Paths.get(pdfPath));
        assertNotNull(fileContent);
        assertTrue(fileContent.length > 0);

        // Clean up the generated file after test
        pdfFile.delete();
    }
}

