package baow.dao;


import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import baow.entity.CarStopHistory;

@Repository
public class GarHisDAO {

	@Resource
	private DataSource dataSource;
	@Resource
	private GarageDAO garageDAO;
	@Resource
	private StopDAO stopDAO;
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public void saveAndDelete(CarStopHistory car)  {
		garageDAO.deleteLeaveCar(car.getCarNo());
		stopDAO.save(car);
		System.out.println(9/0);
		
	}

}
