package com.rohit.springbootehcache.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import com.rohit.springbootehcache.models.Country;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtilities {

	private static CacheManager cacheManager = null;

	public static Cache<Integer, Country> getCache() {
		CachingProvider cachingProvider = Caching.getCachingProvider();
		cacheManager = cachingProvider.getCacheManager();
		MutableConfiguration<Integer, Country> config = new MutableConfiguration<>();
		return cacheManager.createCache("simpleCache", config);
	}

	public static void closeCacheManager() {
		cacheManager.close();
	}

	public static Country getCountry(Integer id, String name) {
		return Country.builder().id(id).name(name).build();
	}

	public static List<Country> getCountryList(Country... countries) {
		return new ArrayList<>(Arrays.asList(countries));
	}

}
