package com.rdvn.ilan.repository;

import com.rdvn.ilan.entity.Advertisement;
import com.rdvn.ilan.enums.AdStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findByCity (String city);
    List<Advertisement> findByPriceBetween (BigDecimal minprice , BigDecimal maxprice);
    List<Advertisement> findByCityAndPriceBetween (String city,BigDecimal minprice , BigDecimal maxprice);
    List<Advertisement> findByPriceGreaterThanEqual(BigDecimal minPrice);
    List<Advertisement> findByPriceLessThanEqual(BigDecimal maxPrice);
    List<Advertisement> findTop10ByStatusOrderByCreatedAtDesc(AdStatus adStatus);
    List<Advertisement> findByStatus(AdStatus adStatus);

}
