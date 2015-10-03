/**
 * ErRabbit Web Console
 * soleaf, mintcode.org
 * https://github.com/soleaf/ErRabbit
 * rabbit.js
 */

/**
 * Init
 */
$().ready(function(){

    // DatePicker
    $("#clean-begin").datepicker({ dateFormat: "yy-mm-dd"});
    $("#clean-end").datepicker({ dateFormat: "yy-mm-dd" });

    // Delete rabbit modal
    $('#deleteModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var rabbitId = button.data('id');
        var modal = $(this);
        modal.find('.modal-title').text('Delete "' + rabbitId +'"');
        modal.find('.well').text('target id : "' + rabbitId +'"');
        modal.find('#deleting_id').val(rabbitId);
    })

    // Clean logs modal
    $('#cleanModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var rabbitId = button.data('id');
        var modal = $(this);
        modal.find('.modal-title').text('Clean "' + rabbitId +'"');
        modal.find('.well').text('target id : "' + rabbitId +'"');
        modal.find('#clean_id').val(rabbitId);
    })

    $("#clean-submit").click(function(){
        if (!dateValidation($("#clean-begin").val())){
            alert("Invalid begin date format");
            return false;
        }
        if (!dateValidation($("#clean-end").val())){
            alert("Invalid end date format");
            return false;
        }
        var begin = new Date($("#clean-begin").val());
        var end = new Date($("#clean-end").val());
        var timeDiff = end.getTime() - begin.getTime();
        if (timeDiff < 0){
            alert("End date should be after before date");
            return false;
        }
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


});
