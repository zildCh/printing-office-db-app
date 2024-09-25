package repositories;
import Util.Columns;
import Util.HibernateFactory;
import entities.Products;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ProductsRepository extends BaseRepo<Products>{
    public List<Products> productList;

    public ProductsRepository() {
        productList = super.getAll();
    }

    @Override
    public List<Products> getAll() {
        productList = super.getAll();
        return productList;
    }
    public List<Products> searchByWorkshopString(String str, Columns col) {
        String hql = String.format("select p From %s p join p.Workshops w where w.%s like :name ", getTableName(), col.getColumnName());
        Session session = HibernateFactory.getSessionFactory().openSession();
        Query<Products> query = session.createQuery(hql, getType());
        query.setParameter("name", "%" + str + "%");
        List<Products> list = query.list();
        session.close();
        return list;
    }

    @Override
    protected Class<Products> getType() {
        return Products.class;
    }

    @Override
    protected String getTableName() {
        return "Products";
    }
}
