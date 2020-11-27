$(document).ready(function() {
	
	var html = "<ul class='nav nav-list menu-second'>";
			html += "<div class='menu-first'>领导批示件督办</div>";
			html += "<li><a href='/dcdb/index.do' style='padding-left:40px;text-decoration:none' target='main_iframe'>新建事项</a></li>";
			html += "<li><a href='/todo.do?ajzt=1' style='padding-left:40px;text-decoration:none' target='main_iframe'>待办事项</a></li>";
			html += "<li><a href='/todo.do?ajzt=2' style='padding-left:40px;text-decoration:none' target='main_iframe'>正在办理事项</a></li>";
			html += "<li><a href='/todo.do?ajzt=3' style='padding-left:40px;text-decoration:none' target='main_iframe'>已办理事项</a></li>";
			html += "<li><a href='/todo.do?ajzt=4' style='padding-left:40px;text-decoration:none' target='main_iframe'>已办结事项</a></li>";
			html += "<li><a href='/todo.do?ajzt=5' style='padding-left:40px;text-decoration:none' target='main_iframe'>已归档列表</a></li>";
			html += "<li><a href='/liucheng.html' style='padding-left:40px;text-decoration:none' target='main_iframe'>流程监控列表</a></li>";
			html += "<li><a href='/fenxi.html' style='padding-left:40px;text-decoration:none' target='main_iframe'>分析列表</a></li>";
			html += "</ul>";
			html += "<ul class='nav nav-list menu-second'>";
			html += "<div class='menu-first'>党组会、检察长办公会决策事项督办</div></ul>";
			html += "<ul class='nav nav-list menu-second'>";
			html += "<div class='menu-first'>代表委员转交件督办</div></ul>";
			html += "<ul class='nav nav-list menu-second'>";
			html += "<div class='menu-first'>上级单位交办事项督办</div></ul>";
			html += "<ul class='nav nav-list menu-second'>";
			html += "<div class='menu-first'>重大会议、重大文件督办</div></ul>";
			html += "<ul class='nav nav-list menu-second'>";
			html += "<div class='menu-first'>个人事项</div></ul>";
			html += "<ul class='nav nav-list menu-second'>";
			html += "<div class='menu-first'>事项填报</div></ul>";

	$("#menu").html(html);

	$('.menu-first').on('click',function(){
		var html = '';
			html += "<li><a href='/dcdb/index.do' style='padding-left:40px;text-decoration:none' target='main_iframe'>新建事项</a></li>";
			html += "<li><a href='/todo.do?ajzt=1' style='padding-left:40px;text-decoration:none' target='main_iframe'>待办事项</a></li>";
			html += "<li><a href='/todo.do?ajzt=2' style='padding-left:40px;text-decoration:none' target='main_iframe'>正在办理事项</a></li>";
			html += "<li><a href='/todo.do?ajzt=3' style='padding-left:40px;text-decoration:none' target='main_iframe'>已办理事项</a></li>";
			html += "<li><a href='/todo.do?ajzt=4' style='padding-left:40px;text-decoration:none' target='main_iframe'>已办结事项</a></li>";
			html += "<li><a href='/todo.do?ajzt=5' style='padding-left:40px;text-decoration:none' target='main_iframe'>已归档列表</a></li>";
        html += "<li><a href='/liucheng.html' style='padding-left:40px;text-decoration:none' target='main_iframe'>流程监控列表</a></li>";
        html += "<li><a href='/fenxi.html' style='padding-left:40px;text-decoration:none' target='main_iframe'>分析列表</a></li>";
		$('.menu-second li').remove();
		$(this).after(html);
	})
});