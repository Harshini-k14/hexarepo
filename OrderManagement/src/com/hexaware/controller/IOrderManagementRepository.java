package com.hexaware.controller;
import java.util.List;

import com.hexaware.exception.AdminUserNotFoundException;
import com.hexaware.exception.OrderNotFoundException;
import com.hexaware.exception.UserNotFoundException;
import com.hexaware.model.*;

public interface IOrderManagementRepository {
	
	
	 void createOrder(User user, List<Product> products) throws UserNotFoundException;
	    void cancelOrder(int userId, int orderId) throws UserNotFoundException, OrderNotFoundException;
	    void createProduct(User user, Product product) throws UserNotFoundException, AdminUserNotFoundException;
	    void createUser(User user);
	    List<Product> getAllProducts();
	    List<Product> getOrderByUser(User user) throws UserNotFoundException;


}
