import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Numerical type question
 */

@Entity
public class NumericalRangeQuestion extends Question {

    private List<Float> answers;
    private float lowerBound;
    private float upperBound;

    /**
     * Default constructor
     */
    public NumericalRangeQuestion() {
        super();
        this.answers = new ArrayList<>();
    }

    /**
     * Constructor with specified question
     * @param question the question text
     * @param lowerBound
     * @param upperBound
     */
    public NumericalRangeQuestion(String question, float lowerBound, float upperBound) {

        super(question);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.answers = new ArrayList<>();

    }

    /**
     * Get the minimum boundary for a question of type number_question
     * @return String
     */
    public Float getLowerBound() {
        return this.lowerBound;
    }


    /**
     * Get the maximum boundary for a question of type number_question
     * @return the maximum value
     */
    public Float getUpperBound() { return this.upperBound; }


    public List<Float> getAnswers() {
        return this.answers;
    }


    public void setAnswers(List<Float> answers) {
        this.answers = answers;
    }



    public boolean addAnswer(Float answer) {
        if(answer <= this.upperBound && answer >= this.lowerBound) {
            this.answers.add(answer);
            return true;
        }
        return false;
    }
}