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
    $(".createSurvey").click(async function() {

        //JSON objects initialization
        var jsonQuestions = [];

        //Carry the error for nested function
        let error = false;

        //Extract surveyName
        let surveyName = $('#surveyName').val();

        //Check if survey name is present
        if(!surveyName) {
            alert("Please specify the name of the survey");
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
            var jsonData = {}

            let questionText = $(this).find("#QuestionText").val();
            jsonData["question"] = questionText
            if (!questionText) {
                alert("One of the questions is empty. Please ensure you fill out all questions")
                error = true;
            }
            var classname = $(this).attr("class");

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
                    let upperBound = $(this).find("#UpperBound").val();
                    let lowerBound = $(this).find("#LowerBound").val();

                    //Check if the bounds are empty
                    if (!upperBound || !lowerBound) {
                        alert("Must fill in both upper bound and lower bound for question: " + questionText)
                        error = true;
                    }

                    //Upperbound must always be greater than lower bound
                    else if (upperBound < lowerBound) {
                        alert("UpperBound must be a lower value than lower bound for question: " + questionText)
                        error = true;
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

                    //Keep track of current option number
                    let count = 1;

                    //loop through each mcq option
                    $(this).find(".MCQOption").each(function () {
                        let option = $(this).val();
                        console.log(option);

                        //Check if all the MCQ options are filled
                        if (!option) {
                            alert("One of the MCQ options is empty for question: " + questionText);
                            error = true
                        }
                        let optionNumber = "option" + count

                        //Add to data structure
                        optionsJSON[optionNumber] = option
                        count++;
                    });

                    if (error) return
                    else {
                        jsonQuestions.push(JSON.stringify({
                            "question": questionText,
                            "options": optionsJSON,
                        }));
                        // console.log(multipleChoiceQuestions)
                    }
                    break

                default:
                    alert("Something went wrong...")
                    return
            }
        });

        //Don't do the ajax call if there was an error with one of the questions
        if (error) return

        //Get survey response in order to get the survey id to do post requests for the questions
        const surveyResponse = await handlePostRequest("http://localhost:8080/admin/survey", surveyName, true)
        console.log(surveyResponse)
        let surveyId = surveyResponse["id"]
        console.log(surveyId)

        for(let i = 0; i < jsonQuestions.length; i++){
            let currQuestion = JSON.parse(jsonQuestions[i]);
            console.log(currQuestion)

            //Send request to add multiple choice question
            if("options" in currQuestion) {
                let multipleChoiceURL = "http://localhost:8080/admin/" + surveyId +"/mcq"
                await handlePostRequest(multipleChoiceURL, jsonQuestions[i])
            }

            //Send request to add numerical range question
            else if("upperBound" in currQuestion && "lowerBound" in currQuestion) {
                let numericalRangeURL = "http://localhost:8080/admin/" + surveyId +"/numerical"
                await handlePostRequest(numericalRangeURL, jsonQuestions[i])
            }

            //Send request to add openEnded question
            else {
                console.log("Sending request for open ended question")
                let openEndedURL = "http://localhost:8080/admin/" + surveyId +"/openEnded"
                console.log(openEndedURL)
                await handlePostRequest(openEndedURL, jsonQuestions[i])
            }
        }
    });

    /**
     * Handler for AJAX post requests for regular requests, and FETCH API requests with Promises
     * @param url
     * @param jsonData
     * @param returnPromise
     * @returns Promise if indicated in the returnPromise flag, otherwise will be void
     */
    async function handlePostRequest(url, jsonData, returnPromise=false) {
        //Handle POST request with ajax
        if(returnPromise === false) {
            $.ajax({
                type: "POST",
                url: url,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: jsonData
            }).then(function (data) {
                console.log("Created post at " + url + " for id " + data.id);
            });
        }
        //Handle POST request with FETCH API since it uses promises
        else{
            const settings = {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: jsonData
            };
            try {
                const fetchResponse = await fetch(url, settings);
                return await fetchResponse.json();
            } catch (e) {
                return e;
            }
        }
    }
});

