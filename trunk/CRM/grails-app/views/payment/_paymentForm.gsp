
<fieldset class="form">
	<div class="fieldcontain">
		<span id="name-label" class="property-label"><g:message code="xPayment.internalID.label" default="Income/Expense Payment Internal Id"/></span>
		<span class="property-value" aria-labelledby="payment-label">${payment.incomePayment? payment.incomePayment.internalID :(payment.expensePayment? payment.expensePayment.internalID : 'unknown payment')}</span>
	</div>
	<f:field bean="payment" property="amount" label="xPayment.amount.label"/>
	<f:field bean="payment" property="date"/>
	
	<h1 class="paymentTitle"><g:message code="payment.title.label"/></h1>
	<f:field bean="payment" property="inAmount"/>
	<f:field bean="payment" property="inCurrency" widget-propId="${payment?.inCurrency?.id}"/>
	<f:field bean="payment" property="inPaymentMethod" widget-propId="${payment?.inPaymentMethod?.id}"/>
	
	<h1 class="paymentTitle"><g:message code="payment.document.title.label"/></h1>
	<f:field bean="payment" property="inPaymentDocument.internalID"/>
	<f:field bean="payment" property="inPaymentDocument.startDate"/>
	<f:field bean="payment" property="inPaymentDocument.endDate"/>
	<f:field bean="payment" property="inPaymentDocument.bank" widget-propId="${payment?.inPaymentDocument?.bank?.id}"/>
	
	
	<g:hiddenField name="incomePayment" value="${payment?.incomePayment?.id}"/>
	<g:hiddenField name="expensePayment" value="${payment?.expensePayment?.id}"/>
	
	<g:hiddenField name="outPaymentMethod" value="${payment?.outPaymentMethod?.id}"/>
	<g:hiddenField name="outCurrency" value="${payment?.outCurrency?.id}"/>
	<g:hiddenField name="outAmount" value="${crm.Utils.formatDecimalsForInput(payment?.outAmount)}"/>
	
</fieldset>
<script type="text/javascript">
	$(document).ready(function() {
		$("#amount").attr("readonly", "readonly");//disable amount field
				
		//display change modal if change > 0
		var changeAmount=${payment.outAmount};
		if(changeAmount > 0){
			$('#display-change-modal-dialog')[0].click();
		}
	});	
</script>
