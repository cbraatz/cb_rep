<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'concession.label', default: 'Concession')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-concession" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-concession" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.concession}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.concession}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
			<fieldset class="fieldcontain">
            	<span id="name-label" class="property-label"><g:message code="concession.startDate.label" default="Start Date"/></span>
		        <f:display bean="concession" property="startDate"/>
		        <span id="name-label" class="property-label"><g:message code="concession.endDate.label" default="End Date"/></span>
				<f:display bean="concession" property="endDate"/>
				
				<span id="totalPrice-label" class="property-label"><g:message code="concession.totalPrice.label" default="Total Price"/></span>
				<f:display bean="concession" property="totalPrice"/>
				<span id="totalCommission-label" class="property-label"><g:message code="concession.totalCommission.label" default="Total Commission"/></span>
				<f:display bean="concession" property="totalCommission"/>
				
				<span id="name-label" class="property-label"><g:message code="concession.client.label" default="Client"/></span>
				<f:display bean="concession" property="client"/>
				<span id="name-label" class="property-label"><g:message code="concession.description.label" default="Description"/></span>
				<f:display bean="concession" property="description"/>
				<span id="name-label" class="property-label"><g:message code="concession.barter.label" default="Barter"/></span>
				<f:display bean="concession" property="barter"/>
				<span id="name-label" class="property-label"><g:message code="concession.financing.label" default="Financing"/></span>
				<f:display bean="concession" property="financing"/>
				<span id="name-label" class="property-label"><g:message code="concession.isNegotiable.label" default="Is Negotiable"/></span>
				<f:display bean="concession" property="isNegotiable"/>
				<span id="name-label" class="property-label"><g:message code="concession.publishInMLS.label" default="Publish in MLS"/></span>
				<f:display bean="concession" property="publishInMLS"/>
				<span id="name-label" class="property-label"><g:message code="concession.publishInPortals.label" default="Publish in Portals"/></span>
				<f:display bean="concession" property="publishInPortals"/>
				<span id="name-label" class="property-label"><g:message code="concession.crmUser.label" default="Agent"/></span>
				<f:display bean="concession" property="crmUser"/>
				<span id="name-label" class="property-label"><g:message code="concession.isForRent.label" default="Is For Rent"/></span>
				<f:display bean="concession" property="isForRent"/>
            </fieldset>
            
            <g:form resource="${this.concession}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.concession}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <g:actionSubmit action="addEditFiles" class="addEditFiles" value="${message(code: 'default.button.add.edit.documents.label', default: 'Add or Edit Documents')}"/>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
            <h1><g:message code="concession.managedProperties.label"/></h1>
            <f:table collection="${concession.managedProperties}" properties="['title', 'area', 'price', 'currency']"/>
            <h1><g:message code="concession.contracts.label"/></h1>
            <f:table collection="${concession.contracts}" properties="['startDate', 'endDate', 'contractType']"/>
        </div>
    </body>
</html>