package crm;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Utils {
    public static String getActionFromUri(String uri){
        int idx=uri.lastIndexOf('/');
        System.out.println("controller="+uri.substring(1,idx));
        return uri.substring(idx+1);
    }
    public static String getControllerFromUri(String uri){
        int idx=uri.lastIndexOf('/');
        return uri.substring(1,idx);
    }
    public static String getUUID(){
    	return UUID.randomUUID().toString();
    }
    public static String getShortUUIDWithNumbers(String id){
    	return id+'-'+Utils.getNumericID()+'-'+UUID.randomUUID().toString().substring(0, 7);
    }
    public static Long getNumericID(){
    	Date date=new Date();
    	return date.getTime();
    }
    public static Date addToDate(Date date, int days, int months){
    	//SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    	//sdf.format(new Date());
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH,months);
		c.add(Calendar.DATE,days);
		return c.getTime();
    }
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    public static String getDateInStr(Date date){
    	DateFormat sdf=new SimpleDateFormat(Utils.getDefaultDateFormat());
    	return sdf.format(date);
    }
    public static String getDateInStrDBFormat(Date date){
    	DateFormat sdf=new SimpleDateFormat(Utils.getDBDateFormat());
    	return sdf.format(date);
    }
    public static Date removeTimeFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    public static String getDefaultDateFormat(){
		return "dd/MM/yyyy";
	}
	public static String getDBDateFormat(){
		return "yyyy/MM/dd";
	}
}
