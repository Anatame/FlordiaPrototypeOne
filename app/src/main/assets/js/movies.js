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

function getServers(){
    let servers = [];
    Array.from($('#servers')[0].children).forEach(e => {
        let server = {
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
    Android.getServers(JSON.stringify(servers));
}

function getSeasonsAndEpisodes(){
    let movieData = {
        seasons: getSeasons(),
        episodes: getEpisodes(),
    };
    Android.getSeasonsAndEpisodes(JSON.stringify(movieData));
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
