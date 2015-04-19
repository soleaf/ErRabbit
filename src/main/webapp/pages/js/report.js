/**
 * Created by soleaf on 2/21/15.
 */

var rabbitId;

$(document).ready(function(){

    // Get information
    rabbitId = $("#report-area").attr("data-rabbitId");

    // Retrieve Calendar
    retrieveCalendar(rabbitId, 2015, 4);
    initReportFeedButton();

    // Init CalendarEvent
    initCalendarYear();
    initCalendarMonth();

    //initCalendarValueChanged();

});

function retrieveCalendar(rabbitId, year, month) {
    showLoading();
    $.ajax({
        url: '/report/list_days.err?id=' + rabbitId + '&y=' + year + '&m=' + month,
        success: function (data) {
            $("#report-calendar").html("");
            $("#report-calendar").append(data);

            $("#report-calendar .day").click(function(){
                $("#cal_d").val($(this).attr("data-value"));
                $("#report-list").html("");
                retrieveReportsFromSelected();
                $("#report-calendar .active").removeClass("active");
                $(this).addClass("active");
            });

            hideLoading();
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
    retrieveCalendar(rabbitId, $("#cal_y").val(), $("#cal_m").val());
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
        url : '/report/list_data.err?id=' + rabbitId + '&page=' + page + '&size=' + size
        + '&y=' + y + '&m=' + m + '&d=' + d,
        success : function(data) {

            $("#report-list").append(data);

            // PagingInfo
            totalPages = $("#report-list #page_total").val();
            if (totalPages > page+1){
                $("#report-feed").show();
                $("#report-feed").attr("data-page", page+1);
            }
            else{
                $("#report-feed").hide();
            }

            $("#report-list .report").click(function(){
                // Report Detail Information Layer Toggle
                var id = $(this).attr("data-id");
                $("#report-list .report-detail-popup").hide();
                $(this).parent().find(".report-detail-popup[data-id='" + id + "']").toggle();
                $("#report-list .report_active").removeClass("report_active");
                $(this).addClass("report_active");
            });

            $("#report-list .close").click(function() {
                // Close button
                $(this).parent().parent().hide();
            });

            $("#report-list .report-detail-popup .base-package-toggle").click(function(){
                // Report Detai Information Graph BasePackage Toggle
                var id = $(this).attr("data-id");
                var toggle = $(this).attr("data-toggle");

                if (toggle == "1"){
                    $(this).parent().find(".base-package").css({display:"inline-block"});
                    $(this).parent().find(".base-package-arrow").css({display:"block"});
                    $(this).parent().find(".base-package-fileName").css({display:"block"});
                    $(this).attr("data-toggle","0");
                }else{
                    $(this).parent().find(".base-package").css({display:"none"});
                    $(this).parent().find(".base-package-arrow").css({display:"none"});
                    $(this).parent().find(".base-package-fileName").css({display:"none"});
                    $(this).attr("data-toggle","1");
                }

            });

            $("#report-list .report-tab li").click(function(){
                // Tab Click to change StackTrace view mode

                var toActiveTab = $(this).attr("data-tab")
                if (toActiveTab == "graph"){
                    $(this).parent().parent().find(".graph").show();
                    $(this).parent().parent().find(".text").hide();
                    $(this).parent().find("li[data-tab='graph']").addClass("active");
                    $(this).parent().find("li[data-tab='text']").removeClass("active");
                }
                else{
                    $(this).parent().parent().find(".graph").hide();
                    $(this).parent().parent().find(".text").show();
                    $(this).parent().find("li[data-tab='text']").addClass("active");
                    $(this).parent().find("li[data-tab='graph']").removeClass("active");
                }

            });

            hideLoading();
        }
    });
}
