package com.naukma.shopspringboot.color;

import com.naukma.shopspringboot.color.model.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ColorService {
    private final ColorRepo colorRepo;

    @Autowired
    public ColorService(ColorRepo colorRepo) {
        this.colorRepo = colorRepo;
    }

    // BUSINESS LOGIC



    // CRUD OPERATIONS

    public List<Color> findAll() {
        List<Color> result = new ArrayList<>();
        for (Color color : colorRepo.findAll()) {
            result.add(color);
        }
        return result;
    }

    public Color findById(Long id) {
        Optional<Color> result = colorRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    public Color create(Color color) {
        return colorRepo.save(color);
    }

    public void update(Color color) {
        colorRepo.save(color);
    }

    public void deleteById(Long id) {
        colorRepo.deleteById(id);
    }

    public void delete(Color color) {
        colorRepo.deleteById(color.getColorId());
    }

    public void deleteAll() {
        colorRepo.deleteAll();
    }
}
