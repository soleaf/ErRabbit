/**
 * ErRabbit Web Console
 * soleaf, mintcode.org
 * https://github.com/soleaf/ErRabbit
 * log.js
 */

var rabbitId;
var isFilter = false; // filter applied status
var loadedGChart = false; // google chart load flag
google.load("visualization", "1", {packages: ["corechart"]});
google.setOnLoadCallback(function () {
    loadedGChart = true;
});

/**
 * Init
 */
$(document).ready(function () {

    // Get information
    rabbitId = $("#log-area").attr("data-rabbitId");

    initLogFeedButton();

    // init logModal button event
    initLogModalButton();

    // Init calendarEvent
    initCalendarYear();
    initCalendarMonth();

    // Filter
    initFilterButtons();
    if ($("#filter_init").val() == "true") {
        filterButtonToggle(true);
    }

    // Retrieve calendar
    if ($("#cal_y").val() != null && $("#cal_m").val() != null) {
        var dd = $("#cal_d").val();
        retrieveCalendar(rabbitId, $("#cal_y").val(), $("#cal_m").val(), dd, function () {
            retrievelogs(rabbitId, 0, 100, $("#cal_y").val(), $("#cal_m").val(), dd);
        });
    }
});

/**
 * Init Filter UI
 */
function initFilterButtons() {

    // Apply
    $("#filter_apply").click(function () {
        if ($("#filter_level").val() == "ALL" && $("#filter_class").val().length < 1) {
            alert("Input class");
            return;
        }
        filterButtonToggle(true);
        $("#log-list").html("");
        retrievelogs(rabbitId, 0, 100, $("#cal_y").val(), $("#cal_m").val(), $("#cal_d").val());
        $('#filterModal').modal('hide');
    });

    // Clear
    $("#filter_clear").click(function () {
        filterButtonToggle(false);
        $("#log-list").html("");
        retrievelogs(rabbitId, 0, 100, $("#cal_y").val(), $("#cal_m").val(), $("#cal_d").val());
        $('#filterModal').modal('hide');
    });
}

/**
 * Change ui after changing filter
 * @param active
 */
function filterButtonToggle(active) {

    isFilter = active;

    if (active) {

        // Hide chart
        showChart(false, null);

        $("#filter_button").addClass("filter-apply");

        var filterText = "";
        if (isFilter && $("#filter_level").val() != "ALL") {
            filterText = "level='" + $("#filter_level").val() + "'"
        }
        if (isFilter && $("#filter_class").val().length > 0) {
            if (filterText.length > 0) {
                filterText = filterText + " class='" + $("#filter_class").val() + "'";
            }
            else {
                filterText = filterText + "class='" + $("#filter_class").val() + "'";
            }
        }

        $("#filter_button_text").text(filterText);
    }
    else {
        $("#filter_button").removeClass("filter-apply");
        $("#filter_button_text").text("FILTER");
        $("#filter_level").val("ALL");
        $("#filter_class").val("");
    }
}

/**
 * Init log modal buttons
 */
function initLogModalButton() {
    $("#popover_log_btn_graph").click(function () {
        $("#popover_log_body .text").fadeOut(function () {
            $("#popover_log_body .graph").fadeIn();
        });
        $("#popover_log_btn_text").show();
        $(this).hide();
    });
    $("#popover_log_btn_text").click(function () {
        $("#popover_log_body .graph").fadeOut(function () {
            $("#popover_log_body .text").fadeIn();
        });
        $("#popover_log_btn_graph").show();
        $(this).hide();
    });
    $("#popover_log_btn_hideothers").click(function () {
        $("#popover_log_body .another-package-set").slideUp();
        ;
        $("#popover_log_btn_showeothers").show();
        $(this).hide();
    });
    $("#popover_log_btn_showeothers").click(function () {
        $("#popover_log_body .another-package-set").slideDown();
        $("#popover_log_btn_hideothers").show();
        $(this).hide();
    });
}

/**
 * Retrieve calendar data
 * @param rabbitId
 * @param year
 * @param month
 * @param selectedDay
 * @param callback
 */
function retrieveCalendar(rabbitId, year, month, selectedDay, callback) {
    showLoading();
    $.ajax({
        url: '/log/list_days.err?id=' + rabbitId + '&y=' + year + '&m=' + month + '&s=' + selectedDay,
        success: function (data) {
            sessionExpireCheck(data);

            $("#log-calendar").html("");
            $("#log-calendar").append(data);

            if (selectedDay > 0) {
                $("#cal_d").val(selectedDay);
            }

            // day cell click
            $("#log-calendar .day").click(function () {
                $("#page-status").hide();
                filterButtonToggle(false);
                $("#cal_d").val($(this).attr("data-value"));
                $("#log-list").html("");
                retrieveLogsFromSelected();
                $("#log-calendar .active").removeClass("active");
                $(this).addClass("active");
            });
            hideLoading();

            if (callback != null) {
                callback();
            }

        }
        , fail: function () {
            alert("fail");
            hideLoading();
        }
    });
}

function sessionExpireCheck(data) {
    if (data.indexOf("id=\"login_page\"") > -1) {
        alert("Session has expired");
        window.location.href = "/login.err";
    }
}

