package com.hibicode.bearstore.resource;

import java.util.ArrayList;
import java.util.List;

import com.hibicode.bearstore.model.Beer;
import com.hibicode.bearstore.repository.BeersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/beers")
public class BearResource {

	@Autowired
	private BeersRepository repo;

	@GetMapping
	@ResponseStatus(HttpStatus.FOUND)
	public List<Beer> index() {
		List<Beer> beers = repo.findAll();
		return beers;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Beer create(@Valid  @RequestBody Beer beer) {
		return repo.save(beer);
	}
}
