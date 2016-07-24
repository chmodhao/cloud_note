function loadnotes(){
	$("#pc_part_2").show();//显示全部笔记
	$("#pc_part_6").hide();//隐藏搜索区
	// 将点击的笔记本li设置成选中状态
	$("#book_list li a").removeClass("checked");
	$(this).find("a").addClass("checked");
	// 获取bookId
	var notebookId = $(this).data("bookId");
	// alert(notebookId);
	// 清空编辑器标题
	$("#input_note_title").val("");
	 // 清空编辑器类容
	um.setContent("");
	 // 发送ajax请求
	$.ajax({
		url : "note/notes.do",
		type : "post",
		data:{"notebookId":notebookId},
		dataType : "json",
		success : function(result) {
			$("#note_list li").remove();
			// $("#note_list").empty();
			if(result.status != 0){
				alert(result.msg);
			}
			if (result.status == 0) {
				var notes=result.data;
				for(var i=0;i<notes.length;i++){
					var noteName=notes[i].cn_note_title;
					var noteId=notes[i].cn_note_id;
					// 拼成笔记li
	 				var s_li = '<li class="online">';
						s_li+= '<a>';
						s_li+= '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'+noteName+'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
						s_li+= '</a>';
						s_li+= '<div class="note_menu" tabindex="-1">';
						s_li+= '	<dl>';
						s_li+= '		<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>';
						s_li+= '		<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>';
						s_li+= '		<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>';
						s_li+= '	</dl>';
						s_li+= '</div>';
						s_li+= '</li>';
					// 将s_li字符串转成jQuery对象
					var $li = $(s_li);
					$li.data("noteId",noteId);
					// 将li添加到笔记的ul区域
					$("#note_list").append($li);
				}
				// 自动单击第一个
				$("#note_list li a:first").click();
			}
		},
		error : function() {
			alert("获取异常");
		},
		async : true
	});
}

function showAddNoteWindow(){
	// 显示背景色，将原页面遮住
	$(".opacity_bg").show();
	// 加载添加笔记本对话框
	$("#can").load("./alert/alert_note.html");
}
function addNote(){
	var noteBookId=$("#book_list li a.checked").parent().data("bookId");
	var noteTitle=$("#input_note").val().trim();
	// 检查笔记名是否为空
	$.ajax({
		url:"note/addnote.do",
		data:{"cn_user_id":userId,"cn_notebook_id":noteBookId,"cn_note_title":noteTitle},
		dataType:"json",
		success:function(result){
			closeAlertWindow();// 关闭对话框
			if(result.status==0){
				// 添加到列表
				var s_li = '<li class="online">';
				s_li+= '<a>';
				s_li+= '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'+noteTitle+'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
				s_li+= '</a>';
				s_li+= '<div class="note_menu" tabindex="-1">';
				s_li+= '	<dl>';
				s_li+= '		<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>';
				s_li+= '		<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>';
				s_li+= '		<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>';
				s_li+= '	</dl>';
				s_li+= '</div>';
				s_li+= '</li>';
				// 将s_li字符串转成jQuery对象
				var $li = $(s_li);
				// 将笔记id绑定到笔记列表
				$li.data("noteId",result.data);
				// 将li添加到笔记的ul区域
				$("#note_list").prepend($li);
				// 设置选中样式
				$("#note_list li a").removeClass("checked");
				$("#note_list li a:first").addClass("checked");
				// 清空编辑器内容
				um.setContent("");
				// 清空编辑器标题
				$("#input_note_title").val("");
				// 填充编辑器标题
				$("#input_note_title").val(noteTitle);
			}else if(result==1){
				alert(result.msg);
			}else{
				alert(result.msg);
			}
		},
		error:function(){
			alert("创建失败");
		},
		async:true
	});
}
// 笔记单击处理函数
function note_list_click(){
	$("#note_list li a").removeClass("checked");
	$(this).addClass("checked");
	// 填充编辑器标题
	var noteTitle=$(this).text();
	$("#input_note_title").val(noteTitle);// 填充编辑器标题
	// 获取noteID
	var noteId=$("#note_list li a.checked").parent().data("noteId");
	$.ajax({
		url:"note/notebody.do",
		dataType:"json",
		data:{"noteId":noteId},
		success:function(result){
			if(result.status==0){
				um.setContent(result.data);// 填充编辑器内容
			}else{
				alert(result.msg);
			}
		},
		async:true
	});
}
// 保存笔记类容
function saveNote(){
	// 获取选中的笔记id
	var noteId=$("#note_list li a.checked").parent().data("noteId");
	// 获取修改后的title
	var noteTitle=$("#input_note_title").val();
	// 更新笔记列表标题
	$("#note_list li a.checked").html('<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'+noteTitle+'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>');
	// 获取编辑框类容
	var noteBody=um.getContent();
	$.ajax({
		url:"note/savenote.do",
		type:"post",
		dataType:"json",
		data:{"cn_note_id":noteId,"cn_note_body":noteBody,"cn_note_title":noteTitle},
		success:function(result){
			if(result.status==0){
				alert(result.msg);
				$("#book_list li a.checked").click();// 保存后刷新笔记列表
			}else{
				alert(result.msg);
			}
		},
		async:true
	});
}

