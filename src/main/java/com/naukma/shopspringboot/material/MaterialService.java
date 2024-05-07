package com.naukma.shopspringboot.material;

import com.naukma.shopspringboot.material.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {
    private final MaterialRepo materialRepo;

    @Autowired
    public MaterialService(MaterialRepo materialRepo) {
        this.materialRepo = materialRepo;
    }

    // BUSINESS LOGIC



    // CRUD OPERATIONS

    public List<Material> findAll() {
        List<Material> result = new ArrayList<>();
        for (Material material : materialRepo.findAll()) {
            result.add(material);
        }
        return result;
    }

    public Material findById(Long id) {
        Optional<Material> result = materialRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    public Material create(Material material) {
        return materialRepo.save(material);
    }

    public void update(Material material) {
        materialRepo.save(material);
    }

    public void deleteById(Long id) {
        materialRepo.deleteById(id);
    }

    public void delete(Material material) {
        materialRepo.deleteById(material.getMaterialId());
    }

    public void deleteAll() {
        materialRepo.deleteAll();
    }
}
