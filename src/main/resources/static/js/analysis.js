$(document).ready(function(){

    // Allocation Events

    // Rabbit
    $("#frm_rabbit_list li").click(function(){
        selectRabbit($(this).attr("data-value"), $(this).attr("data-label"));
    });

    //Group by
    $("#groupby_available li").click(function(){
        $checkBox = $(this).find("INPUT");
        selectGroupByAvailable($checkBox.val(), $checkBox.attr("data-label"));
    });

    $("#run").click(function(){

        // Validations
        if ($("#groupBy").val().length < 1){
            alert("Choose least one 'GROUP BY' element");
            return;
        }
        if ($("INPUT[name=level_trace]").is(':checked') == false &&
            $("INPUT[name=level_debug]").is(':checked') == false &&
            $("INPUT[name=level_info]").is(':checked') == false &&
            $("INPUT[name=level_warn]").is(':checked') == false &&
            $("INPUT[name=level_error]").is(':checked') == false &&
            $("INPUT[name=level_fatal]").is(':checked') == false
        ){
            alert("Choose least one 'LEVEL' element");
            return;
        }

        // Req
        showLoading();
        var formData = $("#frm_anal").serialize();
        var action = $("#frm_anal").attr("action")
        $.ajax({
            type : "POST",
            data : formData,
            url: action,
            success: function (data) {
                $("#result").html("");
                $("#result").append(data);
                hideLoading();
            }
            ,fail: function(){
                // todo : fail
                alert("fail");
                hideLoading();
            }
        });
    });
});

function selectRabbit(value, label){
    $("#frm_rabbit_button").text(label);
    $("#frm_anal INPUT[name='rabbit']").val(value);
}

function selectGroupByAvailable(value, label){
    var html= "<li><input checked type='checkbox' data-label='" + label + "' value='" + value + "'> " + label
        //+ "<span class='glyphicon glyphicon-menu-down' aria-hidden='true'></span>"
        //+ "<span class='glyphicon glyphicon-menu-up' aria-hidden='true'></span>"
        + "</li>";
    $("#groupby_selected").append(html);
    $("#groupby_available LI INPUT[value='" + value+  "']").parent().remove();
    $("#groupby_selected LI INPUT[value='" + value+  "']").parent().click(function(){
        $checkBox = $(this).find("INPUT");
        selectGroupBySelected($checkBox.val(), $checkBox.attr("data-label"));
    });
    addGroupByItemVal(value);
}

function selectGroupBySelected(value, label){
    var html= "<li><input type='checkbox' data-label='" + label + "' value='" + value + "'> " + label + "</li>";
    $("#groupby_available").append(html);
    $("#groupby_selected LI INPUT[value='" + value+  "']").parent().remove();
    $("#groupby_available LI INPUT[value='" + value+  "']").parent().click(function(){
        $checkBox  = $(this).find("INPUT");
        selectGroupByAvailable($checkBox.val(), $checkBox.attr("data-label"));
    });
    removeGroupByItemVal(value);
}

function addGroupByItemVal(val){
    var array = $("#groupBy").val();
    if (array.length < 1){
        array = array + val;
    }
    else{
        array = array + ","+ val;
    }
    $("#groupBy").val(array);
}

function removeGroupByItemVal(val){
    var array = $("#groupBy").val();
    array = array.replace(val+",","");
    array = array.replace(val,"");
    $("#groupBy").val(array);
}