function postClick() {
   var questionId =  $("#question-id").val();

   var content =  $("#comment-content").val();
    console.log("questionId:"+questionId+"content:"+content);
    $.ajax({
        type: "POST",
        url: "/comment",

        data: JSON.stringify({
            "parent_id":questionId,
            "content":content,
            "type":1
        }),
        contentType: "application/json",
        success: function (res) {
            console.log(res)
            if(res.code==200){
                $("#comment-section").hide()
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

