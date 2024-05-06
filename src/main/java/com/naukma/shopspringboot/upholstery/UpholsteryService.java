package com.naukma.shopspringboot.upholstery;

import com.naukma.shopspringboot.upholstery.model.Upholstery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UpholsteryService {
    private final UpholsteryRepo upholsteryRepo;

    @Autowired
    public UpholsteryService(UpholsteryRepo upholsteryRepo) {
        this.upholsteryRepo = upholsteryRepo;
    }

    // BUSINESS LOGIC



    // CRUD OPERATIONS

    public List<Upholstery> findAll() {
        List<Upholstery> result = new ArrayList<>();
        for (Upholstery upholstery : upholsteryRepo.findAll()) {
            result.add(upholstery);
        }
        return result;
    }

    public Upholstery findById(Long id) {
        Optional<Upholstery> result = upholsteryRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    public Upholstery create(Upholstery upholstery) {
        return upholsteryRepo.save(upholstery);
    }

    public void update(Upholstery upholstery) {
        upholsteryRepo.save(upholstery);
    }

    public void deleteById(Long id) {
        upholsteryRepo.deleteById(id);
    }

    public void delete(Upholstery upholstery) {
        upholsteryRepo.deleteById(upholstery.getUpholsteryId());
    }

    public void deleteAll() {
        upholsteryRepo.deleteAll();
    }
}
