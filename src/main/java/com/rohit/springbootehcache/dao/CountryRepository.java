package com.rohit.springbootehcache.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.springbootehcache.models.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

	Optional<Country> findByName(String name);

}
