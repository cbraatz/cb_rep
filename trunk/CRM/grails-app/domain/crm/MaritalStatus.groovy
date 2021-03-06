package crm

class MaritalStatus extends CrmDomain{
	String name;
	static hasMany = [clients:Client, partners:Partner];
	
	static constraints = {
		name(blank:false, nullable:false, unique:true, size:1..50);
	}
	@Override
	public static String getPluralName(){
		return "maritalStatuses";
	}
}
