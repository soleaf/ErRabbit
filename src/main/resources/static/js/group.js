
$(document).ready(function () {
    initButtons();
    initFormModal();
});

function initButtons() {
    $("#new_group").click(function () {
        var modal = $("#formModal");
        modal.find("form").attr("action", "/rabbit/group/insert.err");
        modal.find(".modal-title").html("New Group");
        modal.modal();
    });
    $("button[role=modify]").click(function () {
        var modal = $("#formModal");
        var id = $(this).attr("data_id");
        var name = $(this).attr("data_name");
        modal.find("form").attr("action", "/rabbit/group/modify.err");
        modal.find("INPUT[name='id']").val(id);
        modal.find(".modal-title").html("Modify Group");
        modal.find("INPUT[name='name']").val(name);
        modal.modal();
    });
    $("button[role=delete]").click(function () {
        var modal = $("#deleteModal");
        var id = $(this).attr("data_id");
        var name = $(this).attr("data_name");
        $("#deleteModalName").text(name);
        modal.find("INPUT[name='id']").val(id);
        modal.modal();
    });
}

function initFormModal() {
    var modal = $("#formModal");
    modal.find("form").submit(function () {
        if ($(this).find("INPUT[name=name]").val().length < 1) {
            alert("Insufficient name.");
            return false;
        }
    });
}