package com.naukma.shopspringboot.util;

import com.naukma.shopspringboot.category.model.Category;
import com.naukma.shopspringboot.category.model.CategoryDTO;
import com.naukma.shopspringboot.color.model.Color;
import com.naukma.shopspringboot.color.model.ColorDTO;
import com.naukma.shopspringboot.material.model.Material;
import com.naukma.shopspringboot.material.model.MaterialDTO;
import com.naukma.shopspringboot.order.model.Order;
import com.naukma.shopspringboot.order.model.OrderDTO;
import com.naukma.shopspringboot.product.model.Product;
import com.naukma.shopspringboot.product.model.ProductDTO;
import com.naukma.shopspringboot.subcategory.model.Subcategory;
import com.naukma.shopspringboot.subcategory.model.SubcategoryDTO;
import com.naukma.shopspringboot.user.model.User;
import com.naukma.shopspringboot.user.model.UserDTO;

public class DTOMapper {
    public static CategoryDTO toDTO(Category category) {
        return new CategoryDTO(
                category.getCategoryId(),
                category.getName(),
                category.getDescription()
        );
    }

    public static ColorDTO toDTO(Color color) {
        return new ColorDTO(
                color.getColorId(),
                color.getName(),
                color.getHexCode()
        );
    }

    public static MaterialDTO toDTO(Material material) {
        return new MaterialDTO(
                material.getMaterialId(),
                material.getName()
        );
    }

    public static OrderDTO toDTO(Order order) {
        return new OrderDTO(
                order.getOrderId(),
                order.getOrderProducts().size(),
                order.getOrderDate(),
                order.getPrice(),
                order.getStatus()
        );
    }

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getHeight(),
                product.getLength(),
                product.getWidth(),
                product.getWeight(),
                product.getPackageDescription()
        );
    }

    public static SubcategoryDTO toDTO(Subcategory subcategory) {
        return new SubcategoryDTO(
                subcategory.getSubcategoryId(),
                subcategory.getName()
        );
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getName(),
                user.getSurname(),
                user.getFathername(),
                user.getEmail(),
                user.getPhone(),
                user.getBirthdate()
        );
    }
}
