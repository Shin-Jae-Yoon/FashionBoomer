//package com.capstone.capstone.restdocs.member;
//
//import com.capstone.capstone.member.controller.MemberController;
//import com.capstone.capstone.member.dto.MemberDto;
//import com.capstone.capstone.member.entity.Member;
//import com.capstone.capstone.member.mapper.MemberMapper;
//import com.capstone.capstone.member.service.MemberService;
//import com.google.gson.Gson;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.util.List;
//
//import static com.capstone.capstone.util.ApiDocumentUtils.getDocumentRequest;
//import static com.capstone.capstone.util.ApiDocumentUtils.getDocumentResponse;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(MemberController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//@AutoConfigureRestDocs
//public class MemberControllerRestDocsTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private MemberService memberService;
//
//    @MockBean
//    private MemberMapper mapper;
//
//    @Autowired
//    private Gson gson;
//
//    @Test
//    public void postMemberTest() throws Exception {
//        // given
//        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com", "1234", "홍길동", "010-1234-5678");
//        String content = gson.toJson(post);
//
//        MemberDto.Response responseDto = new MemberDto.Response(1L,
//                "hgd@gmail.com",
//                "1234",
//                "홍길동",
//                "010-1234-5678",
//                Member.MemberStatus.MEMBER_ACTIVE);
//
//        given(mapper.memberPostToMember(Mockito.any(MemberDto.Post.class))).willReturn(new Member());
//
//        given(memberService.createMember(Mockito.any(Member.class))).willReturn(new Member());
//
//        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);
//
//        // when
//        ResultActions actions =
//                mockMvc.perform(
//                        post("/v11/members")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(content)
//                );
//
//        // then
//        actions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.email").value(post.getEmail()))
//                .andExpect(jsonPath("$.data.password").value(post.getPassword()))
//                .andExpect(jsonPath("$.data.name").value(post.getName()))
//                .andExpect(jsonPath("$.data.phone").value(post.getPhone()))
//                .andDo(document(
//                        "post-member",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        requestFields(
//                                List.of(
//                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
//                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
//                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
//                                        fieldWithPath("phone").type(JsonFieldType.STRING).description("휴대폰 번호")
//                                )
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
//                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
//                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
//                                        fieldWithPath("data.password").type(JsonFieldType.STRING).description("비밀번호"),
//                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
//                                        fieldWithPath("data.phone").type(JsonFieldType.STRING).description("휴대폰 번호"),
//                                        fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("회원 상태")
//                                )
//                        )
//                ));
//    }
//}
