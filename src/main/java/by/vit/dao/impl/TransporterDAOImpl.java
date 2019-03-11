package by.vit.dao.impl;

import by.vit.dao.TransporterDAO;
import by.vit.model.Transporter;

public class TransporterDAOImpl extends GenericDAOImpl<Transporter,Long> implements TransporterDAO {
    private volatile static TransporterDAOImpl instance;

    private TransporterDAOImpl() {
        super(Transporter.class);
    }

    /**
     * @return singleton instance of Dao
     */
    public static TransporterDAOImpl getInstance() {
        if (instance == null) {
            synchronized (TransporterDAOImpl.class){
                if(instance==null){
                    instance = new TransporterDAOImpl();
                }
            }
        }
        return instance;
    }
}
