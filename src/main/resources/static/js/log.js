/**
 * Created by soleaf on 2/21/15.
 */

var rabbitId;

$(document).ready(function(){

    // Get information
    rabbitId = $("#report-area").attr("data-rabbitId");

    initReportFeedButton();

    // init reportModal button event
    initReportModalButton();

    // Init calendarEvent
    initCalendarYear();
    initCalendarMonth();

    // Retrieve calendar
    if ($("#cal_y").val() != null && $("#cal_m").val() != null){
        var today = new Date();
        var dd = today.getDate();
        retrieveCalendar(rabbitId, $("#cal_y").val(), $("#cal_m").val(), dd);
        retrieveReports(rabbitId, 0, 100, $("#cal_y").val(), $("#cal_m").val(), dd);
    }

    //initCalendarValueChanged();

});

function initReportModalButton(){
    $("#popover_log_btn_graph").click(function(){
        $("#popover_log_body .graph").show();
        $("#popover_log_body .text").hide();
    });
    $("#popover_log_btn_text").click(function(){
        $("#popover_log_body .graph").hide();
        $("#popover_log_body .text").show();
    });
}

function retrieveCalendar(rabbitId, year, month, selectedDay) {
    showLoading();
    $.ajax({
        url: '/log/list_days.err?id=' + rabbitId + '&y=' + year + '&m=' + month +'&s=' + selectedDay,
        success: function (data) {
            if ($($.parseHTML(data)).filter("#login_page").length > 0) {
                alert("Session is expired");
                window.location.href = "/login.err";
            }
            else{
                $("#report-calendar").html("");
                $("#report-calendar").append(data);

                if (selectedDay > 0){
                    $("#cal_d").val(selectedDay);
                }

                $("#report-calendar .day").click(function(){
                    $("#cal_d").val($(this).attr("data-value"));
                    $("#report-list").html("");
                    retrieveReportsFromSelected();
                    $("#report-calendar .active").removeClass("active");
                    $(this).addClass("active");
                });
                hideLoading();
            }
        }
        ,fail: function(){
            // todo : fail
            alert("fail");
            hideLoading();
        }
    });
}

/**
 * Init CalendarEvent for Year Dropbox
 */
function initCalendarYear(){
    $("#dropdownMenu_year_dropdown LI A").click(function(){
        value = $(this).attr("data-value");
        $("#cal_y").val(value);
        $("#dropdownMenu_year_dropdown .value").text(value);
        reloadCalendarFromSelected();
    });
}

/**
 * Init CalendarEvent for Month Dropbox
 */
function initCalendarMonth(){
    $("#dropdownMenu_month_dropdown LI A").click(function(){
        value = $(this).attr("data-value");
        $("#cal_m").val(value);
        $("#dropdownMenu_month_dropdown .value").text(value);
        reloadCalendarFromSelected();
    });
}

function reloadCalendarFromSelected(){
    retrieveCalendar(rabbitId, $("#cal_y").val(), $("#cal_m").val(), -1);
    $("#report-list").html("");
}

function retrieveReportsFromSelected(){
    retrieveReportsFromSelectedByPage(0);
}

function retrieveReportsFromSelectedByPage(page){
    retrieveReports(rabbitId, page, 100, $("#cal_y").val(), $("#cal_m").val(), $("#cal_d").val());
}

function initReportFeedButton(){
    $("#report-feed").click(function(){
        retrieveReportsFromSelectedByPage($(this).attr("data-page"));
    });
}

/***
 * Get Reports
 * @param rabbitId
 * @param page
 * @param size
 */
function retrieveReports(rabbitId, page, size, y, m, d) {

    showLoading();
    $.ajax({
        url : '/log/list_data.err?id=' + rabbitId + '&page=' + page + '&size=' + size
        + '&y=' + y + '&m=' + m + '&d=' + d,
        success : function(data) {

            //$("#report-head").html(d + " th");
            $("#report-list").append(data);

            // PagingInfo
            totalPages = $("#report-list #page_total").val();
            if (totalPages > parseInt(page)+1){
                $("#report-feed").show();
                $("#report-feed").attr("data-page", parseInt(page)+1);
            }
            else{
                $("#report-feed").hide();
            }

            $("#report-list .report[data-e=true]").click(function(){

                // Report Detail Information Layer Toggle
                var row = $(this);
                $.get(row.data('poload'),function(d) {

                    $("#popover_log_title").html(row.find(".time").text() + " " + row.find(".level").text());
                    $("#popover_log_body").html(d);
                    $("#popover_log").modal();

                });
            });

            hideLoading();
        }
    });
}
