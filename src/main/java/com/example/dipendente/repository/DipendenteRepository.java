package com.example.dipendente.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.dipendente.model.Dipendente;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long> {

	Dipendente findByCf(String cfInput);

}
