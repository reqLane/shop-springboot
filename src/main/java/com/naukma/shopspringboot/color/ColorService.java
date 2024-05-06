package com.naukma.shopspringboot.color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorService {
    private final ColorRepo colorRepo;

    @Autowired
    public ColorService(ColorRepo colorRepo) {
        this.colorRepo = colorRepo;
    }
}
