package survey;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import survey.model.MultipleChoiceQuestion;
import survey.model.Question;
import survey.model.Survey;
import survey.repository.MultipleChoiceQuestionRepository;
import survey.repository.SurveyRepository;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
public class TestWebApp {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurveyRepository service;



    @Test
    public void testGetLastSurvey() throws Exception {
        Survey survey = new Survey("Survey 1");
        survey.setId(1);
        when(service.findTopByOrderByIdDesc()).thenReturn(survey);
        when(service.findById(1)).thenReturn(survey);
        when(service.save(ArgumentMatchers.any())).thenReturn(survey);

        this.mockMvc.perform(get("/lastSurvey")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"name\":\"Survey 1\",\"questions\":[]}")));
    }

    @Test
    public void testDeleteSurvey() throws Exception {
        Survey survey = new Survey("Survey 2");
        survey.setId(2);
        when(service.findById(2)).thenReturn(survey);
        when(service.save(ArgumentMatchers.any())).thenReturn(survey);

        this.mockMvc.perform(delete("/survey/2")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("")));

    }


    @Test
    public void testGetSurveyByID() throws Exception{
        Survey survey = new Survey("Survey 3");
        survey.setId(3);

        when(service.findById(3)).thenReturn(survey);
        when(service.save(ArgumentMatchers.any())).thenReturn(survey);

        this.mockMvc.perform(get("/survey/3")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":3,\"name\":\"Survey 3\",\"questions\":[]}")));


    }

    @Test
    public void testAddNumericalQuestion() throws Exception {

        Survey survey = new Survey("Survey 4");
        survey.setId(4);

        when(service.findById(4)).thenReturn(survey);
        when(service.save(ArgumentMatchers.any())).thenReturn(survey);
        // add a numerical question to the survey
        this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/4/numerical")
                        .contentType(MediaType.APPLICATION_JSON)

                        .content("{\"question\":\"Numerical range question 1\",\"answers\":[],\"lowerBound\":1.5,\"upperBound\":7.5,\"questionType\":\"NUMERICAL_RANGE\"}]}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().json("{\"id\":5,\"question\":\"Numerical range question 1\",\"answers\":[],\"lowerBound\":1.5,\"upperBound\":7.5,\"questionType\":\"NUMERICAL_RANGE\"}]}"));
        // test to see if numerical question has been added to the survey by getting the survey
        this.mockMvc.perform(get("/survey/4")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":4,\"name\":\"Survey 4\",\"questions\":[{\"id\":5,\"question\":\"Numerical range question 1\",\"answers\":[],\"lowerBound\":1.5,\"upperBound\":7.5,\"questionType\":\"NUMERICAL_RANGE\"}]}")));


    }

    @Test
    public void testAddMCQ() throws Exception {
        Survey survey = new Survey("Survey 5");
        survey.setId(5);
        when(service.findById(5)).thenReturn(survey);
        when(service.save(ArgumentMatchers.any())).thenReturn(survey);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/5/mcq")
                        .contentType(MediaType.APPLICATION_JSON)

                        .content("{\"options\":{\"MCQ1 option1\":0,\"MCQ1 option2\":0,\"MCQ1 option3\":0},\"question\":\"Multiple Choice Question\",\"questionType\":\"MULTIPLE_CHOICE\"}]}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().string(containsString("{\"id\":1,\"question\":\"Multiple Choice Question\",\"options\":{\"MCQ1 option2\":0,\"MCQ1 option1\":0,\"MCQ1 option3\":0},\"questionType\":\"MULTIPLE_CHOICE\"}")));

        this.mockMvc.perform(get("/survey/5")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":5,\"name\":\"Survey 5\",\"questions\":[{\"id\":1,\"question\":\"Multiple Choice Question\",\"options\":{\"MCQ1 option2\":0,\"MCQ1 option1\":0,\"MCQ1 option3\":0},\"questionType\":\"MULTIPLE_CHOICE\"}]}")));

    }

    @Test
    public void testAddOpenEndedQuestion() throws Exception{
        Survey survey = new Survey("Survey 6");
        survey.setId(6);
        when(service.findById(6)).thenReturn(survey);
        when(service.save(ArgumentMatchers.any())).thenReturn(survey);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/6/openEnded")
                        .contentType(MediaType.APPLICATION_JSON)

                        .content("{\"question\": \"Open Ended Question\",\"questionType\": \"OPEN_ENDED\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().string(containsString("{\"id\":6,\"question\":\"Open Ended Question\",\"answers\":[],\"questionType\":\"OPEN_ENDED\"}")));

        this.mockMvc.perform(get("/survey/6")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":6,\"name\":\"Survey 6\",\"questions\":[{\"id\":6,\"question\":\"Open Ended Question\",\"answers\":[],\"questionType\":\"OPEN_ENDED\"}]}")));




    }


    @Test
    public void testCreateNewSurvey() throws Exception{
        this.mockMvc.perform(post("/admin/survey")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"New Survey\",\"questions\":[]}")
                ).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"name\":\"New Survey\",\"questions\":[]}")));


    }

    @Test
    public void testBuildSurvey() throws Exception{

        // create a new survey and add 3 questions to the survey

        Survey survey = new Survey("Survey 10");
        survey.setId(10);
        when(service.findById(10)).thenReturn(survey);
        when(service.save(ArgumentMatchers.any())).thenReturn(survey);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/10/openEnded")
                        .contentType(MediaType.APPLICATION_JSON)

                        .content("{\"question\": \"Open Ended Question\",\"questionType\": \"OPEN_ENDED\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().string(containsString("{\"id\":2,\"question\":\"Open Ended Question\",\"answers\":[],\"questionType\":\"OPEN_ENDED\"}")));


        this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/10/numerical")
                        .contentType(MediaType.APPLICATION_JSON)

                        .content("{\"question\":\"Numerical range question 1\",\"answers\":[],\"lowerBound\":1.5,\"upperBound\":7.5,\"questionType\":\"NUMERICAL_RANGE\"}]}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().json("{\"id\":3,\"question\":\"Numerical range question 1\",\"answers\":[],\"lowerBound\":1.5,\"upperBound\":7.5,\"questionType\":\"NUMERICAL_RANGE\"}]}"));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/10/mcq")
                        .contentType(MediaType.APPLICATION_JSON)

                        .content("{\"options\":{\"MCQ1 option1\":0,\"MCQ1 option2\":0,\"MCQ1 option3\":0},\"question\":\"Multiple Choice Question\",\"questionType\":\"MULTIPLE_CHOICE\"}]}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().string(containsString("{\"id\":4,\"question\":\"Multiple Choice Question\",\"options\":{\"MCQ1 option2\":0,\"MCQ1 option1\":0,\"MCQ1 option3\":0},\"questionType\":\"MULTIPLE_CHOICE\"}")));

        this.mockMvc.perform(get("/survey/10")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":10,\"name\":\"Survey 10\",\"questions\":[{\"id\":2,\"question\":\"Open Ended Question\",\"answers\":[],\"questionType\":\"OPEN_ENDED\"},{\"id\":3,\"question\":\"Numerical range question 1\",\"answers\":[],\"lowerBound\":1.5,\"upperBound\":7.5,\"questionType\":\"NUMERICAL_RANGE\"},{\"id\":4,\"question\":\"Multiple Choice Question\",\"options\":{\"MCQ1 option2\":0,\"MCQ1 option1\":0,\"MCQ1 option3\":0},\"questionType\":\"MULTIPLE_CHOICE\"}]}")));

    }
}