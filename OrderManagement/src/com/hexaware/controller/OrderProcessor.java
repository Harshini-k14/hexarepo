package com.hexaware.controller;
import com.hexaware.dao.*;
import com.hexaware.model.*;

import java.sql.SQLException;
import java.util.*;
import com.hexaware.exception.*;

public  class OrderProcessor implements IOrderManagementRepository {
	 private OrderDao orderDao;

	    public OrderProcessor(OrderDao orderDao) {
	        this.orderDao = orderDao;
	    }

	    @Override
	    public void createOrder(User user, List<Product> products) throws UserNotFoundException {
	        if (!orderDao.isUserExists(user)) {
	            createUser(user);
	        }
	        orderDao.createOrder(user, products);
	    }

	    @Override
	    public void cancelOrder(int userId, int orderId) throws UserNotFoundException, OrderNotFoundException {
	        if (!orderDao.isUserExists(userId)) {
			    throw new UserNotFoundException("User with ID " + userId + " not found.");
			}
			if (!orderDao.isOrderExists(orderId)) {
			    throw new OrderNotFoundException("Order with ID " + orderId + " not found.");
			}
			orderDao.cancelOrder(userId, orderId);
	    }


	  /*  @Override
	    public void createProduct(User user, Product product) throws AdminUserNotFoundException {
	        if (!orderDao.isAdminUserExists(user)) {
	            throw new AdminUserNotFoundException("Admin user not found.");
	        }
	        orderDao.createProduct(product);
	    }*/
	    
	    
	    
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

	        Product product;
	        if (type.equalsIgnoreCase("Electronics")) {
	            System.out.print("Enter brand: ");
	            String brand = scanner.nextLine();
	            System.out.print("Enter warrantyPeriod: ");
	            int warrantyPeriod = scanner.nextInt();
	            scanner.nextLine(); 
	            product = new Electronics(productId, productName, description, price, quantityInStock, brand, warrantyPeriod);
	        } else if (type.equalsIgnoreCase("Clothing")) {
	            System.out.print("Enter size: ");
	            String size = scanner.nextLine();
	            System.out.print("Enter color: ");
	            String color = scanner.nextLine();
	            product = new Clothing(productId, productName, description, price, quantityInStock, size, color);
	        } else {
	            product = new Product(productId, productName, description, price, quantityInStock, type);
	        }

	        User adminUser = new User(adminUserId, "", "", "Admin"); 
	        try {
	            orderProcessor.createProduct(adminUser, product);
	        } catch (AdminUserNotFoundException e) {
	            e.printStackTrace();
	        }
	        System.out.println("Product created successfully.");
	    }


	    @Override
	    public void createUser(User user) {
	        orderDao.createUser(user);
	    }

	    @Override
	    public List<Product> getAllProducts() {
	        return orderDao.getAllProducts();
	    }

	    @Override
	    public List<Product> getOrderByUser(User user) {
	        return orderDao.getOrderByUser(user);
}

		@Override
		public void createProduct(User user, Product product) throws UserNotFoundException, AdminUserNotFoundException {
			// TODO Auto-generated method stub
			
		}
}