function show_notemenu(){
// $("#note_list li .note_menu").hide();
	var $menu=$(this).parent().next();
	if($menu.is(":hidden")){// 前面的元素是否符合后面的选择器
		$menu.slideDown(500);
	}else{
		$menu.slideUp(500);
	}
	// 将其他菜单隐藏
	$menu.parent().siblings().find(".note_menu").slideUp(200);
	// 阻止事件冒泡
// return false;
}

// 删除笔记到回收站
function delete_note(){
	// 提示确认框
	var ok=confirm("确认将笔记放入回收站");
	if(!ok){
		return;
	}
	var $li=$(this).parents("li");// 查找所有父节点，选出符合选择器的节点//选中的笔记的li
	var noteId=$li.data("noteId");
// alert(noteId);
	$.ajax({
		url:"note/abandon.do",
		type:"post",
		dataType:"json",
		data:{"noteId":noteId},
		success:function(result){
			if(result.status==0){
				$li.remove();
				alert(result.msg);
				$("#note_list li a:first").click();// 模拟单击一下第一项//刷新笔记列表
			}else{
				alert(result.msg);
			}
		},
		error:function(){
			alert("删除失败");
		},
		async:true
	});
	$li.find(".note_menu").slideUp(500);// 隐藏下拉列表
}

// 分享笔记
function share_note(){
	var $li=$(this).parents("li");
	var noteId=$li.data("noteId");
	$.ajax({
		url:"note/shareNote.do",
		type:"post",
		dataType:"json",
		data:{"noteId":noteId},
		success:function(result){
			if(result.status==0){
				alert(result.msg);
			}else{
				alert(result.msg);
			}
		},
		error:function(){
			alert("分享失败");
		},
		async:true
	});
	$li.find(".note_menu").slideUp(500);// 隐藏下拉列表
}

function search_note(event){
	var keyCode=event.keyCode;
	if(keyCode==13){
		var title=$("#search_note").val().trim();
// alert(title);
		$.ajax({
			url:"note/search.do",
			typa:"post",
			dataType:"json",
			data:{"title":title},
			success:function(result){
//				alert(result.data);
				var search_notes=result.data;
				$("#search_list").empty();
				for(var i=0;i<search_notes.length;i++){
					var share_title=search_notes[i].cn_share_title;
					var share_noteId=search_notes[i].cn_note_id;
					// 添加到列表
					var s_li = '<li class="online">';
					s_li+= '<a>';
					s_li+= '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'+share_title;
					s_li+= '</a>';
					s_li+= '</li>';
					var $s_li=$(s_li);
					$s_li.data("noteId",share_noteId);
					$("#search_list").append($s_li);
				}
				$("#pc_part_6").show();//显示搜索区
				$("#pc_part_2").hide();//隐藏全部笔记区
			},
			error:function(){
				alert("查询失败");
			},
			async:true
		});
	}
}
//分享笔记列表单击处理函数
function search_list_click(){
	$("#search_list li a").removeClass("checked");
	$(this).addClass("checked");
	// 填充编辑器标题
	var noteTitle=$(this).text();
	$("#input_note_title").val(noteTitle);// 填充编辑器标题
	// 获取noteID
	var noteId=$("#search_list li a.checked").parent().data("noteId");
	$.ajax({
		url:"note/notebody.do",
		dataType:"json",
		data:{"noteId":noteId},
		success:function(result){
			if(result.status==0){
				um.setContent(result.data);// 填充编辑器内容
			}else{
				alert(result.msg);
			}
		},
		async:true
	});
}