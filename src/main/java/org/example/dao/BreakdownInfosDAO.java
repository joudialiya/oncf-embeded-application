package org.example.dao;

import org.example.models.BreakdownInfos;
import org.example.tools.HibernateUtils;
import org.hibernate.query.Query;

import java.util.List;

public class BreakdownInfosDAO extends DAO<BreakdownInfos>{
    public BreakdownInfosDAO()
    {
        this.modelClass = BreakdownInfos.class;
    }
    @Override
    public List<BreakdownInfos> search(BreakdownInfos criteria)
    {
        String searchQuery = "from BreakdownInfos where Code = :code";
        Query<BreakdownInfos> query = HibernateUtils.session.createQuery(searchQuery, BreakdownInfos.class);
        query.setParameter("code", criteria.getCode());
        return query.list();
    }
}
