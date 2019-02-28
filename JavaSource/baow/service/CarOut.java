package baow.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import baow.tools.Utils;




public class CarOut extends CarService {
	public CarOut(ParkingSpace p) {
		this.park=p;
		this.conn=Utils.getConnection();
	}
	@Override
	public void run() {
		while(true){
			synchronized (park) {
				try {
					System.out.println(Thread.currentThread().getName()+"↓↓↓----------↓↓↓-----------↓↓↓");
					out(null);
					System.out.println(Thread.currentThread().getName()+"↑↑↑----------↑↑↑-----------↑↑↑");
					Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void out(String carNo){
		if(carNo==null){
			List<String> list=getAllCarsNo("SELECT CIG_CAR_NO FROM CAR_IN_GARAGE");
			if(list.size()!=0){
				carNo=list.get(new Random().nextInt(list.size()));
			}else{
				System.out.println("没车了");
				return;
//				try {
//					throw new MyException("没che了");
//				} catch (MyException e) {
//					e.printStackTrace();
//				}
			}
		}
		long outTime=System.currentTimeMillis();
		long inTime=getInTime(carNo);
		if(inTime==0){
			System.out.println("");
			return;
		}
		long stayTime=outTime-inTime;
		PreparedStatement ps;
		if(isVIPCar(carNo)){
			System.out.println(Thread.currentThread().getName()+"贵宾车 "+carNo+" 一路顺风");
		}else{
			park.setRemaining(park.getRemaining()+1);
			int[] arr=Utils.formatStayTime(stayTime);
			System.err.println(Thread.currentThread().getName()+"临时车 "+carNo+" 停车时间："+Utils.formatStayTime(arr)+" 剩余车位 "+park.getRemaining());
		}
		try {
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("DELETE FROM CAR_IN_GARAGE WHERE CIG_CAR_NO=?");
			ps.setString(1, carNo);
			boolean b=ps.execute();
			ps=conn.prepareStatement("INSERT INTO CAR_STOP_HISTORY(CS_CAR_NO,CS_IN_TIME,CS_OUT_TIME,CS_STOP_TIME)VALUES(?,?,?,?)");
			ps.setString(1, carNo);
			ps.setString(2, Utils.formatTime(inTime));
			ps.setString(3, Utils.formatTime(outTime));
			ps.setString(4, Utils.formatStayTime(Utils.formatStayTime(stayTime)));
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
