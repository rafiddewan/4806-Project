const url = new URL(window.location.href);
const surveyId = url.searchParams.get("surveyId");
let questionIdTypePair = new Map();

$(document).ready(function () {
  $.ajax({
    type: "GET",
    url: `http://127.0.0.1:8080/survey/${surveyId}`,
  })
    .done(function (data) {
      data.questions.forEach((question) => {
        if (question.questionType == "MULTIPLE_CHOICE") {
          questionIdTypePair.set(question.id, "mcq");
        } else if (question.questionType == "NUMERICAL_RANGE") {
          questionIdTypePair.set(question.id, "numerical");
        } else {
          questionIdTypePair.set(question.id, "openEnded");
        }
      });
      generateSurveyForm(data);
      addFormListener();
    })
    .fail(function (jqXHR, textStatus) {
      $("div.surveyForm").html(function () {
        return `<h1>Error: GET request failed</h1>`;
      });
    });
});

function generateSurveyForm(data) {
  $("div.surveyForm").html(function () {
    htmlString = "";
    htmlString += `<h1 class='display-5 text-center mt-3 pt-3'>${data.name}</h1>`;
    htmlString += `
        <form id="myForm">
            <div class="container">
                <div class="row justify-content-center">`;

    data.questions.forEach((question) => {
      switch (question.questionType) {
        case "OPEN_ENDED":
          htmlString += `
            <div class="col-sm-8 p-5 rounded">
                <p><strong>${question.question}</strong></p>
                <textarea class="form-control" id="${question.id}" rows="3" name="${question.id}" required></textarea>
            </div>`;
          break;

        case "MULTIPLE_CHOICE":
          optionsHtml = "";
          const keys = Object.keys(question.options);
          keys.forEach((key) => {
            let id = key.replace(/\s/g, "-");
            optionsHtml += `
                <div class="form-check mb-2">
                    <input class="form-check-input" type="radio" name="${question.id}" id="${id}" value="${key}" required/>
                    <label class="form-check-label" for="${id}">
                        ${key}
                    </label>
                </div>`;
          });
          htmlString += `
            <div class="col-sm-8 p-5 rounded">
                <p><strong>${question.question}</strong></p>
                ${optionsHtml}
            </div>`;
          break;

        case "NUMERICAL_RANGE":
          htmlString += `
            <div class="col-sm-8 p-5 rounded">
                <p><strong>${question.question}</strong></p>
                <div>
                    <input type="number" min="${question.lowerBound}" max="${question.upperBound}" class="form-control" id="${question.id}" name="${question.id}" placeholder="Enter Number" required>
                </div>
                <small class="form-label">Range: ${question.lowerBound} - ${question.upperBound}</small>
            </div>`;
          break;

        default:
          break;
      }
    });

    htmlString += `
                <div class="col-sm-8 text-end m-3">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </div>
    </form>`;

    return htmlString;
  });
}

function addFormListener() {
  $("#myForm").submit(function (e) {
    //prevent Default functionality
    e.preventDefault();

    let formData = $("#myForm").serializeArray();

    console.log(formData);
    console.log(questionIdTypePair);

    let error = false;
    let success = true;
    formData.forEach((data) => {
      let question_type = questionIdTypePair.get(parseInt(data.name));
      console.log(question_type);
      $.ajax({
        type: "PATCH",
        url: `/survey/${question_type}/${data.name}/submit`,
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        data: `{"answer": "${data.value}"}`,
      })
        .done(function (data) {
          console.log("Created POST request for question");
        })
        .fail(function (jqXHR, textStatus) {
          error = true;
        });
      if (error) {
        alert("Submit failed");
      } else {
        alert("Submitted Successfully!");
        window.location.href = "/";
      }
    });
  });
}
