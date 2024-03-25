package com.hexaware.model;

public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    // Constructors
    public Electronics() {
        super(); 
    }

    public Electronics(int productId, String productName, String description, double price, int quantityInStock, String type, String brand, int warrantyPeriod) {
        super(productId, productName, description, price, quantityInStock, type); // Call to superclass constructor
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public Electronics(int productId, String productName, String description, double price, int quantityInStock,
			String brand2, int warrantyPeriod2) {
		// TODO Auto-generated constructor stub
    	 this.brand = brand2;
         this.warrantyPeriod = warrantyPeriod2;
	}

	// Getters
    public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    // Setters
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
    @Override
    public String toString() {
        return "Electronics{" +
                "productId=" + getProductId() +
                ", productName='" + getProductName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", price=" + getPrice() +
                ", quantityInStock=" + getQuantityInStock() +
                ", type='" + getType() + '\'' +
                ", brand='" + brand + '\'' +
                ", warrantyPeriod=" + warrantyPeriod +
                '}';
    }
}

