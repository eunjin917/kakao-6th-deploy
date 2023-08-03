package com.example.kakao.user;


import com.example.kakao.MyRestDoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.example.kakao._core.utils.PrintUtils.getPrettyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserRestControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    @Test
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("addnewuser@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("addnewuser");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void join_isPresent_test() throws Exception {
        // given
        UserRequest.JoinDTO requestDTO = new UserRequest.JoinDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");
        requestDTO.setUsername("ssarmango");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("meta1234!");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_notFound_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("addnewuser@nate.com");
        requestDTO.setPassword("meta1234!");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isNotFound()); // 404

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void login_notMatch_test() throws Exception {
        // given
        UserRequest.LoginDTO requestDTO = new UserRequest.LoginDTO();
        requestDTO.setEmail("ssarmango@nate.com");
        requestDTO.setPassword("wrongpassword!");

        // json 변환
        String requestBody = om.writeValueAsString(requestDTO);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
