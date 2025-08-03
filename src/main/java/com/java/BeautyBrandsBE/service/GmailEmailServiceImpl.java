package com.java.BeautyBrandsBE.service;

import com.java.BeautyBrandsBE.dto.CategoryResponseDTO;
import com.java.BeautyBrandsBE.dto.ListingResponseDTO;
import com.java.BeautyBrandsBE.dto.SubCategoryResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class GmailEmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public GmailEmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Value("${from.address}")
    private  String fromAddress;

    @Value("${to.address}")
    private String toAddress;

    @Async
    @Override
    public void sendEmailToAdmin(ListingResponseDTO responseDTO) {
        String subject = "New Business Submission: " + responseDTO.getListingTitle();

        StringBuilder body = new StringBuilder();
        body.append("Greetings,\n\n");
        body.append("A new business has been successfully submitted to the Beauty Brands platform. Below are the listing details:\n\n");

        body.append("Listing ID: ").append(responseDTO.getListingId()).append("\n")
                .append("Business Name: ").append(responseDTO.getListingTitle()).append("\n")
                .append("Description: ").append(responseDTO.getDescription()).append("\n")
                .append("Address: ").append(responseDTO.getAddress()).append(", ").append(responseDTO.getCity()).append("\n")
                .append("Contact Number: ").append(responseDTO.getContactNumber()).append("\n")
                .append("WhatsApp Number: ").append(responseDTO.getWhatsappNumber()).append("\n")
                .append("Email: ").append(responseDTO.getEmail()).append("\n")
                .append("Website: ").append(responseDTO.getWebsite()).append("\n")
                .append("Listing Active: ").append(responseDTO.getListingActive()).append("\n\n");

        body.append("Categories:\n");
        for(CategoryResponseDTO category : responseDTO.getCategories()){
            body.append("- ").append(category.getCategoryName()).append("\n");
        }
        body.append("\nSubcategories:\n");
        for (SubCategoryResponseDTO sub : responseDTO.getSubCategories()) {
            body.append("- ").append(sub.getSubCategoryName()).append("\n");
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromAddress);
        mailMessage.setTo(toAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(body.toString());

        javaMailSender.send(mailMessage);

    }
}
