$(function() {
	$("#main").height($(window).height() - 40 + 'px');
	$(window).resize(function() {
		$("#main").height($(window).height() - 45 + 'px');
	})
	/*$(".sortable").sortable({
		cursor : "move",
		items : "tr",
		opacity : 0.6,
		revert : true,
		update : function(event, ui) {
			var categoryids = $(this).sortable("toArray");
			var $this = $(this);
		}
	});
	$(".sortable").disableSelection();*/
});

function save() {
	var aa = $("#lists").find("tr").length;
	var count = 0;
	for (var i = 0; i < aa; i++) {
		var id = $("#lists tr:eq(" + i + ") td input").val();
		$.ajax({
			type : "post",
			url : path + '/exportInfo/updateExportSort.do',
			data : {"id":id,"sort":(i+1)},
			dataType : 'text',
			async : false,
			success : function(resp) {
				if(resp != 0){
					count++;
				}
			}
		});
	}
	if(aa == count){
		alert("保存成功");
	}else{
		alert("保存失败");
	}
}

function saveOrder(id,value){
	$.ajax({
		type : "post",
		url : path + '/exportInfo/updateExportSort.do',
		data : {"id":id,"sort":value},
		dataType : 'text',
		async : false,
		success : function(resp) {
			if(resp != 0){
				window.location.href = path + "exportInfo/queryExportInfo.do?perCate="+$("#perCate").val()+"&codekind="+$("#codekind").val()+"&cpage="+$("#cpage").val();
			}
		}
	});
}