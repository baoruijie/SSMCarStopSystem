package baow.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import baow.entity.CarStopHistory;


/**
 * Mapper映射器
 * @author h
 *
 */
@Repository
public interface StopDAO {
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="ArithmeticException")
	public void save(CarStopHistory car) ;

	void saveAndDelete(CarStopHistory car);
}
