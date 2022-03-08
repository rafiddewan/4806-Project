import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Numerical type question
 */

@Entity
public class NumericalRangeQuestion extends Question {

    private List<Float> answers = new ArrayList<>();
    protected int lowerBound;
    protected int upperBound;

    /**
     * Default constructor
     */
    public NumericalRangeQuestion() {
        super();
    }

    /**
     * Constructor with specified question
     * @param question the question text
     */
    public NumericalRangeQuestion(String question, int min, int max) {

        super(question);
        this.lowerBound = min;
        this.upperBound = max;

    }

    /**
     * Get the minimum boundary for a question of type number_question
     * @return String
     */
    public int getLowerBound() {
        return lowerBound;
    }

    /**
     * Set the minimum boundary for a question of type number_question
     * @param lowerBound the minimum value for the question
     */
    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    /**
     * Get the maximum boundary for a question of type number_question
     * @return the maximum value
     */
    public int getUpperBound() { return upperBound; }

    /**
     * Set the maximum boundary for a question of type number_question
     */
    public void setUpperBound(int upperBound) { this.upperBound = upperBound; }



    public List<Float> getAnswers() {
        return answers;
    }


    public void setAnswers(List<Float> answers) {
        this.answers = answers;
    }



    public void addAnswer(Float answer) {
        this.answers.add(answer);
    }
}