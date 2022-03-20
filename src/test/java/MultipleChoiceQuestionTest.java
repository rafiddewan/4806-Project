import org.junit.Before;
import org.junit.Test;
import survey.model.MultipleChoiceQuestion;
import survey.model.QuestionType;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultipleChoiceQuestionTest {

    private MultipleChoiceQuestion mcq;

    @Before
    public void initialize() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Volvo");
        options.add("BMW");
        options.add("Ford");
        options.add("Mazda");
        mcq = new MultipleChoiceQuestion("Whats your fav car?", options);
    }

    @Test
    public void methodTest(){
        assertEquals(mcq.getOptions().get("Volvo"), 0);
    }

    @Test
    public void testAddAnswer() {
        mcq.addAnswer("BMW");
        assertEquals(mcq.getOptions().get("BMW"), 1);
    }

    @Test
    public void testGetQuestionType() {

        assertEquals(mcq.getQuestionType(), QuestionType.MULTIPLE_CHOICE);
    }
}