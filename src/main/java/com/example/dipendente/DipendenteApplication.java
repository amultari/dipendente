package com.example.dipendente;

import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.dipendente.model.Dipendente;
import com.example.dipendente.service.DipendenteService;

@SpringBootApplication
public class DipendenteApplication implements CommandLineRunner {
	
	private static final Logger LOGGER = LogManager.getLogger(DipendenteApplication.class);

	@Autowired
	private DipendenteService dipendenteService;

	public static void main(String[] args) {
		SpringApplication.run(DipendenteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Inizializzo db
		dipendenteService.inserisciNuovo(new Dipendente("Mario", "Rossi",
				new SimpleDateFormat("dd/MM/yyyy").parse("13/12/1978"), "MARROSS78P13H501F"));
		dipendenteService.inserisciNuovo(new Dipendente("Peppe", "Bianchi",
				new SimpleDateFormat("dd/MM/yyyy").parse("01/10/1958"), "PPPBBB58P13H501F"));
		dipendenteService.inserisciNuovo(new Dipendente("Antonio", "Marrone",
				new SimpleDateFormat("dd/MM/yyyy").parse("22/02/1988"), "ANTMAR88P13H501F"));
		dipendenteService.inserisciNuovo(new Dipendente("Ottavio", "Malta",
				new SimpleDateFormat("dd/MM/yyyy").parse("11/04/1962"), "OTTMAL62P13H501F"));
		
		LOGGER.info("...Elenco tutti......");
		dipendenteService.listAll().forEach(d -> LOGGER.info(d.getCf()));
		LOGGER.info("...fine......");

	}

}
