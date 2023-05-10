package com.rohit.springbootehcache.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CountryTest {

	@Test
	@DisplayName("test getter setter tostring")
	void testSetterGetterToString() {
		Country testCountry = new Country();
		testCountry.setId(1);
		testCountry.setName("India");
		assertThat(testCountry.getName()).isEqualTo("India");
		assertThat(testCountry.toString()).isEqualTo("Country(id=1, name=India)");
	}

	@Test
	@DisplayName("test builder")
	void testBuilder() {
		Country.CountryBuilder testCountryBuilder = new Country.CountryBuilder().id(1)
			.name("India");
		assertThat(testCountryBuilder.toString())
			.isEqualTo("Country.CountryBuilder(id=1, name=India)");
		Country testCountry = testCountryBuilder.build();
		assertThat(testCountry.toString()).isEqualTo("Country(id=1, name=India)");
	}

}
