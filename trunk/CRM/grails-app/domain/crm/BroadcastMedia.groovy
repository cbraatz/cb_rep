package crm

class BroadcastMedia {
	String name;
	String urlToSite;
	int adSummaryMaxLength;
	int adTextMaxLength;
	Country country;
	static hasMany = [advertisements:Advertisement, propertyDemands:PropertyDemand];
	static constraints = {
		name(blank: false, nullable:false, unique: true, size:1..50);
		urlToSite(blank: true, nullable:true, size:0..255);
		adSummaryMaxLength(blank: true, nullable:true);
		adTextMaxLength(blank: true, nullable:true);
		country(nullable:true);
	}
}
