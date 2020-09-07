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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@Embedded //���� �ϳ��� �ص���
	private Address address;
	
	@JsonIgnore
	@OneToMany(mappedBy = "member") //���������� ������ �ƴϿ��� ����� �����̿�
	private List<Order> orders = new ArrayList<>();

}
