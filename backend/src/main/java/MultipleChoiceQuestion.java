import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * A question type that allows selecting one option from a dropdown of many
 */

@Entity
public class MultipleChoiceQuestion extends Question {


    private List<String> options;
    private List<String> answers;


    /**
     * Default constructor
     */
    public MultipleChoiceQuestion() {
        super();
        List<String> answers = new ArrayList<>();
    }

    /**
     * Constructor with specified question
     * @param question the question text
     * @param options the possible options to choose from
     */
    public MultipleChoiceQuestion(String question, ArrayList<String> options) {

        super(question);
        this.options = options;
        List<String> answers = new ArrayList<>();

    }

    public Collection<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public Collection<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    /**
     * Adds a new answer
     * @param answer String
     */
    public void addAnswer(String answer) {
        this.answers.add(answer);
    }
}
