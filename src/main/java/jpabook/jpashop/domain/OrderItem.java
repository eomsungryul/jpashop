package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem {

	@Id
	@GeneratedValue
	@Column(name = "order_item_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	private int orderPrice;
	private int count;

	// ==생성 메서드==//
	public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);
		//재고를 깐다.
		item.removeStock(count);
		return orderItem;
	}

	// ==비즈니스 로직==//
	/** 주문 취소 */
	public void cancel() {
		getItem().addStock(count);
	}

	// ==조회 로직==//
	/** 주문상품 전체 가격 조회 */
	public int getTotalPrice() {
		return getOrderPrice() * getCount();
	}

}
