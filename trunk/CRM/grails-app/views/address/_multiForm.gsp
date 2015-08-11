<fieldset class="form">
	<f:field bean="${parentBean? parentBean : 'address'}" property="${parentBean? 'address.streetOne' : 'streetOne'}"/>
	<f:field bean="${parentBean? parentBean : 'address'}" property="${parentBean?  'address.streetTwo' : 'streetTwo'}"/>
	<f:field bean="${parentBean? parentBean : 'address'}" property="${parentBean? 'address.number' : 'number'}"/>
	<f:field bean="${parentBean? parentBean : 'address'}" property="${parentBean? 'address.reference' : 'reference'}"/>
	<f:field bean="${parentBean? parentBean : 'address'}" property="${parentBean? 'address.addressLine' : 'addressLine'}">
		<g:textArea name="${property}" maxlength="500" value="${it.value}" rows="4" cols="70"/>
	</f:field>
	<f:field bean="${parentBean? parentBean : 'address'}" property="${parentBean? 'address.description' : 'description'}">
		<g:textArea name="${property}" maxlength="300" value="${it.value}" rows="4" cols="70"/>
	</f:field>
	<f:field bean="${parentBean? parentBean : 'address'}" property="${parentBean? 'address.code' : 'code'}"/>
	<f:field bean="${parentBean? parentBean : 'address'}" property="${parentBean? 'address.latitude' : 'latitude'}"/>
	<f:field bean="${parentBean? parentBean : 'address'}" property="${parentBean? 'address.longitude' : 'longitude'}"/>
	<f:field bean="${parentBean? parentBean : 'address'}" property="${parentBean? 'address.homePhone' : 'homePhone'}"/>
	<f:field bean="${parentBean? parentBean : 'address'}" property="${parentBean? 'address.city' : 'city'}" input-propId="${parentBean? (parentBean=='partner'? partner?.address?.city?.id : 'other_parent') : address?.city?.id}"/>
	<f:field bean="${parentBean? parentBean : 'address'}" property="${parentBean? 'address.neighborhood' : 'neighborhood'}" input-propId="${parentBean? (parentBean=='partner'? partner?.address?.neighborhood?.id : 'other_parent') : address?.neighborhood?.id}"/>
	<f:field bean="${parentBean? parentBean : 'address'}" property="${parentBean? 'address.zone' : 'zone'}" input-propId="${parentBean? (parentBean=='partner'? partner?.address?.zone?.id : 'other_parent') : address?.zone?.id}"/>
</fieldset>