function retrieveGraph(rabbitId, year, month, selectedDay, callback) {
    showLoading();
    $.ajax({
        url: '/log/day_graph.err?id=' + rabbitId + '&y=' + year + '&m=' + month + '&d=' + selectedDay,
        success: function (data) {

            sessionExpireCheck(data);

            var dataJson = JSON.parse(data);
            showChart(true, function () {
                drawChart(dataJson);
                var retryCount = 0;
                while (loadedGChart == false && retryCount < 500) {
                    retryCount++;
                }
                hideLoading();

                if (callback != null) {
                    callback();
                }
            });


        }
        , fail: function () {
            alert("fail");
            hideLoading();
            showChart(false, null);
        }
    });
}
var chart
function drawChart(dataJson) {

    if (dataJson.data == null) {
        showChart(false, null);
        return;
    }

    var data = google.visualization.arrayToDataTable(dataJson.data);
    var options = {
        width: '100%',
        height: '100%',
        //chartArea: {width: '100%', height: '100%'},
        legend: 'none',
        chartArea: {left: 0, top: 0, width: '100%', height: '100%'},
        //titlePosition: 'in', axisTitlesPosition: 'in',
        backgroundColor: 'transparent',
        hAxis: {
            textPosition: 'in',
            min: 0,
            baselineColor: 'transparent',
            textStyle: {color: '#878787', fontSize: 9},
            gridlines: {count: 24, color: 'transparent'}
        },
        vAxis: {textPosition: 'in', min: 0, baselineColor: 'transparent', gridlines: {color: 'transparent', count: 0}},
        curveType: 'function',
        colors: dataJson.color,
        animation: {
            startup: true,
            easing: 'inAndOut',
            duration: 300
        }
    };

    if (chart == null) {
        chart = new google.visualization.LineChart(document.getElementById('chart'));
    }
    chart.draw(data, options);
    hideLoading();
}

function showChart(show, callBack) {
    if (show) {
        $("#timeline").slideDown(300, callBack);
    }
    else {
        $("#timeline").slideUp(300, callBack);
    }
}

/**
 * Init CalendarEvent for Year Dropbox
 */
function initCalendarYear() {
    $("#dropdownMenu_year_dropdown LI A").click(function () {
        var value = $(this).attr("data-value");
        $("#cal_y").val(value);
        $("#dropdownMenu_year_dropdown .value").text(value);
        reloadCalendarFromSelected();
        filterButtonToggle(false);
    });
}

/**
 * Init CalendarEvent for Month Dropbox
 */
function initCalendarMonth() {
    $("#dropdownMenu_month_dropdown LI A").click(function () {
        filterButtonToggle(false);
        var value = $(this).attr("data-value");
        $("#cal_m").val(value);
        $("#dropdownMenu_month_dropdown .value").text(value);
        reloadCalendarFromSelected();
    });
}

/**
 * Reload calendar
 */
function reloadCalendarFromSelected() {
    filterButtonToggle(false);
    $("#log-list").html("");
    retrieveCalendar(rabbitId, $("#cal_y").val(), $("#cal_m").val(), -1, null);
}

/**
 * Reload logs with current selected parameters
 */
function retrieveLogsFromSelected() {
    retrieveLogsFromSelectedByPage(0);
}

/**
 * Reload logs from selected page
 * @param page
 */
function retrieveLogsFromSelectedByPage(page) {

    retrievelogs(rabbitId, page, 100, $("#cal_y").val(), $("#cal_m").val(), $("#cal_d").val());

}

/**
 * init log feed button event
 */
function initLogFeedButton() {
    $("#log-feed").click(function () {
        retrieveLogsFromSelectedByPage($(this).attr("data-page"));
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
    if (isFilter && $("#filter_level").val() != "ALL") {
        url = url + "&level=" + $("#filter_level").val();
    }
    if (isFilter && $("#filter_class").val().length > 0) {
        url = url + "&class=" + $("#filter_class").val();
    }

    // Chart
    if (page == 0 && isFilter == false) {
        retrieveGraph(rabbitId, $("#cal_y").val(), $("#cal_m").val(), $("#cal_d").val(), null);
    }

    showLoading();
    $("#log-feed").hide();
    $.ajax({
        url: encodeURI(url)
        + '&y=' + y + '&m=' + m + '&d=' + d,
        success: function (data) {

            sessionExpireCheck(data);

            //$("#log-head").html(d + " th");
            $("#log-list").append(data);

            // PagingInfo
            var totalPages = $("#log-list #page_total").val();
            var totalElements = $("#log-list #logs_cnt").val();
            if (totalPages > parseInt(page) + 1) {
                $("#log-feed").fadeIn();
                $("#log-feed").attr("data-page", parseInt(page) + 1);
            }
            else {
                $("#log-feed").hide();
            }

            $("#status_page").text(page + 1);
            $("#status_totalpage").text(totalPages);
            $("#status_elements").text(totalElements);
            $("#page-status").show();


            // Log list item event
            $("#log-list .log[data-e=true]").click(function () {
                // log Detail Information Layer Toggle
                var row = $(this);
                $.get(row.data('poload'), function (d) {

                    if ($($.parseHTML(d)).filter("#login_page").length > 0) {
                        alert("Session is expired");
                        window.location.href = "/login.err";
                    }
                    else {
                        // init filter button
                        $("#popover_log_btn_showeothers").hide();
                        $("#popover_log_btn_hideothers").show();
                        $("#popover_log_btn_text").show();
                        $("#popover_log_btn_graph").hide();

                        $("#popover_log_title").html(row.find(".time").text() + " " + row.find(".level").text());
                        $("#popover_log_body").html(d);
                        $("#popover_log").modal();
                    }
                });
            });

            // log list item's category event
            $("#log-list .categoryName").click(function () {
                $("#filter_class").val($(this).text());
                $("#filter_apply").trigger("click");
            });

            hideLoading();
        }
    });
}
