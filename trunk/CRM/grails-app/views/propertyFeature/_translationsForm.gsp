<%@ page import="crm.commands.PropertyFeatureByLanguageCommand" %>
<g:set var="propertyFeatureByLanguageCommand" value="${new PropertyFeatureByLanguageCommand(propertyFeature)}" />
<fieldset class="form">
        <dl class="feature-list">
           <label class="bold-title-label pf-language-field"><g:message code="propertyFeature.language.label" default="Language"/></label><label class="bold-title-label pf-name-field"><g:message code="propertyFeature.name.label" default="Name"/></label><label class="bold-title-label pf-plural-label"><g:message code="propertyFeature.plural.label" default="Name in Plural"/></label>
	       <g:each in="${propertyFeatureByLanguageCommand.items}" var="item1" status="pf">
		        <dd>
					<span class="">
						<label class="pf-language-field pf-line">${item1.language?.name}</label>
						<g:textField name="items[$pf].name" value="${(item1?.name)}" class="pf-name-field pf-line"/>
						<g:textField name="items[$pf].plural" value="${(item1?.plural)}" class="pf-name-field pf-line"/>
						<g:hiddenField name="items[$pf].language" value="${item1?.language?.id}"/>
					</span>
				</dd>
			</g:each>
		</dl>
		<g:hiddenField name="propertyFeature.id" value="${propertyFeature?.id}"/>
</fieldset>