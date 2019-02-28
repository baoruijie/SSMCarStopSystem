package baow.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import baow.dao.GarageDAO;
import baow.dao.StopDAO;

public class TransactionService {

	private GarageDAO garageDAO;
	private StopDAO stopDAO;
	
    //通过IoC注入
    private TransactionTemplate template;
	public void addTransaction(final GarageDAO garageDAO,final StopDAO stopDAO) {
        template.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                //需要在事务环境中执行的代码
            	garageDAO.deleteLeaveCar("");
            	stopDAO.save(null);
            	
            }
        });
    }
    public void setTemplate(TransactionTemplate template) {
        this.template = template;
    }    
    public GarageDAO getGarageDAO() {
    	return garageDAO;
    }
    public void setGarageDAO(GarageDAO garageDAO) {
    	this.garageDAO = garageDAO;
    }
    public StopDAO getStopDAO() {
    	return stopDAO;
    }
    public void setStopDAO(StopDAO stopDAO) {
    	this.stopDAO = stopDAO;
    }
    public TransactionTemplate getTemplate() {
    	return template;
    }

	
}
