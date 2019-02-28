package baow.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import baow.tools.Constants;


@Service
public class ParkingSpace{
	
	@Autowired
	private CarService carService;
	private static final int TOTAL_SOCI_CAR_SPACE = 1000;
	
	private int remaining;
	private int sociTotal;
	private int vipTotal;
	private static final int VIP_REMAINING =5;
	
	
	public ParkingSpace() throws Exception{
	}
	@PostConstruct
	public void init() throws Exception{
		System.out.println("baow.service.ParkingSpace-->"+carService);
		vipTotal=carService.getVipCarsNum();
		sociTotal=carService.getSociCarsNum();
		carService.close();
		this.remaining=TOTAL_SOCI_CAR_SPACE-vipTotal-sociTotal-VIP_REMAINING;
		Constants.REMAING=this.remaining;
	}
	public int getRemaining() {
		return remaining;
	}
	@Override
	public String toString() {
		return "ParkingSpace [remaining=" + remaining + ", sociTotal="
				+ sociTotal + ", vipTotal=" + vipTotal + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + remaining;
		result = prime * result + sociTotal;
		result = prime * result + vipTotal;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParkingSpace other = (ParkingSpace) obj;
		if (remaining != other.remaining)
			return false;
		if (sociTotal != other.sociTotal)
			return false;
		if (vipTotal != other.vipTotal)
			return false;
		return true;
	}
	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}
}
