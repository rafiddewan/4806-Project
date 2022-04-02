const url = new URL(window.location.href);
const surveyId = url.searchParams.get("surveyId");

$(document).ready(function () {
  $.ajax({
    type: "GET",
    url: "/survey/" + surveyId,
  })
    .done(function (data) {
      generateHtml(data);
      generateAnalytics(data);
    })
    .fail(function (jqXHR, textStatus) {
      $("div.surveyForm").html(function () {
          return `<h1>Error: GET request failed</h1>`;
      });
    });
});

function generateHtml(data) {
  let htmlString = "";
  data.questions.forEach((question) => {
    switch (question.questionType) {
      case "MULTIPLE_CHOICE":
        htmlString += `
            <div class='col-sm-4 col-xl-4'>
                <div class="bg-light rounded h-100 p-4">
                    <h6 class="mb-4">Question - ${question.question}</h6>
                    <canvas id='question-${question.id}' width="451" height="225" style="display: block; box-sizing: border-box; height: 225px; width: 451px;"></canvas>
                </div>
            </div>`;
        break;
      case "NUMERICAL_RANGE":
        htmlString += `
            <div class="col-sm-4 col-xl-4">
                <div class="bg-light rounded h-100 p-4">
                    <h6 class="mb-4">Question - ${question.question}</h6>
                    <canvas id='question-${question.id}' width="451" height="451" style="display: block; box-sizing: border-box; height: 451px; width: 451px;"></canvas>
                </div>
            </div>`;
        break;
      case "OPEN_ENDED":
        htmlString += `
            <div class="col-sm-4 col-xl-4">
                <div class="bg-light rounded h-100 p-4">
                    <h6 class="mb-4">Question - ${question.question}</h6>
                    <p id='question-${question.id}'></p>
                </div>
            </div>`;
        break;
    }
  });

  $("#surveyName").html(data.name);
  $("#resultContainer").html(htmlString);
}

function generateAnalytics(data) {
  data.questions.forEach((question) => {
    switch (question.questionType) {
      case "MULTIPLE_CHOICE":
        // Pie Chart
        var ctx5 = $(`#question-${question.id}`).get(0).getContext("2d");
        var myChart1 = new Chart(ctx5, {
          type: "pie",
          data: {
            labels: Object.keys(question.options),
            datasets: [
              {
                backgroundColor: [
                  "rgba(0, 156, 255, .7)",
                  "rgba(0, 156, 255, .5)",
                  "rgba(0, 156, 255, .3)",
                  "rgba(0, 156, 255, .6)",
                  "rgba(0, 156, 255, .4)",
                  "rgba(0, 156, 255, .3)",
                  "rgba(0, 156, 255, .2)",
                  "rgba(0, 156, 255, .1)",
                ],
                data: Object.values(question.options),
              },
            ],
          },
          options: {
            responsive: true,
          },
        });
        break;

      case "NUMERICAL_RANGE":
        // Single Bar Chart
        var answers = question.answers;
        var uniqueAnswers = [...new Set(answers)];

        function getOccurrence(array, value) {
          return array.filter((v) => v === value).length;
        }

        response = [];

        answers.forEach((value) => {
          response.push(getOccurrence(answers, value));
        });

        var ctx4 = $(`#question-${question.id}`).get(0).getContext("2d");
        var myChart2 = new Chart(ctx4, {
          type: "bar",
          data: {
            labels: uniqueAnswers,
            datasets: [
              {
                label: "Answers",
                backgroundColor: [
                  "rgba(0, 156, 255, .7)",
                  "rgba(0, 156, 255, .6)",
                  "rgba(0, 156, 255, .5)",
                  "rgba(0, 156, 255, .4)",
                  "rgba(0, 156, 255, .3)",
                  "rgba(0, 156, 255, .2)",
                  "rgba(0, 156, 255, .1)"
                ],
                data: response,
              },
            ],
          },
          options: {
            responsive: true,
          },
        });
        break;

      case "OPEN_ENDED":
        $(`#question-${question.id}`).html(function () {
          let answerString = "";
          question.answers.forEach((answer) => {
            answerString += answer + "<br>";
          });
          return answerString;
        });
        break;
    }
  });
}
