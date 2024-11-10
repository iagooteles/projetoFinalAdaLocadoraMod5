package com.projetoFinalWeb.projetoWeb.service;

import com.projetoFinalWeb.projetoWeb.apis.NameClientFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class NameService {
    private final NameClientFeign nameClientFeign;
    private final Random random = new Random();

    @Autowired
    public NameService(NameClientFeign nameClientFeign) {
        this.nameClientFeign = nameClientFeign;
    }

    public String getRandomName() {
        List<String> names = nameClientFeign.getRandomName();

        if (names != null && !names.isEmpty()) {
            return names.get(random.nextInt(names.size()));
        }
        return null;
    }
}
