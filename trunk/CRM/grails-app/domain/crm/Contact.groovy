package crm

import java.util.Date;

class Contact extends CrmDomain{
	Date date;
	String description;
	Concession concession;
	Client client;
	Partner partner;
	ContactType contactType;
	
	static constraints = {
		date(blank:false, nullable:false);
		description(blank:false, nullable:false, widget:'textArea', size:1..300);
		concession(blank:false, nullable:false);
		client(nullable:false);
		partner(nullable:false);
		contactType(nullable:false);
	}
	@Override
	public static String getPluralName(){
		return "contracts";
	}
}
