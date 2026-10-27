package com.rdvn.ilan.entity;

import com.rdvn.ilan.enums.AdStatus;
import jakarta.persistence.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "advertisements")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 150)
    private String title;
    @Column(nullable = false,length = 2000)
    private String description;
    @Column(nullable = false,precision = 12,scale = 2)//precision = toplam basamak scale = virgülden sonraki basamak
    private BigDecimal price;
    @Column(nullable = false,length = 100)
    private String city;
    @Column(nullable = false,length = 150)
    private String category;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdStatus status = AdStatus.PASSIVE;
    @Column(nullable = false)
    private int viewCount = 0;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    public Advertisement(){}
    public Advertisement(String title,String description,BigDecimal price,String city,String category){
        this.title = title;
        this.description = description;
        this.price = price;
        this.city= city;
        this.category = category;
    }

    public void setId(Long id){this.id = id;}
    public Long getId(){return id;}

    public void setTitle(String title){this.title = title;}
    public String getTitle(){return title;}

    public void setDescription(String description){this.description = description;}
    public String getDescription(){return description;}

    public void setPrice(BigDecimal price){this.price = price;}
    public BigDecimal getPrice(){return price;}

    public void setCity(String city){this.city = city;}
    public String getCity(){return city;}

    public void setCategory(String category){this.category = category;}
    public String getCategory(){return category;}

    public void setStatus(AdStatus status){this.status = status;}
    public AdStatus getStatus(){return status;}

    public void setViewCount(int viewCount){this.viewCount = viewCount;}
    public int getViewCount(){return viewCount;}

    public void setCreatedAt(LocalDateTime createdAt){this.createdAt = createdAt;}
    public LocalDateTime getCreatedAt(){return createdAt;}

    public void setUpdatedAt(LocalDateTime updatedAt){this.updatedAt = updatedAt;}
    public LocalDateTime getUpdatedAt(){return updatedAt;}

    public void setUser(User user){this.user = user;}
    public User getUser(){return user;}


}
