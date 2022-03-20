package survey.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/*
 * A class to store open-ended questions
 */
@Entity
public class OpenEndedQuestion extends Question {

    @ApiModelProperty(hidden=true)
    @ElementCollection(targetClass=String.class)
    private List<String> answers;
    private static final QuestionType questionType = QuestionType.OPEN_ENDED;

    public OpenEndedQuestion() {
        super();
        this.answers = new ArrayList<String>();
    }

    public OpenEndedQuestion(String question) {
        super(question);
        this.answers = new ArrayList<String>();
    }

    public List<String> getAnswers() {
        return this.answers;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public boolean addAnswer(String answer) {
        return this.answers.add(answer);
    }
}