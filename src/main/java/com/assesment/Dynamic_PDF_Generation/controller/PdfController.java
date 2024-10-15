package com.assesment.Dynamic_PDF_Generation.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assesment.Dynamic_PDF_Generation.model.InvoiceRequest;
import com.assesment.Dynamic_PDF_Generation.service.PdfService;

@RestController
@RequestMapping("/api/invoice")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/generate")
    public ResponseEntity<FileSystemResource> generatePdf(@RequestBody InvoiceRequest invoiceRequest) {
        try {
            String pdfPath = pdfService.generatePdf(invoiceRequest);
            File file = new File(pdfPath);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new FileSystemResource(file));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}