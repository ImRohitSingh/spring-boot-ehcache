package com.rohit.springbootehcache.service;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.cache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.rohit.springbootehcache.dao.CountryRepository;
import com.rohit.springbootehcache.models.Country;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CountryService {

	private CountryRepository countryRepository;

	private CacheManager cacheManager;

	@Autowired
	public CountryService(CountryRepository countryRepository,
		CacheManager cacheManager) {
		this.countryRepository = countryRepository;
		this.cacheManager = cacheManager;
	}

	public List<Country> findAll() {
		log.info("Finding all coutries from datasource...");
		return countryRepository.findAll();
	}

	@Cacheable(cacheNames = "countryCache", key = "#id", condition = "#id>3")
	public Country findById(Integer id) {
		log.info("Finding coutry with id {} in database...", id);
		return countryRepository.findById(id).get();
	}

	public Cache<Integer, Country> getCache() {
		return this.cacheManager.getCache("countryCache", Integer.class, Country.class);
	}

	@PreDestroy
	protected void destroy() {
		log.info("Closing cache manager connection...");
		this.cacheManager.close();
	}

}
