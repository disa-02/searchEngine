const url = "http://localhost:8080/api";
const input = document.getElementById('text');
const urlParams = new URLSearchParams(window.location.search);
input.value = urlParams.get('text')


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

const indexButton = document.getElementById('indexButton');
indexButton.addEventListener('click',function(){
    fetch(url + "/testSpider")
})