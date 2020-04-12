function postClick() {
   var questionId =  $("#question-id").val();

   var content =  $("#comment-content").val();

   if(!content){
       alert("请输入内容")
       return;
   }
    comment2Target(questionId,content,1);

}
function comment2Target(targetId,content,type) {
    $.ajax({
        type: "POST",
        url: "/comment",

        data: JSON.stringify({
            "parent_id":targetId,
            "content":content,
            "type":type
        }),
        contentType: "application/json",
        success: function (res) {
            console.log(res)
            if(res.code==200){
                //重新加载
                window.location.reload();
            }else if(res.code==2003){
                var isAssible = confirm(res.message);
                if(isAssible){
                    window.open("https://github.com/login/oauth/authorize?client_id=9bed93dab6f7fe0d264f&redirect_uri=http://localhost:8887/callback&scope=user&state=123")
                    window.localStorage.setItem("closeable","true");
                }
            }else{
                alert("出错了")
            }

        },
        dataType: "json"
    });
}


function comment(commendId,content) {
    comment2Target(commendId,content,2);
}

/**
 * 二级回复
 */
function replyClick(e) {
    var id=$(e).data("id");
    var content =$('#content-'+id).val();
    console.log(content)
    comment(id,content);
}

/***
 * 展开二级
 */
function collapse(e) {
   var id=$(e).data("id");
    var comments =$('#comment-'+id);
    console.log(comments)
    var collapse =comments.attr("collapse");
   if(collapse=="true"){
       comments.removeClass("in");
       comments.attr("collapse","false");
       $("#collapse-"+id).css("color","#8c8c8c");
   }else{
       comments.addClass("in");
       $("#collapse-"+id).css("color","#2aabd2");
       comments.attr("collapse","true");
   }

}

