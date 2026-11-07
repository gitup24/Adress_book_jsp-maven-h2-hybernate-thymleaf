package fr.carnet.dao;

import fr.carnet.model.Contact;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ContactDao {

    private final SessionFactory sessionFactory;

    public ContactDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Contact> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session
                .createQuery("from Contact order by lastName, firstName", Contact.class)
                .list();
        }
    }

    public Contact find(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Contact.class, id);
        }
    }

    public void save(Contact contact) {
        sessionFactory.inTransaction(session -> {
            if (contact.getId() == null) {
                session.persist(contact);
            } else {
                session.merge(contact);
            }
        });
    }

    public void delete(Long id) {
        sessionFactory.inTransaction(session -> {
            Contact c = session.find(Contact.class, id);
            if (c != null) {
                session.remove(c);
            }
        });
    }
}
