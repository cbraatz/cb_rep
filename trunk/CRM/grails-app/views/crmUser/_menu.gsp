<div class="more" id="crmUser-item">
	<label><g:message code="crmUser.label" default="User"/></label>
</div>
<ul class="nav-item-list" id="crmUser-opts">
	<li><g:link controller="crmUser" action="create"><g:message code="default.new.label" default="New"/></g:link></li>
	<li><g:link controller="crmUser" action="index"><g:message code="default.list.label" default="List"/></g:link></li>
</ul>
<script type="text/javascript">
	$(document).ready(function() {
		$("#crmUser-item").click(function() {
			displayOrHideOptions();
		});
	});
	
	function displayOrHideOptions(){
		var opts=$("#crmUser-opts");
		if(opts.is(':visible')){
			opts.hide();
		}else{
			opts.show();
		}
	}
</script>