<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<asset:stylesheet href="modal.css"/>		 
<!-- Bootstrap core CSS -->
<!--<link href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.1.1/css/bootstrap.css" rel="stylesheet" media="screen"/> original remplazado por el de arreiba "map.css"-->
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="http://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7/html5shiv.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/respond.js/1.3.0/respond.js"></script>
<![endif]-->


	<div class="container">
		<!-- Modal -->
		<div class="modal fade" id="modalFrm">
		    <div class="modal-dialog modal-lg">
				<div class="modal-content">			        	
					<g:render template="${modalFrm}"/>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</div><!-- /container -->
		 
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<asset:javascript src="jquery-2.1.3.js"/>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<asset:javascript src="bootstrap_min.js"/>