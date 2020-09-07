package jpabook.jpashop.repository.order.simplequery;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

	private final EntityManager em;

	//JPA에서 DTO로 바로 조회 
	public List<OrderSimpleQueryDto> findOrderDtos() {
		// new operation을 써야함 
		return em.createQuery(
				"select new jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name,	o.orderDate, o.status, d.address)"
						+ " from Order o" + " join o.member m" + " join o.delivery d",
				OrderSimpleQueryDto.class).getResultList();
	}
}
