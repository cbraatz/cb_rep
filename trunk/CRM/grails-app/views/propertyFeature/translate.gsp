<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'propertyFeature.label', default: 'PropertyFeature')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-propertyFeature" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="return" action="show" id="${propertyFeature.id}"><g:message code="propertyFeature.label" default="Property Feature" /></g:link></li>
            </ul>
        </div>
        <div id="create-propertyFeature" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.propertyFeature}">
	            <ul class="errors" role="alert">
	                <g:eachError bean="${this.propertyFeature}" var="error">
	                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
	                </g:eachError>
	            </ul>
            </g:hasErrors>
            <g:form action="saveTranslations">
                
				<g:render template="translationsForm"/>
				
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.save.label', default: 'Save')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
