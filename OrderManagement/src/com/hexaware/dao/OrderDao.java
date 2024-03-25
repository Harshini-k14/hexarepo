package com.hexaware.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.hexaware.controller.*;
import com.hexaware.dao.*;
import com.hexaware.model.*;

public class OrderDao {
    private Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public boolean isUserExists(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE userId = ?");
            statement.setInt(1, user.getUserId());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUserExists(int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE userId = ?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isOrderExists(int orderId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE orderId = ?");
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAdminUserExists(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE userId = ? AND role = 'admin'");
            statement.setInt(1, user.getUserId());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (userId, username, password, role) VALUES (?, ?, ?, ?)");
            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createOrder(User user, List<Product> products) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO orders (userId) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, user.getUserId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            int orderId = -1;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            }

            for (Product product : products) {
                PreparedStatement orderDetailsStatement = connection.prepareStatement("INSERT INTO order_details (orderId, productId) VALUES (?, ?)");
                orderDetailsStatement.setInt(1, orderId);
                orderDetailsStatement.setInt(2, product.getProductId());
                orderDetailsStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelOrder(int userId, int orderId) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE orderId = ? AND userId = ?");
            statement.setInt(1, orderId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createProduct(Product product) {
        try {
            PreparedStatement statement = null; 
            
            if (product instanceof Electronics) {
                Electronics electronicProduct = (Electronics) product;
                statement = connection.prepareStatement("INSERT INTO electronics (productId, brand, warrantyPeriod) VALUES (?, ?, ?)");
                statement.setInt(1, electronicProduct.getProductId());
                statement.setString(2, electronicProduct.getBrand());
                statement.setInt(3, electronicProduct.getWarrantyPeriod());
            } else if (product instanceof Clothing) {
                Clothing clothingProduct = (Clothing) product;
                statement = connection.prepareStatement("INSERT INTO clothing (productId, size, color) VALUES (?, ?, ?)");
                statement.setInt(1, clothingProduct.getProductId());
                statement.setString(2, clothingProduct.getSize());
                statement.setString(3, clothingProduct.getColor());
            } else {
                statement = connection.prepareStatement("INSERT INTO products (productId, productName, description, price, quantityInStock, type) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setInt(1, product.getProductId());
                statement.setString(2, product.getProductName());
                statement.setString(3, product.getDescription());
                statement.setDouble(4, product.getPrice());
                statement.setInt(5, product.getQuantityInStock());
                statement.setString(6, product.getType());
            }

            // Execute the statement
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





  /*  public void createProduct(Product product) {
        // Insert the product into the database
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO products (productId, productName, description, price, quantityInStock, type) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, product.getProductId());
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getDescription());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getQuantityInStock());
            statement.setString(6, product.getType());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public List<Product> getAllProducts() {
        
        List<Product> productList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("productId"),
                        resultSet.getString("productName"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantityInStock"),
                        resultSet.getString("type")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getOrderByUser(User user) {
        List<Product> productList = new ArrayList<>();
        try {
        	String query = "SELECT p.* FROM products p JOIN order_details od ON p.productId = od.productId JOIN orders o ON od.orderId = o.orderId WHERE o.userId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getUserId());
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(
                    resultSet.getInt("productId"),
                    resultSet.getString("productName"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("quantityInStock"),
                    resultSet.getString("type")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
        return productList;
    }




}
