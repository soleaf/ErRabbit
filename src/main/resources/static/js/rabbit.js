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

});
