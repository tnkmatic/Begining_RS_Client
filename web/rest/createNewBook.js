$(function() {
    $('#btnCreateNewBoook').click(function(){
       //多重送信を防ぐために送信完了までボタンをdisableにする
       var button = $(this);
       button.attr('disabled', true);

       //POSTで送信するデータ
       var sendData = {
           title : $('#textTitle').val()
       };

       //通信実行
       $.ajax({
            type:'post'                                                 //method = "POST"
           ,url:'http://localhost:8080/Begining_EJB_GF4-war/rs/books'   //POST送信のURL本体
           ,data:JSON.stringify(sendData)
           ,contentType:'application/json'
           ,dataType:'text'
           ,success: function(data) {
               jQuery('#result').append(data);
           }
           ,error: function() {
               alert('Server Error, Please try again later.');
           }
           ,complete: function() {
               button.attr('disabled', false);
           }
       });
    });
});