package com.naukma.shopspringboot.upholstery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpholsteryService {
    private final UpholsteryRepo upholsteryRepo;

    @Autowired
    public UpholsteryService(UpholsteryRepo upholsteryRepo) {
        this.upholsteryRepo = upholsteryRepo;
    }
}
