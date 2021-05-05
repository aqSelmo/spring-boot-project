package com.aqselmo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aqselmo.domain.Cliente;
import com.aqselmo.repositories.ClienteRepository;
import com.aqselmo.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

}
