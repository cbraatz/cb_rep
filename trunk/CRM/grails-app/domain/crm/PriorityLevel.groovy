package crm

class PriorityLevel extends CrmDomain{
	String name;
	String color;
	int level;
	static hasMany = [propertyDemands:PropertyDemand, tasks:Task/*InsuranceDemand*/];
    static constraints = {
		name(blank: false, nullable:false, unique:true, size:1..50);
		color(blank: false, nullable:false, unique:true, size:1..15);
		level(blank: false, nullable:false, unique:true);
    }
	@Override
	public static String getPluralName(){
		return "priorityLevels";
	}
}
