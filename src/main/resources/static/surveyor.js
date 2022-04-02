$(document).ready(function() {
    getSurveys()
});


function getSurveys() {
    //Handle GET request with ajax
    $.ajax({
        type: "GET",
        url: '/surveys',
    })
    .done(function (data) {
        displaySurveys(data)
            
    })
    .fail(function (jqXHR, textStatus) {
        alert("FAILED REQUEST. Please try again");
    });
}

function displaySurveys(data){
    //alert(data)
    var html = ""
    html +="<table class='center styled-table'><thead><th>ID</th> <th>Survey Name</th><th>Close?</th></thead>"
    for(let i = 0; i< data.length; i++){
        html += createTableRow(data[i])
    }
    html+= "</table>"
    $("#surveys").append(html)
}

function createTableRow(survey){
    var id = survey.id
    var name = survey.name
    var isOpen = survey.open
    var html = `<tr><td>${id}</td><td>${name}</td><td>`
    if(isOpen === true){
        html +=`<button type='button' id="${id}" class='btn btn-outline-danger px-4 mt-1' onclick="closeSurvey(this)">Close</button>`
    }
    else{
        html += `<p>Closed <a href='/analytics.html?surveyId=${id}'>see results</a></p>`
    }
    html += "</td></tr>"
    
    return html
}


function closeSurvey(element) {
    surveyId = element.id
    //Handle POST request with ajax
    $.ajax({
        type: "POST",
        url: `/admin/${surveyId}/close`,
    })
    .done(function (data) {
        $(`button#${element.id}`).replaceWith(`closed <a href='/analytics.html?surveyId=${surveyId}'>see results</a>`)  
    })
    .fail(function (jqXHR, textStatus) {
        alert("FAILED REQUEST. Please try again");
    });
}