package com.example.dipendente.controller.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.dipendente.dto.DipendenteDTO;
import com.example.dipendente.dto.OrganoPrevidenzaRequestDTO;
import com.example.dipendente.dto.OrganoPrevidenzaResponseDTO;
import com.example.dipendente.model.Dipendente;
import com.example.dipendente.service.DipendenteService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/dipendente")
public class DipendenteController {

	private static final Logger LOGGER = LogManager.getLogger(DipendenteController.class);

	@Autowired
	private DipendenteService dipendenteService;

	@Autowired
	private WebClient webClient;

	@GetMapping
	public List<DipendenteDTO> getAll() {
		return DipendenteDTO.createDipendenteDTOListFromModelList(dipendenteService.listAll());
	}

	@GetMapping("/{id}")
	public DipendenteDTO findById(@PathVariable(value = "id", required = true) long id) {
		Dipendente dipendenteModel = dipendenteService.cariscaSingoloElemento(id);

		// ora invoco il sistema esterno per capire se il dipendente ha una posizione
		// previdenziale
		// nel caso affermativo valorizzo apposito campo
		// il block significa agire in maniera sincrona, attendendo la risposta
		LOGGER.info("....invocazione servizio esterno....con CF: " + dipendenteModel.getCf());
		OrganoPrevidenzaResponseDTO organoPrevidenzaResponseDTO = webClient.get().uri("/" + dipendenteModel.getCf())
				.retrieve().bodyToMono(OrganoPrevidenzaResponseDTO.class).block();
		LOGGER.info("....invocazione servizio esterno terminata....");

		DipendenteDTO result = DipendenteDTO.buildDipendenteDTOFromModel(dipendenteModel);

		// se l'altra banca ha trovato qualcosa io riempio il DTO altrimenti no
		if (organoPrevidenzaResponseDTO != null && organoPrevidenzaResponseDTO.getCodiceFiscale() != null
				&& organoPrevidenzaResponseDTO.getCodiceFiscale().equals(result.getCf())) {
			result.setNumeroPrevidenziale(organoPrevidenzaResponseDTO.getCodicePrevidenziale());
			LOGGER.info("....invocazione servizio esterno avvenuta con response affermativa....posizione previdenziale: "
					+ organoPrevidenzaResponseDTO.getCodicePrevidenziale());
		}

		return result;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DipendenteDTO createNew(@RequestBody DipendenteDTO dipendenteInput) {
		// ANDREBBE GESTITA CON ADVICE!!!
		// se mi viene inviato un id jpa lo interpreta come update ed a me (producer)
		// non sta bene
		if (dipendenteInput.getId() != null)
			throw new RuntimeException("Non è ammesso fornire un id per la creazione");

		// prima di salvarlo devo verificare se la banca dati esterna lo censisce
		LOGGER.info("....invocazione servizio esterno per censimento nuovo...");
		ResponseEntity<OrganoPrevidenzaResponseDTO> response = webClient.post().uri("")
				.body(Mono.just(new OrganoPrevidenzaRequestDTO(dipendenteInput.getNome(), dipendenteInput.getCognome(),
						dipendenteInput.getCf())), OrganoPrevidenzaRequestDTO.class)
				.retrieve().toEntity(OrganoPrevidenzaResponseDTO.class).block();

		// ANDREBBE GESTITA CON ADVICE!!!
		if (response.getStatusCode() != HttpStatus.CREATED)
			throw new RuntimeException("Errore nella creazione della nuova voce tramite api esterna!!!");

		LOGGER.info("....invocazione servizio esterno per censimento nuovo avvenuta con successo");
		Dipendente dipendenteInserito = dipendenteService.inserisciNuovo(dipendenteInput.buildDipendenteModel());
		return DipendenteDTO.buildDipendenteDTOFromModel(dipendenteInserito);
	}
}
