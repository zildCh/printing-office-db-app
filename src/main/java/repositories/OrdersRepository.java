package repositories;
import Util.Columns;
import Util.HibernateFactory;
import entities.Orders;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class OrdersRepository extends BaseRepo<Orders>{
    public List<Orders> orderList;

    public OrdersRepository() {
        orderList = super.getAll();
    }

    @Override
    public List<Orders> getAll() {
        orderList = super.getAll();
        return orderList;
    }
    public List<Orders> searchByContractsString(String str, Columns col) {
        String hql = String.format("select o From %s o join o.Contracts c where c.%s like :name ", getTableName(), col.getColumnName());
        Session session = HibernateFactory.getSessionFactory().openSession();
        Query<Orders> query = session.createQuery(hql, getType());
        query.setParameter("name", "%" + str + "%");
        List<Orders> list = query.list();
        session.close();
        return list;
    }

    @Override
    protected Class<Orders> getType() {
        return Orders.class;
    }

    @Override
    protected String getTableName() {
        return "Orders";
    }
}
