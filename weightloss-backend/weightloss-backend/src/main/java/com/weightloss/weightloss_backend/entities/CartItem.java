package com.weightloss.weightloss_backend.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "cart_item",
       uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "product_id"}))
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "product_id", nullable = false)
    private Long productId; // reference to Product entity

    @Column(nullable = false)
    private Integer quantity;

    @Column
    private Double price; // optional snapshot price

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public CartItem() {}
    public CartItem(Long productId, Integer quantity, Double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    // getters & setters
}
