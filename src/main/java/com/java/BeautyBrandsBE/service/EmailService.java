package com.java.BeautyBrandsBE.service;

import com.java.BeautyBrandsBE.dto.ListingResponseDTO;

public interface EmailService {
    void sendEmailToAdmin(ListingResponseDTO responseDTO);
}
