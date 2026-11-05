package com.rdvn.ilan.dto;

import java.time.LocalDateTime;

public class AdDetailResponse {
    private String title;
    private String userName;
    private LocalDateTime createdAt;
    private int viewCount;

    public AdDetailResponse(){}
    public AdDetailResponse(String title,String userName, LocalDateTime createdAt,int viewCount){
        this.title = title;
        this.userName = userName;
        this.createdAt = createdAt;
        this.viewCount = viewCount;
    }

    public String getTitle(){return title;}
    public void  setTitle(String title){this.title= title;}
    public String getUserName(){return userName;}
    public void  setUserName(String userName){this.userName= userName;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public void  setCreatedAt(LocalDateTime createdAt){this.createdAt= createdAt;}
    public int getViewCount(){return viewCount;}
    public void  setViewCount(int viewCount){this.viewCount= viewCount;}
}
