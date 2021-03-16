package com.pang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
public class MyGsonConfig {
	
	@Bean
	GsonHttpMessageConverter gsonHttpMessageConverter(){
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.setDateFormat("yyyy/MM/dd").create();
        converter.setGson(gson);
        return converter;
    }
}
