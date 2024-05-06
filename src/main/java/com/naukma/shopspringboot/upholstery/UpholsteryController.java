package com.naukma.shopspringboot.upholstery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/upholsteries")
public class UpholsteryController {
    private final UpholsteryService upholsteryService;

    @Autowired
    public UpholsteryController(UpholsteryService upholsteryService) {
        this.upholsteryService = upholsteryService;
    }
}
