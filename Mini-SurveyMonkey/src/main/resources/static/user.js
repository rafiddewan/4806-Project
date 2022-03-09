$(document).ready(function () {
  $.ajax({
    type: "GET",
    url: "/Survey",
  })
    .done(function (data) {
      generateSurveyForm(data);
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
        <form action="./index.html" method "POST" onsubmit="alert('you submitted the form');">
            <div class="container">
                <div class="row justify-content-center">`;

    data.questions.forEach((question) => {
      switch (question.questionType) {
        case "OPEN_ENDED":
          htmlString += `
            <div class="col-sm-8 p-5 rounded">
                <p><strong>${question.question}</strong></p>
                <textarea class="form-control" id="id2" rows="3"></textarea>
            </div>`;
          break;

        case "MULTIPLE_CHOICE":
          optionsHtml = "";
          const keys = Object.keys(question.options);
          keys.forEach((key) => {
            optionsHtml += `
                <div class="form-check mb-2">
                    <input class="form-check-input" type="radio" name="exampleForm" id="radioExample2" />
                    <label class="form-check-label" for="radioExample2">
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
                    <input type="text" class="form-control" id="id3" placeholder="Enter Number">
                </div>
                <small class="form-label">UpperBound: ${question.upperBound}, LowerBound: ${question.lowerBound}</small>
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
