package repositories;

import Util.Columns;
import Util.HibernateFactory;
import entities.Contracts;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ContractsRepository extends BaseRepo<Contracts>{
    public List<Contracts> contractList;

    public ContractsRepository() {
        contractList = super.getAll();
    }

    @Override
    public List<Contracts> getAll() {
        contractList = super.getAll();
        return contractList;
    }

    public List<Contracts> searchByAddressString(String str, Columns col) {
        String hql = String.format("select c From %s c join c.address a where a.%s like :name ", getTableName(), col.getColumnName());
        Session session = HibernateFactory.getSessionFactory().openSession();
        Query<Contracts> query = session.createQuery(hql, getType());
        query.setParameter("name", "%" + str + "%");
        List<Contracts> list = query.list();
        session.close();
        return list;
    }

    @Override
    protected Class<Contracts> getType() {
        return Contracts.class;
    }


    @Override
    protected String getTableName() {
        return "Contracts";
    }
}

