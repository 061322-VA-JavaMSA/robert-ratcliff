package com.revature.daos;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

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


}
