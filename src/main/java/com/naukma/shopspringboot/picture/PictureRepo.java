package com.naukma.shopspringboot.picture;

import com.naukma.shopspringboot.picture.model.Picture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepo extends CrudRepository<Picture, Long> {
}
