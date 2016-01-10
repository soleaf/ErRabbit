/**
 * ErRabbit Web Console
 * soleaf, mintcode.org
 * https://github.com/soleaf/ErRabbit
 * event.js
 */

/**
 * Init
 */
$(document).ready(function () {

    $('#changeRabbitModal').on('show.bs.modal', function (event) {
        var checked = $("#rabbitSet").val().split(",");
        $("INPUT[NAME='rabbit_ids']").each(function(){
            if (checked != null && checked.length > 0){
                $(this).prop('checked', ($.inArray($(this).val(), checked)) > -1);
            }
            else{
                console.log("fff")
                $(this).prop('checked', false);
            }
        });
    });

    $("INPUT[NAME='rabbit_ids']").click(function(){
        var checked = new Array();
        $("INPUT[NAME='rabbit_ids']").each(function(){
            if ($(this).is(":checked")){
                //console.log("Checked");
                //if (checked.length > 0){
                //    checked+=","
                //}
                checked.push($(this).val());
                //checked+=$(this).val();
            }
        });
        if (checked.length > 0){
            var checkedStr = checked.join(",");
            $("#rabbitSetButton").text(checkedStr);
            $("#rabbitSet").val(checkedStr);
        }
        else{
            $("#rabbitSetButton").text("Select");
            $("#rabbitSet").val("");
        }


    });
});