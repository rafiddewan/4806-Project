import org.junit.Test;
import survey.model.NumericalRangeQuestion;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumericalRangeQuestionTest {
    NumericalRangeQuestion rq1 = new NumericalRangeQuestion("Test1", 1, 10);

    @Test
    public void addAnswerTest(){
        assertEquals(rq1.addAnswer(5F), true );
        assertEquals(rq1.addAnswer(11F), false );
    }

    @Test
    public void getLowerBoundTest(){
        float f = rq1.getLowerBound();
        assertEquals(1, f,1);
    }

    @Test
    public void getUpperBoundTest(){
        float f2 = rq1.getUpperBound();
        assertEquals(10, f2, 10);

    }


}
