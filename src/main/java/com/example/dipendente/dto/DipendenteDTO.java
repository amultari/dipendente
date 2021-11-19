package com.example.dipendente.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.dipendente.model.Dipendente;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class DipendenteDTO {

	private Long id;
	private String nome;
	private String cognome;
	private Date dataNascita;
	private String cf;

	private String numeroPrevidenziale;

	public DipendenteDTO() {

	}

	public DipendenteDTO(Long id, String nome, String cognome, Date dataNascita, String cf) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.cf = cf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getNumeroPrevidenziale() {
		return numeroPrevidenziale;
	}

	public void setNumeroPrevidenziale(String numeroPrevidenziale) {
		this.numeroPrevidenziale = numeroPrevidenziale;
	}

	public static DipendenteDTO buildDipendenteDTOFromModel(Dipendente dipendenteModel) {
		return new DipendenteDTO(dipendenteModel.getId(), dipendenteModel.getNome(), dipendenteModel.getCognome(),
				dipendenteModel.getDataNascita(), dipendenteModel.getCf());
	}

	public static List<DipendenteDTO> createDipendenteDTOListFromModelList(List<Dipendente> modelListInput) {
		return modelListInput.stream()
				.map(dipendenteModel -> new DipendenteDTO(dipendenteModel.getId(), dipendenteModel.getNome(),
						dipendenteModel.getCognome(), dipendenteModel.getDataNascita(), dipendenteModel.getCf()))
				.collect(Collectors.toList());
	}

}
