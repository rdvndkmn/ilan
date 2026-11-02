package com.rdvn.ilan.exception;

public class AdNotFoundException extends RuntimeException{//Burada AdNotFoundException, Java'nın hazır RuntimeException sınıfından miras alıyor.
    public AdNotFoundException(String message) {
        super(message);//super, üst sınıfı ifade eder. Aldığım hata mesajını RuntimeException sınıfının constructor'ına gönder.
    }
}
