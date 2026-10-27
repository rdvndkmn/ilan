package com.rdvn.ilan.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AdUpdateRequest {

        private String title;
        private String description;
        private BigDecimal price;
        private String city;
        private String category;

        public AdUpdateRequest(String title,String description,BigDecimal price,String city,String category){
            this.title=title;
            this.description=description;
            this.price=price;
            this.city=city;
            this.category=category;

        }
        public AdUpdateRequest(){}

}
