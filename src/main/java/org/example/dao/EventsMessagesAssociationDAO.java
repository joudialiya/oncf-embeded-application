package org.example.dao;

import org.example.models.EventsMessagesAssociation;

import java.util.List;

public class EventsMessagesAssociationDAO extends DAO<EventsMessagesAssociation>{
    public EventsMessagesAssociationDAO()
    {
        this.modelClass = EventsMessagesAssociation.class;
    }

    @Override
    public List<EventsMessagesAssociation> search(EventsMessagesAssociation criteria) {
        return null;
    }
}
