create database ordermanagement;
use ordermanagement;

drop table Products;
drop table Electronics;
drop table Clothing;
CREATE TABLE Products (
    productId INT PRIMARY KEY,
    productName VARCHAR(255),
    description TEXT,
    price DECIMAL(10, 2),
    quantityInStock INT,
    type VARCHAR(50) CHECK (type IN ('Electronics', 'Clothing'))
);


CREATE TABLE Electronics (
    productId INT PRIMARY KEY,
    brand VARCHAR(255),
    warrantyPeriod INT,
    FOREIGN KEY (productId) REFERENCES Products(productId)
);


CREATE TABLE Clothing (
    productId INT PRIMARY KEY,
    size VARCHAR(50),
    color VARCHAR(50),
    FOREIGN KEY (productId) REFERENCES Products(productId)
);


CREATE TABLE Users (
    userId INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(50) CHECK (role IN ('Admin', 'User')),
    isAdmin BOOLEAN
   
);
drop table Orders;
CREATE TABLE Orders (
    orderId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT,
    orderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES Users(userId)
);
CREATE TABLE order_details (
    order_detail_id INT PRIMARY KEY AUTO_INCREMENT,
    orderId INT,
    productId INT,
    FOREIGN KEY (orderId) REFERENCES Orders(orderId),  
    FOREIGN KEY (productId) REFERENCES Products(productId)
);

