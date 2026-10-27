package com.rdvn.ilan.dto;

import com.rdvn.ilan.entity.User;
import com.rdvn.ilan.enums.AdStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AdminAdResponse {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String city;
    private String category;
    private AdStatus adStatus;
    private LocalDateTime createdAt;
    private UserSummaryResponse user;

    public AdminAdResponse(){}
    public AdminAdResponse(Long id,String title,String description,BigDecimal price,String city,String category,AdStatus adStatus,LocalDateTime createdAt,UserSummaryResponse user){
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.city = city;
        this.category = category;
        this.adStatus = adStatus;
        this.createdAt = createdAt;
        this.user = user;
    }
}
