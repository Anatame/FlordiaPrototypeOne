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


