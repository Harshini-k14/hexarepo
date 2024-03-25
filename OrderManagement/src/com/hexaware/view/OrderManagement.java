package com.hexaware.view;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.hexaware.controller.*;
import com.hexaware.dao.*;
import com.hexaware.exception.AdminUserNotFoundException;
import com.hexaware.exception.OrderNotFoundException;
import com.hexaware.exception.UserNotFoundException;
import com.hexaware.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderManagement {
    public static void main(String[] args) throws UserNotFoundException {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ordermanagement", "root", "1423");
            OrderDao orderDao = new OrderDao(connection);
            OrderProcessor orderProcessor = new OrderProcessor(orderDao);
            Scanner scanner = new Scanner(System.in);

            boolean running = true;
            while (running) {
                System.out.println("1. Create User");
                System.out.println("2. Create Product");
                System.out.println("3. Create Order");
                System.out.println("4. Cancel Order");
                System.out.println("5. Get All Products");
                System.out.println("6. Get Order by User");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        createUser(orderProcessor, scanner);
                        break;
                    case 2:
                        createProduct(orderProcessor, scanner);
                        break;
                    case 3:
					try {
						createOrder(orderProcessor, scanner);
					} catch (UserNotFoundException e) {
						
						e.printStackTrace();
					}
                        break;
                    case 4:
					try {
						cancelOrder(orderProcessor, scanner);
					} catch (UserNotFoundException e) {
						e.printStackTrace();
					} catch (OrderNotFoundException e) {
						e.printStackTrace();
					}
                        break;
                    case 5:
                        getAllProducts(orderProcessor);
                        break;
                    case 6:
                        getOrderbyUser(orderProcessor, scanner);
                        break;
                    case 7:
                        running = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            }
            scanner.close();
            connection.close(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createUser(OrderProcessor orderProcessor, Scanner scanner) {
        System.out.print("Enter userId: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (Admin/User): ");
        String role = scanner.nextLine();

        User user = new User(userId, username, password, role);
        orderProcessor.createUser(user);
        System.out.println("User created successfully.");
    }

    private static void createProduct(OrderProcessor orderProcessor, Scanner scanner) throws UserNotFoundException {
        System.out.print("Enter admin userId: ");
        int adminUserId = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter productId: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter productName: ");
        String productName = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); 
        System.out.print("Enter quantityInStock: ");
        int quantityInStock = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter type (Electronics/Clothing): ");
        String type = scanner.nextLine();

        if (type.equalsIgnoreCase("Electronics")) {
            System.out.print("Enter brand: ");
            String brand = scanner.nextLine();
            System.out.print("Enter warranty period: ");
            int warrantyPeriod = scanner.nextInt();
            scanner.nextLine(); 
            
           
            Electronics electronicsProduct = new Electronics(productId, productName, description, price, quantityInStock, type, brand, warrantyPeriod);
            
            
            User adminUser = new User(adminUserId, "", "", "Admin"); 
            
            try {
                orderProcessor.createProduct(adminUser, electronicsProduct);
                System.out.println("Electronics product created successfully.");
            } catch (AdminUserNotFoundException e) {
                e.printStackTrace();
            }
        } else if (type.equalsIgnoreCase("Clothing")) {
            System.out.print("Enter size: ");
            String size = scanner.nextLine();
            System.out.print("Enter color: ");
            String color = scanner.nextLine();
            
            Clothing clothingProduct = new Clothing(productId, productName, description, price, quantityInStock, type, size, color);
            
            User adminUser = new User(adminUserId, "", "", "Admin"); 
            
            try {
                orderProcessor.createProduct(adminUser, clothingProduct);
                System.out.println("Clothing product created successfully.");
            } catch (AdminUserNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid product type. Product not created.");
        }
    }


    private static void createOrder(OrderProcessor orderProcessor, Scanner scanner) throws UserNotFoundException {
        System.out.print("Enter userId: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); 

        List<Product> products = new ArrayList<>();
        boolean addingProducts = true;
        while (addingProducts) {
            System.out.print("Enter productId: ");
            int productId = scanner.nextInt();
            scanner.nextLine();             
            System.out.print("Add more products? (yes/no): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("yes")) {
                addingProducts = false;
            }
        }

        User user = new User(userId, "", "", ""); 
        orderProcessor.createOrder(user, products);
        System.out.println("Order created successfully.");
    }

    private static void cancelOrder(OrderProcessor orderProcessor, Scanner scanner) throws UserNotFoundException, OrderNotFoundException {
        System.out.print("Enter userId: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter orderId: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); 

        orderProcessor.cancelOrder(userId, orderId);
        System.out.println("Order cancelled successfully.");
    }

    private static void getAllProducts(OrderProcessor orderProcessor) {
        List<Product> productList = orderProcessor.getAllProducts();
        System.out.println("All Products:");
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    private static void getOrderbyUser(OrderProcessor orderProcessor, Scanner scanner) {
        System.out.print("Enter userId: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); 

        User user = new User(userId, "", "", ""); 
        List<Product> productList = orderProcessor.getOrderByUser(user);
        System.out.println("Products ordered by user:");
        for (Product product : productList) {
            System.out.println(product);
        }
    }
}







