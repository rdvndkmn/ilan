package com.rdvn.ilan.controller;

import com.rdvn.ilan.dto.AdminAdResponse;
import com.rdvn.ilan.entity.Advertisement;
import com.rdvn.ilan.service.AdService;
import com.rdvn.ilan.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class AdminAdController {

    private final AdService adService;
    private final UserService userService;
    public AdminAdController(AdService adService,UserService userService){
        this.adService = adService;
        this.userService = userService;
    }

    @GetMapping("/api/admin/ads/passive")
    public List<AdminAdResponse> getStatus(){
        return adService.getStatus();
    }

    @GetMapping("/api/admin/test")
    public String getAdmin(){
        return userService.getAdmin();
    }

    @PutMapping("/api/admin/ads/{id}/approve")
    public Advertisement approveAd(@PathVariable("id") long id){
        return adService.approveAd(id);
    }
}
