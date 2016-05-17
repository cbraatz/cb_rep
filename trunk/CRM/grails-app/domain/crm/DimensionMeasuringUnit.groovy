package crm

class DimensionMeasuringUnit extends CrmDomain{
	String name;
	String nameInPlural;
	String abbreviation;
	String abbreviationInPlural;
	boolean isDefault;
	boolean isArea;
	static hasMany = [buildingTypes:BuildingType, propertyTypes:PropertyType];
    static constraints = {
		name(blank:false, nullable:false, unique:true, size:1..50);
		nameInPlural(blank:false, nullable:false, unique:true, size:1..50);
		abbreviation(blank:false, nullable:false, unique:true, size:1..50);
		abbreviationInPlural(blank:false, nullable:false, unique:true, size:1..50);
		isDefault(nullable:false);
		isArea(nullable:false);
    }
}
