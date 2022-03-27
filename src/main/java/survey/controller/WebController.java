package survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import survey.model.MultipleChoiceQuestion;
import survey.model.NumericalRangeQuestion;
import survey.model.OpenEndedQuestion;
import survey.model.Survey;
import survey.repository.MultipleChoiceQuestionRepository;
import survey.repository.NumericalRangeQuestionRepository;
import survey.repository.OpenEndedQuestionRepository;
import survey.repository.SurveyRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WebController {

    private SurveyRepository repository;
    private MultipleChoiceQuestionRepository mcqRepository;
    private OpenEndedQuestionRepository openEndedRepository;
    private NumericalRangeQuestionRepository numericalRepository;

    @Autowired
    public WebController(SurveyRepository repository, MultipleChoiceQuestionRepository mcqRepository, OpenEndedQuestionRepository openEndedRepository,
                         NumericalRangeQuestionRepository numericalRepository){
        this.repository = repository;
        this.mcqRepository = mcqRepository;
        this.numericalRepository = numericalRepository;
        this.openEndedRepository = openEndedRepository;
    }

    @GetMapping("/surveys")
    public List<Survey> getAllSurveys(){
        List<Survey> surveys = new ArrayList<Survey>();
        for (Survey survey : repository.findAll()) {
            surveys.add(survey);
        }

        return surveys;
    }

    @GetMapping("/lastSurvey")
    public Survey getLastSurvey(){
        return repository.findTopByOrderByIdDesc();
    }

    @GetMapping("/survey/{surveyId}")
    public Survey getSurveyById(@PathVariable(value="surveyId") int surveyId){
        return repository.findById(surveyId);
    }

    @DeleteMapping("/survey/{surveyId}")
    public void deleteSurveyById(@PathVariable(value="surveyId") int surveyId){
        repository.deleteById(surveyId);
    }

    @PostMapping("/admin/survey")
    public Survey createNewSurvey(@RequestBody Survey survey){
        repository.save(survey);
        return survey;
    }

    @PostMapping("/admin/{surveyId}/close")
    public Survey closeSurvey(@PathVariable(value="surveyId") int surveyId){
        Survey survey = repository.findById(surveyId);
        survey.close();
        repository.save(survey);
        return survey;
    }

    @PostMapping("/admin/{surveyId}/mcq")
    public MultipleChoiceQuestion addMcqQuestionToSurvey(@PathVariable(value="surveyId") int surveyId, @RequestBody MultipleChoiceQuestion mcq){
        Survey survey = repository.findById(surveyId);
        mcqRepository.save(mcq);
        survey.addQuestion(mcq);
        repository.save(survey);
        return mcq;
    }

    @PostMapping("/admin/{surveyId}/numerical")
    public NumericalRangeQuestion addNumericalQuestionToSurvey(@PathVariable(value="surveyId") int surveyId, @RequestBody NumericalRangeQuestion numerical){
        Survey survey = repository.findById(surveyId);
        numericalRepository.save(numerical);
        survey.addQuestion(numerical);
        repository.save(survey);
        return numerical;
    }

    @PostMapping("/admin/{surveyId}/openEnded")
    public OpenEndedQuestion addOpenEndedQuestionToSurvey(@PathVariable(value="surveyId") int surveyId, @RequestBody OpenEndedQuestion openEnded){
        Survey survey = repository.findById(surveyId);
        openEndedRepository.save(openEnded);
        survey.addQuestion(openEnded);
        repository.save(survey);
        return openEnded;
    }
}