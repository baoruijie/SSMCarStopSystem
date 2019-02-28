package baow.controller;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import baow.dao.CarDAO;
import baow.dao.GarHisDAO;
import baow.dao.GarageDAO;
import baow.dao.StopDAO;
import baow.entity.CarStopHistory;
import baow.entity.Garage;
import baow.entity.VipCar;
import baow.tools.Constants;
import baow.tools.Utils;



public class JunitTest {
	private AbstractApplicationContext ac;
	private CarDAO carDAO;
	private GarageDAO garageDAO;
	private StopDAO stopDAO;
	private DataSource ds;
	
	
	@Test
	//字段映射
	public void test1() {
		List<VipCar>list=carDAO.getList();
//		List<VipCar>list=carDAO.getCarList();
		for(VipCar obj:list){
//			System.out.println("JunitTest-->"+obj);
		}
		int a=garageDAO.getSociCarsNum();
		System.out.println("JunitTest-->"+a);//1563033599826
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("carNo","浙B·9XZ51");
		map.put("mobile","15990572573");
//		System.out.println(carDAO.getSingleCarInfo(map));
		System.out.println(carDAO.getMultCarsInfo(map));
	}
	@Test
	//别名映射
	public void test2() throws Exception {
		
//		List<Garage>list=garageDAO.getList();
//		for(Garage v:list){
////			System.out.println("JunitTest-->"+v);
//		}
		Garage g=garageDAO.getLeaveCarInfo("贵F·953PB");
		System.out.println(g+"\r--------------------------------"+"\r"+g.getFlag());
		if(g.getFlag().equals("1")){
			System.out.println("一路顺风");
		}else{
			System.out.println("缴费x元...");
			//计算应缴纳多少停车费
		}
//		//完成后将该条记录删除，并将停车记录保存到历史表。此处应有事务支持。
		String carNo=g.getCarNo();
//		garageDAO.deleteLeaveCar(carNo);
//		System.out.println("删除成功");
		long timeIn=Long.parseLong(g.getTimeIn());
		long timeOut=System.currentTimeMillis();
		long stayTime=timeOut-timeIn;
		CarStopHistory csh=new CarStopHistory(carNo,Utils.formatTime(timeIn),
				Utils.formatTime(timeOut),Utils.formatStayTime(Utils.formatStayTime(stayTime)));
//		GarHisDAO garHisDAO=ac.getBean("garHisDAO",GarHisDAO.class);
//		garHisDAO.saveAndDelete(csh);
		stopDAO=ac.getBean("stopDAOImpl",StopDAO.class);
		stopDAO.saveAndDelete(csh);
		System.out.println("删除并保存为历史成功");
		
	}
	@Test
	public void test3() throws Exception{
		System.out.println(Constants.REMAING);
	}
	
	@Test
	public void test4() throws Exception{
		//测试异常事务是否生效.
		stopDAO=ac.getBean("stopDAOImpl",StopDAO.class);
		stopDAO.save(null);
		System.out.println("JunitTest-->"+ds);
		Connection conn=ds.getConnection();
		System.out.println("JunitTest-->"+conn);
	}
	@Test
	public void test5(){
		System.out.println("JunitTest-->"+"kankanyouduoshaojavabean");
		/*
		ParkingSpace p=ParkingSpace.getParkingSpace();
		System.out.println(p.getRemaining());
		CarService ci=new CarIn(p);
		
		//1573660799615
		CarService co=new CarOut(p);
//		
		Thread out1=new Thread(co,"出口1--> ");
		Thread out2=new Thread(co,"出口2--> ");
		Thread in1=new Thread(ci,"入口1--> ");
		Thread in2=new Thread(ci,"入口2--> ");
		Thread in3=new Thread(ci,"入口3--> ");
		Thread in4=new Thread(ci,"入口4--> ");
//		
		
		in1.start();
		in2.start();
		in3.start();
		in4.start();
		out1.start();
		out2.start();
//		
//		int []arr=new int[4];
//		arr[0]=0;
//		arr[1]=2;
//		arr[2]=23;
//		arr[3]=54;
//		System.out.println(Utils.cal(arr));
		 */
	}
	@Test
	public void test6(){
		int []arr=Utils.formatStayTime(10000*7);
		System.out.println(Utils.formatStayTime(arr));
		
		arr=Utils.formatStayTime(9851000);
		System.out.println(Utils.formatStayTime(arr));
	}
	@Before
	public void init(){
		ac=new ClassPathXmlApplicationContext("mybatis-spring.xml");
		System.out.println("-------------启动容器成功------------------");
		carDAO=ac.getBean("carDAO",CarDAO.class);
		garageDAO=ac.getBean("garageDAO",GarageDAO.class);
		stopDAO=ac.getBean("stopDAO",StopDAO.class);
		System.out.println(stopDAO.toString());
		stopDAO=ac.getBean("stopDAOImpl",StopDAO.class);
		///StopDAOImpl stopDAOImpl=(StopDAOImpl)ac.getBean("stopDAOImpl");//异常
		System.out.println(stopDAO.toString());
		ds=ac.getBean("dataSource",DataSource.class);
	}
	@After
	public void destroy(){
		System.out.println("---------------关闭容器-------------------");
		if(ac!=null)
		ac.close();
	}
}
