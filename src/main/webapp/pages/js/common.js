/**
 * Created by soleaf on 2015. 4. 19..
 */
function showLoading(){

    if ($("#loading") == null){
        loading = "<div class='loading_box' id='loading'>" +
        "<div>loading....</div>" +
        "</div>";
        $("BODY").append(loading);
    }

    $("#loading").fadeIn(100);

}

function hideLoading(){
    $("#loading").fadeOut(100);
}

