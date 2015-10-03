/**
 * ErRabbit Web Console
 * soleaf, mintcode.org
 * https://github.com/soleaf/ErRabbit
 * analysis.js
 */

// Init events
$(document).ready(function(){

    // date picker
    $("#date-end").datepicker({ dateFormat: "yy-mm-dd" });
    $("#date-begin").datepicker({ dateFormat: "yy-mm-dd" });

    // Rabbit Selection
    $("#changeRabbitModal-bt>A").click(function(){
        selectRabbit($(this).attr("data-id"),$(this).attr("data-id"));
        $("#changeRabbitModal").modal('hide');
    });

    // Group by
    $("#groupby_available li").click(function(){
        $checkBox = $(this).find("INPUT");
        selectGroupByAvailable($checkBox.val(), $checkBox.attr("data-label"));
    });

    // Click run analysis
    $("#run").click(function(){

        // Validations
        if ($("#groupBy").val().length < 1) {
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
        if ($("#date-begin").val().length > 0 && !dateValidation($("#date-begin").val())){
            alert("Invalid begin date format");
            return;
        }
        if ($("#date-end").val().length > 0 &&!dateValidation($("#date-end").val())){
            alert("Invalid end date format");
            return;
        }

        // request
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
                alert("fail");
                hideLoading();
            }
        });
    });
});

/**
 * Is validated date format sting?
 * @param str
 * @returns {boolean}
 */
function dateValidation(str){
    // STRING FORMAT yyyy-mm-dd
    if(str=="" || str==null){return false;}

    // m[1] is year 'YYYY' * m[2] is month 'MM' * m[3] is day 'DD'
    var m = str.match(/(\d{4})-(\d{2})-(\d{2})/);

    // STR IS NOT FIT m IS NOT OBJECT
    if( m === null || typeof m !== 'object'){return false;}

    // CHECK m TYPE
    if (typeof m !== 'object' && m !== null && m.size!==3){return false;}

    var ret = true; //RETURN VALUE
    var thisYear = new Date().getFullYear(); //YEAR NOW
    var minYear = 1999; //MIN YEAR

    // YEAR CHECK
    if( (m[1].length < 4) || m[1] < minYear || m[1] > thisYear){ret = false;}
    // MONTH CHECK
    if( (m[1].length < 2) || m[2] < 1 || m[2] > 12){ret = false;}
    // DAY CHECK
    if( (m[1].length < 2) || m[3] < 1 || m[3] > 31){ret = false;}

    return ret;
}

/**
 * select target rabbit
 * @param value
 * @param label
 */
function selectRabbit(value, label){
    $("#frm_rabbit_button").text(label);
    $("#frm_anal INPUT[name='rabbit']").val(value);
}

/**
 * select group by avaliable element
 * @param value
 * @param label
 */
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

/**
 * select group by selected element
 * @param value
 * @param label
 */
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

/**
 * Add group by item to input
 * @param val
 */
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

/**
 * Remove group by item from input
 * @param val
 */
function removeGroupByItemVal(val){
    var array = $("#groupBy").val();
    array = array.replace(val+",","");
    array = array.replace(val,"");;
    if (array.indexOf(",") == 0)
        array = array.substring(1, array.length);
    if (array.lastIndexOf(",") == array.length-1)
        array = array.substring(0, array.length-1);
    $("#groupBy").val(array);
}