package com.ecommerce.dashboard.dto;

import java.time.LocalDate;
import java.util.List;

import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.orders.entity.Order;
import com.ecommerce.products.entity.Product;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Getter
@JsonPropertyOrder({"totalSales", "todaySales", "readyOrders", "shippingOrders", "deliveredOrders", "shortageProducts", "soldOutProducts"})
public class GetWidgetsResponse {

	private final Long totalSales;
	private final Long todaySales;
	private final Long readyOrders;
	private final Long shippingOrders;
	private final Long deliveredOrders;
	private final Long shortageProducts;
	private final Long soldOutProducts;

	private GetWidgetsResponse(Long totalSales, Long todaySales, Long readyOrders, Long shippingOrders,
		Long deliveredOrders, Long shortageProducts, Long soldOutProducts) {
		this.totalSales = totalSales;
		this.todaySales = todaySales;
		this.readyOrders = readyOrders;
		this.shippingOrders = shippingOrders;
		this.deliveredOrders = deliveredOrders;
		this.shortageProducts = shortageProducts;
		this.soldOutProducts = soldOutProducts;
	}

	public static GetWidgetsResponse from(List<Order> allOrders, List<Product> allProducts) {
		LocalDate today = LocalDate.now();

		return new GetWidgetsResponse(
			allOrders.stream().mapToLong(Order::getTotalPrice).sum(),

			(long)allOrders.stream()
				.filter(a -> a.getCreatedAt().toLocalDate().equals(today))
				.mapToDouble(Order::getTotalPrice)
				.sum(),

			allOrders.stream().filter(a -> OrderStatus.READY.equals(a.getStatus())).count(),

			allOrders.stream().filter(a -> OrderStatus.SHIPPING.equals(a.getStatus())).count(),

			allOrders.stream().filter(a -> OrderStatus.DELIVERED.equals(a.getStatus())).count(),

			allProducts.stream().filter(a -> a.getQuantity() <= 5).count(),

			allProducts.stream().filter(a -> a.getQuantity() == 0).count()
		);
	}

}
