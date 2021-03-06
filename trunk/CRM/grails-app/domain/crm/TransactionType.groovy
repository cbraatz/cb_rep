package crm

class TransactionType extends CrmDomain{
	String name;
	String internalID;
	Boolean isDefault;
	Boolean isInternalTransaction;
	static hasMany = [moneyTransactions:MoneyTransaction];
    static constraints = {
		name(blank:false, nullable:false, size:1..50);
		internalID(blank:false, nullable:false, unique:true, size:1..40);
		isDefault(nullable:false);
		isInternalTransaction(nullable:false);
    }
	@Override
	public static String getPluralName(){
		return "transactionTypes";
	}
}
