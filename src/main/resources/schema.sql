CREATE TABLE IF NOT EXISTS Address
(
    addressId     SERIAL PRIMARY KEY,
    streetAddress VARCHAR(255) NOT NULL,
    city          VARCHAR(100) NOT NULL,
    province      VARCHAR(100),
    zipCode       VARCHAR(20)  NOT NULL,
    country       VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Users
(
    userId      SERIAL PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL,
    password    VARCHAR(255) NOT NULL,
    email       VARCHAR(100) NOT NULL,
    firstName   VARCHAR(50),
    lastName    VARCHAR(50),
    phoneNumber VARCHAR(20),
    addressId   INT,
    FOREIGN KEY (addressId) REFERENCES Address (addressId)
    );

ALTER TABLE Address
    ADD COLUMN userId INT,
    ADD CONSTRAINT fk_user FOREIGN KEY (userId) REFERENCES Users (userId);

CREATE TABLE IF NOT EXISTS Category
(
    categoryId   SERIAL PRIMARY KEY,
    categoryName VARCHAR(100) NOT NULL,
    description  TEXT
    );

CREATE TABLE IF NOT EXISTS Product
(
    productId   SERIAL PRIMARY KEY,
    name        VARCHAR(100)   NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2) NOT NULL,
    quantity    INT            NOT NULL,
    categoryId  INT,
    imageURL    VARCHAR(255),
    FOREIGN KEY (categoryId) REFERENCES Category (categoryId)
    );

CREATE TABLE IF NOT EXISTS Cart
(
    cartId     SERIAL PRIMARY KEY,
    userId     INT,
    totalPrice DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (userId) REFERENCES Users (userId)
    );

CREATE TABLE IF NOT EXISTS CartItem
(
    cartItemId SERIAL PRIMARY KEY,
    cartId     INT,
    productId  INT,
    quantity   INT            NOT NULL,
    price      DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cartId) REFERENCES Cart (cartId),
    FOREIGN KEY (productId) REFERENCES Product (productId)
    );

CREATE TABLE IF NOT EXISTS Orders
(
    orderId          SERIAL PRIMARY KEY,
    orderNumber      VARCHAR(50)    NOT NULL,
    userId           INT,
    orderCreatedDate TIMESTAMP      NOT NULL,
    orderStatus      VARCHAR(50)    NOT NULL,
    totalPrice       DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (userId) REFERENCES Users (userId)
    );

CREATE TABLE IF NOT EXISTS OrderItem
(
    orderItemId SERIAL PRIMARY KEY,
    orderId     INT,
    productId   INT,
    quantity    INT            NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (orderId) REFERENCES Orders (orderId),
    FOREIGN KEY (productId) REFERENCES Product (productId)
    );


CREATE TABLE IF NOT EXISTS Category
(
    categoryId   SERIAL PRIMARY KEY,
    categoryName VARCHAR(100) NOT NULL,
    description  TEXT
    );

CREATE TABLE IF NOT EXISTS Product
(
    productId   SERIAL PRIMARY KEY,
    name        VARCHAR(100)   NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2) NOT NULL,
    quantity    INT            NOT NULL,
    categoryId  INT,
    imageURL    VARCHAR(255),
    FOREIGN KEY (categoryId) REFERENCES Category (categoryId)
    );

CREATE TABLE IF NOT EXISTS Cart
(
    cartId     SERIAL PRIMARY KEY,
    userId     INT,
    totalPrice DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (userId) REFERENCES Users (userId)
    );

CREATE TABLE IF NOT EXISTS CartItem
(
    cartItemId SERIAL PRIMARY KEY,
    cartId     INT,
    productId  INT,
    quantity   INT            NOT NULL,
    price      DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cartId) REFERENCES Cart (cartId),
    FOREIGN KEY (productId) REFERENCES Product (productId)
    );

CREATE TABLE IF NOT EXISTS Orders
(
    orderId          SERIAL PRIMARY KEY,
    orderNumber      VARCHAR(50)    NOT NULL,
    userId           INT,
    orderCreatedDate TIMESTAMP      NOT NULL,
    orderStatus      VARCHAR(50)    NOT NULL,
    totalPrice       DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (userId) REFERENCES Users (userId)
    );

CREATE TABLE IF NOT EXISTS OrderItem
(
    orderItemId SERIAL PRIMARY KEY,
    orderId     INT,
    productId   INT,
    quantity    INT            NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (orderId) REFERENCES Orders (orderId),
    FOREIGN KEY (productId) REFERENCES Product (productId)
    );