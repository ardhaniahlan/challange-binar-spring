package com.challangebinarspring.binarfud;

import com.challangebinarspring.binarfud.controller.fileupload.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
@EnableTransactionManagement
public class BinarfudApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinarfudApplication.class, args);
	}

}
