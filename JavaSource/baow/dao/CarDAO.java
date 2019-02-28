package baow.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.springframework.stereotype.Repository;

import baow.entity.VipCar;


/**
 * Mapper映射器
 * @author h
 *
 */
@Repository
public interface CarDAO {
	@Results({
        @Result(property = "id", column = "id"),
        @Result(property = "carNo", column = "CVC_CAR_NO"),
        @Result(property = "belongName", column = "CVC_BELONGNAME"),
        @Result(property = "mobile", column = "CVC_MOBILE"),
        @Result(property = "orderDate", column = "CVC_ORDERDATE"),
        @Result(property = "endDate", column = "CVC_ENDDATE"),
        @Result(property = "state", column = "CVC_STT")})
	/*
	 * 解决字段和列不一致的问题3种方式:
	 * 1.用别名
	 * 2.配置映射
	 * 3.使用全局声明,配置文件驼峰命名设置为ture 会将car_no自动转化为carNo
	 */
	public List<VipCar> getList();
	
	public List<VipCar> getCarList();

	public VipCar getSingleCarInfo(Map<String,String> map);
	
	public List<VipCar> getMultCarsInfo(Map<String,String> map);
	
	public long getArrivedDateWithCarNo(String carNo);

	public int getVipCarsNum();
}
