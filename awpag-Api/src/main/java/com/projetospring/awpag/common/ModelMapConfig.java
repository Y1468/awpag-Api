package com.projetospring.awpag.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapConfig {

    //Transformação de objetos
    @Bean
    public ModelMapper modelMapper(){
        return  new ModelMapper();
    }
}
