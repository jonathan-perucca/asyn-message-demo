var oTable;
var userFromId = 0;
var opened = false;

$(document).ready(function(){
	
	$("#message-popup").dialog({autoOpen: false, modal: false, resizable: false, height: 250, width: 400, title:'Write your message'});
	$("#result").hide();
	$('button[class*=messagesLists]').button();
	
	oTable = $("#messageTable").dataTable({
		"bJQueryUI": true,
		"bAutoWidth": true,
	});

	$("#popMessage").click(function(){ 
		initPopup();
		$("#message-popup").dialog('open');
	});
	
	createFocusInOutEvents("#messageSubject", "Your subject ...");
	createFocusInOutEvents("#messageCorps", "Your message ...");
	
	$("button[class*=messagesLists]").click(function(){
		var id = $(this).attr('id');
		userFromId = id;
		updateTable(id);
		$.jGrowl("You can now send message in name of : " + $(this).html(), {header: 'Information'});
	});
	
	$("input#sendMessage").click(function(){
		if(userFromId > 0){
			var userSelectId = $('#userSelect').val();
			var messageSubject = $('#messageSubject').val();
			var messageCorps = $('#messageCorps').val();
			sendMessage(userSelectId, messageSubject, messageCorps);
		}else{
			$.jGrowl("You must display one member result before this action", {header: 'Information'});
		}
	});
	
});

function initPopup(){
	$("#messageSubject").val("Your subject ...");
	$("#messageCorps").val("Your message ...");
}

function createFocusInOutEvents(field, textValue){
	$(field).focus(function(){
	    $(field).val("");
	});

	$(field).focusout(function(){
	    if($(field).val().length == 0)
	    	$(field).val(textValue);
	});
}

function sendMessage(userSelectId, messageSubject, messageCorps){
	$.ajax({
		type: 'POST',
		url: 'message/',
		data: {'userFrom': userFromId, 'userTo': userSelectId, 'subject': messageSubject, 'content': messageCorps},
		success: function() {
			updateTable(userFromId);
			$.jGrowl("Your message has been sent successfully", {header: 'Message'});
		}
	});
}

function updateTable(userId){
	if(opened)
		$("#result").hide('clip', 500);
	$.get("show/" + userId, function(data){
		$("#messageTable").dataTable().fnClearTable();
		var currentRow = 0;
		$.each(data, function(index, item){
			$("#messageTable").dataTable().fnAddData([
				item.expediteur.name,
				item.subject,
				item.content,
				item.isNew,
				"<input type='button' name=" + item.id + " id=" + item.id +" value='X' onClick='deleteMessage(" + userId + ', ' + item.id + ', ' + currentRow++ + ")' />"
			]);
		});
		opened = true;
		$("#result").show('clip', 500);
	});
}

function callBackDeleteMessage(userId, idMessage, idRow){
	$.ajax({
		type: 'POST',
		url: 'delete/' + userId + '/' + idMessage,
		data: idMessage,
		success: function() {
			oTable.fnDeleteRow(idRow);
			//updateTable(userId);
		}
	});
}

function deleteMessage(userId, idMessage, idRow) {
	$.confirm({
		'title'     : 'Delete Confirmation',
		'message'   : 'You are about to delete this item. <br />It cannot be restored at a later time! Continue?',
		'buttons'   : {
			'Yes'   : {
				'class' : 'blue',
				'action': function(){
					callBackDeleteMessage(userId, idMessage, idRow);
				}
			},
			'No'    : {
				'class' : 'gray',
				'action': function(){}
			}
		}
	});
}