package survey.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Numerical type question
 */

@Entity
public class NumericalRangeQuestion extends Question {

    @ApiModelProperty(hidden=true)
    @ElementCollection(targetClass=Float.class)
    private List<Float> answers;
    private float lowerBound;
    private float upperBound;
    private static final QuestionType questionType = QuestionType.NUMERICAL_RANGE;

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

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setAnswers(List<Float> answers) {
        this.answers = answers;
    }

    public boolean addAnswer(Float answer) {
        if(answer <= this.upperBound && answer >= lowerBound) {
            this.answers.add(answer);
            return true;
        }
        return false;
    }
}