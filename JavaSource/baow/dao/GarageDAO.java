package baow.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import baow.entity.Garage;


/**
 * Mapper映射器
 * @author h
 *
 */
@Repository
public interface GarageDAO {
	/*
	 * 解决字段和列不一致的问题3种方式:
	 * 1.用别名
	 * 2.配置映射
	 * 3.使用全局声明,配置文件驼峰命名设置为ture 会将car_no自动转化为carNo
	 */
	public List<Garage> getList();

	public int getSociCarsNum();
	
//	public Garage saveComingCar(String carNo);
	
	public Garage getLeaveCarInfo(String carNo);

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteLeaveCar(String carNo);
}
