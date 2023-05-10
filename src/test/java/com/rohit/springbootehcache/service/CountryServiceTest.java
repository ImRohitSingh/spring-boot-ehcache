package com.rohit.springbootehcache.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.cache.Cache;
import javax.cache.CacheManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.rohit.springbootehcache.dao.CountryRepository;
import com.rohit.springbootehcache.models.Country;
import com.rohit.springbootehcache.util.TestUtilities;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

	@Mock
	private CountryRepository mockCountryRepository;

	@Mock
	private CacheManager mockCacheManager;

	@InjectMocks
	private CountryService countryService;

	@Test
	@DisplayName("test find all")
	void testfindAll() {
		List<Country> testCountryList = TestUtilities
			.getCountryList(new Country[] { TestUtilities.getCountry(1, "India"),
					TestUtilities.getCountry(1, "Pakistan") });

		when(mockCountryRepository.findAll()).thenReturn(testCountryList);

		List<Country> actualResponse = countryService.findAll();
		assertThat(actualResponse.size()).isEqualTo(2);
		assertThat(actualResponse.get(1).getName()).isEqualTo("Pakistan");
	}

	@Test
	@DisplayName("test find by id")
	void testfindById() {
		Optional<Country> testCountry = Optional.of(TestUtilities.getCountry(1, "India"));

		when(mockCountryRepository.findById(any(Integer.class))).thenReturn(testCountry);

		Country actualResponse = countryService.findById(1);
		assertThat(actualResponse.getId()).isEqualTo(1);
		assertThat(actualResponse.getName()).isEqualTo("India");
	}

	@Test
	@DisplayName("test get cache manager")
	void testGetCache() {
		ReflectionTestUtils.setField(countryService, "cacheManager", mockCacheManager);

		Cache<Integer, Country> testCache = TestUtilities.getCache();

		Country testCountry = TestUtilities.getCountry(1, "India");
		testCache.put(1, testCountry);

		when(mockCacheManager.getCache(anyString(), eq(Integer.class), eq(Country.class)))
			.thenReturn(testCache);

		Cache<Integer, Country> actualCache = countryService.getCache();

		assertThat(testCache).isEqualTo(actualCache);
		assertThat(actualCache.containsKey(1)).isTrue();
		assertThat(actualCache.get(1).getName()).isEqualTo("India");

		TestUtilities.closeCacheManager();

		countryService.destroy();
		verify(mockCacheManager, times(1)).close();
	}

}
