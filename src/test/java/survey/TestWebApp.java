package survey;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import survey.controller.WebController;
import survey.model.Survey;
import survey.repository.SurveyRepository;

//@WebMvcTest(WebController.class)
//public class TestWebApp {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private SurveyRepository service;
//
//    @Test
//    public void testSurvey() throws Exception {
//        Survey survey = new Survey();
//        survey.setId(1);
//        when(service.findById(1)).thenReturn(survey);
//        when(service.save(ArgumentMatchers.any())).thenReturn(survey);
//
//        this.mockMvc.perform(post("/createSurvey")).andExpect(MockMvcResultMatchers.status().isOk());
//        this.mockMvc.perform(get("/lastSurvey")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("{\"id\":1,\"name\":null,\"questions\":[]}")));
//    }
//
//
//}