function loadbooks(){
	//页面装载完毕请求笔记本列表
	$.ajax({
		url : "notebook/loadbooks.do",
		type : "post",
		data:{"userId":userId},
		dataType : "json",
		success : function(result) {
			if (result.status == 0) {
				var books=result.data;
				for(var i=0;i<books.length;i++){
					var bookName=books[i].cn_notebook_name;
					var bookId=books[i].cn_notebook_id;
					var str='<li class="online">';
						str+='<a>';
						str+='<i class="fa fa-book" title="online" rel="tooltip-bottom">';
						str+='</i>'+bookName+'<button type="button" class="btn btn-default btn-xs btn_position btn_delete" title="删除"><i class="fa fa-times"></i></button></a>';
						str+='</li>'
					var $li=$(str);//转换为JQuery对象
					$li.data("bookId",bookId);//bookId绑定到对应的<li>
					//alert(bookId);
					$("#book_list").append($li);//追加<li>
				}
				$("#book_list li:first").click();
			}
		},
		error : function() {
			alert("获取异常");
		},
		async : true
	});
}

function showAddBookWindow(){
	//显示背景色，将原页面遮住
	$(".opacity_bg").show();
	//加载添加笔记本对话框
	$("#can").load("./alert/alert_notebook.html");
}
function closeAlertWindow(){
	//取消显示背景
	$(".opacity_bg").hide();
	//清除类容
	$("#can").empty();
}
function addNoteBook(){
	//获取笔记本名称和用户id
	var bookName=$("#input_notebook").val().trim();
	//格式检查
	if(bookName==""){
		$("#input_notebook_msg").html("笔记本名称为空");
		return;
	}
	//发送ajax请求
	$.ajax({
		url:"notebook/addnotebook.do",
		type:"post",
		data:{"cn_user_id":userId,"cn_notebook_name":bookName},
		dataType:"json",
		success:function(result){
			if(result.status==0){//成功
				//关闭对话框
				closeAlertWindow();
				//添加li
				var bookId=result.data;
				var str='<li class="online">';
				str+='<a>';
				str+='<i class="fa fa-book" title="online" rel="tooltip-bottom">';
				str+='</i>'+bookName+'<button type="button" class="btn btn-default btn-xs btn_position btn_delete" title="删除"><i class="fa fa-times"></i></button></a>';
				str+='</li>'
				var $li=$(str);//转换为JQuery对象
				$li.data("bookId",bookId);//bookId绑定到对应的<li>
				//alert(bookId);
				$("#book_list").prepend($li);//追加<li>
				//将第一个笔记本选中,清空笔记列表和编辑区
				$("#book_list li:first").click();
				//清空编辑器内容
				um.setContent("");
				//清空编辑器标题
				$("#input_note_title").val("");
			}else{
				$("#input_notebook_msg").html(result.msg);
				return;
			}
		},
		async:true
	});
}
//删除笔记本
function delete_notebook(){
//	alert(this);
	var $li=$(this).parents("li");
	// 获取bookId
	var bookId = $li.data("bookId");
//	alert(notebookId);
	//请求,检查笔记本中笔记数量
	$.ajax({
		url:"note/query.do",
		type:"post",
		dataType:"json",
		data:{"bookId":bookId},
		success:function(result){
			if(result.data>0){
				// 提示确认框
				var ok=confirm("确认将不为空的笔记本删除?");
				if(!ok){
					return;
				}else{
					delete_book(bookId);
					$li.remove();
				}
			}else{
				delete_book(bookId);
				$li.remove();
			}
			$("#book_list li:first").click();
		},
		error:function(){
			alert("删除失败")
		},
		async:true
	});
	return false;//阻止事件冒泡
}
function delete_book(bookId){
	//正真删除笔记本操作
	$.ajax({
		url:"notebook/deleteBook.do",
		type:"post",
		dataType:"json",
		data:{"bookId":bookId},
		success:function(result){
			if(result.status==0){
				alert("删除成功")
			}else{
				alert("删除失败")
			}
		},
		error:function(){
			alert("删除失败")
		},
		async:true
	});
}


function showModfifyBookWindow(){
	//显示背景色，将原页面遮住
	$(".opacity_bg").show();
	//加载添加笔记本对话框
	$.ajax({
		url:"./alert/alert_rename.html",
		type:"get",
		dataType:"html",
		success:function(result){
			$("#can").html(result);
		},
		async:false
	});
	var noteBookName=$("#book_list li a.checked").text();
	$("#input_notebook_rename").val(noteBookName);
}
//笔记本修改事件
function modfify_book(){
	var noteBookId=$("#book_list li a.checked").parent().data("bookId");
	var notebookName=$("#input_notebook_rename").val().trim();
	var org_noteBookName=$("#book_list li a.checked").text();
	if(org_noteBookName!=notebookName){//名称不同，执行
		$.ajax({
			url:"notebook/modfify.do",
			type:"post",
			data:{"cn_notebook_name":notebookName,"cn_notebook_id":noteBookId,"cn_user_id":userId},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					closeAlertWindow();
					$("#book_list li a.checked").html("");
					$("#book_list li a.checked").html('<i class="fa fa-book" title="online" rel="tooltip-bottom"></i>'+notebookName+'<button type="button" class="btn btn-default btn-xs btn_position btn_delete" title="删除"><i class="fa fa-times"></i></button>');
					alert(result.msg);
				}else{
					$("#modfify_notebook_msg").html(result.msg)
				}
			},
			error:function(){
				alert("修改失败");
			},
			async:true
		});
	}else{//名称相同
		closeAlertWindow();
	}
}