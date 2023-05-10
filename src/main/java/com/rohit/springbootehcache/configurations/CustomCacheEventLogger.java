package com.rohit.springbootehcache.configurations;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomCacheEventLogger implements CacheEventListener<Object, Object> {

	@Override
	public void onEvent(CacheEvent<?, ?> cacheEvent) {
		log.info("Key: {} | EventType: {} | Old value: {} | New value: {}",
			cacheEvent.getKey(), cacheEvent.getType(), cacheEvent.getOldValue(),
			cacheEvent.getNewValue());
	}

}