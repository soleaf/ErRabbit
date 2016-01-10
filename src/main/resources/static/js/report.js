/**
 * ErRabbit Web Console
 * soleaf, mintcode.org
 * https://github.com/soleaf/ErRabbit
 * report.js
 */

/**
 * Init
 */
$(document).ready(function(){
    $("#empty").hide();
    feedReport(0, 50);
    initReportFeedButton();
    deletingAction();
});

/**
 * Init report feed button
 */
function initReportFeedButton(){
    $("#log-feed").click(function(){
        feedReport($(this).attr("data-page"), 50);
    });
}

/**
 * Feed report
 * @param page
 * @param size
 */
function feedReport(page, size) {

    showLoading();
    $("#report-feed").hide();
    $.ajax({
        url : '/report/list_data.err?page=' + page + '&size=' + size,
        success : function(data) {

            $(data).appendTo($('#report-list')).slideDown();
            if ($('#report-list li').length == 0){
                $("#empty").fadeIn();
            }

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

function deletingAction(){
    $('#deleteModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var deletingId = button.data('deleting-id');
        var modal = $(this);
        modal.find('#deleting-id').val(deletingId);
        modal.find('#deleting-date').text(button.data('deleting-date'));
    });
}
