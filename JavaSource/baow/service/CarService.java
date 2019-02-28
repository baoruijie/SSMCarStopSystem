package baow.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import baow.dao.CarDAO;
import baow.dao.GarageDAO;


@Service
public class CarService implements Runnable{
	
	@Autowired
	private CarDAO carDAO;

	@Resource
	private GarageDAO garageDAO;
	
	
	protected ParkingSpace park;
	@Autowired
	protected DataSource dataSource;
	
	protected Connection conn;
	
	public CarService(){
	}
	@PostConstruct
	public void init() throws Exception{
		System.out.println("baow.service.CarService-->"+dataSource);
		conn=dataSource.getConnection();
		System.out.println("baow.service.CarService-->"+conn);
	}
	public CarService(DataSource ds) throws SQLException{
		this.conn=ds.getConnection();
	}
	public void createCarIn(Object object) {
		
	}

	public int getVipCarsNum() throws Exception {
		return carDAO.getVipCarsNum();
	}
	
	public int getSociCarsNum() throws Exception {
		return garageDAO.getSociCarsNum();
	}

	public void close() {
		try {
			if(conn!=null){
				conn.close();
			}
		} catch (Exception e) {
		}
		
	}

	public List<String> getAllCarsNo(String sql) {
		List<String> list=new ArrayList<String>();
		PreparedStatement ps;
		try {
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				list.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public long getInTime(String carNo) {
		long time=0;
		try {
			PreparedStatement ps= conn.prepareStatement("SELECT CIG_IN_TIME FROM CAR_IN_GARAGE WHERE CIG_CAR_NO=?");
			ps.setString(1, carNo);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				time=rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return time;
	}
	public long getArrivedDate(String carNo) throws Exception {
		long endTime=carDAO.getArrivedDateWithCarNo(carNo);
		return endTime;
	}
	public List<String> getAllCarsNo() {
		List<String> list=new ArrayList<String>();
		PreparedStatement ps;
		try {
			ps= conn.prepareStatement("");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				list.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	public boolean isVIPCar(String carNo){
		boolean isVip=false;
		PreparedStatement ps;
		try {
			ps= conn.prepareStatement("SELECT COUNT(*) FROM CAR_VIP_CUSTOMER WHERE CVC_CAR_NO=?");
			ps.setString(1, carNo);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				int i=rs.getInt(1);
				if(i==1){
					isVip=true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isVip;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
