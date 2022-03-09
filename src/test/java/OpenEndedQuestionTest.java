import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OpenEndedQuestionTest {
    OpenEndedQuestion op = new OpenEndedQuestion("Does this work?");
    public List<String> answers = new ArrayList<String>();

    @Test
    public void addAnswerTest(){
        answers.add("Yes I do like coffee");


    }


}
