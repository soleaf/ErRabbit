<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Modal -->
<div class="modal fade" id="popover_log" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="popover_log_title">Modal title</h4>
      </div>
      <div class="modal-body">
        <div class="btn-group" role="group" aria-label="...">
          <button type="button" class="btn btn-default btn-xs" id="popover_log_btn_graph"><span class="glyphicon glyphicon-signal" aria-hidden="true"></span> Graph</button>
          <button type="button" class="btn btn-default btn-xs" id="popover_log_btn_text"><span class="glyphicon glyphicon-console" aria-hidden="true"></span> Text</button>
          <button type="button" class="btn btn-default btn-xs" id="popover_log_btn_hideothers"><span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span> Hide library packages</button>
          <button type="button" class="btn btn-default btn-xs" id="popover_log_btn_showeothers"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> Show library packages</button>
        </div>
      </div>
      <div class="modal-body" id="popover_log_body">

      </div>
    </div>
  </div>
</div>