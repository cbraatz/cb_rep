<%@ page import="crm.commands.FeatureByBuildingCommand" %>
<%@ page import="crm.commands.FeatureByPropertyCommand" %>
<fieldset class="form">
	<div class="fieldcontain">
		<f:field bean="building" property="builtSize"/>
		<f:field bean="building" property="builtYear"/>
		<f:field bean="building" property="buildingType" widget-propId="${building?.buildingType?.id}"/>
		<f:field bean="building" property="buildingCondition" widget-propId="${building?.buildingCondition?.id}"/>
		<f:field bean="building" property="buildingDescription">
			<g:textArea name="${property}" maxlength="1000" value="${it.value}" rows="5" cols="60"/>
		</f:field>
	</div>
</fieldset>