// Stores the number of the selected theatre.
var selectedTheatreIndex = 0;

var movies = getMovies();
var cinemas = getCinemas();

// Stores all now showing movies.
var nowShowingMovies = new Array();
// Stores all upcoming movies.
var upcomingMovies = new Array();
for (let i = 0; i < movies.length; i++){
    if (movies[i].type == "now"){
        nowShowingMovies.push(movies[i]);
    }
    else if (movies[i].type == "upcoming"){
        upcomingMovies.push(movies[i]);
    }
}

// Stores all now showing movies from this theatre.
var thisCinemaNowShowing = new Array();
// Stores all upcoming movies from this theatre.
var thisCinemaUpComing = new Array();

function setCinemaMovies(selectedTheatreIndex){
    thisCinemaNowShowing.length = 0;
    thisCinemaUpComing.length = 0;
    for (let i = 0; i < cinemas[selectedTheatreIndex].movies.length; i++){
        for (let x = 0; x < movies.length; x++){
            if (cinemas[selectedTheatreIndex].movies[i].id == movies[x].id){
                if (movies[x].type == "now"){
                    thisCinemaNowShowing.push(cinemas[selectedTheatreIndex].movies[i]);
                }
                else {
                    thisCinemaUpComing.push(cinemas[selectedTheatreIndex].movies[i]);
                }
            }
        }
    }
}

// Creates dropdown for all the theatres.
function setTheatres(theatre){
    for (let j = 0; j < cinemas.length; j++){
        document.getElementById(theatre).innerHTML += '<option value="' + j +'" id="' + j +'">' +
            cinemas[j].branchName + 
            '</option>';
    }
    document.getElementById("cinemaName").innerHTML = cinemas[selectedTheatreIndex].branchName;
}

// Writes all the now showing movies of the chosen cinema in gray background to the page.
function nowmoviesListgray(j){
    document.getElementById("nowmoviesList").innerHTML += '<form action="../TicketPage/Ticket-page.html">' +
    '<section>'+
    '<img class="thumbnail" src="' + nowShowingMovies[j].thumbnail + '" alt="Image from the movie' + nowShowingMovies[j].name + '">'+
    '<div class="movie-descriptions">'+
    '<h3>' + nowShowingMovies[j].name + '</h3>'+
    '<hr>'+
    '<span class="description-title">Cast:</span> ' + nowShowingMovies[j].cast+
    '<hr>'+
    '<span class="description-title">Director:</span>' + nowShowingMovies[j].director+
    '<hr>'+
    '<span class="description-title">Duration:</span>' + nowShowingMovies[j].duration+
    '<hr>'+
    '</div>'+
    '<select class="time" name="' + nowShowingMovies[j].name + '" id="Time' + thisCinemaNowShowing[j].id + '">'+
    '</select>'+
    '<label for="Time">Time: </label>'+
    '<button class="button" onclick="submit">Buy Tickets</button>'+
    '</section>'+
    '</form>';
    for (let y = 0; y < thisCinemaNowShowing[j].shows.length; y++){
        document.getElementById("Time" + thisCinemaNowShowing[j].id).innerHTML += '<option value="' + thisCinemaNowShowing[j].shows[y].index +'">' +
        thisCinemaNowShowing[j].shows[y].datetime +
        '</option>';
    }
}

// Writes all the now showing movies of the chosen cinema in white background to the page.
function nowmoviesListWhite(j){
    document.getElementById("nowmoviesList").innerHTML += '<form action="../TicketPage/Ticket-page.html">' +
    '<div class="alternatewhite">' +
    '<section>'+
    '<img class="thumbnail" src="' + nowShowingMovies[j].thumbnail + '" alt="Image from the movie' + nowShowingMovies[j].name + '">'+
    '<div class="movie-descriptions">'+
    '<h3>' + nowShowingMovies[j].name + '</h3>'+
    '<hr>'+
    '<span class="description-title">Cast:</span> ' + nowShowingMovies[j].cast+
    '<hr>'+
    '<span class="description-title">Director:</span>' + nowShowingMovies[j].director+
    '<hr>'+
    '<span class="description-title">Duration:</span>' + nowShowingMovies[j].duration+
    '<hr>'+
    '</div>'+
    '<select class="time" name="' + nowShowingMovies[j].name +'" id="Time' + thisCinemaNowShowing[j].id + '">' +
    '</select>' +
    '<label for="Time">Time: </label>'+
    '<button class="button" onclick="submit">Buy Tickets</button>'+
    '</section>' +
    '</div>' +
    '</form>';
    for (let y = 0; y < thisCinemaNowShowing[j].shows.length; y++){
        document.getElementById("Time" + thisCinemaNowShowing[j].id).innerHTML += '<option value="' + thisCinemaNowShowing[j].shows[y].index +'">' +
        thisCinemaNowShowing[j].shows[y].datetime +
        '</option>';
    }
}

