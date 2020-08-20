package jpabook.jpashop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberRepositoryTest {

	@Autowired	MemberService memberService;
	@Autowired	MemberRepository memberRepository;

	@Test
	public void 회원가입() throws Exception {
		// Given
		Member member = new Member();
		member.setName("kim");
		// When
		Long saveId = memberService.join(member);
		// Then
		assertEquals(member, memberRepository.findOne(saveId));
	}

	@Test//없을 시에 예외처리가 안댐
	public void 중복_회원_예외() throws Exception {
		// Given
		Member member1 = new Member();
		member1.setName("kim");
		Member member2 = new Member();
		member2.setName("kim");
		// When
		memberService.join(member1);
		memberService.join(member2); // 예외가 발생해야 한다.
		// Then
		fail("예외가 발생해야 한다.");
	}

}
