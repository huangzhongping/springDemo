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
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            console.log(res)
            $("#comment-section").hide()
        },
        dataType: "json"
    });

}

