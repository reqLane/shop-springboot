package com.naukma.shopspringboot.picture.model;

import com.naukma.shopspringboot.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "picture")
public class Picture implements Comparable<Picture> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pictureId;

    @Column(nullable = false)
    @Lob
    private byte[] picture;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Picture(byte[] image, Product product) {
        this.picture = image;
        this.product = product;
    }

    @Override
    public int compareTo(Picture o) {
        return Long.compare(pictureId, o.getPictureId());
    }
}
