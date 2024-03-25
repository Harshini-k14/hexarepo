package com.hexaware.model;

public class Clothing extends Product {
    private String size;
    private String color;

    // Constructors
    public Clothing() {
        super(); 
    }

    public Clothing(int productId, String productName, String description, double price, int quantityInStock, String type, String size, String color) {
        super(productId, productName, description, price, quantityInStock, type); // Call to superclass constructor
        this.size = size;
        this.color = color;
    }

    public Clothing(int productId, String productName, String description, double price, int quantityInStock,
			String size2, String color2) {
		// TODO Auto-generated constructor stub
    	 this.size = size2;
         this.color = color2;
	}

	// Getters
    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    // Setters
    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    @Override
    public String toString() {
        return "Clothing{" +
                "productId=" + getProductId() +
                ", productName='" + getProductName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", price=" + getPrice() +
                ", quantityInStock=" + getQuantityInStock() +
                ", type='" + getType() + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}

