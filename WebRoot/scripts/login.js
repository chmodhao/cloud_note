$(function() {
	$('#login').click(login);
	$("#regist_button").click(reg);
	//登录回车事件监听
	$("#dl").keydown(function(event){
		var keyCode=event.keyCode;
		if(keyCode==13){			
			login();
		}
	});
	//注册回车事件监听
	$("#zc").keydown(function(event){
		var keyCode=event.keyCode;
		if(keyCode==13){			
			reg();
		}
	});
	
	//登录处理
	function login() {
		$("#name_msg").html('');
		$("#pwd_msg").html('');
		var name = $("#count").val().trim();
		var pwd = $("#password").val().trim();
		var flag = true;
		if (name == '') {
			flag = false;
			$("#name_msg").html("用户名为空");
		}
		if (pwd == '') {
			flag = false;
			$("#pwd_msg").html("密码为空");
		}
		if (flag) {
			// 采用HTTP Basic Authentication模式传输身份信息
			var msg = name + ":" + pwd;
			var base64_msg = Base64.encode(msg);
			// XmlHttpRequest.setRequestHeader
			$.ajax({
				url : "user/login.do",
				type : "post",
				// data:{"name":name,"password":pwd},
				dataType : "json",
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Authorization", "Basic "
							+ base64_msg);
				},
				success : function(result) {
					if (result.status == 0) {
						var userToken=result.data.userToken;
						var userId=result.data.userId;
						addCookie("userId",userId,0.5);
						addCookie("userToken",userToken,0.5);
						window.location.href = "edit.html";
					}
					if (result.status == 1) {
						$("#name_msg").html(result.msg);
					}
					if (result.status == 2) {
						$("#pwd_msg").html(result.msg);
					}
				},
				error : function() {
					alert("登录异常");
				},
				async : true
			});
		}
	}
	
	//注册处理
	function reg() {
		$("#warning_1 span").html('');
		var name = $("#regist_username").val().trim();
		var pwd = $("#regist_password").val().trim();
		var desc = $("#nickname").val().trim();
		var email=$("#email").val().trim();
		var flag = true;
		if (name == '') {
			flag = false;
			$("#warning_1 span").html("");
			$("#warning_1 span").html("用户名为空");
			$("#warning_1").show();
		}
		if (desc == '') {
			flag = false;
			$("#warning_1_1 span").html("");
			$("#warning_1_1 span").html("昵称不可为空");
			$("#warning_1_1").show();
		}
		if (email == '') {
			flag = false;
			$("#warning_1_2 span").html("");
			$("#warning_1_2 span").html("邮箱不得为空");
			$("#warning_1_2").show();
		}
		if (flag) {
			// 采用HTTP Basic Authentication模式传输身份信息
			var msg = name + ":" + pwd + ":" + desc+":"+email;
			var base64_msg = Base64.encode(msg);
			$.ajax({
				url : "user/regist.do",
				type : "post",
				dataType : "json",
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Authorization", "Basic "
							+ base64_msg);
				},
				success : function(result) {
					if (result.status == 0) {
						alert("注册成功,请前往邮箱激活");
						$("#back").click();
					}
					if (result.status == 1) {
						$("#warning_1 span").html("");
						$("#warning_1 span").html(result.msg);
						$("#warning_1").show();
					}
				},
				error : function() {
					alert("注册异常");
				},
				async : true
			});
		}
	}
});
