import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * A class to store open-ended questions
 */
@Entity
public class OpenEndedQuestion extends Question {

    private List<String> answers;


    public OpenEndedQuestion() {
        super();
        this.answers = new ArrayList<>();
    }


    public OpenEndedQuestion(String question) {
        super(question);
        this.answers = new ArrayList<>();
    }


    public List<String> getAnswers() {
        return this.answers;
    }


    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }


    public boolean addAnswer(String answer) {

        return this.answers.add(answer);
    }

}