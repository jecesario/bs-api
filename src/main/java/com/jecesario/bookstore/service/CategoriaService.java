package com.jecesario.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.jecesario.bookstore.domain.Categoria;
import com.jecesario.bookstore.dtos.CategoriaDTO;
import com.jecesario.bookstore.repositories.CategoriaRepository;
import com.jecesario.bookstore.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria findById(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", tipo: " + Categoria.class.getName()));
	}

	public List<Categoria> findAll() {
		return repository.findAll();
	}

	public Categoria create(Categoria obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Categoria update(Integer id, CategoriaDTO objDto) {
		Categoria obj = findById(id);
		updateData(obj, objDto);
		return repository.save(obj);
	}

	private void updateData(Categoria obj, CategoriaDTO objDto) {
		obj.setNome((objDto.getNome() == null) ? obj.getNome() : objDto.getNome());
		obj.setDescricao((objDto.getDescricao() == null) ? obj.getDescricao() : objDto.getDescricao());
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new com.jecesario.bookstore.service.exceptions.DataIntegrityViolationException(
					"Categoria não pode ser deletada! Possui livros associados");
		}
	}
}
