package survey;

import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    private SurveyRepository repository;

    public WebController(SurveyRepository repository){
        this.repository = repository;

    }

    @PostMapping("/CreateSurvey")
    public Survey createSurvey(){ //Need to account for the request bodies data being sent, need to have discussion of how this will look
        Survey survey = new Survey();
        repository.save(survey);
        return survey; //NOTE: Not implemented yet
    }

    @PostMapping("/Survey")
    public boolean submitSurvey(){
        return false; //not implemented yet
    }


    @GetMapping("/Survey")
    public Survey getSurvey(){
        //Integer surveyId = Integer.parseInt(id);

        //NOTE: CHANGE THIS, STRICTLY FOR TESTING
        return repository.findById(1);
    }
}