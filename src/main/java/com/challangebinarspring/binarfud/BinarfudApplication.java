package com.challangebinarspring.binarfud;

import com.challangebinarspring.binarfud.controller.fileupload.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class BinarfudApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinarfudApplication.class, args);
	}

}
