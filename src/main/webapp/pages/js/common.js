/**
 * Created by soleaf on 2015. 4. 19..
 */
function showLoading(){
    loading = "<div class='loading_box' id='loading'>" +
    "<div>loading....</div>" +
    "</div>";

    $("BODY").append(loading).fadeIn(100);
}

function hideLoading(){
    $("#loading").fadeOut(function(){
        $("#loading").remove();
    },50);
}

