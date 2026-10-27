package com.rdvn.ilan.service;

import com.rdvn.ilan.dto.AdCreateRequest;
import com.rdvn.ilan.dto.AdUpdateRequest;
import com.rdvn.ilan.dto.AdminAdResponse;
import com.rdvn.ilan.dto.UserSummaryResponse;
import com.rdvn.ilan.entity.Advertisement;
import com.rdvn.ilan.entity.User;
import com.rdvn.ilan.enums.AdStatus;
import com.rdvn.ilan.repository.AdvertisementRepository;
import com.rdvn.ilan.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AdService {
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;

    public AdService(AdvertisementRepository advertisementRepository,UserRepository userRepository){
        this.advertisementRepository=advertisementRepository;
        this.userRepository=userRepository;
    }



    public Advertisement saveAdvertisement(AdCreateRequest adCreateRequest){

        SecurityContext context = SecurityContextHolder.getContext();//Bu,o anki güvenlik bilgisini alır.
        Authentication authentication = context.getAuthentication();//Bu, giriş yapmış kullanıcıya ait authentication nesnesini alır.
        String email = authentication.getName();//Bu da authenticated kullanıcının adını bizim için email verir.

        Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()){

                User user1  = user.get();

                Advertisement advertisement = new Advertisement();
                advertisement.setTitle(adCreateRequest.getTitle());
                advertisement.setDescription(adCreateRequest.getDescription());
                advertisement.setPrice(adCreateRequest.getPrice());
                advertisement.setCity(adCreateRequest.getCity());
                advertisement.setCategory(adCreateRequest.getCategory());
                advertisement.setStatus(AdStatus.PASSIVE);
                advertisement.setViewCount(0);
                advertisement.setUser(user1);
                advertisementRepository.save(advertisement);
                return advertisement;
            }

        return null;
    }

    public Advertisement putAdvertisement(Long id, AdUpdateRequest adUpdateRequest) {

        Optional<Advertisement> advertisement = advertisementRepository.findById(id);
        if (advertisement.isPresent()){

            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            String email = authentication.getName();

            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()){
                User putUser = user.get();
                Advertisement putAdvertisement = advertisement.get();
                if (putUser.getId().equals(putAdvertisement.getUser().getId())){
                    putAdvertisement.setTitle(adUpdateRequest.getTitle());

                    putAdvertisement.setDescription(adUpdateRequest.getDescription());
                    putAdvertisement.setPrice(adUpdateRequest.getPrice());
                    putAdvertisement.setCity(adUpdateRequest.getCity());
                    putAdvertisement.setCategory(adUpdateRequest.getCategory());
                    putAdvertisement.setStatus(AdStatus.PASSIVE);
                    putAdvertisement.setUpdatedAt(LocalDateTime.now());
                    advertisementRepository.save(putAdvertisement);
                    return putAdvertisement;
                }
                throw new AccessDeniedException(
                        "Bu ilanı güncelleme yetkiniz yok"
                );
            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Kullanıcı bulunamadı"
            );
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "İlan bulunamadı"
        );
    }

    public Advertisement getId(Long id){
        Optional<Advertisement> advertisement = advertisementRepository.findById(id);
        if (advertisement.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "İlan bulunamadı"
            );
        }
        Advertisement advertisement1 = advertisement.get();
        int newView= advertisement1.getViewCount() +1;
        advertisement1.setViewCount(newView);
        advertisementRepository.save(advertisement1);
        return advertisement1;

    }

    public List<Advertisement> filterAdvertisements(String city, BigDecimal minPrice , BigDecimal maxPrice){

        if (city != null && minPrice != null && maxPrice != null){
            List<Advertisement> advertisements = advertisementRepository.findByCityAndPriceBetween(city,minPrice,maxPrice);
            return advertisements;
        }
        else if(minPrice != null){
            List<Advertisement> advertisements = advertisementRepository.findByPriceGreaterThanEqual(minPrice);
            return advertisements;
        }
        else if(maxPrice != null){
            List<Advertisement> advertisements = advertisementRepository.findByPriceLessThanEqual(maxPrice);
            return advertisements;
        }
        else if (city != null){
            List<Advertisement> advertisements = advertisementRepository.findByCity(city);
            return advertisements;
        }
        else if (minPrice != null && maxPrice != null) {
            List<Advertisement> advertisements = advertisementRepository.findByPriceBetween(minPrice,maxPrice);
            return advertisements;
        }
        else {
            return advertisementRepository.findAll();
        }
    }

    public List<Advertisement> latestAdvertisements(){
        List<Advertisement> advertisementsList = advertisementRepository.findTop10ByStatusOrderByCreatedAtDesc(AdStatus.APPROVED);
       return advertisementsList;
    }

    public List<AdminAdResponse> getStatus(){
        List<Advertisement> advertisementsList = advertisementRepository.findByStatus(AdStatus.PASSIVE);
        List<AdminAdResponse> adminAdResponseList = new ArrayList<>();
        for (Advertisement advertisement : advertisementsList) {

            UserSummaryResponse userSummaryResponse = new UserSummaryResponse();

            userSummaryResponse.setFirstName(advertisement.getUser().getName());
            userSummaryResponse.setLastName(advertisement.getUser().getSurname());
            userSummaryResponse.setEmail(advertisement.getUser().getEmail());

            AdminAdResponse adminAdResponse = new AdminAdResponse();

            adminAdResponse.setId(advertisement.getId());
            adminAdResponse.setTitle(advertisement.getTitle());
            adminAdResponse.setDescription(advertisement.getDescription());
            adminAdResponse.setPrice(advertisement.getPrice());
            adminAdResponse.setCity(advertisement.getCity());
            adminAdResponse.setCategory(advertisement.getCategory());
            adminAdResponse.setAdStatus(advertisement.getStatus());
            adminAdResponse.setCreatedAt(advertisement.getCreatedAt());
            adminAdResponse.setUser(userSummaryResponse);


            adminAdResponseList.add(adminAdResponse);



        }
        return adminAdResponseList;
    }

    public Advertisement approveAd(Long id){
        Optional<Advertisement> advertisement = advertisementRepository.findById(id);
        if (advertisement.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "İlan bulunamadı"
            );
        }
        Advertisement advertisement1 = advertisement.get();
        if (advertisement1.getStatus() == AdStatus.PASSIVE ){
            advertisement1.setStatus(AdStatus.APPROVED);
            advertisementRepository.save(advertisement1);
            return advertisement1;

        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Bu ilan onaylanabilir durumda değil"
            );
        }

    }
}
