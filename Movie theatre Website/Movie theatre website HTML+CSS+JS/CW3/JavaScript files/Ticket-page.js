var cinemas = getCinemas();
var movies = getMovies();
var currentCinema, currentMovie, currentDateTime, currentScreen;
var found = false;
var movieName, movieIndex;
function getIndex(){
    var url = document.location.href;
    var url1 = url.split('?');
    var receivedContent = url1[1];
    if (receivedContent != undefined){
        var receivedContent1 = receivedContent.split('=');
        movieName = receivedContent1[0];
        movieIndex = receivedContent1[1];
    }
}

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

var currentSeats = '';
function changeBackground(seat, id){
    seat.style.background = "#122435";
    seat.style.color = "white";
    addSeats(seat, id);
}

function addSeats(seat, id){
    currentSeats += seat.innerHTML + ', ';
    document.getElementById("seats").innerHTML += seat.innerHTML + ', ';
    submitForm(id);
}

function submitForm(id){
    document.getElementById(id).innerHTML = '';
    document.getElementById(id).innerHTML += '<form action="../Print page/Print-page.html">'+
    '<div id="seats">'+
    'Selected seats ' +
    '<div id="seatList">'+
    '</div>' + 
    '</div>' +
    '<button id="buy" onclick="submit" name="' + movieIndex + '" value="' + currentSeats +'">' +
    'Proceed' +
    '</button>'
    '</form>';
    document.getElementById("seatList").innerHTML += currentSeats;
}