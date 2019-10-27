
var info = document.getElementsByClassName('p2');
console.log(info.length);
var xq = document.getElementsByClassName('p22'); 
console.log(xq.length)
for(let i = 0; i < info.length; i++){
    info[i].onclick = function(){
        xq[i].style.display = 'block';
    }
    xq[i].onmouseleave = function(){
        xq[i].style.display = 'none';
    }
    
}