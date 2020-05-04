//页面加载初始化时
var m = null;//全局变量，参数map
var total=0;//全部数量
$(function(){
    m = new Map();
    var keyword = $("#search_input").val();
    m.set("keyword",keyword);//关键词
    m.set("from",0);//起点
    m.set("size",15);//规模
});

function souzhi_search() {
    var keyword = $("#search_input").val();
    if(keyword===""){
        alert("请输入搜索关键词!!!");
    }else{
        Ajax_Search();
    }
}
function press_enter(a) {
    // 按下enter建开始搜索
    var b = window.event || a;
    if (b.keyCode == 13) souzhi_search();
}
function souzhi_searchOfHot(element) {
    m.set("from",0);//起点
    var keyword = element.text;
    if(keyword===""){
        alert("请输入搜索关键词!!!");
    }else{
        $("#search_input").val(keyword);
        Ajax_Search();
    }

}
$(".item li").click(function(){
    m.set("from",0);//起点
    if(this.children[0]=== undefined){
        console.log("无用点击！！！");
    }else{
        var value =this.children[0].name;
        var name = this.parentElement.attributes.name.value;
        //颜色选择标识
        $(this.parentElement.children).each(function(index,element){
            element.setAttribute("class","");
        })
        this.setAttribute("class","active");
        if(value != "null"){
            m.set(name,value);
        }else{
            m.delete(name);
        }
        Ajax_Search();

    }
});
var flag= true;
function shaixuanAnimate(){
    if(flag){
        $(".element").css("display","none");
    }else{
        $(".element").css("display","block");
    }
    flag=!flag;

}
function Ajax_Search(){
    var url=server+"/souzhi/position/search?"
    var keyword = $("#search_input").val();
    m.set("keyword",keyword);//更新关键词
    if(keyword===""){
        alert("请输入搜索关键词!!!");
        return;
    }
    m.forEach(function (value, key, map) {
        url+="&"+key+"="+value;
    })

    $.ajax({
        type:"GET",
        url,
        async:true,
        dataType: "json",//指定服务器返回的数据类型
        success:function(res){
            if(res.code == 200){
                total=res.data.total;//全部职位数量
                parsePosition_Html(res.data);
            }else{
                alert(res.message);
            }
        }
    })
}

function parsePosition_Html(data){
    $(".content").empty();//清空子元素
    $(data.data).each(function(index,position){
        var htmlParse = '<div class="part">'
            +'<span class="p1"><a href="'+position.detailUrl+'" target="_blank" >'+position.positionName+'</a></span>'
            +'<span class="p2">更多</span>'
            +'<span class="p3">'+position.workSalary+'</span>'
            +'<span class="p4">'+position.workCity+' / '+position.workYear+' / '+position.educationRequire+' / '+position.workNature+'</span>'
            +'<div class="hr"></div>'
            +'<div>'
            +'网站来源:'+ '<span>'+position.originWebsite+'</span>'
            +'</div>'
            +'<div class="p22">'
            +'招聘信息:'
            +'<div>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</div>'
            +'<div>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</div>'
            +'<div>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</div>'
            +'</div>'
            +'</div>'
        $(".content").append(htmlParse);
    });

}
function page_pre(){
    var from = m.get("from");
    var size = m.get("size");
    if(from<=0){
        alert("这是首页哦！！！无法前往上一页");
    }else{
        from-=size;
        m.set("from",from);
        Ajax_Search();
        topFunction();

    }

}
function page_next(){
    var from = m.get("from");
    var size = m.get("size");
    if((from+size)>=total){
        alert("这是最后一页哦！！！无法前往下一页");
    }else{
        from+=size;
        m.set("from",from);
        Ajax_Search();
        topFunction();

    }
}

// 点击按钮，返回顶部
function topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}
