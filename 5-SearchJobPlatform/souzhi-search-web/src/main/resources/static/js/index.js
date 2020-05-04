/**
 * 发起搜索请求
 */

function souzhi_search() {
    var keyword = $("#search_input").val();
    if(keyword===""){
        alert("请输入搜索关键词!!!");
    }else{
        window.location.href = urlSearch+"&keyword="+keyword;
    }
}
function press_enter(a) {
    // 按下enter建开始搜索
    var b = window.event || a;
    if (b.keyCode == 13) souzhi_search();
}