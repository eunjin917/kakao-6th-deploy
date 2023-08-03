package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.example.kakao._core.utils.PrintUtils.getPrettyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void save_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
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

    @WithUserDetails(value = "another@nate.com")
    @Test
    public void save_isEmpty_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                post("/orders/save")
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

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findById_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
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

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findById_notFound_test() throws Exception {
        // given
        int id = 100;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
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

    @WithUserDetails(value = "another@nate.com")
    @Test
    public void findById_forBidden_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/orders/" + id)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isForbidden()); // 403

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
