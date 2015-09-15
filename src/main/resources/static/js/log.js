/**
 * Created by soleaf on 2/21/15.
 */

var rabbitId;
var isFilter;

$(document).ready(function(){

    // Get information
    rabbitId = $("#log-area").attr("data-rabbitId");

    initlogFeedButton();

    // init logModal button event
    initlogModalButton();

    // Init calendarEvent
    initCalendarYear();
    initCalendarMonth();

    // Filter
    initFilterButtons();
    if ($("#filter_init").val() == "true"){
        filterButtonToggle(true);
    }

    // Retrieve calendar
    if ($("#cal_y").val() != null && $("#cal_m").val() != null){
        var dd = $("#cal_d").val();
        retrieveCalendar(rabbitId, $("#cal_y").val(), $("#cal_m").val(), dd, function(){
            retrievelogs(rabbitId, 0, 100, $("#cal_y").val(), $("#cal_m").val(), dd);
        });
    }

    //initCalendarValueChanged();

});

function initFilterButtons(){
    $("#filter_apply").click(function(){
        if($("#filter_level").val() == "ALL" && $("#filter_class").val().length < 1){
            alert("Input class");
            return;
        }
        filterButtonToggle(true);
        $("#log-list").html("");
        retrievelogs(rabbitId, 0, 100, $("#cal_y").val(), $("#cal_m").val(), $("#cal_d").val());
        $('#filterModal').modal('hide');
    });
    $("#filter_clear").click(function(){
        filterButtonToggle(false);
        $("#log-list").html("");
        retrievelogs(rabbitId, 0, 100, $("#cal_y").val(), $("#cal_m").val(), $("#cal_d").val());
        $('#filterModal').modal('hide');
    });
}

function filterButtonToggle(active){
    isFilter = active;
    if (active){
        $("#filter_button").addClass("filter-apply");

        var filterText = "";
        if (isFilter && $("#filter_level").val() != "ALL"){
            filterText = "level='"+$("#filter_level").val() + "'"
        }
        if (isFilter && $("#filter_class").val().length > 0){
            if (filterText.length > 0){
                filterText = filterText + " class='" + $("#filter_class").val() +"'";
            }
            else{
                filterText = filterText + "class='" + $("#filter_class").val() +"'";
            }
        }

        $("#filter_button_text").text(filterText);
    }
    else{
        $("#filter_button").removeClass("filter-apply");
        $("#filter_button_text").text("FILTER");
        $("#filter_level").val("ALL");
        $("#filter_class").val("");
    }
}

function initlogModalButton(){
    $("#popover_log_btn_graph").click(function(){
        $("#popover_log_body .graph").show();
        $("#popover_log_body .text").hide();
    });
    $("#popover_log_btn_text").click(function(){
        $("#popover_log_body .graph").hide();
        $("#popover_log_body .text").show();
    });
}

function retrieveCalendar(rabbitId, year, month, selectedDay, callback) {
    showLoading();
    $.ajax({
        url: '/log/list_days.err?id=' + rabbitId + '&y=' + year + '&m=' + month +'&s=' + selectedDay,
        success: function (data) {
            if ($($.parseHTML(data)).filter("#login_page").length > 0) {
                alert("Session has expired");
                window.location.href = "/login.err";
            }
            else{
                $("#log-calendar").html("");
                $("#log-calendar").append(data);

                if (selectedDay > 0){
                    $("#cal_d").val(selectedDay);
                }

                $("#log-calendar .day").click(function(){
                    filterButtonToggle(false);
                    $("#cal_d").val($(this).attr("data-value"));
                    $("#log-list").html("");
                    retrievelogsFromSelected();
                    $("#log-calendar .active").removeClass("active");
                    $(this).addClass("active");
                });
                hideLoading();
                if (callback != null){
                    callback();
                }
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
        filterButtonToggle(false);
    });
}

/**
 * Init CalendarEvent for Month Dropbox
 */
function initCalendarMonth(){
    $("#dropdownMenu_month_dropdown LI A").click(function(){
        filterButtonToggle(false);
        var value = $(this).attr("data-value");
        $("#cal_m").val(value);
        $("#dropdownMenu_month_dropdown .value").text(value);
        reloadCalendarFromSelected();
    });
}

function reloadCalendarFromSelected(){
    filterButtonToggle(false);
    $("#log-list").html("");
    retrieveCalendar(rabbitId, $("#cal_y").val(), $("#cal_m").val(), -1, null);
}

function retrievelogsFromSelected(){
    retrievelogsFromSelectedByPage(0);
}

function retrievelogsFromSelectedByPage(page){
    retrievelogs(rabbitId, page, 100, $("#cal_y").val(), $("#cal_m").val(), $("#cal_d").val());
}

function initlogFeedButton(){
    $("#log-feed").click(function(){
        retrievelogsFromSelectedByPage($(this).attr("data-page"));
    });
}

/***
 * Get logs
 * @param rabbitId
 * @param page
 * @param size
 */
function retrievelogs(rabbitId, page, size, y, m, d) {

    var url = '/log/list_data.err?id=' + rabbitId + '&page=' + page + '&size=' + size;
    if (isFilter && $("#filter_level").val() != "ALL"){
        url = url + "&level=" + $("#filter_level").val();
    }
    if (isFilter && $("#filter_class").val().length > 0){
        url = url + "&class=" +  $("#filter_class").val();
    }

    showLoading();
    $("#log-feed").hide();
    $.ajax({
        url : encodeURI(url)
        + '&y=' + y + '&m=' + m + '&d=' + d,
        success : function(data) {

            //$("#log-head").html(d + " th");
            $("#log-list").append(data);

            // PagingInfo
            var totalPages = $("#log-list #page_total").val();
            if (totalPages > parseInt(page)+1){
                $("#log-feed").fadeIn();
                $("#log-feed").attr("data-page", parseInt(page)+1);
            }
            else{
                $("#log-feed").hide();
            }

            $("#log-list .log[data-e=true]").click(function(){
                // log Detail Information Layer Toggle
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
