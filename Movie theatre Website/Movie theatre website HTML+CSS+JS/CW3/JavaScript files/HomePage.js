function getMovieName(movieArray, id) {
    for (let i=0; i<movieArray.length; i++) {
        if (movieArray[i].id == id) return movieArray[i].name;
    }
    return undefined;
}
//movies is an array of objects each containing info about a movie
let movies=getMovies();

// Stores all the now showing movies.
var nowShowingMovies = new Array();
var upcomingMovies = new Array();

// Stores all the upcoming movies.
for (let i = 0; i < movies.length; i++){
    if (movies[i].type == "now"){
        nowShowingMovies.push(movies[i]);
    }
    else if (movies[i].type == "upcoming"){
        upcomingMovies.push(movies[i]);
    }
}

// Updates the description of the movie as the trailer changes or a thumbnail is clicked.
function movieDescription(num){
    if (num < nowShowingMovies.length){
        document.getElementById("videoMovieTitle").innerHTML = nowShowingMovies[num].name;
        document.getElementById("videoMovieCast").innerHTML = nowShowingMovies[num].cast;
        document.getElementById("videoMovieDirector").innerHTML = nowShowingMovies[num].director;
        document.getElementById("videoMovieDuration").innerHTML = nowShowingMovies[num].duration;
    }
    else {
        document.getElementById("videoMovieTitle").innerHTML = upcomingMovies[num - nowShowingMovies.length].name;
        document.getElementById("videoMovieCast").innerHTML = upcomingMovies[num - nowShowingMovies.length].cast;
        document.getElementById("videoMovieDirector").innerHTML = upcomingMovies[num - nowShowingMovies.length].director;
        document.getElementById("videoMovieDuration").innerHTML = upcomingMovies[num - nowShowingMovies.length].duration;
    }
}

let k = 0;

// Stores all the sources of the videos. Now showing videos are stored first and then upcoming videos are stored.
var videoSource = new Array();
for (let i = 0; i < nowShowingMovies.length; i++){
    videoSource.push(nowShowingMovies[i].src);
}
for (let i = 0; i < upcomingMovies.length; i++){
    videoSource.push(upcomingMovies[i].src);
}


// Implements the 2 seconds break between the trailers.
function sleep(milliseconds) {
    const date = Date.now();
    let currentDate = null;
    do {
        currentDate = Date.now();
    } while (currentDate - date < milliseconds);
}

// Plays the next video after the current one ends.
function nextVideos(){
    if (k == videoSource.length){
        k = 0;
    }
    sleep(2000);
    playVideo(k);
    movieDescription(k);
    k++;
}

// Writes all the now showing movies in gray background to the page.
function nowShowingGray(j){
    document.getElementById("MoviesList").innerHTML += '<section>'+
    '<img class="thumbnail" src="' + nowShowingMovies[j].thumbnail + '" alt="Image from the movie' + nowShowingMovies[j].name + '" onclick="playVideo('+ j +'); movieDescription(' + j + ');">'+
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
    '<button class="button"><a href="../NowShowingPage/Now-Showing-page.html" target="_blank">Buy Tickets</a></button>'+
    '</section>';
}

// Writes all the now showing movies in white background to the page.
function nowShowingWhite(j){
    document.getElementById("MoviesList").innerHTML += '<div class="alternatewhite">'+
    '<section>'+
    '<img class="thumbnail" src="' + nowShowingMovies[j].thumbnail + '" alt="Image from the movie' + nowShowingMovies[j].name + '" onclick="playVideo('+ j +');  movieDescription(' + j + ');">'+ 
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
    '<button class="button"><a href="../NowShowingPage/Now-Showing-page.html" target="_blank">Buy Tickets</a></button>'+
    '</section>'+
    '</div>';
}

// Writes all the upcoming movies in gray background to the page.
function upComingGray(j){
    document.getElementById("MovieList").innerHTML += '<section>'+
    '<img class="thumbnail" src="' + upcomingMovies[j].thumbnail + '" alt="Image from the movie' + upcomingMovies[j].name + '" onclick="playVideo('+ (nowShowingMovies.length+j) +');  movieDescription(' + (nowShowingMovies.length + j) + ');">'+ 
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
    '<button class="button"><a href="../NowShowingPage/Now-Showing-page.html" target="_blank">Buy Tickets</a></button>'+
    '</section>';
}

// Writes all the upcoming movies in white background to the page.
function upComingWhite(j){
    document.getElementById("MovieList").innerHTML += '<div class="alternatewhite">'+
    '<section>'+
    '<img class="thumbnail" src="' + upcomingMovies[j].thumbnail + '" alt="Image from the movie' + upcomingMovies[j].name + '" onclick="playVideo('+ (nowShowingMovies.length+j) +'); movieDescription(' + (nowShowingMovies.length + j) + ');">'+ 
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
    '<button class="button"><a href="../NowShowingPage/Now-Showing-page.html" target="_blank">Buy Tickets</a></button>'+
    '</section>'+
    '</div>';
}


function nowShowing(nowMovies){
    for (let j = 0; j < nowMovies.length; j++){
        if (j % 2 == 0){
            nowShowingGray(j);
        }
        else {
            nowShowingWhite(j);
        }
    }
}

function upMovies(upComing){
    for (let j = 0; j < upComing.length; j++){
        if (j % 2 == 0){
            upComingGray(j);
        }
        else {
            upComingWhite(j);
        }
    }
}