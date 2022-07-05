function getHtml(){
     Android.getHtml(document.body.innerHTML)
};

function startSearchFromHome(term){
     let form = $('form')[0];
     let inputText = form.children[0];

     inputText.value = "gg";
     inputText.dispatchEvent(new Event('keydown', { 'bubbles': true }));
     inputText.dispatchEvent(new Event('keyup', { 'bubbles': true }));
     form.submit();
};

function startSearchOthers(term){
    let form = $('form')[0];
    let inputText = form.children[0];

    inputText.value = term;
    inputText.dispatchEvent(new Event('keydown', { 'bubbles': true }));
    inputText.dispatchEvent(new Event('keyup', { 'bubbles': true }));
    form.submit();
};

function getMovieList(){
    const movieList = [];
    Array.from($(".filmlist")[0].children)
        .filter(e => {
            return !Array.from(e.classList).includes("clearfix")
        })
        .forEach(e => {

            const movieItem = {
                thumbnail: "",
                title: "",
                type: "",
                source: ""
            };

            const thumbnail = e.querySelector('img').getAttribute('src');
            const title = e.querySelectorAll('a')[1].innerText;

            let type = '';
            if(e.querySelector('.type').innerText.includes('TV')){
                type = 'TV';
            }
            else{
                type = 'MOVIE';
            }

            const source = e.querySelectorAll('a')[1].getAttribute('href');

            movieItem.thumbnail = thumbnail;
            movieItem.title = title;
            movieItem.type = type;
            movieItem.source = source;

            movieList.push(movieItem);
        });

    const finalData = {
        list: movieList
    }
    Android.getMovieList(JSON.stringify(finalData))
}

function getMovieControls(){
    let movieData = {
        servers: getServers(),
        seasons: getSeasons(),
        seasonWiseEpisodes: getEpisodes(),
    };
    Android.getMovieControls(JSON.stringify(movieData));
}

function getServers(){
    const servers = [];
    Array.from($('#servers')[0].children).forEach(e => {
        const server = {
            name: "",
            dataId: ""
        };
        if(Array.from(e.classList).includes("server")){
            server.dataId = e.getAttribute('data-id');
            Array.from(e.childNodes).forEach(node => {
                if(node.nodeName == "DIV"){
                    server.name = node.innerText
                };
            });
            servers.push(server);
        };
    });

    return servers;
}


function getSeasons(){
    let seasons = [];
    Array.from($('#seasons')[0].children[1].children).forEach(e => {
        let season = {name: ""};
        season.name = e.children[1].innerText;
        seasons.push(season);
    });
    return seasons;
}

function getEpisodes(){

    let finalData = [];

    Array.from($('.episodes')).forEach(e => {
        let episodes = [];
        Array.from(e.children).forEach(range => {
            Array.from(range.children).forEach(episode => {
                let episodeObj = {
                    name: "",
                    dataId: "",
                    source: ""
                };
                let epsChild = episode.children[0];

                let name = epsChild.getAttribute("title");
                let dataId = epsChild.getAttribute("data-kname");
                let href = epsChild.getAttribute("href");

                episodeObj.name = name;
                episodeObj.dataId = dataId;
                episodeObj.source = href;

                episodes.push(episodeObj);
            });
        });
        finalData.push(episodes);
    });

    return finalData;
}

function selectServer(dataId){
    Array.from($('#servers')[0].children).forEach(e => {
        if(Array.from(e.classList).includes("server")){
            console.log(e.getAttribute('data-id'));
            if(e.getAttribute('data-id') == dataId){
                e.click();
            };
        };
    });
}

function selectEpisode(dataId){
        Array.from($('.episodes')).forEach(e => {
            Array.from(e.children).forEach(range => {
                Array.from(range.children).forEach(episode => {
                    let epsChild = episode.children[0];
                    let mDataId = epsChild.getAttribute("data-kname");
                    let href = epsChild.getAttribute("href");
                    if(mDataId == dataId){
                        epsChild.click();
                        console.log(mDataId);
                        Android.epsSelected(mDataId);
                    };
                });
            });
        });
}


let MOVIE_CONTROLS_INTERVAL = setInterval(function(){
    if($('#servers').length > 0){
        getMovieControls();
        clearInterval(MOVIE_CONTROLS_INTERVAL);
    };
}, 100);

let MOVIE_LIST = setInterval(function(){
    if($('.filmlist').length > 0){
        getMovieList();
        clearInterval(MOVIE_LIST);
    };
}, 100);
