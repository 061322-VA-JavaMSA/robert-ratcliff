package com.revature.services;

import com.revature.daos.ReimbursementDAO;
import com.revature.daos.ReimbursementHibernate;
import com.revature.daos.UserDAOS;
import com.revature.daos.UserHibernate;
import com.revature.models.Reimbursement;
import com.revature.models.User;

import java.util.List;

public class ReimburseService {

    private ReimbursementDAO ud = new ReimbursementHibernate();

    public List<Reimbursement> getReimbursements(){return ud.getReimbursements();}
    public List<Reimbursement> getByAuthorId(int auth){return ud.getByAuthorId(auth);}
    public Reimbursement createReimburse(Reimbursement newReimb){return ud.createReimburse(newReimb);}
    public void acceptDeclineReimburse(Reimbursement r){ud.acceptDeclineReimburse(r);}
    public Reimbursement getById(int id){return ud.getById(id);}
}
