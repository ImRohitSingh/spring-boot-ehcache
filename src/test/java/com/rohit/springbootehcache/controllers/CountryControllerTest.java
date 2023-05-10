package com.rohit.springbootehcache.controllers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import javax.cache.Cache;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.rohit.springbootehcache.models.Country;
import com.rohit.springbootehcache.service.CountryService;
import com.rohit.springbootehcache.util.TestUtilities;

@SpringBootTest
@AutoConfigureMockMvc
class CountryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CountryService mockCountryService;

	@Test
	@DisplayName("test find all")
	void testfindAll() throws Exception {
		when(mockCountryService.findAll()).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/all")).andDo(print()).andExpect(status().isOk());
		verify(mockCountryService, times(1)).findAll();
	}

	@Test
	@DisplayName("test find by id when record in cache")
	void testfindById_whenRecordInCache() throws Exception {
		Country testCountry = TestUtilities.getCountry(1, "India");
		Cache<Integer, Country> testCache = TestUtilities.getCache();
		testCache.put(1, testCountry);

		doReturn(testCache).when(mockCountryService).getCache();
		doReturn(testCountry).when(mockCountryService).findById(anyInt());

		mockMvc.perform(get("/id/1")).andDo(print()).andExpect(status().isOk());

		TestUtilities.closeCacheManager();
	}

	@Test
	@DisplayName("test find by id when record not in cache")
	void testfindById_whenRecordNotInCache() throws Exception {
		Country testCountry = TestUtilities.getCountry(1, "India");

		doReturn(TestUtilities.getCache()).when(mockCountryService).getCache();
		doReturn(testCountry).when(mockCountryService).findById(anyInt());

		mockMvc.perform(get("/id/1")).andDo(print()).andExpect(status().isOk());

		TestUtilities.closeCacheManager();
	}

}
