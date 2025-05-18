package dao;

import entity.CardPayment;
import entity.CashPayment;
import jakarta.persistence.*;

public class PaymentDAO {
    private EntityManagerFactory emf;

    public PaymentDAO() {
        emf = Persistence.createEntityManagerFactory("your-persistence-unit-name");
    }

    public void insertCardPayment(CardPayment cp) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(cp);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void insertCashPayment(CashPayment cp) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(cp);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
