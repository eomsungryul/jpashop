package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)//이렇게하면 new Order에러나게해서 이렇게하지말고 다른 방식으로 해야되는구나.. 라고 인식함
@Getter @Setter
public class Order {

	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private Long id;
	private String userName;

	@ManyToOne(fetch = FetchType.LAZY) // 연관관계의 주인
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // 캐스케이드 올은 오더 주문하면 items 까지 다 persist까지 함
	//그래서 기준이 어디까지 적용을 하냐면 딱 주문까지만,,,오더 private 한것만 적용
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // 1대1 관계시에 FK가 있는곳에 연관관계의 주인은 ORDERS로 결정
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;

	private LocalDateTime orderDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

	// ==연관관계 편의 메서드==// 핵심적으로 컨트롤 하는쪽이 들고있는게 좋다
	public void setMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}

	// ==생성 메서드==//
	public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
		Order order = new Order();
		order.setMember(member);
		order.setDelivery(delivery);
		for (OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}
		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		return order;
	}

	// ==비즈니스 로직==//
	/** 주문 취소 */
	public void cancel() {
		if (delivery.getStatus() == DeliveryStatus.COMP) {
			throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
		}
		this.setStatus(OrderStatus.CANCEL);
		for (OrderItem orderItem : orderItems) {
			orderItem.cancel();
		}
	}

	// ==조회 로직==//
	/** 전체 주문 가격 조회 */
	public int getTotalPrice() {
		int totalPrice = 0;
		for (OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		return totalPrice;
	}

}
