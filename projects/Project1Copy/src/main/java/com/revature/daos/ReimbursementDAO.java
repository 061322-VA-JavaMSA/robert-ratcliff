package com.revature.daos;

import com.revature.models.Reimbursement;

import java.util.List;

public interface ReimbursementDAO {
    List<Reimbursement> getReimbursements();
    List<Reimbursement> getByAuthorId(int auth);
    Reimbursement createReimburse(Reimbursement newReimb);
}
