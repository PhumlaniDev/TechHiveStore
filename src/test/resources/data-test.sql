-- Test data for Address table
INSERT INTO Address (address_id, street_name, city, province, zip_code, country)
VALUES
    (1, 'Main Street', 'Cape Town', 'Western Cape', '8000', 'South Africa'),
    (2, 'Highway Ave', 'Johannesburg', 'Gauteng', '2000', 'South Africa'),
    (3, 'Beach Road', 'Durban', 'KwaZulu-Natal', '4001', 'South Africa');

-- Test data for Users table
INSERT INTO Users (user_id, username, password, email, first_name, last_name, phone_number, role, address_id)
VALUES
    (1, 'johndoe', 'password123', 'johndoe@example.com', 'John', 'Doe', '0812345678', 'user', 1),
    (2, 'janedoe', 'securepassword', 'janedoe@example.com', 'Jane', 'Doe', '0823456789', 'admin', 2),
    (3, 'alexsmith', 'hashedpassword', 'alexsmith@example.com', 'Alex', 'Smith', '0834567890', 'user', 3);

-- Test data for Category table
INSERT INTO Category (category_id, category_name, description)
VALUES
    (1, 'Electronics', 'Devices and gadgets like phones, laptops, and accessories.'),
    (2, 'Books', 'Fiction, non-fiction, educational materials, and more.'),
    (3, 'Clothing', 'Apparel and accessories for men, women, and children.');

-- Test data for Product table
INSERT INTO Product (product_id, name, description, price, category_id)
VALUES
    (1, 'Smartphone', 'Latest model smartphone with advanced features.', 8999.99, 1),
    (2, 'Laptop', 'Lightweight laptop suitable for work and gaming.', 15999.99, 1),
    (3, 'Novel', 'Bestselling fiction book.', 199.99, 2),
    (4, 'T-shirt', 'Cotton t-shirt in various sizes.', 99.99, 3);

-- Test Data for CartItem table
INSERT INTO Cart (cart_id, user_id, created_at, updated_at)
VALUES
    (1, 1, 2025-01-24 14:38:22, 2025-01-24 14:38:22), -- Cart for User 1
    (2, 2, 2025-01-24 14:38:22, 2025-01-24 14:38:22); -- Cart for User 2

-- Test Data for CartItem table
INSERT INTO CartItem (cart_item_id, cart_id, product_id, quantity, created_at, updated_at)
VALUES
    (1, 1, 1, 2, 2025-01-24 14:38:22, 2025-01-24 14:38:22), -- User 1's cart has 2 units of Product 1
    (2, 1, 2, 1, 2025-01-24 14:38:22, 2025-01-24 14:38:22), -- User 1's cart has 1 unit of Product 2
    (3, 2, 3, 3, 2025-01-24 14:38:22, 2025-01-24 14:38:22); -- User 2's cart has 3 units of Product 3

-- Test Data for Orders
INSERT INTO Orders (order_id, user_id, address_id, total_price, status, created_at, updated_at)
VALUES
    (1, 1, 1, 799.98, 'Completed', 2025-01-24 14:38:22, 2025-01-24 14:38:22), -- Order for User 1
    (2, 2, 2, 1499.97, 'Pending', 2025-01-24 14:38:22, 2025-01-24 14:38:22); -- Order for User 2

-- Test Data for OrderItem
INSERT INTO OrderItem (order_item_id, order_id, product_id, quantity, price, created_at, updated_at)
VALUES
    (1, 1, 1, 2, 399.99, 2025-01-24 14:38:22, 2025-01-24 14:38:22), -- User 1 ordered 2 units of Product 1
    (2, 1, 2, 1, 399.99, 2025-01-24 14:38:22, 2025-01-24 14:38:22), -- User 1 ordered 1 unit of Product 2
    (3, 2, 3, 3, 499.99, 2025-01-24 14:38:22, 2025-01-24 14:38:22); -- User 2 ordered 3 units of Product 3
