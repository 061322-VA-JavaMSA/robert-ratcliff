package com.revature.dtos;

import com.revature.models.Reimbursement;

import java.util.Date;
import java.util.Objects;

public class ReimburseDTO {
    private int id;
    private String description;
    private Date dueDate;
    private int status;

    public ReimburseDTO(){
        super();
    }

    public ReimburseDTO(Reimbursement r){
        id = r.getId();
        description = r.getDescription();
        dueDate = r.getSubmitted();
        status = r.getStatusId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReimburseDTO that = (ReimburseDTO) o;
        return id == that.id && status == that.status && Objects.equals(description, that.description) && Objects.equals(dueDate, that.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, dueDate, status);
    }

    @Override
    public String toString() {
        return "ReimburseDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", status=" + status +
                '}';
    }
}
