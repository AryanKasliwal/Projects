var currentCinema, currentMovie, currentDateTime, currentScreen;
var found = false;
var cinemas = getCinemas();
var movies = getMovies();
var chosenSeats = new Array();
var movieIndex;
function findSeats(){
    var url = document.location.href;
    var url1 = url.split('?');
    var receivedData = url1[1];
    if (receivedData != undefined){
        var temp = receivedData.split('=')[0];
        movieIndex = receivedData.split('=')[0];
        var seats = receivedData.split('=')[1];
        var selectedSeats = seats.split('+');
        for (let i = 0; i < selectedSeats.length; i++){
            var temp = '';
            temp += selectedSeats[i][0];
            temp += selectedSeats[i][1];
            chosenSeats.push(temp);
        }
        chosenSeats.pop(chosenSeats[chosenSeats.length - 1]);
    }
}
findSeats();
function setMovieDescription(index){
    for (let i = 0; i < cinemas.length; i++){
        for (let j = 0; j < cinemas[i].movies.length; j++){
            for (let k = 0; k < cinemas[i].movies[j].shows.length; k++){
                if (cinemas[i].movies[j].shows[k].index == index){
                    currentDateTime = cinemas[i].movies[j].shows[k].datetime;
                    currentScreen = cinemas[i].movies[j].shows[k].house;
                    currentCinema = cinemas[i].branchName;
                    for (let l = 0; l < movies.length; l++){
                        if (movies[l].id == cinemas[i].movies[j].id){
                            currentMovie = movies[l].name;
                            found = true;
                            break;
                        }
                    }
                    if (found){
                        break;
                    }
                }
            }
            if (found){
                break;
            }
        }
        if (found){
            break;
        }
    }
}
setMovieDescription(movieIndex);
function grayTicket(content, i){
    var tempMovie = currentMovie, tempCinema = currentCinema, tempTime = currentDateTime, tempHouse = currentScreen;
    document.getElementById(content).innerHTML += '<div class="tickets">' +
        '<h2>Ticket' + (i + 1) + '</h2>' +
        'Theatre: ' + tempCinema +
        '<hr>' +
        'Movie: ' + tempMovie +
        '<hr>' +
        'Date and time: ' + tempTime +
        '<hr>' +
        'Screen: ' + tempHouse +
        '<hr>' +
        'Seat: ' + chosenSeats[i];
        '</div>';
}
function whiteTicket(content, i){
    document.getElementById(content).innerHTML += '<div class="alternatewhite">' +
        '<div class="tickets">' +
        '<h2>Ticket' + (i + 1) + '</h2>' +
        'Theatre: ' + currentCinema +
        '<hr>' +
        'Movie: ' + currentMovie +
        '<hr>' +
        'Date and time: ' + currentDateTime +
        '<hr>' +
        'Screen: ' + currentScreen +
        '<hr>' +
        'Seat: ' + chosenSeats[i];
        '</div>' +
        '</div>';
}

function printTicket(content){
    for (let i = 0; i < chosenSeats.length; i++){
        if (i % 2 == 0){
            grayTicket(content, i);
        }
        else {
            whiteTicket(content, i);
        }
    }
}