package baow.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import baow.entity.VipCar;
import baow.tools.Utils;


@Service
public class DoCarVIP {
	@Resource
	private DataSource ds;
	private Connection conn;
	private String[] strArr={"京","津","沪","渝",
			"蒙","新","藏","宁","桂",
			"港","澳",
			"黑","吉","辽","冀","晋","青","鲁","豫","苏","皖","浙",
			"闽","赣","湘","鄂","粤","台","琼","甘","陕","川","贵","云"};
	private String insertSql="INSERT INTO CAR_VIP_CUSTOMER(CVC_CAR_NO,CVC_BELONGNAME,CVC_MOBILE,CVC_ORDERDATE,CVC_ENDDATE)"
			+ " VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE CVC_ENDDATE=?";
	private String querySql="SELECT * FROM CAR_VIP_CUSTOMER WHERE "
			+ "(? IS NULL OR CVC_CAR_NO=?) AND "
			+ "(? IS NULL OR CVC_BELONGNAME=?) AND "
			+ "(? IS NULL OR CVC_MOBILE=?)";
	
	
	public DoCarVIP() {
		conn=Utils.getConnection();
	}
	public static void main(String[] args) throws Exception{
//		for(int i=20013;i>0;i++){
			Thread.sleep(100);
//			String regex = "[\\p{InCJK Unified Ideographs}&&\\P{Cn}]]";
			System.out.print(new DoCarVIP().getVIPinfo(null, null, "13566909246"));
//		}
		//) - 0xF7FE(63486
	}
	public String createCarNo() {
		String carNo="";
		String[] area={"A","B","C","D","E","F","G","H","J","K","L"};
		String[] zm={"A","B","C","D","E","F","G","H","J","K","L","M","N","P","Q","R","S","T","U","V","W","X","Y","Z"};
		Random r=new Random();
		carNo+=strArr[r.nextInt(strArr.length)]+area[r.nextInt(area.length)]+"·";
		int mLength=0;
		for(int i=0;i<5;i++){
			int m=r.nextInt(10);
			if(m<3 && mLength<=2){
				carNo+=zm[r.nextInt(zm.length)];
				mLength++;
			}else{
				carNo+=r.nextInt(10);
			}
		}
		return carNo;
	}

	public String getName(){
		String name="";
		Random r=new Random();
		name+=strArr[r.nextInt(strArr.length)]+strArr[r.nextInt(strArr.length)];
		if(r.nextInt(2)+2>2){
			name+=strArr[r.nextInt(strArr.length)];
		}
		return name;
	}
	
	public String getMobileNo(){
		String s="";
		for(int i=0;i<10;i++){
			s+=new Random().nextInt(10);
		}
		return "1"+s;
	}
	/**
	 * 查询，可通过车牌，姓名，手机号查询到消息
	 * @param carNo
	 * @param name
	 * @param mobile
	 * @return
	 */
	public VipCar getVIPinfo(String carNo,String name,String mobile){
		VipCar customer=new VipCar();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(querySql);
			ps.setString(1,carNo);ps.setString(2,carNo);
			ps.setString(3, name);ps.setString(4, name);
			ps.setString(5,mobile);ps.setString(6,mobile);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				customer.setCarNo(rs.getString(2));
				customer.setBelongName(rs.getString(3));
				customer.setMobile(rs.getString(4));
				customer.setOrderDate(rs.getString(5));
				customer.setEndDate(rs.getString(6));
				customer.setState(rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}
	/**
	 * 返回true 办理成功
	 * @param carNo
	 * @param name
	 * @param mobile
	 * @param month
	 * @return
	 */
	public boolean doAddVip(String carNo,String name,String mobile,int month){
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) conn.prepareStatement(insertSql);
			ps.setString(1,carNo);
			ps.setString(2, name);
			ps.setString(3,mobile);
			ps.setLong(4,System.currentTimeMillis());
			ps.setLong(5,Utils.getMonthsLater(month));
			ps.setLong(6, 0);
			return ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
