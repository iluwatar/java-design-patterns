package com.iluwatar.optimisticconcurrency;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * An entity class.
 */
@Entity
@Table(name = "products")
public class Product {
    /**
     * ID of a product.
     * This is the primary key in the Product table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    /**
     * Version of a product.
     * This is used for optimistic concurrency pattern.
     */
    private int version;

    /**
     * The name of a product.
     */
    private String name;

    /**
     * The description of a product.
     */
    private String description;

    /**
     * The price of a product.
     */

    private double price;

    /**
     * The amount that is in stock of a product.
     */
    private int amountInStock;

    /**
     * An empty constructor.
     */
    public Product() {

    }

    /**
     * Construct a Product instance with specified info.
     * @param productName The product's name.
     *             It should not be blank.
     * @param desc The product's description.
     *                    It should not be blank.
     * @param productPrice The product's price.
     *              It should not be negative.
     * @param amount The product's amount in stock.
     *                      It should not be negative.
     */
    public Product(final String productName, final String desc,
                   final double productPrice, final int amount) {
        setName(productName);
        setDescription(desc);
        setPrice(productPrice);
        setAmountInStock(amount);
    }

    /**
     * Construct a new Product instance by clone from another instance.
     * @param other The product to clone from.
     */
    public Product(final Product other) {
        setId(other.getId());
        setVersion(other.getVersion());
        setName(other.getName());
        setDescription(other.getDescription());
        setPrice(other.getPrice());
        setAmountInStock(other.getAmountInStock());
    }

    /**
     * Get the id of the product.
     * @return id of the product.
     */
    public long getId() {
        return id;
    }

    /**
     * Set id of the product.
     * @param productId to set for the product.
     */
    public void setId(final long productId) {
        this.id = productId;
    }

    /**
     * Get the version of the product.
     * @return version of the product.
     */
    public int getVersion() {
        return version;
    }

    /**
     * Set the version of the product.
     * @param productVersion to set for the product
     */
    public void setVersion(final int productVersion) {
        this.version = productVersion;
    }

    /**
     * Get the name of the product.
     * @return name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the product.
     * @param productName name to set for the product. It should not be blank.
     */
    public void setName(final String productName) {
        if (productName.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        this.name = productName;
    }

    /**
     * Get the description of the product.
     * @return product's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the product's description.
     * @param desc description to set for the product. It should not be blank.
     */
    public void setDescription(final String desc) {
        if (desc.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        this.description = desc;
    }

    /**
     * Get the price of the product.
     * @return product's price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the product's price.
     * @param productPrice price to set for the product.
     *                     It should not be negative.
     */
    public void setPrice(final double productPrice) {
        if (productPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = productPrice;
    }

    /**
     * Get the amount of product in stock.
     * @return amountInStock.
     */
    public int getAmountInStock() {
        return amountInStock;
    }

    /**
     * Set the amount of product in stock.
     * @param amount amount to set for the product.
     *                   It should not be negative.
     */
    public void setAmountInStock(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amountInStock = amount;
    }
}
