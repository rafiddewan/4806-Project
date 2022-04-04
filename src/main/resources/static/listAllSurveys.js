$(document).ready(function () {
  getSurveys();
});

function getSurveys() {
  //Handle GET request with ajax
  $.ajax({
    type: "GET",
    url: "/surveys",
  })
    .done(function (data) {
      displaySurveys(data);
    })
    .fail(function (jqXHR, textStatus) {
      alert("FAILED REQUEST. Please try again");
    });
}

function displaySurveys(data) {
  //alert(data)
  var html = "";
  html +=
    "<table class='center styled-table'><thead><th>ID</th> <th>Survey Name</th><th>Start?</th></thead>";
  for (let i = 0; i < data.length; i++) {
    html += createTableRow(data[i]);
  }
  html += "</table>";
  $("#surveys").append(html);
}

function createTableRow(survey) {
  var id = survey.id;
  var name = survey.name;
  var isOpen = survey.open;
  var html = `<tr><td>${id}</td><td>${name}</td><td>`;
  if (isOpen === true) {
    html += `<button type='button' id="${id}" class='btn btn-outline-success px-4 mt-1' onclick="startSurvey(this)">Start</button>`;
  } else {
    html += "<p>Closed</p>";
  }
  html += "</td></tr>";

  return html;
}

function startSurvey(element) {
  surveyId = element.id;
  window.location.href = `/user.html?surveyId=${surveyId}`;
}
