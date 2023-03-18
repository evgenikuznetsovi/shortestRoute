

function  makeRequest(url, method, callback, body) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = callback;

    xhr.open(method, url);
    xhr.send(body)
}

function getPlanets() {
    makeRequest('http://localhost:8080/planets', 'GET', function () {

        const json = JSON.parse(this.responseText);
        let planet_from = document.getElementById('planet_from');
        let planet_to = document.getElementById('planet_to');

        for (let i =0; i < json.length; i++) {
            let option_from = document.createElement('option');
            let option_to = document.createElement('option');

            option_from.textContent = json[i].planetName;
            option_to.textContent = json[i].planetName;
            option_from.value = json[i].planetId;
            option_to.value = json[i].planetId;
            planet_from.appendChild(option_from);
            planet_to.appendChild(option_to);
        }
    });
}
function planetsToString(planets) {
    let result = "";
    for (let i =0; i < planets.length; i++) {
        if(i == planets.length-1) {
            result += planets[i].planetName;
            continue;
        }
        result += planets[i].planetName + " -> ";
    }
    return result;

}


function getRoutePlanets() {
    let planet_from = document.getElementById('planet_from').value;
    let planet_to = document.getElementById('planet_to').value;
    let delay = document.querySelector("input[type='radio'][name=delay]:checked").value;
    makeRequest('http://localhost:8080/calculate/path?planet_from='+planet_from+'&planet_to='+planet_to+'&delay='+delay, 'GET', function () {
        const json = JSON.parse(this.responseText.trim());




        document.getElementById('travel-path').innerHTML = planetsToString(json);
    });
}

function uploadFile() {
    document.getElementById('wait').style.display = "inline";
    let fileElement = document.getElementById('file');
    let file = fileElement.files[0];
    let formData = new FormData();
    formData.set('document', file);
    makeRequest("http://localhost:8080/data/upload-galaxy-data","POST",function(){

        if(this.status == 200) {
            location.reload();
        }
    },formData);

}