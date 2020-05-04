
//ip+端口号
var server="http://"+window.location.hostname+":"+window.location.port;
var urlSearch = server+"/list?&from=0&size=15";
/**
 * 选用拉勾的推荐词系统
 */
function souzhi_suggest() {
    var a = $("#search_input").val();
    var url = "https://suggest.lagou.com/suggestion/mix?suggestback=jQuery111306229897592697022_1588331080552&type=1&num=10&_=1588331080558";
    var data = {input:a};
    $.ajax({
        type:"GET",
        url,
        data,
        dataType: "jsonp",//指定服务器返回的数据类型
        async:true,
        success:function(res){
            if (res.POSITION && res.POSITION.length ){
                $("#ui-id-1").empty();//清空子元素
                $("#ui-id-1").append('<li class="ui-autocomplete-category" style="border-top: none;">职位</li>');
                  $(res.POSITION).each(function(index,position){
                    addSuggest_Html(position);
                  });
                 $("#ui-id-1").css("display","block");//清空子元素

            }
        }
    })
    // $.get(url,data,function(data){
    //     console.log(data);
    // })
    
}
function sugges_animate(element){
    $("#ui-id-1").css("display","none");//清空子元素
}
/**
 * 在推荐ul下，动态生成html节点
 * @param {*} data 
 */
function addSuggest_Html(position){
    var ulSugg = $("#ui-id-1");
    var count = position.hotness>450?450:position.hotness;
    var tipText = count==450?'大于':'共';
    var htmlSugg = '<li class="ui-menu-item" role="presentation" onclick="click_suggest(this)">'
                    +'<a class="ui-corner-all" tabindex="-1">'
                        +'<span class="fl">'+position.cont+'</span>'
                        +'<span class="fr">'+tipText+'<i>'+count+'个</i>职位</span>'
                    +'</a>'
                    +'</li>';
    ulSugg.append(htmlSugg);
}


function click_suggest(element){
    var keyword = element.firstElementChild.firstElementChild.textContent;
    window.location.href = urlSearch+"&keyword="+keyword;
}
