package com.example.dipendente.dto;

public class OrganoPrevidenzaResponseDTO {
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String codicePrevidenziale;

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

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCodicePrevidenziale() {
		return codicePrevidenziale;
	}

	public void setCodicePrevidenziale(String codicePrevidenziale) {
		this.codicePrevidenziale = codicePrevidenziale;
	}

}
