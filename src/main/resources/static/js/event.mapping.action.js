$(document).ready(function(){

    $("#allActions>.list-group-item").click(clickAllActions);
    $("#linkedActions>.list-group-item").click(clickLinkedActions);

    $("#submit").submit(function(){
        var linked = new Array();
        $("#linkedActions>.list-group-item").each(function(){
            linked.push($(this).attr("data-id"));
        });
        $("#input-linkedActions").val(linked.join(","));
    });
});


function clickAllActions(){
    $("#linkedActions").append($(this));
    $(this).unbind("click");
    $(this).bind("click", clickLinkedActions);
}

function clickLinkedActions(){
    $("#allActions").append($(this));
    $(this).unbind("click");
    $(this).bind("click", clickAllActions);
}