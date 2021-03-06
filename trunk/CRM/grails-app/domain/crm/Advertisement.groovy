package crm

class Advertisement extends CrmDomain{
	String summary;
	String content;
	BroadcastMedia broadcastMedia;
	Boolean isContactProvenance;
	Boolean byOwner;
	Boolean byCompany;
	Date postDate;
	String urlToPost;
	String relatedInternalID;
	Locale locale;
	PropertyDemand propertyDemand;
	ManagedProperty managedProperty;
    static constraints = {
		summary(blank: true, nullable:true, size:0..500);
		content(blank: true, nullable:true, size:0..100);
		broadcastMedia(nullable:false);
		isContactProvenance(nullable:false);
		byOwner(nullable:false);
		byCompany(nullable:false);
		postDate(blank: true, nullable:true);
		urlToPost(blank: true, nullable:true, size:0..255);
		relatedInternalID(blank: true, nullable: true, size:0..40);//to define related transactions, transactions without related transactions will be null
		locale(nullable:false);
		propertyDemand(nullable:true);
		managedProperty(nullable:true);
    }
	@Override
	public static String getPluralName(){
		return "advertisements";
	}
}
