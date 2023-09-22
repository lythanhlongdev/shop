package com.ltldev.shop.configuration;


import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
@Configurable
public class config {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
