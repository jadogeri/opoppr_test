package com.opao.pp_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {
    "com.opao.pp_api" // Forces Spring to scan your package and target/generated packages completely
})
@RestController	
public class PpApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PpApiApplication.class, args);
	}

}
