package com.java.BeautyBrandsBE;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BeautyBrandsBeApplication {

	public static void main(String[] args) {
//		//	For Local Development
//		Dotenv dotenv = Dotenv.load();
//		System.setProperty("DB_URL", dotenv.get("DB_URL"));
//		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
//		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
//		System.setProperty("SPRING_MAIL_USERNAME",dotenv.get("SPRING_MAIL_USERNAME"));
//		System.setProperty("SPRING_MAIL_PASSWORD",dotenv.get("SPRING_MAIL_PASSWORD"));
//		System.setProperty("TO_ADDRESS", dotenv.get("TO_ADDRESS"));


		SpringApplication.run(BeautyBrandsBeApplication.class, args);
		System.out.println("Hello World!!!");

	}

}
