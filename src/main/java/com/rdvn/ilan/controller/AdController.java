package com.rdvn.ilan.controller;

import com.rdvn.ilan.dto.AdCreateRequest;
import com.rdvn.ilan.dto.AdUpdateRequest;
import com.rdvn.ilan.entity.Advertisement;
import com.rdvn.ilan.service.AdService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AdController {

    private final AdService adService;
    public AdController(AdService adService){
        this.adService=adService;
    }

    @PostMapping("/api/ads")
    public Advertisement saveAd(@RequestBody AdCreateRequest adCreateRequest){
        return adService.saveAdvertisement(adCreateRequest);
    }

    @PutMapping("/api/ads/{id}")
    public Advertisement putAd(@RequestBody AdUpdateRequest updateRequest,@PathVariable("id") Long id){
        return adService.putAdvertisement(id,updateRequest);
    }

    @GetMapping("/api/ads/{id}")
    public Advertisement getId(@PathVariable("id") Long id){
        return adService.getId(id);
    }

    @GetMapping("/api/ads")
    public List<Advertisement> filterAdvertisements(@RequestParam(required = false) String city,@RequestParam(required = false) BigDecimal minPrice,@RequestParam(required = false) BigDecimal maxPrice){
        return adService.filterAdvertisements(city,minPrice,maxPrice);
    }


    @GetMapping("/api/ads/latest")
    public List<Advertisement> latestAdvertisements(){
        return adService.latestAdvertisements();
    }


}
