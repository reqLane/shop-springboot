package com.naukma.shopspringboot.product.model;

import com.naukma.shopspringboot.category.model.Category;
import com.naukma.shopspringboot.color.model.Color;
import com.naukma.shopspringboot.order_product.model.OrderProduct;
import com.naukma.shopspringboot.picture.model.Picture;
import com.naukma.shopspringboot.subcategory.model.Subcategory;
import com.naukma.shopspringboot.material.model.Material;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int length;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(nullable = false, length = 300)
    private String packageDescription;

    @OneToMany(mappedBy = "product")
    private Set<Picture> pictures = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    private Subcategory subcategory;

    @OneToMany(mappedBy = "product")
    private Set<OrderProduct> orderProducts = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_color",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id"))
    private Set<Color> colors = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_material",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    private Set<Material> materials = new HashSet<>();

    public Product(String name, String description, BigDecimal price,
                   int height, int length, int width, BigDecimal weight,
                   String packageDescription, Subcategory subcategory) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.height = height;
        this.length = length;
        this.width = width;
        this.weight = weight;
        this.packageDescription = packageDescription;
        this.subcategory = subcategory;
    }

    public int trendingIndex() {
        int index = 0;
        for (OrderProduct orderProduct : orderProducts) {
            Timestamp oneMonthAgo = Timestamp.valueOf(LocalDateTime.now().minusMonths(1));
            if(orderProduct.getOrder().getOrderDate().after(oneMonthAgo)) {
                index += orderProduct.getAmount();
            }
        }
        return index;
    }

    public boolean passesFilters(Category category,
                                 Subcategory subcategory,
                                 String search,
                                 BigDecimal priceMin,
                                 BigDecimal priceMax,
                                 Set<Material> materials,
                                 Set<Color> colors) {
        Set<Material> mIntersection = new HashSet<>();
        if(materials != null) {
            mIntersection.addAll(materials);
            mIntersection.retainAll(getMaterials());
        }
        Set<Color> cIntersection = new HashSet<>();
        if(colors != null) {
            cIntersection.addAll(colors);
            cIntersection.retainAll(getColors());
        }

        return (category == null || getSubcategory().getCategory() == category)
                && (subcategory == null || getSubcategory() == subcategory)
                && (search == null || name.toLowerCase().contains(search.toLowerCase()))
                && (priceMin == null || price.compareTo(priceMin) >= 0)
                && (priceMax == null || price.compareTo(priceMax) <= 0)
                && (materials == null || materials.isEmpty() || !mIntersection.isEmpty())
                && (colors == null || colors.isEmpty() || !cIntersection.isEmpty());
    }
}
