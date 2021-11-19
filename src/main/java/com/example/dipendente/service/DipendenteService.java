package com.example.dipendente.service;

import java.util.List;

import com.example.dipendente.model.Dipendente;

public interface DipendenteService {
	
	public Dipendente save(Dipendente transientInstance);

	public List<Dipendente> listAll();

	public Dipendente cariscaSingoloElemento(Long id);
	
	public Dipendente cercaPerCodiceFiscale(String cfInput);
}
