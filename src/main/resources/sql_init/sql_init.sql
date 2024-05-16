CREATE TABLE IF NOT EXISTS category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL,
    description VARCHAR(150) NOT NULL,
    picture LONGBLOB NOT NULL
);

CREATE TABLE IF NOT EXISTS subcategory (
    subcategory_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    name VARCHAR(20) UNIQUE NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);

CREATE TABLE IF NOT EXISTS product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    subcategory_id INT NOT NULL,
    name VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(500) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    height INT NOT NULL,
    length INT NOT NULL,
    width INT NOT NULL,
    weight DECIMAL(10,2) NOT NULL,
    package_description VARCHAR(300) NOT NULL,
    FOREIGN KEY (subcategory_id) REFERENCES subcategory(subcategory_id)
);

CREATE TABLE IF NOT EXISTS picture (
    picture_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    picture LONGBLOB NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE IF NOT EXISTS user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(16) NOT NULL,
    surname VARCHAR(16) NOT NULL,
    fathername VARCHAR(16),
    email VARCHAR(50) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL UNIQUE,
    birthdate DATE,
    password VARCHAR(72) NOT NULL,
    role ENUM('ADMIN', 'USER') NOT NULL DEFAULT 'USER'
);

CREATE TABLE IF NOT EXISTS orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    price DECIMAL(10,2) NOT NULL,
    status ENUM('COMPLETED', 'PENDING', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE IF NOT EXISTS color (
    color_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    hex_code VARCHAR(6) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS material (
    material_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS product_color (
    product_id INT,
    color_id INT,
    PRIMARY KEY (product_id, color_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    FOREIGN KEY (color_id) REFERENCES color(color_id)
);

CREATE TABLE IF NOT EXISTS product_material (
    product_id INT,
    material_id INT,
    PRIMARY KEY (product_id, material_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    FOREIGN KEY (material_id) REFERENCES material(material_id)
);

CREATE TABLE IF NOT EXISTS order_product (
    order_id INT,
    product_id INT,
    amount INT NOT NULL,
    color_id INT NOT NULL,
    material_id INT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    FOREIGN KEY (color_id) REFERENCES color(color_id),
    FOREIGN KEY (material_id) REFERENCES material(material_id)
);
