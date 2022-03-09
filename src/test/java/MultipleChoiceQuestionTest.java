import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class MultipleChoiceQuestionTest {


    @Test
    public void methodTest(){
        ArrayList<String> options = new ArrayList<String>();
        options.add("Volvo");
        options.add("BMW");
        options.add("Ford");
        options.add("Mazda");
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion("Whats your fav car?", options);
        assertEquals(,mcq.getOptions());



        mcq = new MultipleChoiceQuestion("Fav car brand?", options);

    }




}
