function loginUser(){
	var param= {
		username:$("#username").val(),
		password:$("#pass").val()
	}
	
	var ser_data= JSON.stringify(param);
	
	$.ajax({
		type:"POST",
		contentType:'application/json; charset=UTF-8',
		url:'controlUser',
		data: ser_data,
		success:function(data){
			if(data=='OK'){
				$(location).attr('href', 'index');
			}
			else if(data=='ERROR'){
				alert("Kullanıcı adı veya Şifrenizi Kontrol Edin!");
			}
		},error:function(data){
			alert(data);
		}	
	});
}