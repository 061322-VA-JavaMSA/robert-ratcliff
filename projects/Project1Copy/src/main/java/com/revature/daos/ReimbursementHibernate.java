package com.revature.daos;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class ReimbursementHibernate implements ReimbursementDAO{

    @Override
    public List<Reimbursement> getReimbursements() {
        List<Reimbursement> reimb = null;

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            reimb = s.createQuery("from Reimbursement ", Reimbursement.class).list();
        }

        return reimb;
    }

    @Override
    public List<Reimbursement> getByAuthorId(int auth) {
        List<Reimbursement> reimb = null;

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Reimbursement> cq = cb.createQuery(Reimbursement.class);
            Root<Reimbursement> root = cq.from(Reimbursement.class);

            Predicate predicateForAuthor= cb.equal(root.get("author"), auth);

            cq.select(root).where(predicateForAuthor);

            reimb = s.createQuery(cq).list();
        }

        return reimb;
    }

    //for some reason, it will not put the new reimbursements into the table

    @Override
    public void createReimburse(Reimbursement newReimb) {
        //set this to -1 so it does not change a request that is already created
        newReimb.setId(-1);
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            Transaction tx = s.beginTransaction();
            int id = (int) s.save(newReimb);
            newReimb.setId(id);
            tx.commit();
        }catch(ConstraintViolationException e){
            // add log
            e.getStackTrace();
        }
    }


}
