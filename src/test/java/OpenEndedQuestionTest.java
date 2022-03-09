import org.junit.Before;
import org.junit.Test;
import survey.MultipleChoiceQuestion;
import survey.OpenEndedQuestion;
import survey.QuestionType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpenEndedQuestionTest {
    private OpenEndedQuestion openQuestion;

    @Before
    public void initialize() {
        openQuestion = new OpenEndedQuestion("Whats your hobby?");
    }

    @Test
    public void testAddAnswer() {
        boolean response = openQuestion.addAnswer("My hobby is Soccer");
        assertEquals(response, true);
    }

    @Test
    public void testGetQuestionType() {
        assertEquals(openQuestion.getQuestionType(), QuestionType.OPEN_ENDED);
    }

    @Test
    public void testSetAndGetAnswer() {
        ArrayList<String> answers = new ArrayList<String>();
        answers.add("My hobby is Soccer");
        answers.add("My hobby is Soccer 2");
        answers.add("My hobby is Soccer 3");
        answers.add("My hobby is Soccer 4");

        openQuestion.setAnswers(answers);
        assertEquals(openQuestion.getAnswers(), answers);
    }
}
