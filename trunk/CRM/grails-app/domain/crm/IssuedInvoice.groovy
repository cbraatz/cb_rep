package crm

import java.util.Date;
import org.hibernate.collection.internal.PersistentSet

class IssuedInvoice {
	String number;
	Date date;
	Double amount;
	Double amountInDefaultCurrency;
	Double amountInIncomeCurrency;
	Double deductibleAmount;
	Double totalTax;
	Boolean isAccounting;
	Boolean isCanceled;
	Currency currency;
	Client client;
	IncomePayment incomePayment;
	//ThirdPartyPayment thirdPartyPayment
	
    static constraints = {
		number(blank: false, nullable:false, unique:'client', size:1..40);
		date(blank: false, nullable:false);
		amount(blank: false, nullable:false);
		amountInDefaultCurrency(blank:true, nullable:true);
		amountInIncomeCurrency(blank:false, nullable:false);
		deductibleAmount(blank:true, nullable:true);
		totalTax(blank:false, nullable:false);
		isAccounting(nullable:false);
		isCanceled(nullable:false);
		currency(nullable:false);
		client(nullable:false);
		incomePayment(nullable:false);
		//thirdPartyPayment(true);
    }
	
	public Double getIssuedInvoiceAmountInIncomeCurrency(){
		List<IssuedInvoice> list=IssuedInvoice.executeQuery("from IssuedInvoice where incomePayment = :i and isCanceled = :c",[i: this.incomePayment, c:true]);	
		double amount=0;
		if(list.size()>0){
			list.each{
				amount=amount+it.amountInIncomeCurrency.doubleValue();
			}
		}
		return amount;
	}
}