// Writes all the upcoming movies of the chosen cinema in gray background to the page.
function upmoviesListgray(j){
    document.getElementById("upmoviesList").innerHTML += '<form action="../TicketPage/Ticket-page.html">' + 
    '<section>'+
    '<img class="thumbnail" src="' + upcomingMovies[j].thumbnail + '" alt="Image from the movie' + upcomingMovies[j].name + '">'+
    '<div class="movie-descriptions">'+
    '<h3>' + upcomingMovies[j].name + '</h3>'+
    '<hr>'+
    '<span class="description-title">Cast:</span> ' + upcomingMovies[j].cast+
    '<hr>'+
    '<span class="description-title">Director:</span>' + upcomingMovies[j].director+
    '<hr>'+
    '<span class="description-title">Duration:</span>' + upcomingMovies[j].duration+
    '<hr>'+
    '</div>'+
    '<select class="time" name="' + upcomingMovies[j].name +'" id="Time' + thisCinemaUpComing[j].id + '">' +
    '</select>'+
    '<label for="Time">Time: </label>'+
    '<button class="button" onclick="submit">Buy Tickets</button>'+
    '</section>' +
    '</form>';
    for (let y = 0; y < thisCinemaUpComing[j].shows.length; y++){
        document.getElementById("Time" + thisCinemaUpComing[j].id).innerHTML += '<option value="' + thisCinemaUpComing[j].shows[y].index +'">' +
        thisCinemaUpComing[j].shows[y].datetime +
        '</option>';
    }
}

// Writes all the upcoming movies of the chosen cinema in white background to the page.
function upmoviesListWhite(j){
    document.getElementById("upmoviesList").innerHTML += '<form action="../TicketPage/Ticket-page.html">' + 
    '<div class="alternatewhite">' +
    '<section>'+
    '<img class="thumbnail" src="' + upcomingMovies[j].thumbnail + '" alt="Image from the movie' + upcomingMovies[j].name + '">'+
    '<div class="movie-descriptions">'+
    '<h3>' + upcomingMovies[j].name + '</h3>'+
    '<hr>'+
    '<span class="description-title">Cast:</span> ' + upcomingMovies[j].cast+
    '<hr>'+
    '<span class="description-title">Director:</span>' + upcomingMovies[j].director+
    '<hr>'+
    '<span class="description-title">Duration:</span>' + upcomingMovies[j].duration+
    '<hr>'+
    '</div>'+
    '<select class="time" name="' + upcomingMovies[j].name + '" id="Time' + thisCinemaUpComing[j].id + '">'+
    '</select>' +
    '<label for="Time">Time: </label>'+
    '<button class="button" onclick="submit">Buy Tickets</button>'+
    '</section>' +
    '</div>' +
    '</form>';
    for (let y = 0; y < thisCinemaNowShowing[j].shows.length; y++){
        document.getElementById("Time" + thisCinemaUpComing[j].id).innerHTML += '<option value="' + thisCinemaUpComing[j].shows[y].index +'">' +
        thisCinemaUpComing[j].shows[y].datetime +
        '</option>';
    }
}

function nowMovies(nowPlaying){
    for (let j = 0; j < nowPlaying.length; j++){
        if (j % 2 == 0){
            nowmoviesListgray(j);
        }
        else {
            nowmoviesListWhite(j);
        }
    }
}

function upMovies(upPlaying){
    for (let j = 0; j < upPlaying.length; j++){
        if (j % 2 == 0){
            upmoviesListgray(j);
        }
        else {
            upmoviesListWhite(j);
        }
    }
}

function isSelected(){
    selectedTheatreIndex = document.getElementById("theatre").selectedIndex;
    document.getElementById("nowmoviesList").innerHTML = '';
    document.getElementById("upmoviesList").innerHTML = '';
    document.getElementById("cinemaName").innerHTML = cinemas[selectedTheatreIndex].branchName;
    setCinemaMovies(selectedTheatreIndex);
    nowMovies(thisCinemaNowShowing);
    upMovies(thisCinemaUpComing);
}