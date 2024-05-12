package com.naukma.shopspringboot.material;

import com.naukma.shopspringboot.material.model.Material;
import com.naukma.shopspringboot.material.model.MaterialDTO;
import com.naukma.shopspringboot.util.DTOMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MaterialService {
    private final MaterialRepo materialRepo;

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

    public Set<Material> findAll() {
        Set<Material> result = new HashSet<>();
        for (Material material : materialRepo.findAll()) {
            result.add(material);
        }
        return result;
    }

    public Optional<Material> findById(Long id) {
        return materialRepo.findById(id);
    }

    public Material save(Material material) {
        return materialRepo.save(material);
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
