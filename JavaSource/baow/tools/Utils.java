package baow.tools;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


public class Utils {

	public static Connection getConnection() {
		Connection conn=null;
		try {
			InputStream is=Utils.class.getClassLoader().getResourceAsStream("config.properties");
			Properties prop=new Properties();
			prop.load(is);
			String driverClass=prop.getProperty("classname");
			String url=prop.getProperty("url");
			String userName=prop.getProperty("username");
			String password=prop.getProperty("password");
			Class.forName(driverClass);
			conn=DriverManager.getConnection(url, userName,password);
//			prop.setProperty("username", userName);
//			prop.setProperty("password", password);
//			conn=DriverManager.getConnection(url, prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static String formatMilTime(long time,String format){
		SimpleDateFormat sdf=null;
		if(format==null||format==""){
			sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			return sdf.format(new Date(time));
		}else{
			sdf=new SimpleDateFormat(format);
			return sdf.format(new Date(time));
		}
	}
	public static String formatDtTime(Date date,String pattern){
		
		SimpleDateFormat sdf=null;
		if(pattern==null||pattern==""){
			sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			return sdf.format(date);
		}
		if(pattern.equals("yyyy-MM-dd HH:mm:ss")){
			sdf=new SimpleDateFormat(pattern);
			return sdf.format(date);
		}else{
			sdf=new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
	}
	/**
	 * 0-30分钟免费，每小时2元，封顶12元
	 * @param arr
	 * @return
	 */
	public static int cal(int[]arr){
		int i=0;
		int day=arr[0];
		int hour=arr[1];
		int minute=arr[2];
		if(day==0){
			if(hour==0 && minute<=30){
				return 0;
			}else if(hour==0){
				i=Constants.EVERY_HOUR_FEE;
			}else if(hour<6 && minute>0){
				i=Constants.EVERY_HOUR_FEE*(hour+1);
			}else if(hour<6 && minute==0){
				i=Constants.EVERY_HOUR_FEE*hour;
			}else{
				i=Constants.EVERY_DAY_FEE;
			}
		}else{
			if(hour==0 && minute>0){
				i=14;
			}else if(hour<6 && minute>0){
				i=Constants.EVERY_HOUR_FEE*(hour+1)+Constants.EVERY_DAY_FEE*day;
			}else if(hour<6 && minute==0){
				i=Constants.EVERY_HOUR_FEE*hour+day*Constants.EVERY_DAY_FEE;
			}else{
				i=Constants.EVERY_DAY_FEE*(day+1);
			}
		}
		return i;
	}
	public static int[] formatStayTime(long stayTime){
		long day=24*60*60*1000;
		long hour=3600000;
		long minute=60000;
		long second=1000;
		int[] arr=new int[4];
		arr[0]=(int)(stayTime/day);
		arr[1]=(int)(stayTime/hour%24);
		arr[2]=(int)(stayTime/minute%60);
		arr[3]=(int)(stayTime/second%60);
		return arr;
	}
	
	public static String formatStayTime(int[] arr){
		String s="";
		if(arr[0]>0){
			s+=arr[0]+"天";
		}
		s+=arr[1]+"小时"+arr[2]+"分"+arr[3]+"秒";
		return s;
	}
	
	public static String formatTime(long time){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
		return sdf.format(new Date(time));
	}
	/**
	 * 获取某天的最晚一刻
	 * @param c
	 * @return
	 */
	public static long getLastTimeOfDay(Calendar c){
		c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE),23,59,59);
		return c.getTimeInMillis();
	}
	/**
	 * 获取当天加上若干个月
	 * @param month
	 * @return
	 */
	public static long getMonthsLater(int month){
		Calendar calendar=Calendar.getInstance();
		calendar.add(2, month);
		return Utils.getLastTimeOfDay(calendar);
	}
	public static void release(Connection conn,PreparedStatement ps,ResultSet rs){
		if(conn!=null){
			try {
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
