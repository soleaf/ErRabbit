/**
 * Created by soleaf on 2015. 4. 19..
 */

$().ready(function(){

    $('#deleteModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var rabbitId = button.data('id');
        var modal = $(this);
        modal.find('.modal-title').text('Delete "' + rabbitId +'"');
        modal.find('.well').text('target id : "' + rabbitId +'"');
        modal.find('#deleting_id').val(rabbitId);
    })

});
