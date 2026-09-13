package fu.de200475.dao;

import fu.de200475.pojo.Employee;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeDAO {

    private final EntityManagerFactory emf;

    public EmployeeDAO() {
        this.emf = Persistence.createEntityManagerFactory("hsf302FU");
    }

    public EmployeeDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // ---------- CREATE (TODO 0.3) ----------
    public void save(Employee e) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(e);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void insert(Employee e) {
        save(e);
    }

    // ---------- READ (TODO 0.4) ----------
    public Employee findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public List<Employee> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ---------- READ with Conditions (TODO 0.5) ----------
    public Employee findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Employee> result = em.createQuery(
                            "SELECT e FROM Employee e WHERE e.email = :email", Employee.class)
                    .setParameter("email", email)
                    .getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public List<Employee> findBySalaryGreaterThanAndActive(BigDecimal minSalary) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT e FROM Employee e WHERE e.salary > :minSalary AND e.active = true",
                            Employee.class)
                    .setParameter("minSalary", minSalary)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ---------- UPDATE (TODO 0.6) ----------
    public Employee update(Employee e) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee merged = em.merge(e);
            tx.commit();
            return merged;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // ---------- DELETE (TODO 0.7) ----------
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee e = em.find(Employee.class, id);
            if (e != null) {
                em.remove(e);
            }
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void deleteById(Long id) {
        delete(id);
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
