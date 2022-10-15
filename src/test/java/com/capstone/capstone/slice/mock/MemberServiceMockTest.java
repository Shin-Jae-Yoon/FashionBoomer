//package com.capstone.capstone.slice.mock;
//
//import com.capstone.capstone.exception.BusinessLogicException;
//import com.capstone.capstone.member.entity.Member;
//import com.capstone.capstone.member.repository.MemberRepository;
//import com.capstone.capstone.member.service.MemberService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//public class MemberServiceMockTest {
//    @Mock
//    private MemberRepository memberRepository;
//
//    @InjectMocks
//    private MemberService memberService;
//
//    @Test
//    public void createMemberTest() {
//        // given
//        Member member = new Member("hgd@gmail.com", "홍길동", "1234", "010-1111-1111");
//        given(memberRepository.findByEmail(member.getEmail()))
//                .willReturn(Optional.of(member));
//
//        // when / then
//        assertThrows(BusinessLogicException.class, () -> memberService.createMember(member));
//    }
//}