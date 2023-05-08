const url = "http://localhost:8080/api";
const input = document.getElementById('text');
const urlParams = document.location.href.split('?text=');
console.log(urlParams)

const value = urlParams[1]
console.log(value)

input.value = value.replaceAll("%20",' ')


fetch(url + '/search?text=' + value)
    .then(response => response.json())
    .then(json => {
        console.log(json)
        let html = '';
        for(let jsonResult of json){
            html += getHtml(jsonResult);
        }
        document.getElementById("links").outerHTML = html;
        
    })
        


function search(){
    const values = '?text=' + input.value;
    document.location.href = 'searchResults.html' + values;

}
const boton = document.getElementById('button');
boton.onclick = search
document.addEventListener('keydown',function(event){
    if(event.key === "Enter"){
        boton.click();
    }
})



function getHtml(jsonResult){
    return `<div class="result">
        <h3><a href="${jsonResult.url}">${jsonResult.title}</a></h3>
        <p>${jsonResult.description}</p>
    </div>`;
}