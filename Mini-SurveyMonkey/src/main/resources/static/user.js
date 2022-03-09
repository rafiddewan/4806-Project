$(document).ready(function() {
    $.ajax({
        type: "GET",
        url: "myurl"
      })
    .done(function( data ) {
        alert( "Data Received: " + data );
        data = {
            "id": 1,
            "name": "Survey 1 name",
            "questions": [
                {
                    "id": 2,
                    "question": "MCQ question 1",
                    "options": {
                        "MCQ1 option2": 0,
                        "MCQ1 option1": 0,
                        "MCQ1 option3": 0
                    },
                    "questionType": "MULTIPLE_CHOICE"
                },
                {
                    "id": 3,
                    "question": "MCQ question 2",
                    "options": {
                        "MCQ2 option1": 0,
                        "MCQ2 option2": 0,
                        "MCQ2 option3": 0
                    },
                    "questionType": "MULTIPLE_CHOICE"
                },
                {
                    "id": 4,
                    "question": "open ended Question 1",
                    "answers": [],
                    "questionType": "OPEN_ENDED"
                },
                {
                    "id": 5,
                    "question": "Numerical range question 1",
                    "answers": [],
                    "lowerBound": 1.5,
                    "upperBound": 7.5,
                    "questionType": "NUMERICAL_RANGE"
                }
            ]
        }
        generateSurveyForm(data);
    })
    .fail(function(jqXHR, textStatus) {
        data = {
            "id": 1,
            "name": "Survey 1 name",
            "questions": [
                {
                    "id": 2,
                    "question": "MCQ question 1",
                    "options": {
                        "MCQ1 option2": 0,
                        "MCQ1 option1": 0,
                        "MCQ1 option3": 0
                    },
                    "questionType": "MULTIPLE_CHOICE"
                },
                {
                    "id": 3,
                    "question": "MCQ question 2",
                    "options": {
                        "MCQ2 option1": 0,
                        "MCQ2 option2": 0,
                        "MCQ2 option3": 0
                    },
                    "questionType": "MULTIPLE_CHOICE"
                },
                {
                    "id": 4,
                    "question": "open ended Question 1",
                    "answers": [],
                    "questionType": "OPEN_ENDED"
                },
                {
                    "id": 5,
                    "question": "Numerical range question 1",
                    "answers": [],
                    "lowerBound": 1.5,
                    "upperBound": 7.5,
                    "questionType": "NUMERICAL_RANGE"
                }
            ]
        }
        generateSurveyForm(data);
    })
});

function generateSurveyForm(data) {
    data.
}