<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-contact" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <li><g:link class="return" action="show" controller="managedProperty" id="${contact?.managedProperty?.id}"><g:message code="managedProperty.label" default="Managed Property" /></g:link></li>
            </ul>
        </div>
        <div id="show-contact" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            
			<fieldset class="fieldcontain">
				<span id="managedProperty-label" class="property-label"><g:message code="contact.managedProperty.label" default="Concession"/></span>
		        <f:display bean="contact" property="managedProperty.id"/>
            	<span id="date-label" class="property-label"><g:message code="contact.date.label" default="Date"/></span>
		        <f:display bean="contact" property="date"/>
		        <span id="description-label" class="property-label"><g:message code="contact.description.label" default="Description"/></span>
		        <f:display bean="contact" property="description"/>
		        <span id="contactType-label" class="property-label"><g:message code="contact.contactType.label" default="Contact Type"/></span>
		        <f:display bean="contact" property="contactType"/>
		        <span id="crmUser-label" class="property-label"><g:message code="contact.crmUser.label" default="User"/></span>
		        <f:display bean="contact" property="crmUser"/>
		        <span id="client-label" class="property-label"><g:message code="contact.client.label" default="Client"/></span>
		        <f:display bean="contact" property="client"/>
            </fieldset>
            
            <g:form resource="${this.contact}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.contact}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
