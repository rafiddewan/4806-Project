package survey.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
 * A class to store surveys, comprising a list of questions
 */

@Entity
public class Survey {

    @ApiModelProperty(hidden=true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    private String name;

    @ApiModelProperty(hidden=true)
    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions;
    private boolean open;

    public Survey() {
        this.questions = new ArrayList<>();
        this.open = true;
    }

    public Survey(String name) {
        this.name = name;
        this.questions = new ArrayList<>();
        this.open = true;
    }

    public Survey(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
        this.open = true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void addQuestion(Question question) { this.questions.add(question); }

    public void close() {
        this.open = false;
    }
}

