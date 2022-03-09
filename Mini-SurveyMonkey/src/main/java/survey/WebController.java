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
        return survey;
    }
/*
    @PostMapping("/Survey")
    public Optional<survey.Survey> submitSurvey(@RequestParam(value = "id", defaultValue = "1") String id, @RequestParam(value = "name", defaultValue = "New Buddy Name") String name,
                                          @RequestParam(value = "phoneNumber", defaultValue = "111-111-1111") String phoneNumber){
        Integer bookId = Integer.parseInt(id);
        Optional<AddressBook> response = repository.findById(bookId);
        if(response.isPresent()){
            AddressBook book = response.get();
            book.addBuddy(new BuddyInfo(name, phoneNumber));
            repository.save(book);
            return response;
        }
        return response;
    }*/


    @GetMapping("/Survey")
    public Survey getSurvey(){
        //Integer surveyId = Integer.parseInt(id);

        //NOTE: CHANGE THIS, STRICTLY FOR TESTING
        return repository.findById(1);
    }
}