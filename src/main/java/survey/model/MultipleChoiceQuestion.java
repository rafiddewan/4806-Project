package survey.model;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A question type that allows selecting one option from a dropdown of many
 */

@Entity
public class MultipleChoiceQuestion extends Question {

    private HashMap<String,Integer> options;
    private static final QuestionType questionType = QuestionType.MULTIPLE_CHOICE;

    /**
     * Default constructor
     */
    public MultipleChoiceQuestion() {
        super();
        this.options = new HashMap<String,Integer>();
    }

    /**
     * Constructor with specified question
     * @param question the question text
     * @param options the possible options to choose from
     */
    public MultipleChoiceQuestion(String question, ArrayList<String> options) {
        super(question);
        this.options = new HashMap<String,Integer>();
        for(String option: options){
            this.options.put(option,0);
        }
    }

    public HashMap<String,Integer> getOptions() {
        return options;
    }

    public void setOptions(HashMap<String,Integer> options) {
        this.options = options;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    /**
     * Adds a new answer
     * @param option String
     */
    public void addAnswer(String option) {
        this.options.put(option,this.options.get(option) + 1);
    }
}