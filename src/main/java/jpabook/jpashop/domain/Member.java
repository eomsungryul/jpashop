package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;
	
	@OneToOne
	private Order order;
	
	private String name;
	
	@Embedded //둘중 하나만 해도됨
	private Address address;
	
	@OneToMany(mappedBy = "member") //연관관계의 주인이 아니에여 멤버가 주인이여
	private List<Order> orders = new ArrayList<>();

}
