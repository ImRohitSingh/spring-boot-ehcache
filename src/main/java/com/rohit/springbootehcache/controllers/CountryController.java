package com.rohit.springbootehcache.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.springbootehcache.models.Country;
import com.rohit.springbootehcache.service.CountryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CountryController {

	private CountryService countryService;

	@Autowired
	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}

	@GetMapping(path = "/all")
	public ResponseEntity<List<Country>> findAll() {
		return new ResponseEntity<>(countryService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/id/{id}")
	public ResponseEntity<Country> findById(@PathVariable(name = "id") Integer id) {
		if (countryService.getCache().containsKey(id)) {
			log.info("{} found in cache, database will not be looked up...", id);
		} else {
			log.info("{} not found in cache, database will be looked up...", id);
		}
		return new ResponseEntity<>(countryService.findById(id), HttpStatus.OK);
	}

}
