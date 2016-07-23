<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title><g:message code="reportDesigner.step2.title"/></title>
    </head>
    <body>
        <a href="#create-buildingFeature" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <g:form>
	    <div class="nav" role="navigation">
	        <ul>
	            <li><g:actionSubmit action="step1" class="return" value="${message(code: 'reportDesigner.previous.step.button', default: 'Previous')}" onclick="return confirm('${message(code: 'reportDesigner.button.return.to.step1.confirm.message', default: 'Are you sure, All data will lost?')}');"/></li>
	            <li><g:actionSubmit action="step3" class="advance" value="${message(code: 'reportDesigner.next.step.button', default: 'Next')}" /></li>
	        </ul>
	    </div>
	        <div id="reportDesigner-step2" class="content scaffold-create" role="main">
	            <h1><g:message code="reportDesigner.step2.title" default="STEP 2"/></h1>  
			   	<fieldset class="form">
					<dl class="feature-list">
						<label class="bold-title-label report-field-name column-field report-column-name-field"><g:message code="reportDesigner.step2.column.name.label" default="Column"/></label><label class="bold-title-label report-field-title selected-field"><g:message code="reportDesigner.step2.column.selected.label" default="Selected"/></label><label class="bold-title-label report-field-title filterby-label"><g:message code="reportDesigner.step2.column.filter.by.label" default="Filter By"/></label><label class="bold-title-label report-field-title sortby-label"><g:message code="reportDesigner.step2.column.sort.by.label" default="Sort By"/></label><label class="bold-title-label report-field-title groupby-label"><g:message code="reportDesigner.step2.column.group.by.label" default="Group By"/></label>
						<g:each in="${reportDesigner.reportDesignerColumns}" var="item1" status="i">
							<dd>
								<span class="">
									<label class="report-column-name-field pf-line"><g:message code="${item1.getLabelName()}"/></label>
									<g:checkBox name="reportDesigner.reportDesignerColumns[$i].selected" value="${(item1?.selected)}" class="pf-name-field pf-line"/>
									<g:checkBox name="reportDesigner.reportDesignerColumns[$i].filterBy" value="${(item1?.filterBy)}" class="pf-name-field pf-line"/>
									<g:checkBox name="reportDesigner.reportDesignerColumns[$i].sortBy" value="${(item1?.sortBy)}" class="pf-name-field pf-line"/>
									<g:checkBox name="reportDesigner.reportDesignerColumns[$i].groupBy" value="${(item1?.groupBy)}" class="pf-name-field pf-line"/>
									<g:hiddenField name="reportDesigner.reportDesignerColumns[$i].sortOrder" value="${item1?.sortOrder}"/>
									<g:hiddenField name="reportDesigner.reportDesignerColumns[$i].dataType" value="${item1?.dataType}"/>
									<g:hiddenField name="reportDesigner.reportDesignerColumns[$i].groupOrder" value="${item1?.groupOrder}"/>
									<g:hiddenField name="reportDesigner.reportDesignerColumns[$i].tableName" value="${item1?.tableName}"/>
									<g:hiddenField name="reportDesigner.reportDesignerColumns[$i].propertyName" value="${item1?.propertyName}"/>
									<g:hiddenField name="reportDesigner.reportDesignerColumns[$i].foreignTableName" value="${item1?.foreignTableName}"/>
									<g:hiddenField name="reportDesigner.reportDesignerColumns[$i].foreignTableDisplay" value="${item1?.foreignTableDisplay}"/>
									<g:hiddenField name="reportDesigner.reportDesignerColumns[$i].columnWidth" value="${item1?.columnWidth}"/>
									<g:hiddenField name="reportDesigner.reportDesignerColumns[$i].filterCriteria" value="${item1?.filterCriteria}"/>
									<g:hiddenField name="reportDesigner.reportDesignerColumns[$i].primaryFilterValue" value="${item1?.primaryFilterValue}"/>
									<g:hiddenField name="reportDesigner.reportDesignerColumns[$i].secondaryFilterValue" value="${item1?.secondaryFilterValue}"/>
									
									<br/>
								</span>
							</dd>
						</g:each>
					</dl>
					<g:hiddenField name="reportDesigner.reportType" value="${reportDesigner?.reportType}"/>
					<g:hiddenField name="reportDesigner.name" value="${reportDesigner?.name}"/>
					<g:hiddenField name="reportDesigner.hasFilter" value="${reportDesigner?.hasFilter}"/>
					<g:hiddenField name="reportDesigner.hasGroup" value="${reportDesigner?.hasGroup}"/>
					<g:hiddenField name="reportDesigner.hasSort" value="${reportDesigner?.hasSort}"/>
					<g:hiddenField name="reportDesigner.reportFolder" value="${item1?.reportFolder?.id}"/>
					<g:hiddenField name="cameFromStep" value="2"/>
				</fieldset>
						
		  	    <fieldset class="buttons">
		  			<g:actionSubmit action="step1" class="return" value="${message(code: 'reportDesigner.previous.step.button', default: 'Previous')}" onclick="return confirm('${message(code: 'reportDesigner.button.return.to.step1.confirm.message', default: 'Are you sure, All data will lost?')}');"/>
	                <g:actionSubmit action="step3" class="advance" value="${message(code: 'reportDesigner.next.step.button', default: 'Next')}"/>
		  		</fieldset>
		  	</div>
		</g:form>
    </body>
</html>


