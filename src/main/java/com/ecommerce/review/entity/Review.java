package com.ecommerce.review.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.AccessType;

import com.ecommerce.common.BaseEntity;
import com.ecommerce.orders.entity.Order;
import com.ecommerce.products.entity.Product;
import com.ecommerce.users.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE reviews SET deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE review_id = ?")
@SQLRestriction("deleted = false")
public class Review extends BaseEntity {

	@Id
	private Long reviewId;

	private int rating;

	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_Id", nullable = false)
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_Id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_Id", nullable = false)
	private Product product;

	public Review(Long reviewId, int rating, String content, Order order, User user, Product product) {
		this.reviewId = reviewId;
		this.rating = rating;
		this.content = content;
		this.order = order;
		this.user = user;
		this.product = product;
	}
}
