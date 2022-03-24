$(document).ready(function() {
    //Add's new multiple choice question
    $(".new-MCQ").click(function(){
        var numOptions = Math.floor($("#MCQOptionsNum").val());
        var newMCQ = `<div class='multipleChoiceQuestion' id="question">
                        <label for='QuestionText'>Question:</label>
                        <input type='text' id='QuestionText'>`;
        if(numOptions < 1){
            alert("Number of Multiple Choice Options must be greater than 0!");
            return 
        }
        for(var i = 1; i <= numOptions;i++){
            var option = `<br><label>Option ${i}: <input type='text' id='MCQOption'></label>`;
            newMCQ = newMCQ.concat(option);
        }
        newMCQ = newMCQ.concat('</div>');
        $("#Questions").append(newMCQ) 

        
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

    //Should send a POST to the endpoint with the servey details as a JSON
    $(".createSurvey").click(function(){  
        //  
    });

});

