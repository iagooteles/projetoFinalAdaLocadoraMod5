package com.projetoFinalWeb.projetoWeb.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "nameGeneratorClient", url = "https://gerador-nomes.wolan.net")
public interface NameClientFeign {
    @GetMapping("/nome/aleatorio")
    List<String> getRandomName();
}
