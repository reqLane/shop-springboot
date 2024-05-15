package com.naukma.shopspringboot;

import com.naukma.shopspringboot.category.CategoryRepo;
import com.naukma.shopspringboot.category.model.Category;
import com.naukma.shopspringboot.color.ColorRepo;
import com.naukma.shopspringboot.color.model.Color;
import com.naukma.shopspringboot.material.model.Material;
import com.naukma.shopspringboot.order.OrderRepo;
import com.naukma.shopspringboot.order.model.Order;
import com.naukma.shopspringboot.order_product.OrderProductRepo;
import com.naukma.shopspringboot.order_product.model.OrderProduct;
import com.naukma.shopspringboot.picture.PictureRepo;
import com.naukma.shopspringboot.picture.model.Picture;
import com.naukma.shopspringboot.product.ProductRepo;
import com.naukma.shopspringboot.product.model.Product;
import com.naukma.shopspringboot.subcategory.SubcategoryRepo;
import com.naukma.shopspringboot.subcategory.model.Subcategory;
import com.naukma.shopspringboot.material.MaterialRepo;
import com.naukma.shopspringboot.user.UserRepo;
import com.naukma.shopspringboot.user.model.User;
import com.naukma.shopspringboot.user.role.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@SpringBootTest
class ShopSpringbootApplicationTests {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private SubcategoryRepo subcategoryRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private PictureRepo pictureRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ColorRepo colorRepo;
    @Autowired
    private MaterialRepo materialRepo;
    @Autowired
    private OrderProductRepo orderProductRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ShopSpringbootApplicationTests(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @BeforeEach
    public void cleanDB() {
        orderProductRepo.deleteAll();
        for (Product p : productRepo.findAll()) {
            p.getColors().clear();
            p.getMaterials().clear();
            productRepo.save(p);
        }
        materialRepo.deleteAll();
        colorRepo.deleteAll();
        orderRepo.deleteAll();
        userRepo.deleteAll();
        pictureRepo.deleteAll();
        productRepo.deleteAll();
        subcategoryRepo.deleteAll();
        categoryRepo.deleteAll();
    }

    @Test
    void fillDatabase() {
        //region Category
        Category tables = new Category("Tables", "They are strong and reliable construction. High quality assembly.");
        Category chairs = new Category("Chairs", "Made of high quality materialIds. Complete with a hard or soft seat.");
        Category benches = new Category("Benches", "Qualitatively assembled and distinguished by multi-layer processed materialIds.");
        Category cabinetFurniture = new Category("Furniture", "Oak and metal shelves, chests of drawers, sideboards for any taste.");
        Category beds = new Category("Beds", "The material is solid oak. Completed orthopedic base for the mattress.");
        List<Category> categories = new ArrayList<>(Arrays.asList(tables, chairs, benches, cabinetFurniture, beds));
        //endregion

        //region Subcategory
        Subcategory diningTables = new Subcategory("Dining tables", tables);
        Subcategory writingTables = new Subcategory("Writing tables", tables);
        Subcategory coffeeTables = new Subcategory("Coffee tables", tables);
        Subcategory magazineTables = new Subcategory("Magazine tables", tables);

        Subcategory armchairs = new Subcategory("Armchairs", chairs);
        Subcategory stools = new Subcategory("Stools", chairs);
        Subcategory computerChairs = new Subcategory("Computer chairs", chairs);
        Subcategory barChairs = new Subcategory("Bar chairs", chairs);

        Subcategory woodenBenches = new Subcategory("Wooden benches", benches);
        Subcategory softBenches = new Subcategory("Soft benches", benches);
        Subcategory gardenBenches = new Subcategory("Garden benches", benches);
        Subcategory barBenches = new Subcategory("Bar benches", benches);

        Subcategory cabinets = new Subcategory("Cabinets", cabinetFurniture);
        Subcategory chests = new Subcategory("Chests", cabinetFurniture);
        Subcategory nightstands = new Subcategory("Nightstands", cabinetFurniture);
        Subcategory shelves = new Subcategory("Shelves", cabinetFurniture);

        Subcategory singleBeds = new Subcategory("Single beds", beds);
        Subcategory doubleBeds = new Subcategory("Double beds", beds);
        Subcategory foldingBeds = new Subcategory("Folding beds", beds);
        Subcategory storageBeds = new Subcategory("Storage beds", beds);

        List<Subcategory> subcategories = new ArrayList<>(Arrays.asList(
                diningTables, writingTables, coffeeTables, magazineTables,
                armchairs, stools, computerChairs, barChairs,
                woodenBenches, softBenches, gardenBenches, barBenches,
                cabinets, chests, nightstands, shelves,
                singleBeds, doubleBeds, foldingBeds, storageBeds
        ));
        //endregion

        //region User
        String encryptedPassword = bCryptPasswordEncoder.encode("pass");
        User vlad = new User("Vladyslav", "Marchenko", "vlad5000191@gmail.com", "0677728468", encryptedPassword, UserRole.USER);
        //endregion

        //region Color
        Color wildOak = new Color("Wild oak", "A28B71");
        Color ivory = new Color("Ivory", "EDE7E1");
        Color ashTree = new Color("Ash tree", "C2B4A2");
        Color italianWalnut = new Color("Italian walnut", "645857");
        Color anthracite = new Color("Anthracite", "252F3A");
        List<Color> colors = new ArrayList<>(Arrays.asList(wildOak, ivory, ashTree, italianWalnut, anthracite));
        //endregion

        //region Material
        Material wood = new Material("Wood");
        Material metal = new Material("Metal");
        Material glass = new Material("Glass");
        List<Material> materials = new ArrayList<>(Arrays.asList(wood, metal, glass));
        //endregion

        //region Product
        //region Dining tables
        Product dt1 = new Product("SANDSBERG Dining table",
                "This table for 4 blends a warm wood expression with sturdy metal in a slim design that’s pleasing to the eye even in smaller spaces. Pair it with SANDSBERG chair to create a welcoming and coordinated look.",
                new BigDecimal("2599.99"),
                118,
                141,
                72,
                new BigDecimal("12.4"),
                "This product comes as 2 packages.",
                diningTables);
        Product dt2 = new Product("PINNTORP Dining table",
                "This wooden table seats 4 people comfortably. Inspired by Swedish furniture tradition, the simple design adds warmth and character, making this a table for talking and sharing great moments together.",
                new BigDecimal("2599.99"),
                118,
                141,
                72,
                new BigDecimal("11.2"),
                "This product comes as 2 packages.",
                diningTables);
        Product dt3 = new Product("BJURSTA Dining table",
                "Becomes a practical shelf for small things when folded down.",
                new BigDecimal("2499.99"),
                78,
                98,
                72,
                new BigDecimal("8.8"),
                "This product comes as 1 packages.",
                diningTables);
        Product dt4 = new Product("GRASALA Dining table",
                "A durable table for 2 with a neat yet sturdy design suitable for eating, working, entertaining or crafting. A good size for smaller spaces and it pairs up perfectly with GRASALA chair.",
                new BigDecimal("1799.99"),
                83,
                52,
                45,
                new BigDecimal("7.1"),
                "This product comes as 2 packages.",
                diningTables);
        Product dt5 = new Product("JOKKMOKK Dining table",
                "A simple, sturdy set that’s perfect for your breakfast nook or smaller dining area. The solid black-brown pine holds up well over time and will endure all the family meals and activities around the table.",
                new BigDecimal("4449.99"),
                86,
                56,
                41,
                new BigDecimal("9.6"),
                "This product comes as 4 packages.",
                diningTables);
        Product dt6 = new Product("VOXLOV Dining table",
                "The natural bamboo makes this table light and versatile, but the hardwearing material is also warm and inviting. The bent wood enhances the Scandinavian expression.",
                new BigDecimal("17199.99"),
                89,
                59,
                66,
                new BigDecimal("12.5"),
                "This product comes as 3 packages.",
                diningTables);
        //endregion
        //region Writing tables
        Product wt1 = new Product("SANDSBERG Writing table",
                "This table for 4 blends a warm wood expression with sturdy metal in a slim design that’s pleasing to the eye even in smaller spaces. Pair it with SANDSBERG chair to create a welcoming and coordinated look.",
                new BigDecimal("2999.99"),
                111,
                161,
                75,
                new BigDecimal("10.4"),
                "This product comes as 2 packages.",
                writingTables);
        Product wt2 = new Product("PINNTORP Writing table",
                "This wooden table seats 4 people comfortably. Inspired by Swedish furniture tradition, the simple design adds warmth and character, making this a table for talking and sharing great moments together.",
                new BigDecimal("3599.99"),
                108,
                101,
                66,
                new BigDecimal("11.2"),
                "This product comes as 2 packages.",
                writingTables);
        Product wt3 = new Product("BJURSTA Writing table",
                "Becomes a practical shelf for small things when folded down.",
                new BigDecimal("1499.99"),
                79,
                90,
                62,
                new BigDecimal("8.8"),
                "This product comes as 1 packages.",
                writingTables);
        Product wt4 = new Product("GRASALA Writing table",
                "A durable table for 2 with a neat yet sturdy design suitable for eating, working, entertaining or crafting. A good size for smaller spaces and it pairs up perfectly with GRASALA chair.",
                new BigDecimal("8799.99"),
                113,
                102,
                105,
                new BigDecimal("7.1"),
                "This product comes as 2 packages.",
                writingTables);
        //endregion
        //region Coffee tables
        Product ct1 = new Product("SANDSBERG Coffee table",
                "This table for 4 blends a warm wood expression with sturdy metal in a slim design that’s pleasing to the eye even in smaller spaces. Pair it with SANDSBERG chair to create a welcoming and coordinated look.",
                new BigDecimal("2999.99"),
                122,
                100,
                98,
                new BigDecimal("12.9"),
                "This product comes as 4 packages.",
                coffeeTables);
        Product ct2 = new Product("PINNTORP Coffee table",
                "This wooden table seats 4 people comfortably. Inspired by Swedish furniture tradition, the simple design adds warmth and character, making this a table for talking and sharing great moments together.",
                new BigDecimal("3599.99"),
                118,
                141,
                72,
                new BigDecimal("11.5"),
                "This product comes as 2 packages.",
                coffeeTables);
        Product ct3 = new Product("BJURSTA Coffee table",
                "Becomes a practical shelf for small things when folded down.",
                new BigDecimal("1499.99"),
                78,
                98,
                72,
                new BigDecimal("8.9"),
                "This product comes as 1 packages.",
                coffeeTables);
        Product ct4 = new Product("GRASALA Coffee table",
                "A durable table for 2 with a neat yet sturdy design suitable for eating, working, entertaining or crafting. A good size for smaller spaces and it pairs up perfectly with GRASALA chair.",
                new BigDecimal("8799.99"),
                83,
                52,
                45,
                new BigDecimal("7.5"),
                "This product comes as 3 packages.",
                coffeeTables);
        //endregion
        //region Magazine tables
        Product mt1 = new Product("SANDSBERG Magazine table",
                "This table for 4 blends a warm wood expression with sturdy metal in a slim design that’s pleasing to the eye even in smaller spaces. Pair it with SANDSBERG chair to create a welcoming and coordinated look.",
                new BigDecimal("2999.99"),
                122,
                100,
                98,
                new BigDecimal("12.9"),
                "This product comes as 4 packages.",
                magazineTables);
        Product mt2 = new Product("PINNTORP Magazine table",
                "This wooden table seats 4 people comfortably. Inspired by Swedish furniture tradition, the simple design adds warmth and character, making this a table for talking and sharing great moments together.",
                new BigDecimal("3599.99"),
                118,
                141,
                72,
                new BigDecimal("11.5"),
                "This product comes as 2 packages.",
                magazineTables);
        Product mt3 = new Product("BJURSTA Magazine table",
                "Becomes a practical shelf for small things when folded down.",
                new BigDecimal("1499.99"),
                78,
                98,
                72,
                new BigDecimal("8.9"),
                "This product comes as 1 packages.",
                magazineTables);
        Product mt4 = new Product("GRASALA Magazine table",
                "A durable table for 2 with a neat yet sturdy design suitable for eating, working, entertaining or crafting. A good size for smaller spaces and it pairs up perfectly with GRASALA chair.",
                new BigDecimal("8799.99"),
                83,
                52,
                45,
                new BigDecimal("7.5"),
                "This product comes as 3 packages.",
                magazineTables);
        //endregion

        //region Armchairs
        Product ac1 = new Product("REMSTA Armchair",
                "Softly rounded corners and nice details give REMSTA armchair a traditional look. The cover has a smooth feel, the shape gives lumbar support – and compact packaging reduces climate impact during transport.",
                new BigDecimal("8999.99"),
                88,
                60,
                72,
                new BigDecimal("14.6"),
                "This product comes as 1 packages.",
                armchairs);
        Product ac2 = new Product("POANG Armchair",
                "POANG armchair has stylish curved lines in bentwood, providing nice support for the neck and comfy resilience. It’s been in our range for several decades and is still just as popular. Want to try it too?",
                new BigDecimal("4599.99"),
                77,
                69,
                58,
                new BigDecimal("10.5"),
                "This product comes as 2 packages.",
                armchairs);
        Product ac3 = new Product("HERRAKRA Armchair",
                "A bold design with a strong character and charmingly rounded shapes, upholstered in an easy-care fabric. HERRÅKRA armchair goes well in your living room and in in your office’s reception area.",
                new BigDecimal("9199.99"),
                78,
                98,
                72,
                new BigDecimal("8.9"),
                "This product comes as 1 package.",
                armchairs);
        Product ac4 = new Product("SKAPAFORS Armchair",
                "A small and neat easy chair that goes just as well in the living room as in the bedroom or hallway, even if space is limited.",
                new BigDecimal("8799.99"),
                83,
                52,
                45,
                new BigDecimal("9.5"),
                "This product comes as 3 packages.",
                armchairs);
        //endregion
        //region Stools
        Product sc1 = new Product("REMSTA Stool",
                "Softly rounded corners and nice details give REMSTA stool a traditional look. The cover has a smooth feel, the shape gives lumbar support – and compact packaging reduces climate impact during transport.",
                new BigDecimal("7999.99"),
                88,
                60,
                72,
                new BigDecimal("14.6"),
                "This product comes as 1 packages.",
                stools);
        Product sc2 = new Product("POANG Stool",
                "POANG stool has stylish curved lines in bentwood, providing nice support for the neck and comfy resilience. It’s been in our range for several decades and is still just as popular. Want to try it too?",
                new BigDecimal("5599.99"),
                77,
                69,
                58,
                new BigDecimal("10.5"),
                "This product comes as 2 packages.",
                stools);
        Product sc3 = new Product("HERRAKRA Stool",
                "A bold design with a strong character and charmingly rounded shapes, upholstered in an easy-care fabric. HERRÅKRA stool goes well in your living room and in in your office’s reception area.",
                new BigDecimal("10199.99"),
                78,
                98,
                72,
                new BigDecimal("8.9"),
                "This product comes as 1 package.",
                stools);
        Product sc4 = new Product("SKAPAFORS Stool",
                "A small and neat easy chair that goes just as well in the living room as in the bedroom or hallway, even if space is limited.",
                new BigDecimal("2799.99"),
                83,
                52,
                45,
                new BigDecimal("9.5"),
                "This product comes as 3 packages.",
                stools);
        //endregion
        //region Computer chairs
        Product cc1 = new Product("REMSTA Computer chair",
                "Softly rounded corners and nice details give REMSTA computer chair a traditional look. The cover has a smooth feel, the shape gives lumbar support – and compact packaging reduces climate impact during transport.",
                new BigDecimal("8999.99"),
                88,
                60,
                72,
                new BigDecimal("14.6"),
                "This product comes as 1 packages.",
                computerChairs);
        Product cc2 = new Product("POANG Computer chair",
                "POANG computer chair has stylish curved lines in bentwood, providing nice support for the neck and comfy resilience. It’s been in our range for several decades and is still just as popular. Want to try it too?",
                new BigDecimal("4599.99"),
                77,
                69,
                58,
                new BigDecimal("10.5"),
                "This product comes as 2 packages.",
                computerChairs);
        Product cc3 = new Product("HERRAKRA Computer chair",
                "A bold design with a strong character and charmingly rounded shapes, upholstered in an easy-care fabric. HERRÅKRA computer chair goes well in your living room and in in your office’s reception area.",
                new BigDecimal("9199.99"),
                78,
                98,
                72,
                new BigDecimal("8.9"),
                "This product comes as 1 package.",
                computerChairs);
        Product cc4 = new Product("SKAPAFORS Computer chair",
                "A small and neat easy chair that goes just as well in the living room as in the bedroom or hallway, even if space is limited.",
                new BigDecimal("8799.99"),
                83,
                52,
                45,
                new BigDecimal("9.5"),
                "This product comes as 3 packages.",
                computerChairs);
        //endregion
        //region Bar chairs
        Product bc1 = new Product("REMSTA Bar chair",
                "Softly rounded corners and nice details give REMSTA bar chair a traditional look. The cover has a smooth feel, the shape gives lumbar support – and compact packaging reduces climate impact during transport.",
                new BigDecimal("9999.99"),
                88,
                60,
                72,
                new BigDecimal("14.6"),
                "This product comes as 1 packages.",
                barChairs);
        Product bc2 = new Product("POANG Bar chair",
                "POANG bar chair has stylish curved lines in bentwood, providing nice support for the neck and comfy resilience. It’s been in our range for several decades and is still just as popular. Want to try it too?",
                new BigDecimal("4799.99"),
                77,
                69,
                58,
                new BigDecimal("10.5"),
                "This product comes as 2 packages.",
                barChairs);
        Product bc3 = new Product("HERRAKRA Bar chair",
                "A bold design with a strong character and charmingly rounded shapes, upholstered in an easy-care fabric. HERRÅKRA bar chair goes well in your living room and in in your office’s reception area.",
                new BigDecimal("9099.99"),
                78,
                98,
                72,
                new BigDecimal("8.9"),
                "This product comes as 1 package.",
                barChairs);
        Product bc4 = new Product("SKAPAFORS Bar chair",
                "A small and neat easy chair that goes just as well in the living room as in the bedroom or hallway, even if space is limited.",
                new BigDecimal("8199.99"),
                83,
                52,
                45,
                new BigDecimal("9.5"),
                "This product comes as 3 packages.",
                barChairs);
        //endregion

        //region Wooden benches
        Product wb1 = new Product("NAMMARO Wooden bench",
                "Create a comfy living room feel outdoors with NAMMARO series. With everything you need for chill moments, long dinners and cheerful summer parties, no matter if you have a balcony, terrace or garden.",
                new BigDecimal("2999.99"),
                88,
                60,
                72,
                new BigDecimal("14.6"),
                "This product comes as 1 packages.",
                woodenBenches);
        Product wb2 = new Product("RAGRUND Wooden bench",
                "Our RAGRUND bathroom furniture adds warmth to your bathroom and will outlast splashes and showering teens. The natural bamboo material is durable and works perfectly in humid spaces like bathrooms.",
                new BigDecimal("2599.99"),
                77,
                69,
                58,
                new BigDecimal("10.5"),
                "This product comes as 2 packages.",
                woodenBenches);
        Product wb3 = new Product("PERJOHAN Wooden bench",
                "This multi-purpose bench works just as well by the table as in the bedroom or hallway. The storage space underneath keeps things like toys or board games handy and the cut-out handle makes it easy to move.",
                new BigDecimal("2199.99"),
                78,
                98,
                72,
                new BigDecimal("8.9"),
                "This product comes as 1 package.",
                woodenBenches);
        Product wb4 = new Product("SKOGSTA Wooden bench",
                "Solid wood is a durable natural material which can be sanded and surface treated when required.",
                new BigDecimal("2799.99"),
                83,
                52,
                45,
                new BigDecimal("9.5"),
                "This product comes as 3 packages.",
                woodenBenches);
        //endregion
        //region Soft benches
        Product sb1 = new Product("NAMMARO Soft bench",
                "Create a comfy living room feel outdoors with NAMMARO series. With everything you need for chill moments, long dinners and cheerful summer parties, no matter if you have a balcony, terrace or garden.",
                new BigDecimal("3999.99"),
                88,
                60,
                72,
                new BigDecimal("14.6"),
                "This product comes as 1 packages.",
                softBenches);
        Product sb2 = new Product("RAGRUND Soft bench",
                "Our RAGRUND bathroom furniture adds warmth to your bathroom and will outlast splashes and showering teens. The natural bamboo material is durable and works perfectly in humid spaces like bathrooms.",
                new BigDecimal("3599.99"),
                77,
                69,
                58,
                new BigDecimal("10.5"),
                "This product comes as 2 packages.",
                softBenches);
        Product sb3 = new Product("PERJOHAN Soft bench",
                "This multi-purpose bench works just as well by the table as in the bedroom or hallway. The storage space underneath keeps things like toys or board games handy and the cut-out handle makes it easy to move.",
                new BigDecimal("3199.99"),
                78,
                98,
                72,
                new BigDecimal("8.9"),
                "This product comes as 1 package.",
                softBenches);
        Product sb4 = new Product("SKOGSTA Soft bench",
                "Solid wood is a durable natural material which can be sanded and surface treated when required.",
                new BigDecimal("3799.99"),
                83,
                52,
                45,
                new BigDecimal("9.5"),
                "This product comes as 3 packages.",
                softBenches);
        //endregion
        //region Garden benches
        Product gb1 = new Product("NAMMARO Garden bench",
                "Create a comfy living room feel outdoors with NAMMARO series. With everything you need for chill moments, long dinners and cheerful summer parties, no matter if you have a balcony, terrace or garden.",
                new BigDecimal("5999.99"),
                88,
                60,
                72,
                new BigDecimal("14.6"),
                "This product comes as 1 packages.",
                gardenBenches);
        Product gb2 = new Product("RAGRUND Garden bench",
                "Our RAGRUND bathroom furniture adds warmth to your bathroom and will outlast splashes and showering teens. The natural bamboo material is durable and works perfectly in humid spaces like bathrooms.",
                new BigDecimal("5599.99"),
                77,
                69,
                58,
                new BigDecimal("10.5"),
                "This product comes as 2 packages.",
                gardenBenches);
        Product gb3 = new Product("PERJOHAN Garden bench",
                "This multi-purpose bench works just as well by the table as in the bedroom or hallway. The storage space underneath keeps things like toys or board games handy and the cut-out handle makes it easy to move.",
                new BigDecimal("4199.99"),
                78,
                98,
                72,
                new BigDecimal("8.9"),
                "This product comes as 1 package.",
                gardenBenches);
        Product gb4 = new Product("SKOGSTA Garden bench",
                "Solid wood is a durable natural material which can be sanded and surface treated when required.",
                new BigDecimal("1799.99"),
                83,
                52,
                45,
                new BigDecimal("9.5"),
                "This product comes as 3 packages.",
                gardenBenches);
        //endregion
        //region Bar benches
        Product bb1 = new Product("NAMMARO Bar bench",
                "Create a comfy living room feel outdoors with NAMMARO series. With everything you need for chill moments, long dinners and cheerful summer parties, no matter if you have a balcony, terrace or garden.",
                new BigDecimal("7999.99"),
                88,
                60,
                72,
                new BigDecimal("14.6"),
                "This product comes as 1 packages.",
                barBenches);
        Product bb2 = new Product("RAGRUND Bar bench",
                "Our RAGRUND bathroom furniture adds warmth to your bathroom and will outlast splashes and showering teens. The natural bamboo material is durable and works perfectly in humid spaces like bathrooms.",
                new BigDecimal("6599.99"),
                77,
                69,
                58,
                new BigDecimal("10.5"),
                "This product comes as 2 packages.",
                barBenches);
        Product bb3 = new Product("PERJOHAN Bar bench",
                "This multi-purpose bench works just as well by the table as in the bedroom or hallway. The storage space underneath keeps things like toys or board games handy and the cut-out handle makes it easy to move.",
                new BigDecimal("6199.99"),
                78,
                98,
                72,
                new BigDecimal("8.9"),
                "This product comes as 1 package.",
                barBenches);
        Product bb4 = new Product("SKOGSTA Bar bench",
                "Solid wood is a durable natural material which can be sanded and surface treated when required.",
                new BigDecimal("9799.99"),
                83,
                52,
                45,
                new BigDecimal("9.5"),
                "This product comes as 3 packages.",
                barBenches);
        //endregion

        //region Cabinets
        Product cb1 = new Product("IVAR Cabinet",
                "These spacious metal cabinets with mesh doors can be hung on a wall, placed on the floor or built into IVAR storage system. Fits just as well in the living room as in the kitchen or hallway.",
                new BigDecimal("8099.99"),
                88,
                60,
                72,
                new BigDecimal("14.6"),
                "This product comes as 1 packages.",
                cabinets);
        Product cb2 = new Product("BESTA Cabinet",
                "Go ahead and put things aside for a while! A sideboard combination gives you plenty of space to store things and a surface to create an attractive display - or to unload serving dishes while you eat.",
                new BigDecimal("12599.99"),
                77,
                69,
                58,
                new BigDecimal("10.5"),
                "This product comes as 2 packages.",
                cabinets);
        Product cb3 = new Product("SEKTION Cabinet",
                "SEKTION kitchen system gives you endless possibilities to design your dream kitchen. Paired with VALLSTENA fronts in white, you get a timeless expression that always feels right.",
                new BigDecimal("5299.99"),
                78,
                98,
                72,
                new BigDecimal("8.9"),
                "This product comes as 1 package.",
                cabinets);
        Product cb4 = new Product("BAGGEBO Cabinet",
                "Stylish design that matches your existing decor or other items in the BAGGEBO storage series. This glass and mesh display cabinet is simplicity at its best and makes eye-catchers of your finest things.",
                new BigDecimal("1999.99"),
                83,
                52,
                45,
                new BigDecimal("9.5"),
                "This product comes as 3 packages.",
                cabinets);
        //endregion
        //region Chests
        Product ch1 = new Product("IVAR Chest",
                "These spacious metal chests with mesh doors can be hung on a wall, placed on the floor or built into IVAR storage system. Fits just as well in the living room as in the kitchen or hallway.",
                new BigDecimal("3099.99"),
                88,
                60,
                72,
                new BigDecimal("14.6"),
                "This product comes as 1 packages.",
                chests);
        Product ch2 = new Product("BESTA Chest",
                "Go ahead and put things aside for a while! A sideboard combination gives you plenty of space to store things and a surface to create an attractive display - or to unload serving dishes while you eat.",
                new BigDecimal("6599.99"),
                77,
                69,
                58,
                new BigDecimal("10.5"),
                "This product comes as 2 packages.",
                chests);
        Product ch3 = new Product("SEKTION Chest",
                "SEKTION kitchen system gives you endless possibilities to design your dream kitchen. Paired with VALLSTENA fronts in white, you get a timeless expression that always feels right.",
                new BigDecimal("999.99"),
                78,
                98,
                72,
                new BigDecimal("8.9"),
                "This product comes as 1 package.",
                chests);
        Product ch4 = new Product("BAGGEBO Chest",
                "Stylish design that matches your existing decor or other items in the BAGGEBO storage series. This glass and mesh display cabinet is simplicity at its best and makes eye-catchers of your finest things.",
                new BigDecimal("2299.99"),
                83,
                52,
                45,
                new BigDecimal("9.5"),
                "This product comes as 3 packages.",
                chests);
        //endregion
        //region Nightstands
        Product ns1 = new Product("IVAR Nightstand",
                "These spacious metal nightstands with mesh doors can be hung on a wall, placed on the floor or built into IVAR storage system. Fits just as well in the living room as in the kitchen or hallway.",
                new BigDecimal("1099.99"),
                88,
                60,
                72,
                new BigDecimal("14.6"),
                "This product comes as 1 packages.",
                nightstands);
        Product ns2 = new Product("BESTA Nightstand",
                "Go ahead and put things aside for a while! A sideboard combination gives you plenty of space to store things and a surface to create an attractive display - or to unload serving dishes while you eat.",
                new BigDecimal("1599.99"),
                77,
                69,
                58,
                new BigDecimal("10.5"),
                "This product comes as 2 packages.",
                nightstands);
        Product ns3 = new Product("SEKTION Nightstand",
                "SEKTION kitchen system gives you endless possibilities to design your dream kitchen. Paired with VALLSTENA fronts in white, you get a timeless expression that always feels right.",
                new BigDecimal("899.99"),
                78,
                98,
                72,
                new BigDecimal("8.9"),
                "This product comes as 1 package.",
                nightstands);
        Product ns4 = new Product("BAGGEBO Nightstand",
                "Stylish design that matches your existing decor or other items in the BAGGEBO storage series. This glass and mesh display cabinet is simplicity at its best and makes eye-catchers of your finest things.",
                new BigDecimal("1299.99"),
                83,
                52,
                45,
                new BigDecimal("9.5"),
                "This product comes as 3 packages.",
                nightstands);
        //endregion
        //region Shelves
        Product sh1 = new Product("IVAR Shelf",
                "These spacious metal shelves with mesh doors can be hung on a wall, placed on the floor or built into IVAR storage system. Fits just as well in the living room as in the kitchen or hallway.",
                new BigDecimal("2099.99"),
                88,
                60,
                72,
                new BigDecimal("14.6"),
                "This product comes as 1 packages.",
                shelves);
        Product sh2 = new Product("BESTA Shelf",
                "Go ahead and put things aside for a while! A sideboard combination gives you plenty of space to store things and a surface to create an attractive display - or to unload serving dishes while you eat.",
                new BigDecimal("2199.99"),
                77,
                69,
                58,
                new BigDecimal("10.5"),
                "This product comes as 2 packages.",
                shelves);
        Product sh3 = new Product("SEKTION Shelf",
                "SEKTION kitchen system gives you endless possibilities to design your dream kitchen. Paired with VALLSTENA fronts in white, you get a timeless expression that always feels right.",
                new BigDecimal("1899.99"),
                78,
                98,
                72,
                new BigDecimal("8.9"),
                "This product comes as 1 package.",
                shelves);
        Product sh4 = new Product("BAGGEBO Shelf",
                "Stylish design that matches your existing decor or other items in the BAGGEBO storage series. This glass and mesh display cabinet is simplicity at its best and makes eye-catchers of your finest things.",
                new BigDecimal("599.99"),
                83,
                52,
                45,
                new BigDecimal("9.5"),
                "This product comes as 3 packages.",
                shelves);
        //endregion

        //region Single beds
        Product sg1 = new Product("NEIDEN Single bed",
                "The natural solid wood is beautiful as it is or you can make it more personal by staining, painting or waxing it. Also, the bed frame is high enough so you can place storage boxes underneath.",
                new BigDecimal("8679.99"),
                88,
                60,
                72,
                new BigDecimal("24.6"),
                "This product comes as 1 packages.",
                singleBeds);
        Product sg2 = new Product("TARVA Single bed",
                "TARVA bed frame is a modern example of Scandinavian furniture tradition – a simple design and untreated wood. A timeless expression mixes nicely with a variety of other styles and furniture.",
                new BigDecimal("4129.99"),
                77,
                69,
                58,
                new BigDecimal("30.5"),
                "This product comes as 2 packages.",
                singleBeds);
        Product sg3 = new Product("SLATTUM Single bed",
                "SLATTUM bed frame has soft upholstery and a padded headboard that complete the stylish and simple lines. Easy to like – and convenient to bring home thanks to the whole frame coming in a single package.",
                new BigDecimal("9199.99"),
                78,
                98,
                72,
                new BigDecimal("45.9"),
                "This product comes as 1 package.",
                singleBeds);
        Product sg4 = new Product("BRIMNES Single bed",
                "A sofa by day and a bed for one – or two – by night. The two large drawers give plenty of space for comforters, pillows and bed linen. A smart solution when you live in a small space.",
                new BigDecimal("8799.99"),
                83,
                52,
                45,
                new BigDecimal("29.5"),
                "This product comes as 3 packages.",
                singleBeds);
        //endregion
        //region Double beds
        Product db1 = new Product("NEIDEN Double bed",
                "The natural solid wood is beautiful as it is or you can make it more personal by staining, painting or waxing it. Also, the bed frame is high enough so you can place storage boxes underneath.",
                new BigDecimal("6679.99"),
                88,
                60,
                72,
                new BigDecimal("22.6"),
                "This product comes as 1 packages.",
                doubleBeds);
        Product db2 = new Product("TARVA Double bed",
                "TARVA bed frame is a modern example of Scandinavian furniture tradition – a simple design and untreated wood. A timeless expression mixes nicely with a variety of other styles and furniture.",
                new BigDecimal("14129.99"),
                77,
                69,
                58,
                new BigDecimal("32.5"),
                "This product comes as 2 packages.",
                doubleBeds);
        Product db3 = new Product("SLATTUM Double bed",
                "SLATTUM bed frame has soft upholstery and a padded headboard that complete the stylish and simple lines. Easy to like – and convenient to bring home thanks to the whole frame coming in a single package.",
                new BigDecimal("19199.99"),
                78,
                98,
                72,
                new BigDecimal("47.9"),
                "This product comes as 1 package.",
                doubleBeds);
        Product db4 = new Product("BRIMNES Double bed",
                "A sofa by day and a bed for one – or two – by night. The two large drawers give plenty of space for comforters, pillows and bed linen. A smart solution when you live in a small space.",
                new BigDecimal("15759.99"),
                83,
                52,
                45,
                new BigDecimal("27.5"),
                "This product comes as 3 packages.",
                doubleBeds);
        //endregion
        //region Folding beds
        Product fb1 = new Product("NEIDEN Folding bed",
                "The natural solid wood is beautiful as it is or you can make it more personal by staining, painting or waxing it. Also, the bed frame is high enough so you can place storage boxes underneath.",
                new BigDecimal("12279.99"),
                88,
                60,
                72,
                new BigDecimal("22.6"),
                "This product comes as 1 packages.",
                foldingBeds);
        Product fb2 = new Product("TARVA Folding bed",
                "TARVA bed frame is a modern example of Scandinavian furniture tradition – a simple design and untreated wood. A timeless expression mixes nicely with a variety of other styles and furniture.",
                new BigDecimal("10929.99"),
                77,
                69,
                58,
                new BigDecimal("32.5"),
                "This product comes as 2 packages.",
                foldingBeds);
        Product fb3 = new Product("SLATTUM Folding bed",
                "SLATTUM bed frame has soft upholstery and a padded headboard that complete the stylish and simple lines. Easy to like – and convenient to bring home thanks to the whole frame coming in a single package.",
                new BigDecimal("8229.99"),
                78,
                98,
                72,
                new BigDecimal("47.9"),
                "This product comes as 1 package.",
                foldingBeds);
        Product fb4 = new Product("BRIMNES Folding bed",
                "A sofa by day and a bed for one – or two – by night. The two large drawers give plenty of space for comforters, pillows and bed linen. A smart solution when you live in a small space.",
                new BigDecimal("16449.99"),
                83,
                52,
                45,
                new BigDecimal("27.5"),
                "This product comes as 3 packages.",
                foldingBeds);
        //endregion
        //region Storage beds
        Product st1 = new Product("NEIDEN Storage bed",
                "The natural solid wood is beautiful as it is or you can make it more personal by staining, painting or waxing it. Also, the bed frame is high enough so you can place storage boxes underneath.",
                new BigDecimal("13279.99"),
                88,
                60,
                72,
                new BigDecimal("22.6"),
                "This product comes as 1 packages.",
                storageBeds);
        Product st2 = new Product("TARVA Storage bed",
                "TARVA bed frame is a modern example of Scandinavian furniture tradition – a simple design and untreated wood. A timeless expression mixes nicely with a variety of other styles and furniture.",
                new BigDecimal("10089.99"),
                77,
                69,
                58,
                new BigDecimal("32.5"),
                "This product comes as 2 packages.",
                storageBeds);
        Product st3 = new Product("SLATTUM Storage bed",
                "SLATTUM bed frame has soft upholstery and a padded headboard that complete the stylish and simple lines. Easy to like – and convenient to bring home thanks to the whole frame coming in a single package.",
                new BigDecimal("8009.99"),
                78,
                98,
                72,
                new BigDecimal("47.9"),
                "This product comes as 1 package.",
                storageBeds);
        Product st4 = new Product("BRIMNES Storage bed",
                "A sofa by day and a bed for one – or two – by night. The two large drawers give plenty of space for comforters, pillows and bed linen. A smart solution when you live in a small space.",
                new BigDecimal("23449.99"),
                83,
                52,
                45,
                new BigDecimal("27.5"),
                "This product comes as 3 packages.",
                storageBeds);
        //endregion

        //region Products list init
        ArrayList<Product> products = new ArrayList<>(Arrays.asList(
                dt1,dt2,dt3,dt4,dt5,dt6,
                wt1,wt2,wt3,wt4,
                ct1,ct2,ct3,ct4,
                mt1,mt2,mt3,mt4,
                ac1,ac2,ac3,ac4,
                sc1,sc2,sc3,sc4,
                cc1,cc2,cc3,cc4,
                bc1,bc2,bc3,bc4,
                wb1,wb2,wb3,wb4,
                sb1,sb2,sb3,sb4,
                gb1,gb2,gb3,gb4,
                bb1,bb2,bb3,bb4,
                cb1,cb2,cb3,cb4,
                ch1,ch2,ch3,ch4,
                ns1,ns2,ns3,ns4,
                sh1,sh2,sh3,sh4,
                sg1,sg2,sg3,sg4,
                db1,db2,db3,db4,
                fb1,fb2,fb3,fb4,
                st1,st2,st3,st4
        ));
        //endregion
        //endregion
        
        //region Product colorIds
        for (Product product : products) {
            Collections.shuffle(colors);
            product.getColors().addAll(colors.subList(0, 3));
        }
        //endregion

        //region Product materialIds
        for (Product product : products) {
            Category productCategory = product.getSubcategory().getCategory();

            product.getMaterials().add(wood);
            if(productCategory == tables
                    || (productCategory == benches && product.getSubcategory() != woodenBenches)
                    || productCategory == cabinetFurniture) {
                product.getMaterials().add(metal);
            }
            if(productCategory == tables) {
                product.getMaterials().add(glass);
            }
        }
        //endregion

        //region Pictures
        List<Picture> pictures = new ArrayList<>();
        //region dt
        pictures.add(generatePictureForProduct(dt1, "dt11.avif"));
        pictures.add(generatePictureForProduct(dt1, "dt12.jpg"));
        pictures.add(generatePictureForProduct(dt1, "dt13.jpg"));

        pictures.add(generatePictureForProduct(dt2, "dt21.jpg"));
        pictures.add(generatePictureForProduct(dt2, "dt22.avif"));
        pictures.add(generatePictureForProduct(dt2, "dt23.avif"));

        pictures.add(generatePictureForProduct(dt3, "dt31.webp"));
        pictures.add(generatePictureForProduct(dt3, "dt32.avif"));
        pictures.add(generatePictureForProduct(dt3, "dt33.avif"));

        pictures.add(generatePictureForProduct(dt4, "dt41.jpg"));
        pictures.add(generatePictureForProduct(dt4, "dt42.jpg"));
        pictures.add(generatePictureForProduct(dt4, "dt43.avif"));

        pictures.add(generatePictureForProduct(dt5, "dt51.avif"));
        pictures.add(generatePictureForProduct(dt5, "dt52.avif"));
        pictures.add(generatePictureForProduct(dt5, "dt53.avif"));

        pictures.add(generatePictureForProduct(dt6, "dt61.avif"));
        pictures.add(generatePictureForProduct(dt6, "dt62.avif"));
        pictures.add(generatePictureForProduct(dt6, "dt63.jpeg"));
        //endregion
        //region wt
        pictures.add(generatePictureForProduct(wt1, "wt11.avif"));
        pictures.add(generatePictureForProduct(wt1, "wt12.avif"));
        pictures.add(generatePictureForProduct(wt1, "wt13.avif"));

        pictures.add(generatePictureForProduct(wt2, "wt21.avif"));
        pictures.add(generatePictureForProduct(wt2, "wt22.avif"));
        pictures.add(generatePictureForProduct(wt2, "wt23.avif"));

        pictures.add(generatePictureForProduct(wt3, "wt31.avif"));
        pictures.add(generatePictureForProduct(wt3, "wt32.avif"));
        pictures.add(generatePictureForProduct(wt3, "wt33.avif"));

        pictures.add(generatePictureForProduct(wt4, "wt41.avif"));
        pictures.add(generatePictureForProduct(wt4, "wt42.avif"));
        pictures.add(generatePictureForProduct(wt4, "wt43.avif"));
        //endregion
        //region ct
        pictures.add(generatePictureForProduct(ct1, "ct11.avif"));
        pictures.add(generatePictureForProduct(ct1, "ct12.avif"));
        pictures.add(generatePictureForProduct(ct1, "ct13.avif"));

        pictures.add(generatePictureForProduct(ct2, "ct21.avif"));
        pictures.add(generatePictureForProduct(ct2, "ct22.webp"));
        pictures.add(generatePictureForProduct(ct2, "ct23.webp"));

        pictures.add(generatePictureForProduct(ct3, "ct31.avif"));
        pictures.add(generatePictureForProduct(ct3, "ct32.avif"));
        pictures.add(generatePictureForProduct(ct3, "ct33.avif"));

        pictures.add(generatePictureForProduct(ct4, "ct41.avif"));
        pictures.add(generatePictureForProduct(ct4, "ct42.avif"));
        pictures.add(generatePictureForProduct(ct4, "ct43.avif"));
        //endregion
        //region mt
        pictures.add(generatePictureForProduct(mt1, "mt11.avif"));
        pictures.add(generatePictureForProduct(mt1, "mt12.avif"));
        pictures.add(generatePictureForProduct(mt1, "mt13.avif"));

        pictures.add(generatePictureForProduct(mt2, "mt21.avif"));
        pictures.add(generatePictureForProduct(mt2, "mt22.jpg"));
        pictures.add(generatePictureForProduct(mt2, "mt23.avif"));

        pictures.add(generatePictureForProduct(mt3, "mt31.avif"));
        pictures.add(generatePictureForProduct(mt3, "mt32.avif"));
        pictures.add(generatePictureForProduct(mt3, "mt33.avif"));

        pictures.add(generatePictureForProduct(mt4, "mt41.avif"));
        pictures.add(generatePictureForProduct(mt4, "mt42.avif"));
        pictures.add(generatePictureForProduct(mt4, "mt43.avif"));
        //endregion

        //region ac
        pictures.add(generatePictureForProduct(ac1, "ac11.avif"));
        pictures.add(generatePictureForProduct(ac1, "ac12.avif"));
        pictures.add(generatePictureForProduct(ac1, "ac13.avif"));

        pictures.add(generatePictureForProduct(ac2, "ac21.avif"));
        pictures.add(generatePictureForProduct(ac2, "ac22.avif"));
        pictures.add(generatePictureForProduct(ac2, "ac23.avif"));

        pictures.add(generatePictureForProduct(ac3, "ac31.avif"));
        pictures.add(generatePictureForProduct(ac3, "ac32.avif"));
        pictures.add(generatePictureForProduct(ac3, "ac33.avif"));

        pictures.add(generatePictureForProduct(ac4, "ac41.webp"));
        pictures.add(generatePictureForProduct(ac4, "ac42.avif"));
        pictures.add(generatePictureForProduct(ac4, "ac43.avif"));
        //endregion
        //region sc
        pictures.add(generatePictureForProduct(sc1, "sc11.avif"));
        pictures.add(generatePictureForProduct(sc1, "sc12.avif"));
        pictures.add(generatePictureForProduct(sc1, "sc13.avif"));

        pictures.add(generatePictureForProduct(sc2, "sc21.avif"));
        pictures.add(generatePictureForProduct(sc2, "sc22.webp"));
        pictures.add(generatePictureForProduct(sc2, "sc23.avif"));

        pictures.add(generatePictureForProduct(sc3, "sc31.avif"));
        pictures.add(generatePictureForProduct(sc3, "sc32.avif"));
        pictures.add(generatePictureForProduct(sc3, "sc33.webp"));

        pictures.add(generatePictureForProduct(sc4, "sc41.avif"));
        pictures.add(generatePictureForProduct(sc4, "sc42.avif"));
        pictures.add(generatePictureForProduct(sc4, "sc43.avif"));
        //endregion
        //region cc
        pictures.add(generatePictureForProduct(cc1, "cc11.avif"));
        pictures.add(generatePictureForProduct(cc1, "cc12.avif"));
        pictures.add(generatePictureForProduct(cc1, "cc13.avif"));

        pictures.add(generatePictureForProduct(cc2, "cc21.avif"));
        pictures.add(generatePictureForProduct(cc2, "cc22.avif"));
        pictures.add(generatePictureForProduct(cc2, "cc23.avif"));

        pictures.add(generatePictureForProduct(cc3, "cc31.avif"));
        pictures.add(generatePictureForProduct(cc3, "cc32.avif"));
        pictures.add(generatePictureForProduct(cc3, "cc33.jpg"));

        pictures.add(generatePictureForProduct(cc4, "cc41.avif"));
        pictures.add(generatePictureForProduct(cc4, "cc42.avif"));
        pictures.add(generatePictureForProduct(cc4, "cc43.avif"));
        //endregion
        //region bc
        pictures.add(generatePictureForProduct(bc1, "bc11.avif"));
        pictures.add(generatePictureForProduct(bc1, "bc12.avif"));
        pictures.add(generatePictureForProduct(bc1, "bc13.avif"));

        pictures.add(generatePictureForProduct(bc2, "bc21.avif"));
        pictures.add(generatePictureForProduct(bc2, "bc22.avif"));
        pictures.add(generatePictureForProduct(bc2, "bc23.avif"));

        pictures.add(generatePictureForProduct(bc3, "bc31.avif"));
        pictures.add(generatePictureForProduct(bc3, "bc32.avif"));
        pictures.add(generatePictureForProduct(bc3, "bc33.avif"));

        pictures.add(generatePictureForProduct(bc4, "bc41.avif"));
        pictures.add(generatePictureForProduct(bc4, "bc42.avif"));
        pictures.add(generatePictureForProduct(bc4, "bc43.avif"));
        //endregion

        //region wb
        pictures.add(generatePictureForProduct(wb1, "wb11.avif"));
        pictures.add(generatePictureForProduct(wb1, "wb12.avif"));
        pictures.add(generatePictureForProduct(wb1, "wb13.avif"));

        pictures.add(generatePictureForProduct(wb2, "wb21.avif"));
        pictures.add(generatePictureForProduct(wb2, "wb22.avif"));

        pictures.add(generatePictureForProduct(wb3, "wb31.avif"));
        pictures.add(generatePictureForProduct(wb3, "wb32.avif"));
        pictures.add(generatePictureForProduct(wb3, "wb33.avif"));

        pictures.add(generatePictureForProduct(wb4, "wb41.avif"));
        pictures.add(generatePictureForProduct(wb4, "wb42.avif"));
        pictures.add(generatePictureForProduct(wb4, "wb43.avif"));
        //endregion
        //region sb
        pictures.add(generatePictureForProduct(sb1, "sb11.avif"));
        pictures.add(generatePictureForProduct(sb1, "sb12.avif"));

        pictures.add(generatePictureForProduct(sb2, "sb21.avif"));
        pictures.add(generatePictureForProduct(sb2, "sb22.webp"));
        pictures.add(generatePictureForProduct(sb2, "sb23.avif"));

        pictures.add(generatePictureForProduct(sb3, "sb31.avif"));
        pictures.add(generatePictureForProduct(sb3, "sb32.avif"));

        pictures.add(generatePictureForProduct(sb4, "sb41.avif"));
        pictures.add(generatePictureForProduct(sb4, "sb42.avif"));
        pictures.add(generatePictureForProduct(sb4, "sb43.webp"));
        //endregion
        //region gb
        pictures.add(generatePictureForProduct(gb1, "gb11.avif"));
        pictures.add(generatePictureForProduct(gb1, "gb12.jpg"));
        pictures.add(generatePictureForProduct(gb1, "gb13.avif"));

        pictures.add(generatePictureForProduct(gb2, "gb21.webp"));
        pictures.add(generatePictureForProduct(gb2, "gb22.avif"));
        pictures.add(generatePictureForProduct(gb2, "gb23.avif"));

        pictures.add(generatePictureForProduct(gb3, "gb31.avif"));
        pictures.add(generatePictureForProduct(gb3, "gb32.avif"));

        pictures.add(generatePictureForProduct(gb4, "gb41.avif"));
        pictures.add(generatePictureForProduct(gb4, "gb42.avif"));
        pictures.add(generatePictureForProduct(gb4, "gb43.avif"));
        //endregion
        //region bb
        pictures.add(generatePictureForProduct(bb1, "bb11.avif"));
        pictures.add(generatePictureForProduct(bb1, "bb12.avif"));
        pictures.add(generatePictureForProduct(bb1, "bb13.avif"));

        pictures.add(generatePictureForProduct(bb2, "bb21.avif"));
        pictures.add(generatePictureForProduct(bb2, "bb22.avif"));
        pictures.add(generatePictureForProduct(bb2, "bb23.avif"));

        pictures.add(generatePictureForProduct(bb3, "bb31.png"));
        pictures.add(generatePictureForProduct(bb3, "bb32.jpg"));
        pictures.add(generatePictureForProduct(bb3, "bb33.jpg"));

        pictures.add(generatePictureForProduct(bb4, "bb41.png"));
        pictures.add(generatePictureForProduct(bb4, "bb42.jpg"));
        pictures.add(generatePictureForProduct(bb4, "bb43.png"));
        //endregion

        //region cb
        pictures.add(generatePictureForProduct(cb1, "cb11.avif"));
        pictures.add(generatePictureForProduct(cb1, "cb12.avif"));
        pictures.add(generatePictureForProduct(cb1, "cb13.avif"));

        pictures.add(generatePictureForProduct(cb2, "cb21.avif"));
        pictures.add(generatePictureForProduct(cb2, "cb22.webp"));
        pictures.add(generatePictureForProduct(cb2, "cb23.avif"));

        pictures.add(generatePictureForProduct(cb3, "cb31.avif"));
        pictures.add(generatePictureForProduct(cb3, "cb32.avif"));
        pictures.add(generatePictureForProduct(cb3, "cb33.avif"));

        pictures.add(generatePictureForProduct(cb4, "cb41.avif"));
        pictures.add(generatePictureForProduct(cb4, "cb42.avif"));
        pictures.add(generatePictureForProduct(cb4, "cb43.avif"));
        //endregion
        //region ch
        pictures.add(generatePictureForProduct(ch1, "ch11.avif"));
        pictures.add(generatePictureForProduct(ch1, "ch12.avif"));
        pictures.add(generatePictureForProduct(ch1, "ch13.avif"));

        pictures.add(generatePictureForProduct(ch2, "ch21.avif"));
        pictures.add(generatePictureForProduct(ch2, "ch22.avif"));
        pictures.add(generatePictureForProduct(ch2, "ch23.avif"));

        pictures.add(generatePictureForProduct(ch3, "ch31.avif"));
        pictures.add(generatePictureForProduct(ch3, "ch32.avif"));
        pictures.add(generatePictureForProduct(ch3, "ch33.avif"));

        pictures.add(generatePictureForProduct(ch4, "ch41.avif"));
        pictures.add(generatePictureForProduct(ch4, "ch42.avif"));
        pictures.add(generatePictureForProduct(ch4, "ch43.avif"));
        //endregion
        //region ns
        pictures.add(generatePictureForProduct(ns1, "ns11.avif"));
        pictures.add(generatePictureForProduct(ns1, "ns12.avif"));
        pictures.add(generatePictureForProduct(ns1, "ns13.avif"));

        pictures.add(generatePictureForProduct(ns2, "ns21.avif"));
        pictures.add(generatePictureForProduct(ns2, "ns22.avif"));
        pictures.add(generatePictureForProduct(ns2, "ns23.avif"));

        pictures.add(generatePictureForProduct(ns3, "ns31.avif"));
        pictures.add(generatePictureForProduct(ns3, "ns32.avif"));
        pictures.add(generatePictureForProduct(ns3, "ns33.avif"));

        pictures.add(generatePictureForProduct(ns4, "ns41.avif"));
        pictures.add(generatePictureForProduct(ns4, "ns42.avif"));
        pictures.add(generatePictureForProduct(ns4, "ns43.avif"));
        //endregion
        //region sh
        pictures.add(generatePictureForProduct(sh1, "sh11.avif"));
        pictures.add(generatePictureForProduct(sh1, "sh12.avif"));
        pictures.add(generatePictureForProduct(sh1, "sh13.avif"));

        pictures.add(generatePictureForProduct(sh2, "sh21.avif"));
        pictures.add(generatePictureForProduct(sh2, "sh22.avif"));
        pictures.add(generatePictureForProduct(sh2, "sh23.avif"));

        pictures.add(generatePictureForProduct(sh3, "sh31.avif"));
        pictures.add(generatePictureForProduct(sh3, "sh32.avif"));
        pictures.add(generatePictureForProduct(sh3, "sh33.avif"));

        pictures.add(generatePictureForProduct(sh4, "sh41.avif"));
        pictures.add(generatePictureForProduct(sh4, "sh42.webp"));
        pictures.add(generatePictureForProduct(sh4, "sh43.avif"));
        //endregion

        //region sg
        pictures.add(generatePictureForProduct(sg1, "sg11.avif"));
        pictures.add(generatePictureForProduct(sg1, "sg12.avif"));
        pictures.add(generatePictureForProduct(sg1, "sg13.avif"));

        pictures.add(generatePictureForProduct(sg2, "sg21.avif"));
        pictures.add(generatePictureForProduct(sg2, "sg22.avif"));
        pictures.add(generatePictureForProduct(sg2, "sg23.avif"));

        pictures.add(generatePictureForProduct(sg3, "sg31.avif"));
        pictures.add(generatePictureForProduct(sg3, "sg32.avif"));
        pictures.add(generatePictureForProduct(sg3, "sg33.avif"));

        pictures.add(generatePictureForProduct(sg4, "sg41.avif"));
        pictures.add(generatePictureForProduct(sg4, "sg42.avif"));
        pictures.add(generatePictureForProduct(sg4, "sg43.avif"));
        //endregion
        //region db
        pictures.add(generatePictureForProduct(db1, "db11.avif"));
        pictures.add(generatePictureForProduct(db1, "db12.avif"));
        pictures.add(generatePictureForProduct(db1, "db13.avif"));

        pictures.add(generatePictureForProduct(db2, "db21.avif"));
        pictures.add(generatePictureForProduct(db2, "db22.avif"));
        pictures.add(generatePictureForProduct(db2, "db23.avif"));

        pictures.add(generatePictureForProduct(db3, "db31.avif"));
        pictures.add(generatePictureForProduct(db3, "db32.avif"));
        pictures.add(generatePictureForProduct(db3, "db33.avif"));

        pictures.add(generatePictureForProduct(db4, "db41.avif"));
        pictures.add(generatePictureForProduct(db4, "db42.avif"));
        pictures.add(generatePictureForProduct(db4, "db43.avif"));
        //endregion
        //region fb
        pictures.add(generatePictureForProduct(fb1, "fb11.avif"));
        pictures.add(generatePictureForProduct(fb1, "fb12.avif"));
        pictures.add(generatePictureForProduct(fb1, "fb13.avif"));

        pictures.add(generatePictureForProduct(fb2, "fb21.avif"));
        pictures.add(generatePictureForProduct(fb2, "fb22.avif"));
        pictures.add(generatePictureForProduct(fb2, "fb23.avif"));

        pictures.add(generatePictureForProduct(fb3, "fb31.avif"));
        pictures.add(generatePictureForProduct(fb3, "fb32.avif"));
        pictures.add(generatePictureForProduct(fb3, "fb33.avif"));

        pictures.add(generatePictureForProduct(fb4, "fb41.avif"));
        pictures.add(generatePictureForProduct(fb4, "fb42.avif"));
        pictures.add(generatePictureForProduct(fb4, "fb43.avif"));
        //endregion
        //region st
        pictures.add(generatePictureForProduct(st1, "st11.avif"));
        pictures.add(generatePictureForProduct(st1, "st12.avif"));
        pictures.add(generatePictureForProduct(st1, "st13.avif"));

        pictures.add(generatePictureForProduct(st2, "st21.avif"));
        pictures.add(generatePictureForProduct(st2, "st22.avif"));
        pictures.add(generatePictureForProduct(st2, "st23.avif"));

        pictures.add(generatePictureForProduct(st3, "st31.avif"));
        pictures.add(generatePictureForProduct(st3, "st32.avif"));
        pictures.add(generatePictureForProduct(st3, "st33.avif"));

        pictures.add(generatePictureForProduct(st4, "st41.avif"));
        pictures.add(generatePictureForProduct(st4, "st42.avif"));
        pictures.add(generatePictureForProduct(st4, "st43.avif"));

        setCategoryPicture(tables, "tables.png");
        setCategoryPicture(chairs, "chairs.png");
        setCategoryPicture(benches, "benches.jpg");
        setCategoryPicture(cabinetFurniture, "cabinetFurniture.png");
        setCategoryPicture(beds, "beds.png");
        //endregion
        //endregion

        //region Orders
        Order ord1 = new Order(vlad);
        ord1.getOrderProducts().add(new OrderProduct(4, ord1, dt1, getRandomColor(dt1), getRandomMaterial(dt1)));
        ord1.getOrderProducts().add(new OrderProduct(1, ord1, dt6, getRandomColor(dt6), getRandomMaterial(dt6)));
        ord1.getOrderProducts().add(new OrderProduct(3, ord1, bc4, getRandomColor(bc4), getRandomMaterial(bc4)));
        ord1.getOrderProducts().add(new OrderProduct(1, ord1, fb3, getRandomColor(fb3), getRandomMaterial(fb3)));
        ord1.calculatePrice();

        Order ord2 = new Order(vlad);
        ord2.getOrderProducts().add(new OrderProduct(1, ord2, sh4, getRandomColor(sh4), getRandomMaterial(sh4)));
        ord2.getOrderProducts().add(new OrderProduct(2, ord2, bc2, getRandomColor(bc2), getRandomMaterial(bc2)));
        ord2.getOrderProducts().add(new OrderProduct(1, ord2, ct3, getRandomColor(ct3), getRandomMaterial(ct3)));
        ord2.getOrderProducts().add(new OrderProduct(4, ord2, sc4, getRandomColor(sc4), getRandomMaterial(sc4)));
        ord2.calculatePrice();

        List<Order> orders = new ArrayList<>(List.of(ord1, ord2));

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (Order order : orders) {
            orderProducts.addAll(order.getOrderProducts());
        }
        //endregion

        //region Persisting
        categoryRepo.saveAll(categories);
        subcategoryRepo.saveAll(subcategories);
        userRepo.save(vlad);
        colorRepo.saveAll(colors);
        materialRepo.saveAll(materials);
        productRepo.saveAll(products);
        pictureRepo.saveAll(pictures);
        orderRepo.saveAll(orders);
        orderProductRepo.saveAll(orderProducts);
        //endregion
    }

    private Picture generatePictureForProduct(Product product, String imageName) {
        String pictureDir = "src/main/resources/static/images";
        try {
            byte[] imageByteArray = Files.readAllBytes(Path.of(pictureDir, imageName));
            return new Picture(imageByteArray, product);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setCategoryPicture(Category category, String imageName) {
        String pictureDir = "src/main/resources/static/images";
        try {
            byte[] imageByteArray = Files.readAllBytes(Path.of(pictureDir, imageName));
            category.setPicture(imageByteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Color getRandomColor(Product product) {
        List<Color> colors = new ArrayList<>(product.getColors());
        int colorIndex = (int) (Math.random() * colors.size());
        return colors.get(colorIndex);
    }

    private Material getRandomMaterial(Product product) {
        List<Material> materials = new ArrayList<>(product.getMaterials());
        int colorIndex = (int) (Math.random() * materials.size());
        return materials.get(colorIndex);
    }
}
