package com.rdvn.ilan.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class AdCreateRequest {
    private String title;
    private String description;
    private BigDecimal price;
    private String city;
    private String category;

    public AdCreateRequest(String title,String description,BigDecimal price,String city,String category){
        this.title=title;
        this.description=description;
        this.price=price;
        this.city=city;
        this.category=category;

    }
    public AdCreateRequest(){}
}
