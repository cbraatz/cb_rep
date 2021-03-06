package crm

class CrmConfig extends CrmDomain{
    String dateFormat;
    String companyName;
	String plan;
	Integer dbVersion;
    static constraints = {
        dateFormat(blank:false, nullable:false, size:1..50);
        companyName(blank:false, nullable:false, size:1..100);
		dbVersion(blank:false, nullable:false, size:1..100);
    }
	@Override
	public static String getPluralName(){
		return "crmDomains";
	}
}
