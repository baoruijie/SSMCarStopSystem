package baow.service;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Random;

import baow.tools.Utils;



public class CarIn extends CarService{
	public CarIn(ParkingSpace p){
		this.park=p;
		this.conn=Utils.getConnection();
	}
	@Override
	public void run() {
		while(true){
			synchronized (park) {
				try {
//					Thread.sleep(1500);
					if(park.getRemaining()<=0){
//						System.out.println("暂无车位");
						continue;
					}
					System.out.println(Thread.currentThread().getName()+"↓↓↓----------↓↓↓-----------↓↓↓");
					in(null);
					System.out.println(Thread.currentThread().getName()+"↑↑↑----------↑↑↑-----------↑↑↑");
//					Thread.sleep(1500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void in(String carNo){
		if(carNo==null){
			if(new Random().nextInt(16)%5==1){
				//vip
				List<String> list=getAllCarsNo("SELECT CVC_CAR_NO FROM CAR_VIP_CUSTOMER");
				carNo=list.get(new Random().nextInt(list.size()));
			}else{
				//临时车
				DoCarVIP d=new DoCarVIP();
				carNo=d.createCarNo();
			}
		}
		int flag=0;
		PreparedStatement ps;
		try {
			if(isVIPCar(carNo)){
				flag=1;
				long arrivedTime=getArrivedDate(carNo);
				System.out.println(Thread.currentThread().getName()+"贵宾车："+carNo+" "+Utils.formatTime(arrivedTime));
				long remaining=arrivedTime-System.currentTimeMillis();
				int days=Utils.formatStayTime(remaining)[0];
				if(days<5){
					System.out.println(Thread.currentThread().getName()+"剩余日期不足5天");
				}
			}else{
				int remaining=park.getRemaining();
				if(remaining<=0){
					System.out.println(Thread.currentThread().getName()+"没有车位了");
					return;
				}
				park.setRemaining(remaining-1);
				System.err.println(Thread.currentThread().getName()+"欢迎光临 "+carNo+" 剩余车位："+park.getRemaining());
			}
			ps=conn.prepareStatement("INSERT INTO CAR_IN_GARAGE(CIG_CAR_NO,CIG_IN_TIME,CIG_FLAG) VALUES(?,?,?)");
			ps.setString(1,carNo);
			ps.setString(2, String.valueOf(System.currentTimeMillis()));
			ps.setString(3, String.valueOf(flag));
			ps.execute();
		} catch (Exception e) {
			System.out.println("重复进场，等待人工处理。。。");
		}
	}
}
