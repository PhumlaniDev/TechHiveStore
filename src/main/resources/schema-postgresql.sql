-- Create database (if required, uncomment and configure appropriately)
--  CREATE DATABASE IF NOT EXISTS tech_hive_db
--  WITH OWNER = macgyver
--  ENCODING = 'UTF8'
--  CONNECTION LIMIT = -1;
--
--  -- Use the newly created or existing database
--  USE tech_hive_db;

CREATE TABLE IF NOT EXISTS Address (
    address_id AUTO_INCREMENT PRIMARY KEY,
    street_name VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    province VARCHAR(100),
    zip_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Users (
    user_id AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone_number VARCHAR(20),
    role VARCHAR(20),
    address_id INT,
    FOREIGN KEY (address_id) REFERENCES Address (address_id)
    );

CREATE TABLE IF NOT EXISTS Category (
    category_id AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
    );

CREATE TABLE IF NOT EXISTS Product (
    product_id AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    category_id INT,
    imageURL VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES Category (category_id)
    );

CREATE TABLE IF NOT EXISTS Cart (
    cart_id AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    total_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id)
    );

CREATE TABLE IF NOT EXISTS CartItem (
    cart_item_id AUTO_INCREMENT PRIMARY KEY,
    cart_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES Cart (cart_id),
    FOREIGN KEY (product_id) REFERENCES Product (product_id)
    );

CREATE TABLE IF NOT EXISTS Orders (
    order_id AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    user_id INT,
    address_id BIGINT,
    order_created_date TIMESTAMP NOT NULL,
    order_status VARCHAR(50) NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id)
    FOREIGN KEY (address_id) REFERENCES address(address_id);
    );

CREATE TABLE IF NOT EXISTS OrderItem (
    order_item_id AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders (order_id),
    FOREIGN KEY (product_id) REFERENCES Product (product_id)
    );

--  -- Grant all privileges to the user 'phumlanidev'
--  GRANT ALL PRIVILEGES ON tech_hive_db.* TO 'macgyver'@'localhost';
--
--  -- Make sure privileges are reloaded
--  FLUSH PRIVILEGES;