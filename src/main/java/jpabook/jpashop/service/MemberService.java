package jpabook.jpashop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
//javax 와 spring이 있는데 스프링이 쓸수있는게 많아서 추천
@Transactional(readOnly = true)
// 데이터의 변경이 없는 읽기 전용 메서드에 사용, 영속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용) 데이터베이스 드라이버가 지원하면 DB에서 성능 향상
@RequiredArgsConstructor // final로 되어있는애들은 생성자를 만들어줌
public class MemberService {

//	@Autowired //이렇게 많이 쓰지만, 단점이 좀 있음
//	private MemberRepository memberRepository;

//	private MemberRepository memberRepository;
//	@Autowired //애플리케이션 돌다가 바꿔버리면 망함.. 근데 대게 없기에 사용
//	public void setMemberRepository(MemberRepository memberRepository) {
//
//	}

	// 그래서 생성자 인잭션을 함
	private final MemberRepository memberRepository; // final 하는것을 권장 컴파일 할때 체크를 해주기 때문

//	@RequiredArgsConstructor //final로 되어있는애들은 생성자를 만들어줌
//	public MemberService(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}

	/**
	 * 회원가입
	 */
	@Transactional // 쓰기는 true 넣으면 안댐(디폴트값이 false라서) 나머지들은 다 ture 적용
	public Long join(Member member) {

		validateDuplicateMember(member); // 중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByName(member.getName()); // 유니크 제약조건을 거는 것을 권장.. was가 여러개 있기 때문
		if (!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	/**
	 * 전체 회원 조회
	 */
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}

	/**
	 * 회원 수정
	 */
	@Transactional
	public void update(Long id, String name) {
		Member member = memberRepository.findOne(id);
		member.setName(name);
	}


}
