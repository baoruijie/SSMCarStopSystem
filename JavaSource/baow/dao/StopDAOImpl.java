package baow.dao;


import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import baow.entity.CarStopHistory;

@Repository
public class StopDAOImpl implements StopDAO {

	@Resource
	private DataSource dataSource;
	@Resource
	private GarageDAO garageDAO;
	@Resource
	private StopDAO stopDAO;
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	@Override
	public void saveAndDelete(CarStopHistory car)  {
		garageDAO.deleteLeaveCar(car.getCarNo());
		stopDAO.save(car);
//		System.out.println(9/0);
		
	}
	@Override
	//测试异常时事务是否生效.
	public void save(CarStopHistory car) {
		String sql="insert into car_stop_history(CS_CAR_NO,CS_IN_TIME,CS_OUT_TIME,CS_STOP_TIME)	values('1','2','3','4')";
		jdbcTemplate.execute(sql);
		System.out.println(9/0);
	}

}
