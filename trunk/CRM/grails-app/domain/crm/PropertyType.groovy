package crm

class PropertyType extends CrmDomain{
	String name;
	String plural;
	String description;
	DimensionMeasuringUnit dimensionMeasuringUnit;
	
	static hasMany = [propertyFeatures:PropertyFeature, propertyDemands:PropertyDemand, managedProperties:ManagedProperty, keyWords:KeyWord, propertyTypesByLanguage:PropertyTypeByLanguage];
	static constraints = {
		name(blank: false, nullable:false, size:1..50);
		plural(blank: false, nullable:false, size:1..50);
		description(blank:false, nullable:false, widget:'textArea', size:1..100);
		dimensionMeasuringUnit(blank: false, nullable:false);
	}	
	@Override
	public static String getPluralName(){
		return "propertyTypes";
	}
}
