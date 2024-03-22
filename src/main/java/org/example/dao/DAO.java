package org.example.dao;

import org.example.models.BreakdownInfos;
import org.example.tools.HibernateUtils;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class DAO<T> {
    public Class<T> modelClass;
    public void setModelClass(Class<T> modelClass) {
        this.modelClass = modelClass;
    }

    public final void create(T obj) {
        Session session = HibernateUtils.session;
        Transaction tx = session.beginTransaction();
        session.persist(obj);
        tx.commit();
    }

    public final void delete(T obj) {
        Session session = HibernateUtils.session;
        Transaction tx = session.beginTransaction();
        session.delete(obj);
        tx.commit();
    }

    public final void update(T obj) {
        Session session = HibernateUtils.session;
        Transaction tx = session.beginTransaction();;
        session.update(obj);
        tx.commit();
    }

    public T getById(Long id) {
        return HibernateUtils.session.get(modelClass, id);
    }

    public List<T> getAll() {
        return HibernateUtils.session.createQuery("from " + modelClass.getName()).list();
    }

    public abstract List<T> search(T criteria);
}
