package crm

import java.util.List;

class PropertyFeature {
	String name;
	String description;
	String defaultWebIcon;
	Boolean hasValue;
	static belongsTo = PropertyType;
	static hasMany = [featuresByProperty:FeatureByProperty, propertyTypes:PropertyType, propertyFeaturesByPropertyDemand:PropertyFeatureByPropertyDemand];
	static constraints = {
		name(blank:false, nullable:false, unique:true, size:1..50);
		description(blank:true, nullable:true, widget:'textArea', size:0..100);
		defaultWebIcon(blank:true, nullable:true, widget:'textArea', size:0..255);
		hasValue(nullable:false);
	}
	
	public static List<FeatureByProperty> getEmptyFeatureByPropertyListForEachPropertyFeature(){
		List<FeatureByProperty> list = new ArrayList<FeatureByProperty>();
		PropertyFeature.getAll().each{
			list.add(new FeatureByProperty(propertyFeature: it, value: new Float(0)));
		}
		return list;
	}
}
