import org.junit.Test;
import survey.model.OpenEndedQuestion;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class QuestionTest {
    OpenEndedQuestion op = new OpenEndedQuestion("Does this work?");


    @Test
    public void IdTest(){
        op.setId(1);
        assertEquals(1, op.getId());
    }

    @Test
    public void QuestionTest(){
        op.setQuestion("Is this a new question now?");
        assertEquals("Is this a new question now?", op.getQuestion());
    }

}
