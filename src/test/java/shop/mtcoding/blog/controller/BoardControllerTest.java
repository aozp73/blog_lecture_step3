package shop.mtcoding.blog.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog.dto.board.BoardReq.BoardUpdateReqDto;
import shop.mtcoding.blog.dto.board.BoardResp;
import shop.mtcoding.blog.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog.dto.reply.ReplyResp.ReplyDetailRespDto;
import shop.mtcoding.blog.model.Love;
import shop.mtcoding.blog.model.User;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    private MockHttpSession mockSession;

    // @BeforeAll // ?????? Test ????????? ?????? ?????? ?????? ??????
    // public static void all() {
    // // ????????? ?????????
    // }

    // @AfterEach // ?????????
    // public void teardown() {
    // // ????????? ?????????, ?????? ?????????
    // }

    @BeforeEach // Test ????????? ?????? ?????? ????????? ?????????
    public void setUp() {
        // ????????? insert
        User user = new User();
        user.setId(1);
        user.setUsername("ssar");
        user.setPassword("1234");
        user.setEmail("ssar@nate.com");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        mockSession = new MockHttpSession();
        mockSession.setAttribute("principal", user);
    }

    @Test
    public void update_test() throws Exception {
        // given
        int id = 1;
        BoardUpdateReqDto boardUpdateReqDto = new BoardUpdateReqDto();
        boardUpdateReqDto.setTitle("??????1-??????");
        boardUpdateReqDto.setContent("??????1-??????");

        // json?????? ???????????? ?????? ??? ?????? (@RequestBody)
        String requestBody = om.writeValueAsString(boardUpdateReqDto);
        System.out.println("????????? : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                put("/board/" + id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .session(mockSession));

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(1));
    }

    @Test
    public void delete_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(delete("/board/" + id).session(mockSession));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("????????? :" + responseBody);

        // ?????? ?????????
        // om.readValue(responseBody, Response.class);

        // then
        /**
         * jsonPath
         * ???????????? ?????? ??? : $
         * ???????????? : ???(.) ??????
         * ?????? ?????? ??? : [0]
         */

        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void detail_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get("/board/" + id).session(mockSession));
        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        BoardDetailRespDto boardDto = (BoardDetailRespDto) map.get("boardDto");
        List<ReplyDetailRespDto> replyDtos = (List<ReplyDetailRespDto>) map.get("replyDtos");
        Love loveDto = (Love) map.get("loveDto");
        // String boardJson = om.writeValueAsString(boardDto);
        // String replyListJson = om.writeValueAsString(replyDtos);
        // System.out.println("????????? : "+boardJson);
        // System.out.println("????????? : "+replyListJson);

        // then
        resultActions.andExpect(status().isOk());
        assertThat(boardDto.getUsername()).isEqualTo("ssar");
        assertThat(boardDto.getUserId()).isEqualTo(2);
        assertThat(boardDto.getTitle()).isEqualTo("1?????? ??????");
        assertThat(replyDtos.get(0).getComment()).isEqualTo("??????3");
        assertThat(replyDtos.get(0).getUsername()).isEqualTo("love");
        assertThat(loveDto.getBoardId()).isEqualTo(1);
    }

    @Test
    public void main_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/"));

        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        List<BoardResp.BoardMainRespDto> dtos = (List<BoardResp.BoardMainRespDto>) map.get("dtos");

        String model = om.writeValueAsString(dtos);
        System.out.println("????????? : " + model);

        // then
        resultActions.andExpect(status().isOk());
        assertThat(dtos.get(0).getTitle()).isEqualTo("6?????? ??????");
    }

    // @Test
    // public void save_test() throws Exception {
    // // given
    // String title = "";
    // for (int i = 0; i < 99; i++) {
    // title += "???";
    // }

    // String requestBody = "title=" + title + "&content=??????1";
    // // when
    // ResultActions resultActions = mvc.perform(
    // post("/board")
    // .content(requestBody)
    // .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    // .session(mockSession));

    // // then
    // resultActions.andExpect(status().is3xxRedirection());
    // }
    @Test
    public void save_test() throws Exception {
        // given
        BoardSaveReqDto boardSaveReqDto = new BoardSaveReqDto();
        boardSaveReqDto.setTitle("??????");
        boardSaveReqDto.setContent("??????");

        String requestBody = om.writeValueAsString(boardSaveReqDto);
        // when
        ResultActions resultActions = mvc.perform(
                post("/board")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .session(mockSession));

        System.out.println("save_test : ");
        // then
        resultActions.andExpect(status().isCreated());
    }
}
