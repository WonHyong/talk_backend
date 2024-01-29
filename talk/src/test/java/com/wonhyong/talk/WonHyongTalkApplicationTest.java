/*
package com.wonhyong.talk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonhyong.talk.board.dto.PostDto;
import com.wonhyong.talk.member.dto.MemberRequestDto;
import com.wonhyong.talk.member.dto.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class WonHyongTalkApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("통합테스트")
    void integrationTest() throws Exception {
        final MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("wonhyoung")
                .email("ld@test.com")
                .password("1234")
                .build();

        //회원 가입
        ResultActions signUpAction = mockMvc.perform(post("/api/members/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(memberRequestDto))
                .accept(MediaType.APPLICATION_JSON));

        signUpAction.andExpect(status().isOk());

        //로그인
        ResultActions signInAction = mockMvc.perform(post("/api/members/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(memberRequestDto))
                .accept(MediaType.APPLICATION_JSON));

        String signInResultString = signInAction.andReturn().getResponse().getContentAsString();
        TokenResponse signInResult = mapper.readValue(signInResultString, TokenResponse.class);

        //글쓰기
        PostDto.Request postRequest = new PostDto.Request("test1", "content1");
        String content = new ObjectMapper().writeValueAsString(postRequest);

        performAndExpect(post("/api/boards")
                .header("X-AUTH-TOKEN", signInResult.getAccessToken())
                .content(content), status().isOk());

        //글수정
        performAndExpect(put("/api/boards/1")
                .header("X-AUTH-TOKEN", signInResult.getAccessToken())
                .content(content), status().isForbidden());

        //좋아요
        performAndExpect(post("/api/boards/1/like")
                .header("X-AUTH-TOKEN", signInResult.getAccessToken()), status().isOk());

        //삭제
        performAndExpect(delete("/api/boards/1")
                .header("X-AUTH-TOKEN", signInResult.getAccessToken()), status().isForbidden());
    }

    private void performAndExpect(MockHttpServletRequestBuilder builder, ResultMatcher status) throws Exception {
        ResultActions editActions = mockMvc.perform(
                builder.contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        editActions.andExpect(status);
    }
}*/
