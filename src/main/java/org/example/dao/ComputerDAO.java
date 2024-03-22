package org.example.dao;

import org.example.models.Computer;

import java.util.List;

public class ComputerDAO extends DAO<Computer>{
    public ComputerDAO()
    {
        this.modelClass = Computer.class;
    }

    @Override
    public List<Computer> search(Computer criteria) {
        return null;
    }
}
