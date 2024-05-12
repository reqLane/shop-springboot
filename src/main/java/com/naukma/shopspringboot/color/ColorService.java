package com.naukma.shopspringboot.color;

import com.naukma.shopspringboot.color.model.Color;
import com.naukma.shopspringboot.color.model.ColorDTO;
import com.naukma.shopspringboot.util.DTOMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ColorService {
    private final ColorRepo colorRepo;

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

    public Set<Color> findAll() {
        Set<Color> result = new HashSet<>();
        for (Color color : colorRepo.findAll()) {
            result.add(color);
        }
        return result;
    }

    public Optional<Color> findById(Long id) {
        return colorRepo.findById(id);
    }

    public Color save(Color color) {
        return colorRepo.save(color);
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
