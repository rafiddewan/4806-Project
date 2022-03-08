import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * A class to store surveys, comprising a list of questions
 */

@Entity
public class Survey {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)

    private List<Question> questions;
    private boolean open;


    public Survey(){
       this.questions = new ArrayList<>();
        this.open = true;
    }


    public Survey(String name){
        this.name = name;
        this.questions = new ArrayList<>();
        this.open = true;
    }


    public Survey(String name, List<Question> questions){
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


    public void setId(long id) {
        this.id = id;
    }


    public long getId() {
        return this.id;
    }


    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }


    public List<Question> getQuestions() {
        return this.questions;
    }


    public void close(){
        this.open = false;
    }

}