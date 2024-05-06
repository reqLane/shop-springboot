package com.naukma.shopspringboot;

import com.naukma.shopspringboot.category.CategoryRepo;
import com.naukma.shopspringboot.category.model.Category;
import com.naukma.shopspringboot.color.ColorRepo;
import com.naukma.shopspringboot.color.model.Color;
import com.naukma.shopspringboot.order.OrderRepo;
import com.naukma.shopspringboot.order.model.Order;
import com.naukma.shopspringboot.order_product.OrderProductRepo;
import com.naukma.shopspringboot.order_product.model.OrderProduct;
import com.naukma.shopspringboot.order_product.model.OrderProductId;
import com.naukma.shopspringboot.product.ProductRepo;
import com.naukma.shopspringboot.product.model.Product;
import com.naukma.shopspringboot.subcategory.SubcategoryRepo;
import com.naukma.shopspringboot.subcategory.model.Subcategory;
import com.naukma.shopspringboot.upholstery.UpholsteryRepo;
import com.naukma.shopspringboot.upholstery.model.Upholstery;
import com.naukma.shopspringboot.user.UserRepo;
import com.naukma.shopspringboot.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ShopSpringbootApplicationTests {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private SubcategoryRepo subcategoryRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ColorRepo colorRepo;
    @Autowired
    private UpholsteryRepo upholsteryRepo;
    @Autowired
    private OrderProductRepo orderProductRepo;

    @BeforeEach
    public void cleanDB() {
        orderProductRepo.deleteAll();
        for (Product p : productRepo.findAll()) {
            p.getColors().clear();
            p.getUpholsteries().clear();
            productRepo.save(p);
        }
        upholsteryRepo.deleteAll();
        colorRepo.deleteAll();
        orderRepo.deleteAll();
        userRepo.deleteAll();
        productRepo.deleteAll();
        subcategoryRepo.deleteAll();
        categoryRepo.deleteAll();
    }

    @Test
    void dbTest() {
        Category c = new Category();
        c.setName("Chairs");
        c.setDescription("Good Chairs");
        categoryRepo.save(c);

        for(int i = 0; i < 3; i++) {
            Subcategory s = new Subcategory();
            s.setName("subcategory " + (i+1));
            s.setCategory(c);
            subcategoryRepo.save(s);
        }

        int counter = 1;
        for (Subcategory s : subcategoryRepo.findAll()) {
            Product p = new Product();
            p.setName("product " + counter);
            p.setDescription("description " + counter);
            p.setPrice(new BigDecimal(1.0 * counter));
            p.setHeight(counter * 10);
            p.setLength(counter * 15);
            p.setWidth(counter * 5);
            p.setWeight(new BigDecimal(counter * 50));
            p.setPackageDescription("Package description " + counter);
            p.setSubcategory(s);
            productRepo.save(p);
            counter++;
        }

        User u = new User();
        u.setName("Vlad");
        u.setSurname("Halis");
        u.setFathername("King");
        u.setEmail("vlad5000191@gmail.com");
        u.setPhone("0677728468");
        u.setPassword("encoded3228");
        userRepo.save(u);

        for(int i = 0; i < 3; i++) {
            Color clr = new Color();
            clr.setName("Color " + i);
            clr.setHexCode("ff11f" + i);
            colorRepo.save(clr);
        }

        for(int i = 0; i < 3; i++) {
            Upholstery uph = new Upholstery();
            uph.setName("Upholstery " + i);
            upholsteryRepo.save(uph);
        }

        for (Product p : productRepo.findAll()) {
            for (Color clr : colorRepo.findAll()) {
                p.getColors().add(clr);
            }
            for (Upholstery uph : upholsteryRepo.findAll()) {
                p.getUpholsteries().add(uph);
            }
            productRepo.save(p);
        }

        Order o = new Order();
        o.setOrderDate(new Timestamp(System.currentTimeMillis()));
        o.setUser(u);

        counter = 1;
        List<OrderProduct> ops = new ArrayList<>();
        BigDecimal orderPrice = new BigDecimal(0);
        for (Product p : productRepo.findAll()) {
            OrderProduct op = new OrderProduct();

            OrderProductId opid = new OrderProductId();
            opid.setOrderId(o.getOrderId());
            opid.setProductId(p.getProductId());

            op.setId(opid);
            op.setOrder(o);
            op.setProduct(p);

            op.setAmount(counter * 10);
            counter++;

            for (Color clr : p.getColors()) {
                op.setColor(clr);
            }
            for (Upholstery uph : p.getUpholsteries()) {
                op.setUpholstery(uph);
            }

            orderPrice = orderPrice.add(p.getPrice().multiply(new BigDecimal(op.getAmount())));

            ops.add(op);
        }

        o.setPrice(orderPrice);
        orderRepo.save(o);

        orderProductRepo.saveAll(ops);
    }

}
