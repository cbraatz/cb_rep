import crm.Currency;

import java.util.Date;

import grails.util.Environment;
import crm.*;

import java.text.SimpleDateFormat
class BootStrap {

    def init = { servletContext ->
		println("Starting init......................."+Environment.current);
		if(Environment.current.equals(Environment.DEVELOPMENT)){
			addDevelopmentData();
		}
		println("Finishing init......................");
    }
    def destroy = {
    }
	private void addDevelopmentData(){
		
		//ContextPermission
		this.saveObj(new ContextRole(name: "Basic", isActive:true));
		this.saveObj(new ContextRole(name: "Administrative", isActive:true));
		this.saveObj(new ContextRole(name: "Sales", isActive:true));

		//ContextPermission
		this.saveObj(new ContextPermission(name: "Create Bank",permissionId:"CREATE_BANK"));
		this.saveObj(new ContextPermission(name: "View Bank",permissionId:"VIEW_BANK"));
		this.saveObj(new ContextPermission(name: "Edit Bank",permissionId:"EDIT_BANK"));
		this.saveObj(new ContextPermission(name: "Delete Bank",permissionId:"DELETE_BANK"));
		
		//Language
		this.saveObj(new Language(name: "Espa�ol", symbol:"es", prepositionOfPlace:"en"));
		this.saveObj(new Language(name: "English", symbol:"en", prepositionOfPlace:"in"));
		this.saveObj(new Language(name: "Portugu�s", symbol:"pt", prepositionOfPlace:"em"));
		this.saveObj(new Language(name: "Deutsch", symbol:"de", prepositionOfPlace:"in"));
		
		//Bank
		this.saveObj(new Bank(name: "Banco Atlas"));
		this.saveObj(new Bank(name: "Banco Continental"));
		this.saveObj(new Bank(name: "Banco Familiar"));
		this.saveObj(new Bank(name: "Banco Itap�a"));
		this.saveObj(new Bank(name: "Banco Ita�"));
		this.saveObj(new Bank(name: "Banco Regional"));
		this.saveObj(new Bank(name: "Banco Visi�n"));
		
		//country
		this.saveObj(new Country(name: "Paraguay"));
		this.saveObj(new Country(name: "Argentina"));		
		this.saveObj(new Country(name: "Brasil"));		
		this.saveObj(new Country(name: "Uruguay"));		
		this.saveObj(new Country(name: "Deutschland"));
		this.saveObj(new Country(name: "United States"));
		
		//department
		this.saveObj(new Department(name: "Itap�a", country: Country.findByName("Paraguay")));		
		this.saveObj(new Department(name: "Alto Paran�", country: Country.findByName("Paraguay")));		
		
		//city
		this.saveObj(new City(name: "Obligado", department: Department.findByName("Itap�a")));		
		this.saveObj(new City(name: "Encarnaci�n", department: Department.findByName("Itap�a")));		
		this.saveObj(new City(name: "Hohenau", department: Department.findByName("Itap�a")));
		
		//MaritalStatus
		this.saveObj(new MaritalStatus(name: "Soltero/a"));		
		this.saveObj(new MaritalStatus(name: "Casado/a"));
		
		//Gender
		this.saveObj(new Gender(name: "Masculino"));		
		this.saveObj(new Gender(name: "Femenino"));
		
		//profession
		this.saveObj(new Profession(name: "Agricultor"));		
		this.saveObj(new Profession(name: "Ingeniero Agr�nomo"));		
		this.saveObj(new Profession(name: "Agente de Seguros"));
		
		//neighborhood
		this.saveObj(new Neighborhood(name:"Centro", description:"test"));		
		this.saveObj(new Neighborhood(name:"Barrio Los Colonos", description:"test"));		
		this.saveObj(new Neighborhood(name:"Los Cedrales", description:"test"));
		
		//Zone
		this.saveObj(new Zone(name: "Colonias Unidas", description: "Obligado + Hohenau + Bella Vista"));
		
		//Address
		this.saveObj(new Address(streetOne:"Calle 1", streetTwo:"Calle 2", number:1568, addressLine:"Calle 1 1522 c/ Calle 2", reference:"Frente al Centro de Salud.", description:"Casa de rejas blancas y patio amplio.", code:"6000", latitude:Double.parseDouble("-27.054249533179895"), longitude:Double.parseDouble("-55.6293557718102"), homePhone:"0988541258", city: City.findByName("Obligado"), neighborhood: Neighborhood.findByName("Centro"), zone: Zone.findByName("Colonias Unidas")));
		
		//PartnerRole
		this.saveObj(new PartnerRole(name: "Admin", isEmployee:false, description:"Default Partner."));
		
		//Partner
		this.saveObj(new Partner(name: "Default", gender: Gender.findByName("Masculino"), phone:"0", IDNumber:"0", emailAddress: "admin_partner@test.com", birthDate: new SimpleDateFormat(Utils.getDefaultDateFormat()).parse("01/01/1900"), address:Address.findByAddressLine("Calle 1 1522 c/ Calle 2"), isActive:true, partnerRole:PartnerRole.findByName("Admin"), isAgent:true));
		
		//crmuser
		this.saveObj(new CrmUser(partner: Partner.findByName("Default"), name:"nobody", password:CrmUser.encodePassword("123456"), emailAddress: "default_user@test.com", isAdmin:true, isActive:true, isDefault:true));
		
		//ClientCategory
		this.saveObj(new ClientCategory(name: "Peque�o", description:"Clientes con sueldo m�nimo o menos."));		
		this.saveObj(new ClientCategory(name: "Peque�o-Medio", description:"Clientes con m�s de sueldo m�nimo, hasta 3 sueldos m�nimo."));
		
		//Client
		this.saveObj(new Client(name: "Cliente 1", description: "test", IDNumber:"0", birthDate: new SimpleDateFormat(Utils.getDefaultDateFormat()).parse("15/06/1980"), phone:"0985000000", phone2:"0985000001", notificationPhone:"0985000002", emailAddress: "cliente1@test.com", nationality: Country.findByName("Paraguay"), maritalStatus: MaritalStatus.findByName("Soltero/a"), profession: Profession.findByName("Agricultor"), gender: Gender.findByName("Masculino"), address:Address.findByAddressLine("Calle 1 1522 c/ Calle 2"), category:ClientCategory.findByName("Peque�o"), owner: CrmUser.findByName("nobody"), isActive:true, readsEmail:true, readsSms:true, receiveNotifications:true, isProspectiveClient:false));
		
		//Vendor
		this.saveObj(new Vendor(name: "Cooperativa Colonias Unidas", description: "Cooperativa", phone:"071720512", emailAddress: "ccu@test.com", TIN:"80090084-2"));
		
		//TaxRate
		this.saveObj(new TaxRate(name: "10 %", percentage:10));		
		this.saveObj(new TaxRate(name: "5 %", percentage:5));		
		this.saveObj(new TaxRate(name: "0 %", percentage:0));
		
		//BuildingFeature
		this.saveObj(new BuildingFeature(name: "Acondicionador de aire", plural:"Acondicionadores de aire", description: "Cuenta con acondicionador de aire (antiguo o split)", hasValue:true));		
		this.saveObj(new BuildingFeature(name: "Cocina con amoblado b�sico", plural:"Cocinas con amoblado b�sico", description: "Cuenta con los muebles b�sicos de cocina", hasValue:false));		
		this.saveObj(new BuildingFeature(name: "Amoblamiento completo", plural:"Amoblamiento completo", description: "Cuenta con amoblamiento completo de todo el inmueble", hasValue:false));
		
		//PropertyFeature
		this.saveObj(new PropertyFeature(name: "Agua potable", plural:"Agua potable", description: "Cuenta servicio de agua potable", hasValue:false));
		this.saveObj(new PropertyFeature(name: "Ande", plural:"Ande", description: "Cuenta con servicio de Ande", hasValue:false));
		this.saveObj(new PropertyFeature(name: "Ba�o", plural:"Ba�os", description: "Cantidad de ba�os", defaultWebIcon:"b-icon b-icon--bathrooms", hasValue:true));
		this.saveObj(new PropertyFeature(name: "Dormitorio", plural:"Dormitorios", description: "Cantidad de dormitorios", defaultWebIcon: "b-icon b-icon--bed", hasValue:true));
		this.saveObj(new PropertyFeature(name: "Garage", plural:"Garages", description: "Espacios para veh�culos", defaultWebIcon: "b-icon b-icon--garage", hasValue:true));
		
		//DimensionMeasuringUnit
		this.saveObj(new DimensionMeasuringUnit(name: "Metro", nameInPlural:"Metros", abbreviation:"m", abbreviationInPlural:"m", isDefault:true, isArea:false));		
		this.saveObj(new DimensionMeasuringUnit(name: "Metro cuadrado", nameInPlural:"Metros cuadrados", abbreviation:"m2", abbreviationInPlural:"m2", isDefault:true, isArea:true));		
		this.saveObj(new DimensionMeasuringUnit(name: "Hect�rea", nameInPlural:"Hect�reas", abbreviation:"ha", abbreviationInPlural:"ha", isDefault:true, isArea:true));
		
		//BuildingType
		this.saveObj(new BuildingType(name: "Casa", description:"Inmueble independiente y de uso habitacional", dimensionMeasuringUnit:DimensionMeasuringUnit.findByAbbreviation("m2"), language:Language.findByName("Espa�ol")));		
		this.saveObj(new BuildingType(name: "Edificio de Departamentos", description:"Inmueble independiente que est� dividido en departamentos", dimensionMeasuringUnit:DimensionMeasuringUnit.findByAbbreviation("m2"), language:Language.findByName("Espa�ol")));		
		this.saveObj(new BuildingType(name: "Piso", description:"Un piso de un edificio", dimensionMeasuringUnit:DimensionMeasuringUnit.findByAbbreviation("m2"), language:Language.findByName("Espa�ol")));		
		this.saveObj(new BuildingType(name: "Local Comercial", description:"Inmueble en planta baja destinado a actividades comerciales", dimensionMeasuringUnit:DimensionMeasuringUnit.findByAbbreviation("m2"), language:Language.findByName("Espa�ol")));		
		this.saveObj(new BuildingType(name: "Dep�sito", description:"Inmueble destinado a guardar/depositar bienes", dimensionMeasuringUnit:DimensionMeasuringUnit.findByAbbreviation("m2"), language:Language.findByName("Espa�ol")));
		
		//BuildingFeatureByBuildingType
		//casa
		  BuildingType bt=BuildingType.findByName("Casa");
			//Acondicionador de aire
			BuildingFeature bf=BuildingFeature.findByName("Acondicionador de aire");
			this.saveObj(bt.addToBuildingFeatures(bf));
			this.saveObj(bf.addToBuildingTypes(bt));
			//Cocina con amoblado b�sico
			bf=BuildingFeature.findByName("Cocina con amoblado b�sico");
			this.saveObj(bt.addToBuildingFeatures(bf));
			this.saveObj(bf.addToBuildingTypes(bt));
			//Amoblamiento completo
			bf=BuildingFeature.findByName("Amoblamiento completo");
			this.saveObj(bt.addToBuildingFeatures(bf));
			this.saveObj(bf.addToBuildingTypes(bt));
		//Edificio de Departamentos
			bt=BuildingType.findByName("Edificio de Departamentos");
			  //Acondicionador de aire
			  bf=BuildingFeature.findByName("Acondicionador de aire");
			  this.saveObj(bt.addToBuildingFeatures(bf));
			  this.saveObj(bf.addToBuildingTypes(bt));
			  //Cocina con amoblado b�sico
			  bf=BuildingFeature.findByName("Cocina con amoblado b�sico");
			  this.saveObj(bt.addToBuildingFeatures(bf));
			  this.saveObj(bf.addToBuildingTypes(bt));
			  //Amoblamiento completo
			  bf=BuildingFeature.findByName("Amoblamiento completo");
			  this.saveObj(bt.addToBuildingFeatures(bf));
			  this.saveObj(bf.addToBuildingTypes(bt));
		//Piso
			  bt=BuildingType.findByName("Piso");
				//Acondicionador de aire
				bf=BuildingFeature.findByName("Acondicionador de aire");
				this.saveObj(bt.addToBuildingFeatures(bf));
				this.saveObj(bf.addToBuildingTypes(bt));
				//Cocina con amoblado b�sico
				bf=BuildingFeature.findByName("Cocina con amoblado b�sico");
				this.saveObj(bt.addToBuildingFeatures(bf));
				this.saveObj(bf.addToBuildingTypes(bt));
				//Amoblamiento completo
				bf=BuildingFeature.findByName("Amoblamiento completo");
				this.saveObj(bt.addToBuildingFeatures(bf));
				this.saveObj(bf.addToBuildingTypes(bt));
		//Usage
		this.saveObj(new Usage(name: "Cultivo", description:"Destinado al cultivo de rubros permitidos (Soja, trigo, ma�z, sorgo, avena, etc.)"));		
		this.saveObj(new Usage(name: "Ganader�a", description:"Destinado a la producci�n animal."));
		this.saveObj(new Usage(name: "Uso habitacional", description:"Destinado a la construcci�n de viviendas."));
		this.saveObj(new Usage(name: "Uso comercial", description:"Destinado a la construcci�n de locales comerciales."));
		
		//Locale
		this.saveObj(new Locale(name: "Espa�ol-Paraguay", isDefault:true, symbol:"es_PY", language:Language.findByName("Espa�ol"), country:Country.findByName("Paraguay")));
		this.saveObj(new Locale(name: "Alem�n-Alemania", isDefault:false, symbol:"de_GE", language:Language.findByName("Deutsch"), country:Country.findByName("Deutschland")));
		//Domain 
		this.saveObj(new Domain(name: "inmuebles-paraguay.com.py", realPath:"D:/TRABAJOS/crm/git_projects/root/web_trunk/HTML/html", locale:Locale.findBySymbol("es_PY"), realEstateFolder:"inmuebles"));
		this.saveObj(new Domain(name: "inmobilien-paraguay.com.de", realPath:"D:/TRABAJOS/crm/git_projects/root/web_trunk/HTML/html", locale:Locale.findBySymbol("de_GE"), realEstateFolder:"inmobilien"));
		
		//OperationType
		this.saveObj(new OperationType(sale:"En Venta",rent:"En Alquiler",language:Language.findByName("Espa�ol")));
		this.saveObj(new OperationType(sale:"Zum Verkauf",rent:"Zu vermieten",language:Language.findByName("Deutsch")));
		
		//BroadcastMedia
		this.saveObj(new BroadcastMedia(name: "Facebook", adSummaryMaxLength:0, adTextMaxLength:0, urlToSite:"https://es-la.facebook.com/"));		
		this.saveObj(new BroadcastMedia(name: "Clasipar", adSummaryMaxLength:0, adTextMaxLength:0, urlToSite:"http://clasipar.paraguay.com/", Country: Country.findByName("Paraguay")));		
		this.saveObj(new BroadcastMedia(name: "Quebarato", adSummaryMaxLength:0, adTextMaxLength:0, urlToSite:"http://www.quebarato.com.py/", Country: Country.findByName("Paraguay")));		
		this.saveObj(new BroadcastMedia(name: "Bolet�n Coop. Colonias Unidas", adSummaryMaxLength:0, adTextMaxLength:0, Country: Country.findByName("Paraguay")));
		
		//BuildingCondition
		this.saveObj(new BuildingCondition(name: "Nuevo"));		
		this.saveObj(new BuildingCondition(name: "Semi nuevo"));		
		this.saveObj(new BuildingCondition(name: "Buen estado"));		
		this.saveObj(new BuildingCondition(name: "Refaccionado"));		
		this.saveObj(new BuildingCondition(name: "Necesita refacciones menores"));		
		this.saveObj(new BuildingCondition(name: "Necesita refacciones importantes"));
		
		//PriorityLevel
		this.saveObj(new PriorityLevel(name: "M�xima", color: "red", level:1));		
		this.saveObj(new PriorityLevel(name: "Alta", color: "orange", level:2));		
		this.saveObj(new PriorityLevel(name: "Media", color: "yellow", level:3));		
		this.saveObj(new PriorityLevel(name: "Baja", color: "green", level:4));	
		
		//TaskStatus
		this.saveObj(new TaskStatus(name: "Nueva", isNew:true, isClosed:false));
		this.saveObj(new TaskStatus(name: "Calendarizada", isNew:false, isClosed:false));
		this.saveObj(new TaskStatus(name: "En Proceso", isNew:false, isClosed:false));
		this.saveObj(new TaskStatus(name: "Terminada", isNew:false, isClosed:true));
		this.saveObj(new TaskStatus(name: "Cancelada", isNew:false, isClosed:true));
		
		//NotificationMethod
		this.saveObj(new NotificationMethod(name: "Email", isEmail:true, isSms:false, isInternetMessage1:false));
		this.saveObj(new NotificationMethod(name: "Mensaje de Texto", isEmail:false, isSms:true, isInternetMessage1:false));
		this.saveObj(new NotificationMethod(name: "WhatsApp", isEmail:false, isSms:false, isInternetMessage1:true));
		
		
		//InterestLevel
		this.saveObj(new InterestLevel(name: "M�ximo", color: "red", level:1));		
		this.saveObj(new InterestLevel(name: "Muy Interesado", color: "orange", level:2));		
		this.saveObj(new InterestLevel(name: "Interesado", color: "yellow", level:3));		
		this.saveObj(new InterestLevel(name: "Poco Interesado", color: "green", level:4));
		
		//Currency
		this.saveObj(new Currency(name: "Guaran�", plural:"Guaran�es", symbol:"Gs", hasDecimals:false, isDefault:true, isInvoicingCurrency:true, country: Country.findByName("Paraguay")));		
		this.saveObj(new Currency(name: "D�lar", plural:"D�lares", symbol:"USS", hasDecimals:true, isDefault:false, isInvoicingCurrency:true, country: Country.findByName("Estados Unidos")));
		this.saveObj(new Currency(name: "Euro", plural:"Euros", symbol:"�", hasDecimals:true, isDefault:false, isInvoicingCurrency:false));
		
		//DefaultsDateRage
		this.saveObj(new DefaultsDateRange(startDate:new Date(), endDate:null, taxRate:TaxRate.findByName("10 %"), currency:null));
		this.saveObj(new DefaultsDateRange(startDate:new Date(), endDate:null, taxRate:null, currency:Currency.findByName("Guaran�")));
		
		//CurrencyExchange
		this.saveObj(new CurrencyExchange(date:new SimpleDateFormat(Utils.getDefaultDateFormat()).parse("16/09/2015"), buy:new Double("5000"), sell:new Double("5050"), sourceCurrency:Currency.findBySymbol("Gs"), targetCurrency:Currency.findBySymbol("USS")));
		this.saveObj(new CurrencyExchange(date:new SimpleDateFormat(Utils.getDefaultDateFormat()).parse("13/09/2015"), buy:new Double("5100"), sell:new Double("5150"), sourceCurrency:Currency.findBySymbol("Gs"), targetCurrency:Currency.findBySymbol("USS")));
		this.saveObj(new CurrencyExchange(date:new SimpleDateFormat(Utils.getDefaultDateFormat()).parse("14/09/2015"), buy:new Double("5200"), sell:new Double("5250"), sourceCurrency:Currency.findBySymbol("Gs"), targetCurrency:Currency.findBySymbol("USS")));
		this.saveObj(new CurrencyExchange(date:new SimpleDateFormat(Utils.getDefaultDateFormat()).parse("15/09/2015"), buy:new Double("5300"), sell:new Double("5350"), sourceCurrency:Currency.findBySymbol("Gs"), targetCurrency:Currency.findBySymbol("USS")));
		this.saveObj(new CurrencyExchange(date:new SimpleDateFormat(Utils.getDefaultDateFormat()).parse("15/09/2015"), buy:new Double("6000"), sell:new Double("6050"), sourceCurrency:Currency.findBySymbol("Gs"), targetCurrency:Currency.findByName("Euro")));
		
		//PaymentMethod
		this.saveObj(new PaymentMethod(name: "Efectivo", discountPercentage:0, isCash:true, hasStartDate:false, hasEndDate:false, hasBank:false));
		this.saveObj(new PaymentMethod(name: "Cheque", discountPercentage:0, isCash:false, hasStartDate:true, hasEndDate:false, hasBank:true));
		this.saveObj(new PaymentMethod(name: "Pagar�", discountPercentage:0, isCash:false, hasStartDate:true, hasEndDate:false, hasBank:false));
		this.saveObj(new PaymentMethod(name: "Tarjeta de Cr�dito", discountPercentage:3, isCash:false, hasStartDate:false, hasEndDate:false, hasBank:true));
		
		//DemandStatus
		this.saveObj(new DemandStatus(name: "Nueva", isNew:true, isClosed:false));
		this.saveObj(new DemandStatus(name: "Necesita m�s informaci�n", isNew:false, isClosed:false));
		this.saveObj(new DemandStatus(name: "Verificada", isNew:false, isClosed:false));
		this.saveObj(new DemandStatus(name: "Listo para cerrar", isNew:false, isClosed:false));
		this.saveObj(new DemandStatus(name: "Cerrado", isNew:false, isClosed:true));
		
		//ContractType
		this.saveObj(new ContractType(name: "No Eclusivo", isExclusive:false, description:"Contrato no exclusivo.", commissionPercentage:4, billingDefaultDescription:"Intermediaci�n de venta de inmueble. Contrato no exclusivo."));
		this.saveObj(new ContractType(name: "Eclusivo", isExclusive:true, description:"Contrato exclusivo.", commissionPercentage:3, billingDefaultDescription:"Intermediaci�n de venta de inmueble. Contrato exclusivo."));
		
		//Contract
		this.saveObj(new Contract(internalID:"1", date:new Date(), contractType:ContractType.findByDescription("Contrato exclusivo.")));
		
		//PropertyType
		this.saveObj(new PropertyType(name:"Sitio", plural:"Sitios", internalID:"SITIO", dimensionMeasuringUnit:DimensionMeasuringUnit.findByAbbreviation("m2"), description:"Lote de tierra generalmente urbano.", language:Language.findByName("Espa�ol")));
		this.saveObj(new PropertyType(name:"Terreno agr�cola", plural:"Terrenos agr�colas", internalID:"TERRENO_AGRICOLA", dimensionMeasuringUnit:DimensionMeasuringUnit.findByAbbreviation("ha"), description:"Lote de tierra generalmente rural destinado a la producci�n agr�cola", language:Language.findByName("Espa�ol")));
		this.saveObj(new PropertyType(name:"Estancia", plural:"Estancias", internalID:"ESTANCIA", dimensionMeasuringUnit:DimensionMeasuringUnit.findByAbbreviation("ha"), description:"Lote de tierra generalmente rural destinado a la producci�n animal.", language:Language.findByName("Espa�ol")));
		
		//ManagedProperty
		ManagedProperty managedProperty1=new ManagedProperty(title:"Terreno de 1200m2 en Obligado Centro, al lado del Centro de Salud", propertyDescription:"Terreno con vereda y �rboles frutales", measures:"20m x 60m", publicAddress:"Obligado Centro, cerca del Centro de Salud", publicCashPrice:"240.000 USS", price:240000, currency:Currency.findBySymbol("USS"), value:250000,
			clientInitialPrice:240000, addedDate:new Date(), placedBillboards:1, area:1200,excess:2, owner:Client.findByName("Cliente 1"), address:Address.findByAddressLine("Calle 1 1522 c/ Calle 2"), propertyType:PropertyType.findByName("Sitio"), valueDegree:1);
		this.saveObj(managedProperty1);
		System.out.println(managedProperty1.id);
		
		//PropertyUsage
		this.saveObj(new PropertyUsage(usage:Usage.findByName("Uso habitacional"), managedProperty:ManagedProperty.findByTitle("Terreno de 1200m2 en Obligado Centro, al lado del Centro de Salud"), quantity:100, isQuantityInPercentage:true, isCurrentUsage:false));
		this.saveObj(new PropertyUsage(usage:Usage.findByName("Uso comercial"), managedProperty:ManagedProperty.findByTitle("Terreno de 1200m2 en Obligado Centro, al lado del Centro de Salud"), quantity:100, isQuantityInPercentage:true, isCurrentUsage:false));
		
		//Building
		this.saveObj(new Building(builtSize:500, builtYear:2013, managedProperty:ManagedProperty.findByTitle("Terreno de 1200m2 en Obligado Centro, al lado del Centro de Salud"), buildingType:BuildingType.findByName("Casa"), buildingCondition:BuildingCondition.findByName("Semi nuevo"), buildingDescription:"Es una casa estilo moderno, sin goteras y ladrillo visto."));
		
		//Concession
		Concession concession1=new Concession(isNegotiable:false, startDate:new SimpleDateFormat(Utils.getDefaultDateFormat()).parse("15/06/2015"), endDate: new SimpleDateFormat(Utils.getDefaultDateFormat()).parse("15/06/2016"), commissionAmount:9600, commissionPercentage:4, description:"Ninguna", //propertyDemand:PropertyDemand,
									contract:Contract.findByInternalID("1"), publishInMLS:false, publishInPortals:false, barter:"NO", financing:"NO", client:Client.findByName("Cliente 1"), agent:CrmUser.findByName("nobody"), isActive:true, soldByCompany:false, isForRent:false);
		concession1.addToManagedProperties(managedProperty1);
		this.saveObj(concession1);
		managedProperty1.addToConcessions(concession1);
		this.saveObj(managedProperty1);
		
		
		//UploadedImage
		this.saveObj(new UploadedImage(description:"test image", fileName:"image1.jpg", isMainImage:true, addToWeb:true, sizeInKB:1221 , managedProperty:managedProperty1));
		this.saveObj(new UploadedImage(description:"test image", fileName:"image2.jpg", isMainImage:true, addToWeb:true, sizeInKB:1221 , managedProperty:managedProperty1));
		//managedProperty1.createOrUpdateWebPage();
		//InterestType
		this.saveObj(new InterestType(name:"Simple", description:"Inter�s Simple", internalID:"1", isSimpleInterest:true));
		
		//ExpenseType
		this.saveObj(new ExpenseType(name:"Comisi�n por Intermediaci�n", description:"Pago de comisiones por servicios prestados por Agentes Inmobiliarios", internalID:"1", selfInvoiceDefaultDescription:"Honorarios por servicios de intermediaci�n Inmobiliaria", taxRate:TaxRate.findByName("10 %"), isCompanyExpense:true));//mejorar busqueda o no incluir esto por defecto
		
		//CommissionType
		this.saveObj(new CommissionType(name:"Comisi�n de Venta de Inmueble", description:"Comisi�n por cerrar una venta de un inmueble", internalID:"1", selfInvoiceDefaultDescription:"Honorarios por servicios de intermediaci�n Inmobiliaria", expenseType:ExpenseType.findByInternalID("1")));
		this.saveObj(new CommissionType(name:"Comisi�n de Captaci�n de Inmueble", description:"Comisi�n por captaci�n de un inmueble vendido", internalID:"2", selfInvoiceDefaultDescription:"Honorarios por servicios de intermediaci�n Inmobiliaria", expenseType:ExpenseType.findByInternalID("1")));
		
		//IncomeType
		this.saveObj(new IncomeType(name:"Honoraios por venta de Concesi�n", description:"Cobro de honorarios por intermediaci�n en la venta de una concesi�n inmobiliaria", isConcessionRelated:true, billingDefaultDescription:"Honorarios por servicios de intermediaci�n Inmobiliaria", taxRate:TaxRate.findByName("10 %")));
		this.saveObj(new IncomeType(name:"Honorarios por tasaci�n", description:"Cobro de honorarios por servicio de tasaci�n inmobiliaria", isConcessionRelated:false, billingDefaultDescription:"Honorarios por servicios de tasaci�n Inmobiliaria", taxRate:TaxRate.findByName("10 %")));
		
		//PaymentPlan
		this.saveObj(new PaymentPlan(name:"2 pagos anuales con 50% de entrega", initialFreeTimeInDays:0, regularPaymentsInDays:0, regularPaymentsInMonths:12, initialPaymentPercentage:50, numberOfParts:3, interestPercentage:0));
		this.saveObj(new PaymentPlan(name:"12 pagos mensuales y 20% entrega en 15", initialFreeTimeInDays:15, regularPaymentsInDays:0, regularPaymentsInMonths:1, initialPaymentPercentage:20, numberOfParts:12, interestPercentage:0));
		this.saveObj(new PaymentPlan(name:"5 pagos quincenales sin entrega", initialFreeTimeInDays:0, regularPaymentsInDays:15, regularPaymentsInMonths:0, initialPaymentPercentage:0, numberOfParts:5, interestPercentage:0));
		
		//TransactionType
		this.saveObj(new TransactionType(name:"Cobro de Ingresos", internalID:"INCOME_PAYMENT", isDefault:true, isInternalTransaction:false));
		this.saveObj(new TransactionType(name:"Cobro de Ingresos", internalID:"EXPENSE_PAYMENT", isDefault:true, isInternalTransaction:false));
		this.saveObj(new TransactionType(name:"Cambio", internalID:"PAYMENT_CHANGE", isDefault:true, isInternalTransaction:false));
		
		//InvoicesPrinting
		this.saveObj(new InvoicesPrinting(printingNumber:65898521, startDate: new SimpleDateFormat(Utils.getDefaultDateFormat()).parse("01/01/2015"), endDate: new SimpleDateFormat(Utils.getDefaultDateFormat()).parse("01/01/2016"), firstNumber:"001", secondNumber:"001", thirdStartNumber:"0000001", quantity:50));
		
		//ActionType
		this.saveObj(new ActionType(name:"Corpida de pasto a cargo del cliente", description:"Corpida de pasto que paga el cliente.", hasCost:true, clientPays:true));
		this.saveObj(new ActionType(name:"Corpida de pasto a cargo de la Inmobiliaria", description:"Corpida de pasto que paga la inmobiliaria.", hasCost:true, clientPays:false));
		this.saveObj(new ActionType(name:"Recorrido para verificar estado", description:"Recorrido al inmueble para ver su estado.", hasCost:false, clientPays:false));
		
		//ContactType
		this.saveObj(new ContactType(name:"Informacion por email", description:"Informaci�n suministrada al cliente por email.", email:true, phoneCall:false, chat:false, showing:false, personally:false));
		this.saveObj(new ContactType(name:"Informacion por chat", description:"Informaci�n suministrada al cliente por chat.", email:false, phoneCall:false, chat:true, showing:false, personally:false));
		this.saveObj(new ContactType(name:"Informacion por llamada telef�nica", description:"Informaci�n suministrada al cliente por llamada telef�nica.", email:false, phoneCall:true, chat:false, showing:false, personally:false));
		this.saveObj(new ContactType(name:"Visita del inmueble con el cliente", description:"Visita del inmueble con el cliente.", email:false, phoneCall:false, chat:false, showing:true, personally:false));
		this.saveObj(new ContactType(name:"Informacion brindada personalmente", description:"Informaci�n suministrada al cliente personalmente.", email:false, phoneCall:false, chat:false, showing:false, personally:true));
			
		//CrmConfig
		this.saveObj(new CrmConfig(dateFormat:"dd/MM/yyyy", companyName:"MacroInmuebles"/*, crmPartnerImagePath:"uploads/partner", crmPropertyImagePath:"uploads/property", webPropertyImagePath:"img/crm/property", webPartnerImagePath:"img/crm/partner"*/));
	}
	
	private void saveObj(Object obj){
		if (!obj.save(flush: true)) {
			GUtils.printErrors(obj, null);
		}
		
	}
	
}
