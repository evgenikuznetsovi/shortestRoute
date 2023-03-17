function makeRequest(url, method, callback){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = callback(xhr);
    xhr.open(method, url);
    xhr.send()
}