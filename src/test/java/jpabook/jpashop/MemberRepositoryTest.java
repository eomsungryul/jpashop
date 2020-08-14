package jpabook.jpashop;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

	@Autowired public MemberRepository memberRepository;

	@Test
	@Transactional
	@Rollback(false)
	public void testMember() throws Exception {
		//given
		Member member = new Member();
		member.setUserName("memeberA");

		//when
		Long saveId = memberRepository.save(member);
		Member findMember = memberRepository.find(saveId);

		//then
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId()); //바로 ..W찍어서 사용하숭 있음
		Assertions.assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
		Assertions.assertThat(findMember).isEqualTo(member);

		System.out.println("fin == mem"+(findMember==member));


	}

}
