import org.junit.Test;
import survey.OpenEndedQuestion;
import survey.Question;
import survey.Survey;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SurveyTest {
    Survey survey = new Survey("Test Survey");
    List<Question> questions = new ArrayList<>();
    Question q1 = new OpenEndedQuestion();
    Question q2 = new OpenEndedQuestion();
    private boolean open;

    @Test
    public void isClosedTest(){
        assertEquals(false, open);
    }

    @Test
    public void getNameTest(){
        assertEquals("Test Survey", survey.getName());
    }

    @Test
    public void setQuestionTest(){
        questions.add(q1);
        questions.add(q2);
        survey.setQuestions(questions);
        assertEquals(questions, survey.getQuestions());
    }

    @Test
    public void IdTest(){
        survey.setId(1);
        assertEquals(survey.getId(), 1);
    }


    @Test
    public void closeTest(){
        survey.close();
        assertFalse(open);
    }

}
