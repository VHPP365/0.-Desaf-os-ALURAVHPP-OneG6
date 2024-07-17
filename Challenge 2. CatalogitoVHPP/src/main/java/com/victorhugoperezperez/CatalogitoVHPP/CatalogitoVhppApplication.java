package com.victorhugoperezperez.CatalogitoVHPP;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogitoVhppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CatalogitoVhppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Holis");
		PrincipalVHPP principalVHPP = new PrincipalVHPP();
		principalVHPP.muestraElMenu();
	}
}


