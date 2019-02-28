package baow.controller;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import baow.service.CarIn;
import baow.service.CarOut;
import baow.service.CarService;
import baow.service.ParkingSpace;
import baow.tools.Constants;


@Controller
public class CarController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	//http://localhost:8080/SSMCarStopSystem/hello.do
	@RequestMapping("/hello")
	public void test(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		System.out.println("baow.controller.CarController-->"+Constants.REMAING);
		
	}
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		ThreadPoolExecutor pooExecutor=new ThreadPoolExecutor(10,20,2000,TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>());
		try {
			final CarService carIn=new CarIn(null);
			final CarService carOut=new CarOut(null);
			pooExecutor.execute(new Runnable(){
				@Override
				public void run(){
					while(true){
//						synchronized (parkingSpace) {
//							try {
//								Thread.sleep(2000);
//								carIn.createCarIn(null);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
					}
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
