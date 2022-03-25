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

        console.log(surveyName);
        if(!surveyName) {
            alert("Please specify the name of the survey");
            error = true;
            return
        }
        const obj = JSON.stringify({"name": surveyName});
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
                        "questionText": questionText,
                        // "answers": [],
                        "questiontype": "OPEN_ENDED"
                    }));
                    // console.log(openEndedQuestions)
                    break

                //Numerical range questions
                case 'numberRangeQuestion':
                    let upperBound = $(this).find("#UpperBound").val();
                    let lowerBound = $(this).find("#LowerBound").val();

                    //Check if the bounds are empty
                    if (!upperBound || !lowerBound) {
                        alert("Must fill in both upper bound and lower bound for question: " + questionText)
                        error = true;
                        return
                    }

                    //Upperbound must always be greater than lower bound
                    else if (upperBound < lowerBound) {
                        alert("UpperBound must be a lower value than lower bound for question: " + questionText)
                        error = true;
                        return
                    }
                    else{
                    //Create JSON Object for Numerical Range Question
                        jsonQuestions.push(JSON.stringify({
                            "questionText": questionText,
                            // "answers": [],
                            "lowerBound": lowerBound,
                            "upperBound": upperBound,
                            "questiontype": "NUMERICAL_RANGE"
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
                            "questionText": questionText,
                            // "answers": [],
                            "options": optionsJSON,
                            "questiontype": "MULTIPLE_CHOICE"
                        }));
                        // console.log(multipleChoiceQuestions)
                    }
                    break

                default:
                    alert("Something went wrong...")
                    return
            }

            //Don't do the ajax call if there was an error with one of the questions
            if (error) return
        });

        let surveyId = null
        console.log(obj)
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/admin/survey",
            data: obj
        }).then(function (data) {
            surveyId = data.id
        });

        for(let i = 0; i < jsonQuestions.length; i++){

            if(jsonQuestions[i]["questiontype"] === "OPEN_ENDED") {
                let openEndedURL = "http://localhost:8080/admin/" + surveyId +"/openEnded"
                $.ajax({
                    type: "POST",
                    url: openEndedURL,
                    data: jsonQuestions[i]
                }).then(function (data) {
                    console.log("Created Open Ended Question " + data.id)
                });
            }

            else if(jsonQuestions[i]["questiontype"] === "NUMERICAL_RANGE") {
                let numericalRangeURL = "http://localhost:8080//admin/" + surveyId +"/numericalRange"
                $.ajax({
                    type: "POST",
                    url: numericalRangeURL,
                    data: jsonQuestions[i]
                }).then(function (data) {
                    console.log("Created Numerical range Question " + data.id)
                });
            }

            else if(jsonQuestions[i]["questiontype"] === "MULTIPLE_CHOICE") {
                let multipleChoiceURL = "http://localhost:8080/admin/" + surveyId +"/mcq"
                $.ajax({
                    type: "POST",
                    url: multipleChoiceURL,
                    data: jsonQuestions[i]
                }).then(function (data) {
                    console.log("Created Multiple Choice Question " + data.id)
                });
            }
        }
    });
});
