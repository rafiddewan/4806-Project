$(document).ready(function() {
    //Add's new multiple choice question
    $(".new-MCQ").click(function(){
        var numOptions = Math.floor($("#MCQOptionsNum").val());
        var newMCQ = `<div class='multipleChoiceQuestion' id="question">
                        <label for='QuestionText'>Question:</label>
                        <input type='text' id='QuestionText'>`;
        if(numOptions < 2){
            alert("Number of Multiple Choice Options must be at least 2!");
            return 
        }
        for(var i = 1; i <= numOptions; i++){
            var option = `<br><label>Option ${i}: <input type='text' class='MCQOption'></label>`;
            newMCQ = newMCQ.concat(option);
        }
        newMCQ = newMCQ.concat('</div>');
        $("#Questions").append(newMCQ) ;        
    });

    //Add's new numerical range question
    $(".new-NumQ").click(function(){
        var newNumQuestion = `<div class='numberRangeQuestion' id="question">
                                <label for='QuestionText'>Question:</label>
                                <input type='text' id='QuestionText'>
                                <label for="UpperBound">Upper Bound: </label>
                                <input type="number" id="UpperBound">
                                <label for="LowerBound">Lower Bound: </label>
                                <input type="number" id="LowerBound">
                            </div>`
        $("#Questions").append(newNumQuestion)  
    });

    //Add's new open ended question
    $(".new-OpenQ").click(function(){
        var newOpenQuestion = `<div class='openEndedQuestion' id="question">
                                <label for='QuestionText'>Question:</label>
                                <input type='text' id='QuestionText'>
                                </div>`
        $("#Questions").append(newOpenQuestion)    
    });

    //Should send a POST to the endpoint with the survey details as a JSON
    $(".createSurvey").click(function() {

        //JSON objects initialization
        var jsonQuestions = [];

        //Carry the error for nested function
        let error = false;

        //Extract surveyName
        let surveyName = $('#surveyName').val();

        //Check if survey name is present
        if(!surveyName) {
            alert("ERROR: Please specify the name of the survey");
            error = true;
            return
        }

        surveyName = JSON.stringify({"name": surveyName});
        let questions = $('div[id^="Questions"]');

        //Handle empty questions
        if (questions.is(':empty')) {
            alert("ERROR: There must be atleast 1 question in the survey!")
            error = true;
            return
        }

        questions.children().each(function () {
            //Contains jsonData of the question
            if(error !== true) {
                error = addJSONQuestion(jsonQuestions, this)
            }
        });

        //Don't do the ajax call if there was an error with one of the questions
        if (error === true) return

        //Get survey response in order to get the survey id to do post requests for the questions
        handlePostRequest("http://localhost:8080/admin/survey", surveyName, jsonQuestions)
    });

    /**
     * Create the MCQ, Open-ended or Numerical Question object that will be added to jsonQuestions
     * @param jsonQuestions
     * @param question
     * @returns a boolean that's true if there is an error, otherwise it's void
     */
    function addJSONQuestion(jsonQuestions, question){

        //Get question and check if its empty
        let questionText = $(question).find("#QuestionText").val();
        if (!questionText) {
            alert("ERROR: One of the questions is empty. Please ensure you fill out all questions")
            return true
        }

        let classname = $(question).attr("class");

        switch (classname) {
            //open ended questions
            case 'openEndedQuestion':

                //Create JSON Object for openEndedQuestion
                jsonQuestions.push(JSON.stringify({
                    "question": questionText,
                }));
                break

            //Numerical range questions
            case 'numberRangeQuestion':
                let upperBound = parseInt($(question).find("#UpperBound").val());
                let lowerBound = parseInt($(question).find("#LowerBound").val());

                //check if its not a number
                if(isNaN(upperBound) || isNaN(lowerBound)) {
                    alert("ERROR: Upper Bound or Lower Bound is not a value for question " + questionText)
                    return true
                }

                //Check if the bounds are empty
                if (!upperBound || !lowerBound) {
                    alert("ERROR: Must fill in both upper bound and lower bound for question: " + questionText)
                    return true
                }

                //upperBound must always be greater than lowerBound
                else if (upperBound < lowerBound) {
                    alert("ERROR: Upper Bound must be a larger value than Lower Bound for question: " + questionText)
                    return true
                }
                else{
                    //Create JSON Object for Numerical Range Question
                    jsonQuestions.push(JSON.stringify({
                        "question": questionText,
                        "lowerBound": lowerBound,
                        "upperBound": upperBound
                    }));
                }
                // console.log(numericalRangeQuestions)
                break

            //Multiple choice questions
            case 'multipleChoiceQuestion':

                //Declare optionsJSON
                let optionsJSON = {}

                //loop through each mcq option
                let error = false
                $(question).find(".MCQOption").each(function () {
                    let option = $(this).val();
                    console.log(option);

                    //Check if all the MCQ options are filled
                    if (!option) {
                        alert("ERROR: One of the MCQ options is empty for question: " + questionText);
                        error = true
                    }

                    if(option in optionsJSON) {
                        alert("ERROR: Choices for MCQ must be unique for question " + questionText)
                        error = true
                    }

                    //Add to data structure
                    optionsJSON[option] = 0;
                });

                // Return the error that occured within the option
                if (error) return error

                jsonQuestions.push(JSON.stringify({
                        "question": questionText,
                        "options": optionsJSON,
                }));
                    // console.log(multipleChoiceQuestions)
                break

            default:
                alert("ERROR: Something went wrong...")
                return true
        }

    }
    /**
     * Handler for AJAX post requests for regular requests, and FETCH API requests with Promises
     * @param url
     * @param jsonData
     * @param jsonQuestions
     * @return id of the response that's created
     */
    function handlePostRequest(url, jsonData, jsonQuestions=null) {
        //Handle POST request with ajax
        $.ajax({
            type: "POST",
            url: url,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: jsonData
        })
            .done(function (data) {
                console.log("Created POST request at " + url + " for id " + data.id);
                if(jsonQuestions !== null) sendQuestions(data.id, jsonQuestions);
        })
            .fail(function (jqXHR, textStatus) {
                alert("FAILED REQUEST. Please try again");
        });
    }

    /**
     * Sends a list of questions back the handlePostRequest handler to create questions for the survey
     * @param surveyId
     * @param jsonQuestions
     */
    function sendQuestions(surveyId, jsonQuestions){
        for(let i = 0; i < jsonQuestions.length; i++){
            let currQuestion = JSON.parse(jsonQuestions[i]);

            //Send request to add multiple choice question
            if("options" in currQuestion) {
                let multipleChoiceURL = "http://localhost:8080/admin/" + surveyId +"/mcq"
                handlePostRequest(multipleChoiceURL, jsonQuestions[i])
            }

            //Send request to add numerical range question
            else if("upperBound" in currQuestion && "lowerBound" in currQuestion) {
                let numericalRangeURL = "http://localhost:8080/admin/" + surveyId +"/numerical"
                handlePostRequest(numericalRangeURL, jsonQuestions[i])
            }

            //Send request to add openEnded question
            else {
                // console.log("Sending request for open ended question")
                let openEndedURL = "http://localhost:8080/admin/" + surveyId +"/openEnded"
                // console.log(openEndedURL)
                handlePostRequest(openEndedURL, jsonQuestions[i])
            }
        }
    }

});

