$(function() {
    $('#getall').click(function() {
       $.getJSON(
         'http://localhost:8080/Begining_EJB_GF4-war/rs/books'
       )
       .done(function(data){
         var result = document.getElementById('result');
         result.innerHTML = '';
         
         var str = '<table border="1">';
         for (var i = 0; i < data.length; i++) {
             str += '<tr><td>' + data[i]['isbn'] + '</td><td>' + data[i]['title'] + '</td><td>' + data[i]['description'] + '</td><td>' + data[i]['price'] + '</td></tr>'; 
         }
         str += '</table>';
         result.innerHTML = str;
       });
    });
});


