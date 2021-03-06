package survey.model;

/*
 * A class to represent all questions
 */
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Question {

    @ApiModelProperty(hidden=true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String question;

    public Question() {}

    public Question(String question) {
        this.question = question;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}


