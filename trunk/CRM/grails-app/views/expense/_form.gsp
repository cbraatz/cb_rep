
<fieldset class="form">
	<f:field bean="expense" property="date"/>
	<f:field bean="expense" property="description">
		<g:textArea name="${property}" maxlength="200" value="${it.value}" rows="4" cols="50"/>
	</f:field>
	<f:field bean="expense" property="amount"/>
	<f:field bean="expense" property="currency" widget-propId="${expense?.currency?.id}"/>
	<f:field bean="expense" property="vendor" widget-propId="${expense?.vendor?.id}"/>
	<f:field bean="expense" property="expenseType" widget-propId="${expense?.expenseType?.id}"/>
	<f:field bean="expense" property="isCredit"/>
	<div id="paymentPlan-id">
		<f:field bean="expense" property="paymentPlan" widget-propId="${expense?.paymentPlan?.id}"/>
	</div>
</fieldset>
<script>
	function displayOrHidePaymentPlan(){
		if($("#isCredit").is(":checked")){
			$("#paymentPlan-id").show();
		}else{
			$("#paymentPlan-id").hide();
		}
	}
	
	$("#isCredit").change(function() {
		displayOrHidePaymentPlan();
	});
	
	displayOrHidePaymentPlan();
</script>