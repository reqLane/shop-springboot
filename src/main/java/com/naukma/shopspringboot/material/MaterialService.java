package com.naukma.shopspringboot.material;

import com.naukma.shopspringboot.material.model.Material;
import com.naukma.shopspringboot.material.model.MaterialDTO;
import com.naukma.shopspringboot.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MaterialService {
    private final MaterialRepo materialRepo;

    @Autowired
    public MaterialService(MaterialRepo materialRepo) {
        this.materialRepo = materialRepo;
    }

    // BUSINESS LOGIC

    public List<MaterialDTO> getAllMaterials() {
        return findAll()
                .stream()
                .sorted(Comparator.comparingLong(Material::getMaterialId))
                .map(DTOMapper::toDTO)
                .toList();
    }

    // CRUD OPERATIONS

    public Material getMaterialEntityByName(String name) {
        return materialRepo.getMaterialByNameEqualsIgnoreCase(name);
    }

    private Set<Material> findAll() {
        Set<Material> result = new HashSet<>();
        for (Material material : materialRepo.findAll()) {
            result.add(material);
        }
        return result;
    }

    private Material findById(Long id) {
        Optional<Material> result = materialRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    private Material create(Material material) {
        return materialRepo.save(material);
    }

    private void update(Material material) {
        materialRepo.save(material);
    }

    private void deleteById(Long id) {
        materialRepo.deleteById(id);
    }

    private void delete(Material material) {
        materialRepo.deleteById(material.getMaterialId());
    }

    private void deleteAll() {
        materialRepo.deleteAll();
    }
}
