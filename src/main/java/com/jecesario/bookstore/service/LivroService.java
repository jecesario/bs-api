package com.jecesario.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jecesario.bookstore.domain.Categoria;
import com.jecesario.bookstore.domain.Livro;
import com.jecesario.bookstore.repositories.LivroRepository;
import com.jecesario.bookstore.service.exceptions.ObjectNotFoundException;

@Service
public class LivroService {

	@Autowired
	private LivroRepository repository;
	
	@Autowired
	private CategoriaService categoriaService;

	public Livro findById(Integer id) {
		Optional<Livro> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", tipo: " + Livro.class.getName()));
	}

	public List<Livro> findAll(Integer idCategoria) {
		categoriaService.findById(idCategoria);
		return repository.findAllByCategoria(idCategoria);
	}
	
	

	public Livro update(Integer id, Livro obj) {
		Livro newObj = findById(id);
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	private void updateData(Livro newObj, Livro obj) {
		newObj.setTitulo((obj.getTitulo() == null) ? newObj.getTitulo() : obj.getTitulo());
		newObj.setNomeAutor((obj.getNomeAutor() == null) ? newObj.getNomeAutor() : obj.getNomeAutor());
		newObj.setTexto((obj.getTexto() == null) ? newObj.getTexto() : obj.getTexto());
	}

	public Livro create(Integer idCategoria, Livro obj) {
		obj.setId(null);
		Categoria cat = categoriaService.findById(idCategoria);
		obj.setCategoria(cat);
		return repository.save(obj);
	}

	public void delete(Integer id) {
		Livro obj = findById(id);
		repository.delete(obj);
	}

}
