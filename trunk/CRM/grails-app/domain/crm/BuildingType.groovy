package crm

class BuildingType {
	String name;
	String description;
	DimensionMeasuringUnit dimensionMeasuringUnit;
	static hasMany = [buildingFeatures:BuildingFeature, propertyDemands:PropertyDemand, buildings:Building];
	
	static constraints = {
		name(blank: false, nullable:false, unique:true, size:1..50);
		description(blank:false, nullable:false, widget:'textArea', size:1..100);
		dimensionMeasuringUnit(nullable:false);
	}
}
