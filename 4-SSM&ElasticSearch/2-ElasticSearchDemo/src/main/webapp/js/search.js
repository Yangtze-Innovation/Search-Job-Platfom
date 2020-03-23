function search_func(){
    var httpRequest = new XMLHttpRequest();
    var url = window.location.protocol+"//"+window.location.host;
    httpRequest.open("POST",url+"/2-ElasticSearchDemo/search.do",true);
    httpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    var keyword = document.getElementById("search_input").value;
    var body={
        "keyword":keyword
    }
    httpRequest.send("keyword="+keyword);
    httpRequest.onreadystatechange = ()=>{
        if(httpRequest.readyState == 4 && httpRequest.status == 200){
            var data = JSON.parse(httpRequest.responseText);
            showjob_func(data);
        }
    }    
}

function showjob_func(data){
    var content = document.getElementById("content");
    var jobs = data.rows;
    for(var i =0; i<jobs.length;i++){
        console.log(jobs[i]);
        var part = document.createElement("div");
        var p_class = document.createAttribute("class");
        p_class.value = "part";
        part.setAttributeNode(p_class);
        var html =  '<span class="p1"><a href="'+jobs[i].url+'">'+jobs[i].jobname+'</a></span>';
        html += '   <span class="p2">更多</span>';
        html+= '          <span class="p3">'+jobs[i].salary+'</span>';
        html += '   <span class="p4">'+jobs[i].city +'/'+ jobs[i].experience+'/'+ jobs[i].education +'/'+ '全职'+'</span>';
        html +='          <div class="hr"></div>';  
        html += '    <div>';   
        html += '        网站来源: <span>'+jobs[i].website+'</span>';
        html += '      </div>';
        html += '       <div class="p22">';
        html += '<div>'+jobs[i].detail+'</div>'
        html +='       </div>';
       part.innerHTML = html;
       content.appendChild(part);
    }
    
    var info = document.getElementsByClassName('p2');
    var xq = document.getElementsByClassName('p22'); 
    for(let i = 0; i < info.length; i++){
        info[i].onclick = function(){
            xq[i].style.display = 'block';
        }
        xq[i].onmouseleave = function(){
            xq[i].style.display = 'none';
        }
    }
}