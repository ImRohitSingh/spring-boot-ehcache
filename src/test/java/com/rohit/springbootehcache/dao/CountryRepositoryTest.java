package com.rohit.springbootehcache.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.rohit.springbootehcache.models.Country;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CountryRepositoryTest {

	@Autowired
	private CountryRepository countryRepository;

	@Test
	@Order(1)
	@DisplayName("test find by id")
	public void test_findById() {
		Country actualCountry = countryRepository.findById(1).get();
		assertThat(actualCountry.getName()).isEqualTo("USA");
	}

	@Test
	@Order(2)
	@Rollback(value = false)
	@DisplayName("test update country")
	public void test_update() {
		Country actualCountry = countryRepository.findById(1).get();
		actualCountry.setName("India");
		Country savedCountry = countryRepository.save(actualCountry);

		assertThat(savedCountry.getName()).isEqualTo("India");
	}

	@Test
	@Order(3)
	@DisplayName("test find by name")
	public void test_findByName() {
		assertThrows(NoSuchElementException.class, () -> {
			countryRepository.findByName("USA").get();
		});

		Country actualCountry = countryRepository.findByName("India").get();
		assertThat(actualCountry.getId()).isEqualTo(1);
	}

	@Test
	@Order(4)
	@DisplayName("test find all")
	public void test_findAll() {
		List<Country> countries = countryRepository.findAll();
		assertThat(countries.size()).isEqualTo(5);
	}

	@Test
	@Order(5)
	@Rollback(value = false)
	@DisplayName("test delete")
	public void test_delete() {
		Country deleteCountry = countryRepository.findById(5).get();
		countryRepository.delete(deleteCountry);

		assertThrows(NoSuchElementException.class, () -> {
			countryRepository.findByName(deleteCountry.getName()).get();
		});
	}

}
