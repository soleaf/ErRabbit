$(document).ready(function(){
    feedReport(0, 50);
    initReportFeedButton();
});


function initReportFeedButton(){
    $("#log-feed").click(function(){
        feedReport($(this).attr("data-page"), 50);
    });
}

function feedReport(page, size) {

    showLoading();
    $("#report-feed").hide();
    $.ajax({
        url : '/report/list_data.err?page=' + page + '&size=' + size,
        success : function(data) {
            $("#report-list").append(data);

            // PagingInfo
            var totalPages = $("#log-list #page_total").val();
            if (totalPages > parseInt(page)+1){
                $("#report-feed").fadeIn();
                $("#report-feed").attr("data-page", parseInt(page)+1);
            }
            else{
                $("#report-feed").hide();
            }
            hideLoading();
        }
    });
}
