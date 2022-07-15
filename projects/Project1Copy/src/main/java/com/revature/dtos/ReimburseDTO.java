package com.revature.dtos;

import com.revature.models.Reimbursement;

import java.util.Date;
import java.util.Objects;

public class ReimburseDTO {
    private int id;
    private String description;
    private Date dueDate;
    private int status;
    private int amount;
    private String receipt;
    private int type;
    private int author;

    public ReimburseDTO(){
        super();
    }

    public ReimburseDTO(Reimbursement r){
        id = r.getId();
        description = r.getDescription();
        dueDate = r.getSubmitted();
        status = r.getStatusId();
        amount = r.getAmount();
        receipt = r.getReceipt();
        type = r.getTypeId();
        author = r.getAuthor();
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "ReimburseDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", status=" + status +
                ", amount=" + amount +
                ", receipt='" + receipt + '\'' +
                ", type=" + type +
                ", author=" + author +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReimburseDTO that = (ReimburseDTO) o;
        return id == that.id && status == that.status && amount == that.amount && type == that.type && author == that.author && Objects.equals(description, that.description) && dueDate.equals(that.dueDate) && Objects.equals(receipt, that.receipt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, dueDate, status, amount, receipt, type, author);
    }
}
