package com.assesment.Dynamic_PDF_Generation.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data  
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequest {  
    private String seller; 
    private String sellerGstin;  
    private String sellerAddress;  
    private String buyer;
    private String buyerGstin; 
    private String buyerAddress;
    private List<Item> items;

    @Data  
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {  
        private String name;
        private String quantity; 
        private double rate;
        private double amount;
    }  
} 
