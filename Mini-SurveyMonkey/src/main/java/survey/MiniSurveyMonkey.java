package survey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class MiniSurveyMonkey {

    private static final Logger log = LoggerFactory.getLogger(MiniSurveyMonkey.class);

    public static void main(String[] args){
        SpringApplication.run(MiniSurveyMonkey.class, args);
    }

    @Bean
    public CommandLineRunner demo(SurveyRepository repository) {
        return (args) -> {
            // save a few questions in a survey
            MultipleChoiceQuestion mcq1 = new MultipleChoiceQuestion("MCQ question 1",new ArrayList<String>(Arrays.asList("MCQ1 option1", "MCQ1 option2", "MCQ1 option3")));
            MultipleChoiceQuestion mcq2 = new MultipleChoiceQuestion("MCQ question 2",new ArrayList<String>(Arrays.asList("MCQ2 option1", "MCQ2 option2", "MCQ2 option3")));
            OpenEndedQuestion oeq1 = new OpenEndedQuestion("open ended Question 1");
            NumericalRangeQuestion rangeq1 = new NumericalRangeQuestion("Numerical range question 1", 1.5F, 7.5F);
            Survey survey = new Survey("Survey 1 name", new ArrayList<Question>(Arrays.asList(mcq1,mcq2,oeq1,rangeq1)));
            repository.save(survey);

                      // fetch all Surveys
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Survey surv : repository.findAll()) {
                log.info(surv.getId().toString());
            }
            log.info("");
        };
    }
}
