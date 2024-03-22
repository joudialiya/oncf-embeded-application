package org.example.dao;

import org.example.models.Rame;

import java.util.List;

public class RameDAO extends DAO<Rame>{
    public RameDAO()
    {
        this.modelClass = Rame.class;
    }

    @Override
    public List<Rame> search(Rame criteria) {
        return null;
    }
}
