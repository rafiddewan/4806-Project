package survey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import survey.model.*;
import survey.repository.SurveyRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
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
            Survey survey1 = new Survey("Survey 1 name", new ArrayList<Question>(Arrays.asList(mcq1,mcq2,oeq1,rangeq1)));
            repository.save(survey1);

            // fetch all Surveys
            log.info("Surveys found with findAll():");
            log.info("-------------------------------");
            for (Survey survey : repository.findAll()) {
                log.info(survey.getId().toString());
            }
            log.info("");
        };
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "My REST API",
                "Some custom description of API.",
                "API TOS",
                "Terms of service",
                new Contact("John Doe", "www.example.com", "myeaddress@company.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
