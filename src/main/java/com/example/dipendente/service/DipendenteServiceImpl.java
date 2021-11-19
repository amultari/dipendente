package com.example.dipendente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dipendente.model.Dipendente;
import com.example.dipendente.repository.DipendenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService {
	
	@Autowired
	private DipendenteRepository dipendenteRepository;

	@Override
	public List<Dipendente> listAll() {
		return (List<Dipendente>) dipendenteRepository.findAll();
	}

	@Override
	public Dipendente cariscaSingoloElemento(Long id) {
		return dipendenteRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Dipendente save(Dipendente transientInstance) {
		return dipendenteRepository.save(transientInstance);
	}

	@Override
	public Dipendente cercaPerCodiceFiscale(String cfInput) {
		return dipendenteRepository.findByCf(cfInput);
	}

}
