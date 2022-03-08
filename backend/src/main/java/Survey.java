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

    private List<Question> questions = new ArrayList<>();
    private boolean open = true;


    public Survey(){ }


    public Survey(String name){ this.name = name; }


    public Survey(String name, List<Question> questions){
        this.name = name;
        this.questions = questions;
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


    public Collection<Question> getQuestions() {
        return questions;
    }


    public void close(){
        open = false;
    }

}