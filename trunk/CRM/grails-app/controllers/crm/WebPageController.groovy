package crm

import static org.springframework.http.HttpStatus.*
import crm.exception.WebPageCreationException;
import grails.transaction.Transactional
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Transactional(readOnly = true)
class WebPageController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
		ManagedProperty managedProperty;
		if(params.pid != null){
			managedProperty=ManagedProperty.get(params.pid);
			if(managedProperty != null){
		        params.max = Math.min(max ?: 10, 100)
		        respond managedProperty, model:[webPageCount: managedProperty.webPages.size()]
			}else{
				render(view:'/error', model:[message: message(code: 'default.invalid.paramethers.error', args: ["pid = "+params.pid])]);
			}
		}else{
			render(view:'/error', model:[message: message(code: 'default.invalid.paramethers.error', args: ["pid = null"])]);
		}
    }

    def show(WebPage webPage) {
		webPage.updateWebPageUrl();
        respond webPage
    }

    def create() {
		WebPage webPage=new WebPage(params);
		if(params.pid!=null){
			ManagedProperty managedProperty=ManagedProperty.get(params.pid);
			Concession concession=managedProperty.concession;
			webPage.domain=Domain.findByLocale(Locale.getDefaultLocale());
			webPage.agentComment=AgentComment.findByLocale(webPage.domain.locale);
			webPage.price=(managedProperty.publicCreditPrice ? managedProperty.publicCreditPrice : managedProperty.publicCashPrice);
			KeyWord kw=KeyWord.findByLocaleAndPropertyType(webPage.domain.locale, managedProperty.propertyType);
			webPage.keyWords=(kw!=null?kw.getExtendedKeys(managedProperty):"");
			OperationType ot=OperationType.findByLanguage(webPage.domain.locale.language);
			webPage.operationType=(concession.isForRent?ot.rent:ot.sale);
			webPage.managedProperty=managedProperty;
			webPage.inWeb=false;
			if(!webPage.managedProperty.hasImagesForWeb()) {
				webPage.errors.rejectValue('',message(code:'managedProperty.images.required.error').toString());
			}			
		}
		respond webPage
    }
	
    @Transactional
    def save(WebPage webPage) {
        if (webPage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

		webPage=this.validateObject(webPage);
		
        if (webPage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond webPage.errors, view:'create'
            return
        }
        if(webPage.save(flush:true)){
			List<String> errList1=this.transferImagesToWeb(webPage);
			List<String> errList2=this.createOrUpdateWebPage(webPage);
			if(!errList1.empty){
				errList1.each{
					webPage.errors.rejectValue('',"Error:"+it);
				}
			}
			if(!errList2.empty){
				errList2.each{
					webPage.errors.rejectValue('',"Error:"+it);
				}
			}
		}else{
			webPage.errors.rejectValue('',message(code:'webPage.save.error').toString());
		}
		
		if (webPage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond webPage.errors, view:'create'
            return
        }
		
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'webPage.label', default: 'WebPage'), webPage.id])
                redirect webPage
            }
            '*' { respond webPage, [status: CREATED] }
        }
    }
	private WebPage validateObject(WebPage webPage){
		if (webPage.title) {
			if (webPage.title.charAt(webPage.title.length()-1)!='.') {
				webPage.errors.rejectValue('title',message(code:'webPage.title.point.finish.required.error').toString());
			}
		}
		if (webPage.summary) {
			if (webPage.summary.charAt(webPage.summary.length()-1)!='.') {
				webPage.errors.rejectValue('summary',message(code:'webPage.summary.point.finish.required.error').toString());
			}
		}
		if (webPage.firstDescription) {
			if (webPage.firstDescription.charAt(webPage.firstDescription.length()-1)!='.') {
				webPage.errors.rejectValue('firstDescription',message(code:'webPage.firstDescription.point.finish.required.error').toString());
			}
		}
		if (webPage.secondDescription) {
			if (webPage.secondDescription.charAt(webPage.secondDescription.length()-1)!='.') {
				webPage.errors.rejectValue('secondDescription',message(code:'webPage.secondDescription.point.finish.required.error').toString());
			}
		}
		if (webPage.thirdDescription) {
			if (webPage.thirdDescription.charAt(webPage.thirdDescription.length()-1)!='.') {
				webPage.errors.rejectValue('thirdDescription',message(code:'webPage.thirdDescription.point.finish.required.error').toString());
			}
		}
		if (webPage.callToAction) {
			if (webPage.callToAction.charAt(webPage.callToAction.length()-1)!='.') {
				webPage.errors.rejectValue('callToAction',message(code:'webPage.callToAction.point.finish.required.error').toString());
			}
		}
		if (webPage.agentComment) {
			if (webPage.agentComment.charAt(webPage.agentComment.length()-1)!='.') {
				webPage.errors.rejectValue('agentComment',message(code:'webPage.agentComment.point.finish.required.error').toString());
			}
		}		
		return webPage;
	}
	private List<String> createOrUpdateWebPage(WebPage webPage){
		List<String> errs=new ArrayList<String>();
		try{
			String pageContainer=webPage.getWebPagePath();
			String title=GUtils.replaceIncorrectChars(webPage.title);

			//def f = request.getFile('photo')
			new File(pageContainer).mkdirs()
			String filePath=pageContainer+Utils.getLocalSeparatorChar()+title+webPage.id+".html";//t�tulo finaliza con . que es remplazado por _ y luego va el id de la WebPage
			String fileContent=GUtils.readFile(webPage.getDomainRealPathForWeb()+Utils.getLocalSeparatorChar()+"real_estate_detail.html");
			Concession conc=webPage.managedProperty.concession;
			
			//PTITLE
			int idx=fileContent.indexOf("@#PTITLE");
			int idx2;
			String str;
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#PTITLE"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#PTITLE"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				fileContent=fileContent.replace(str, webPage.title);
			}
			//PDESC
			idx=fileContent.indexOf("@#PDESC");
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#PDESC"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#PDESC"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				fileContent=fileContent.replace(str, webPage.summary);
			}
			//HEADER
			idx=fileContent.indexOf("@#HEADER");
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#HEADER"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#HEADER"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				fileContent=fileContent.replace(str, webPage.title);
			}
			//RESUM
			idx=fileContent.indexOf("@#RESUM");
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#RESUM"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#RESUM"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				fileContent=fileContent.replace(str, webPage.summary);
			}
			//FIRSTP
			idx=fileContent.indexOf("@#FIRSTP");
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#FIRSTP"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#FIRSTP"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				fileContent=fileContent.replace(str, webPage.firstDescription);
			}
			//FIRSTP
			idx=fileContent.indexOf("@#SECONDP");
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#SECONDP"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#SECONDP"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				fileContent=fileContent.replace(str, webPage.secondDescription);
			}
			//FIRSTP
			idx=fileContent.indexOf("@#THIRDP");
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#THIRDP"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#THIRDP"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				fileContent=fileContent.replace(str, webPage.thirdDescription);
			}
			//FIRSTP
			idx=fileContent.indexOf("@#CALLTOACT");
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#CALLTOACT"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#CALLTOACT"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				fileContent=fileContent.replace(str, webPage.callToAction);
			}
			//PKEYW
			idx=fileContent.indexOf("@#PKEYW");
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#PKEYW"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#PKEYW"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				fileContent=fileContent.replace(str, webPage.keyWords);
			}
			//PRICE
			idx=fileContent.indexOf("@#PRICE");
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#PRICE"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#PRICE"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				fileContent=fileContent.replace(str, webPage.price);
			}
			//ID
			idx=fileContent.indexOf("@#ID");
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#ID"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#ID"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				fileContent=fileContent.replace(str, webPage.managedProperty.id.toString());
			}
			//PDESC
			idx=fileContent.indexOf("@#TYPE");
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#TYPE"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#TYPE"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				fileContent=fileContent.replace(str, webPage.operationType);
			}
			//PF
			def fbp=webPage.managedProperty.featuresByProperty;
			idx=fileContent.indexOf("@#PF");
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#PF"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#PF"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				if(fbp.size() > 0){
					String newStr, prevStr;
					boolean fir=true;
					fbp.each{
						if(null != it.propertyFeature.defaultWebIcon){//si tiene icono quiere decir que es para agregar a la web
							newStr=str.replace("@@PFICON@@",it.propertyFeature.defaultWebIcon);
							newStr=newStr.replace("@@PFNAME@@"," "+(it.propertyFeature.hasValue? it.value+" "+(it.value == 1 ? it.propertyFeature.name : it.propertyFeature.plural) : it.propertyFeature.name));
							if(fir){
								newStr=newStr.substring(4,newStr.length()-2);
								idx=fileContent.indexOf(str);
								if(idx>=0){
									fileContent=fileContent.replace(str, newStr);
								}else{
									errs.add(message(code: 'webPage.template.property.feature.replace.error', args: [str]).toString());
									CrmLogger.logError(this.getClass(), message(code: 'webPage.template.property.feature.replace.error', args: [str]).toString());
								}
								fir=false;
								str=str.substring(4,str.length()-2);
							}else{
								int x=fileContent.indexOf(prevStr)+prevStr.length();
								String str1=fileContent.substring(0,x);
								String str2=fileContent.substring(x+1);
								fileContent=str1+"\n"+newStr+"\n"+str2;
							}
							prevStr=new String(newStr);
						}
					}
				}else{
					fileContent=fileContent.replace(str, "");//borra el tag por defecto si no hay property features que mostrar
				}
			}
			//AGENTCOM
			idx=fileContent.indexOf("@#AGENTCOM");
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#AGENTCOM"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#AGENTCOM"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				fileContent=fileContent.replace(str, webPage.agentComment);
			}
			//AGENTPH
			idx=fileContent.indexOf("@#AGENTPH");
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["@#AGENTPH"]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["@#AGENTPH"]).toString());
			}else{
				idx2=fileContent.indexOf("#@",idx+2);
				str=fileContent.substring(idx,idx2+2);
				fileContent=fileContent.replace(str, grailsApplication.config.getProperty('web.image.partner')+Utils.getLocalSeparatorChar()+conc.crmUser.partner.id + Utils.getLocalSeparatorChar() + "profile.jpg");
			}
			//Holders.config.getProperty('web.image.partner') se usa para obtener los datos de application.yml desde la domain class
			//IMPORT - LINKS
			//#1
			str="rel='stylesheet' href=\"";
			idx=fileContent.indexOf(str);
			if(idx>=0){
				fileContent=fileContent.replace(str, "rel='stylesheet' href=\""+webPage.domain.realPath+Utils.getLocalSeparatorChar());
			}else{
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["#1: "+str]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["#1: "+str]).toString());
			}
			//#2
			str="<script src=\"js";
			idx=fileContent.indexOf(str);
			if(idx>=0){
				fileContent=fileContent.replace(str, "<script src=\""+webPage.domain.realPath+Utils.getLocalSeparatorChar()+"js");
			}else{
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["#2: "+str]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["#2: "+str]).toString());
			}
			//#3
			str="<script src=\'js";
			idx=fileContent.indexOf(str);
			if(idx>=0){
				fileContent=fileContent.replace(str, "<script src=\'"+webPage.domain.realPath+Utils.getLocalSeparatorChar()+"js");
			}else{
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["#3: "+str]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["#3: "+str]).toString());
			}
			
			//adding images
			String direPath=webPage.domain.realPath+Utils.getLocalSeparatorChar()+grailsApplication.config.getProperty('web.image.property')+Utils.getLocalSeparatorChar()+webPage.managedProperty.id;
			def dire = new File(direPath);
			def fir=true;
			int addedCount=0;
			String newStr, prevStr=null;
			if( dire.exists() ){
				if(dire.isDirectory()){
					if(dire.list().length>0){
						dire.eachFile(){ file->
							UploadedImage uploadedImage=UploadedImage.findByFileName(file.name);
							if(uploadedImage){
								if(uploadedImage.addToWeb){
									newStr="<li data-transition=\"slotfade-vertical\" data-slotamount=\"7\"><img data-retina src=\""+dire.getAbsolutePath().replace("\\", "/")+"/"+file.name+"\"></li>";
									if(fir){
										String oldStr="<li data-transition=\"slotfade-vertical\" data-slotamount=\"7\"><img data-retina src=\"img/crm/home/b3.jpg\"></li>";
										idx=fileContent.indexOf(oldStr);
										if(idx>=0){
											fileContent=fileContent.replace(oldStr, newStr);
										}else{
											errs.add(message(code: 'webPage.template.property.image.replace.error', args: [oldStr]).toString());
											CrmLogger.logError(this.getClass(), message(code: 'webPage.template.property.image.replace.error', args: [oldStr]).toString());
										}
										fir=false;
									}else{
										int x=fileContent.indexOf(prevStr)+prevStr.length();
										String str1=fileContent.substring(0,x);
										String str2=fileContent.substring(x+1);
										fileContent=str1+"\n"+newStr+"\n"+str2;
									}
									addedCount++;
									prevStr=new String(newStr);
								}
							}else{
								errs.add(message(code: 'webPage.file.not.found.error', args: [file.name]).toString());
								CrmLogger.logError(this.getClass(), message(code: 'webPage.file.not.found.error', args: [file.name]).toString());
							}
						}
					}else{
						errs.add(message(code: 'webPage.directory.empty.error', args: [direPath]).toString());
						CrmLogger.logError(this.getClass(), message(code: 'webPage.directory.empty.error', args: [direPath]).toString());
					}
				}else{
					errs.add(message(code: 'webPage.directory.is.not.directory.error', args: [direPath]).toString());
					CrmLogger.logError(this.getClass(), message(code: 'webPage.directory.is.not.directory.error', args: [direPath]).toString());
				}
			}else{
				errs.add(message(code: 'webPage.directory.not.exist.error', args: [direPath]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.directory.not.exist.error', args: [direPath]).toString());
			}
			if(addedCount==0){
				errs.add(message(code: 'webPage.add.files.error').toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.add.files.error').toString());
			}
			//IMPORTS #4
			str="src=\"img";
			idx=fileContent.indexOf(str);
			if(idx>=0){
				fileContent=fileContent.replace(str, "src=\""+webPage.domain.realPath+Utils.getLocalSeparatorChar()+"img");//esta despues de agregar las imagenes xq sino remplaza la imagen por defecto
			}else{
				errs.add(message(code: 'webPage.template.missing.string.error', args: ["#4: "+str]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: ["#4: "+str]).toString());
			}
			//share title-summary
			str="Summary here...";
			idx=fileContent.indexOf(str);
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: [str]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: [str]).toString());
			}else{
				fileContent=fileContent.replaceAll(str, webPage.summary);
			}
			
			webPage.inWeb=true;
			webPage.pageUrl=filePath.replace(webPage.domain.realPath, "");//guarda la url sin la direccion fija del dominio
			
			//share url
			str="www.par.com.py";
			idx=fileContent.indexOf(str);
			if(idx<0){
				errs.add(message(code: 'webPage.template.missing.string.error', args: [str]).toString());
				CrmLogger.logError(this.getClass(), message(code: 'webPage.template.missing.string.error', args: [str]).toString());
			}else{
				fileContent=fileContent.replaceAll(str, webPage.getWebPage());
			}
			GUtils.removeAllFilesFromDirectory(pageContainer);//borra las paginas anteriores
			GUtils.writeFile(filePath, fileContent);
			webPage.save();
			
		}catch(Exception e){
			errs.add(message(code: 'webPage.creation.error', args: ["Message: "+e.message+". Detailed Message: "+e.detailMessage]).toString());
			CrmLogger.logError(this.getClass(), message(code: 'webPage.creation.error', args: ["Message: "+e.message+". Detailed Message: "+e.detailMessage]).toString());
		}
		return errs;
	}
	
	private List<String> transferImagesToWeb(WebPage webPage){
		List<String> errs = new ArrayList<String>();
		//property uploaded images transfer to target domain web page
		String originPath=grailsApplication.config.getProperty('crm.upload.image.property')+Utils.getLocalSeparatorChar()+webPage.managedProperty.id;
		String targetPath=webPage.domain.realPath + Utils.getLocalSeparatorChar() + grailsApplication.config.getProperty('web.image.property') + Utils.getLocalSeparatorChar() + webPage.managedProperty.id;
		File origin=new File(originPath);
		File target=new File(targetPath);
		File targetFile, originFile;
		boolean copy=false;
		if(origin.exists()){
			if(!target.exists()){
				target.mkdirs()
			}
			origin.eachFile(){ ff->
				if( !ff.isDirectory() ){
					targetFile= new File(targetPath + Utils.getLocalSeparatorChar() + ff.name);		
					copy=true;
					originFile=new File(ff.getAbsolutePath());
					if(targetFile.exists()){
						byte[]of=ff.readBytes();
						byte[]tf=targetFile.readBytes();
						if(of.equals(tf)){
							copy=false;
						}/*else{
							targetFile.delete();//if I'll copy it I need to delete target file first
							targetFile= new File(targetPath);
						}*/
					}
					if(copy){
						//System.out.println("Transfiriendo property image. De"+originFile.getAbsolutePath()+" a "+targetFile.getAbsolutePath());
						try{
							Files.copy(originFile.toPath(),targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
						}catch(Exception e){
							CrmLogger.logException(this.getClass(), "Exception copying property image in method transferImagesToWeb.", e);
						}
					}/*else{
						System.out.println("Transfiriendo property image."+originFile.name);
					}*/
				}
			}
			
		}else{
			errs.add(message(code: 'upload.property.image.origin.not.exist.error', args: [originPath]).toString());
			CrmLogger.logError(this.getClass(), message(code: 'upload.property.image.origin.not.exist.error', args: [originPath]).toString());
		}
		//verify transfer and delete unnecessary images
		if(target.exists()){
			boolean exists=false;
			target.eachFile(){ ft->
				exists=false;
				if( !ft.isDirectory() ){
					origin.eachFile(){ fo->
						if( !fo.isDirectory() ){
							if(ft.name.equals(fo.name)){
								exists=true;
							}
						}
					}
					if(!exists){
						try{
							ft.delete();
						}catch(Exception e){
							CrmLogger.logException(this.getClass(), "Exception deleting property from web site in method transferImagesToWeb.", e);
						}
						System.out.println("Borrando property image en el sitio web."+ft.name);
					}
				}
			}
		}else{
			errs.add(message(code: 'upload.property.image.copy.failed.error', args: [targetPath]).toString());
			CrmLogger.logError(this.getClass(), message(code: 'upload.property.image.copy.failed.error', args: [targetPath]).toString());
		}
		
		//partner uploaded profile image transfer to target domain web page
		Concession conc=webPage.managedProperty.concession;
		originPath=grailsApplication.config.getProperty('crm.upload.image.partner') + Utils.getLocalSeparatorChar()+conc.crmUser.partner.id + Utils.getLocalSeparatorChar() + "profile.jpg";
		targetPath=webPage.domain.realPath + Utils.getLocalSeparatorChar() + grailsApplication.config.getProperty('web.image.partner') + Utils.getLocalSeparatorChar() + conc.crmUser.partner.id;
		origin=new File(originPath);
		target=new File(targetPath);
		if(origin.exists()){
			if(!target.exists()){
				if(target.mkdirs()==false){
					crm.CrmLogger.logError(this.getClass(), "Target was not created.");
				}
			}
			//targetPath=targetPath + Utils.getLocalSeparatorChar() + "profile.jpg"
			targetFile= new File(targetPath + Utils.getLocalSeparatorChar() + "profile.jpg");
			copy=true;
			if(targetFile.exists()){
				byte[]of=origin.readBytes();
				byte[]tf=targetFile.readBytes();
				if(of.equals(tf)){
					copy=false;
				}/*else{
					targetFile.delete();//if I'll copy it I need to delete target file first
					targetFile= new File(targetPath);
				}*/
			}
			if(copy){
				//origin.transferTo(targetFile);
				try{
					Files.copy(origin.toPath(),targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				}catch(Exception e){
					CrmLogger.logException(this.getClass(), "Exception copying partner profile image in method transferImagesToWeb.", e);
				}
			}/*else{
				System.out.println("Transfiriendo profile image.");
			}*/
		}else{
			errs.add(message(code: 'upload.partner.origin.profile.image.not.exist.error', args: [originPath]).toString());
			CrmLogger.logError(this.getClass(), message(code: 'upload.partner.origin.profile.image.not.exist.error', args: [originPath]).toString());
		}	
		return errs;	   
	}
	
    def edit(WebPage webPage) {
        respond webPage
    }

    @Transactional
    def update(WebPage webPage) {
        if (webPage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
		
		webPage=this.validateObject(webPage);
		
        if (webPage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond webPage.errors, view:'edit'
            return
        }

        boolean err=false;
        if(webPage.save(flush:true)){
			List<String> errList1=this.transferImagesToWeb(webPage);
			List<String> errList2=this.createOrUpdateWebPage(webPage);
			if(!errList1.empty){
				errList1.each{
					webPage.errors.rejectValue('',"Error:"+it);
				}
			}
			if(!errList2.empty){
				errList2.each{
					webPage.errors.rejectValue('',"Error:"+it);
				}
			}
		}else{
			webPage.errors.rejectValue('',message(code:'webPage.save.error').toString());
			err=true;
		}
		
		if (err==true) {
			transactionStatus.setRollbackOnly()
			respond webPage.errors, view:'edit'
			return
		}

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'webPage.label', default: 'WebPage'), webPage.id])
                redirect webPage
            }
            '*'{ respond webPage, [status: OK] }
        }
    }

    @Transactional
    def delete(WebPage webPage) {

        if (webPage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        webPage.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'webPage.label', default: 'WebPage'), webPage.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'webPage.label', default: 'WebPage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
