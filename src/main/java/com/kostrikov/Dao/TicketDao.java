package com.kostrikov.Dao;

import com.kostrikov.Entity.Ticket;
import com.kostrikov.utils.HibernateConfiguration;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class TicketDao {
    public static void addTicket(Session session, Ticket ticket) {
        session.beginTransaction();
        session.persist(ticket);
        session.getTransaction().commit();
    }

    public static Optional<Ticket> updateTicket(Session session, Ticket ticket) {
        Ticket updatedTicket = null;
        session.beginTransaction();
        updatedTicket = session.merge(ticket);
        session.getTransaction().commit();
        return Optional.ofNullable(updatedTicket);
    }

    public static Optional<Ticket> findById(Session session, Long id){
        Ticket ticket;
        session.beginTransaction();
        ticket = session.get(Ticket.class, id);
        session.getTransaction().commit();
        return Optional.ofNullable(ticket);
    }

    public static void deleteTicket(Session session, Ticket ticket){
        session.beginTransaction();
        session.remove(ticket);
        session.getTransaction().commit();
    }

}
