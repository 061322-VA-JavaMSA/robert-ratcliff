package com.revature.daos;

import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.revature.models.User;
import com.revature.util.HibernateUtil;

public class UserHibernate implements UserDAOS {

    @Override
    public void insertUser(User u) {
        u.setUserId(-1);
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            Transaction tx = s.beginTransaction();
            int id = (int) s.save(u);
            u.setUserId(id);
            tx.commit();
        } catch(ConstraintViolationException e) {
            //log it
        }
        //return u;
    }

    @Override
    public User getById(int id) {
        User user = null;

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            user = s.get(User.class, id);
        }

        return user;
    }

    @Override
    public User getUser(User u) {
        String username = u.getUsername();
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            u = s.get(User.class, username);
        }
        return u;
    }

    @Override
    public User getByUsername(String username) {
        User u = new User();

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            u = s.get(User.class, username);
        }
        return u;
    }

    @Override
    public User getByLogin(String username, String passwd) {
        User u = null;

        try(Session s = HibernateUtil.getSessionFactory().openSession()){

            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            Predicate predicateForUsername = cb.equal(root.get("username"), username);
            Predicate predicateForPassword = cb.equal(root.get("password"), passwd);
            Predicate predicateCombined = cb.and(predicateForUsername,predicateForPassword);

            cq.select(root).where(predicateCombined);

            u = s.createQuery(cq).getSingleResult(); //didn't add cast (User) for redenduncy, but might be good practice?
        }

        return u;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = null;

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            users = s.createQuery("from User", User.class).list();
        }

        return users;
    }

}