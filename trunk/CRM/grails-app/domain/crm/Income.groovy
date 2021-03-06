package crm

import org.hibernate.collection.internal.PersistentSet


class Income extends CrmDomain{
	Date date;
	String description;
	Double amount;
	Boolean isPaid;
	Boolean isCredit;
	PaymentPlan paymentPlan;
	Currency currency;
	Client client;
	String relatedToId;//defined in IncomeType >> RelatedDomain
	IncomeType incomeType;
	
	static hasMany = [incomePayments:IncomePayment/*,GoodsSaleDetail*/];
		
    static constraints = {
		date(blank:false, nullable:false);
		description(blank:true, nullable:true, widget:'textArea', size:0..200);
		amount(blank:false, nullable:false);
		isCredit(nullable:false);
		isPaid(nullable:false);
		paymentPlan(nullable:true);
		currency(nullable:false);
		client(nullable:true);
		relatedToId(blank:true, nullable:true, size:0..60);
		incomeType(nullable:true);
    }
	static mapping = {
		incomePayments sort: "dueDate"
	}
	
	public Income(){

	}
	public Income(def params){
		this.properties = params;
	}
	@Override
	public static String getPluralName(){
		return "incomes";
	}
	
	public boolean areAllIncomePaymentsPaid(){
		PersistentSet list=this.incomePayments;
		list.each{
			if(!it.isPaid){
				return false
			}
		}
		return true;
	}
	
	public boolean hasPayedPayments(){
		this.incomePayments.each{
			if(it.getPayedTotalAmount().doubleValue() > 0 || it.isPaid){
				return true;
			}
		}
		return false;
	}
	
	public boolean removeAllPayments(){
		this.incomePayments.each{
			it.delete();
		}
		this.incomePayments.clear();
		
		if(this.incomePayments.size() > 0){
			return false;
		}else{
			return true;
		}
	}
}