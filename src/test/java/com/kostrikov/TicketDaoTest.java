package com.kostrikov;

import com.kostrikov.Dao.TicketDao;
import com.kostrikov.Entity.Ticket;
import com.kostrikov.utils.HibernateConfiguration;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.beanvalidation.HibernateTraversableResolver;
import org.junit.jupiter.api.*;

import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketDaoTest {

    Ticket ticket;

    @BeforeEach
    public void initBeforeEach() {
        ticket = Ticket.builder()
                .passportNo("NMNBV2")
                .passengerName("Лариса Тельникова")
                .flightId(9L)
                .seatNo("B2")
                .cost(4217.00).build();

        @Cleanup Session session = HibernateConfiguration.buildSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Ticket").executeUpdate();
        session.getTransaction().commit();
        System.out.println("Table 'ticket' has been cleared and prepared for testing");
    }

    @Test
    @DisplayName("test add ticket method")
    void addTicketTest() {
        @Cleanup SessionFactory factory = HibernateConfiguration.buildSessionFactory();
        @Cleanup Session session = factory.openSession();
        TicketDao.addTicket(session, ticket);
        Assertions.assertNotNull(ticket.getId());
    }

    @Test
    @DisplayName("test update ticket method")
    void updateTicketTest() {
        @Cleanup SessionFactory factory = HibernateConfiguration.buildSessionFactory();
        @Cleanup Session session = factory.openSession();

        double expectedCost = ticket.getCost() + 1000;

        TicketDao.addTicket(session, ticket);
        Assertions.assertNotNull(ticket.getId());

        ticket.setCost(expectedCost);
        Optional<Ticket> updatedTicket = TicketDao.updateTicket(session, ticket);

        Assertions.assertAll(() -> {
            Assertions.assertTrue(updatedTicket.isPresent());
            Assertions.assertEquals(expectedCost, updatedTicket.get().getCost());
        });
    }

    @Test
    @DisplayName("test delete ticket method")
    void deleteTicketTest() {
        @Cleanup SessionFactory factory = HibernateConfiguration.buildSessionFactory();
        @Cleanup Session session = factory.openSession();

        TicketDao.addTicket(session, ticket);
        Long id = ticket.getId();
        Assertions.assertNotNull(id);

        TicketDao.deleteTicket(session, ticket);

        TicketDao.findById(session, id);
        Assertions.assertTrue(TicketDao.findById(session, id).isEmpty());
    }
}
