package com.naukma.shopspringboot.color;

import com.naukma.shopspringboot.color.model.Color;
import com.naukma.shopspringboot.color.model.ColorDTO;
import com.naukma.shopspringboot.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ColorService {
    private final ColorRepo colorRepo;

    @Autowired
    public ColorService(ColorRepo colorRepo) {
        this.colorRepo = colorRepo;
    }

    // BUSINESS LOGIC

    public List<ColorDTO> getAllColors() {
        return findAll()
                .stream()
                .sorted(Comparator.comparingLong(Color::getColorId))
                .map(DTOMapper::toDTO)
                .toList();
    }

    // CRUD OPERATIONS

    public Color getColorEntityByName(String name) {
        return colorRepo.getColorByNameEqualsIgnoreCase(name);
    }

    private Set<Color> findAll() {
        Set<Color> result = new HashSet<>();
        for (Color color : colorRepo.findAll()) {
            result.add(color);
        }
        return result;
    }

    private Color findById(Long id) {
        Optional<Color> result = colorRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    private Color create(Color color) {
        return colorRepo.save(color);
    }

    private void update(Color color) {
        colorRepo.save(color);
    }

    private void deleteById(Long id) {
        colorRepo.deleteById(id);
    }

    private void delete(Color color) {
        colorRepo.deleteById(color.getColorId());
    }

    private void deleteAll() {
        colorRepo.deleteAll();
    }
}
