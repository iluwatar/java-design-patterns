package com.iluwatar.optimistic.concurrency.model;
import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private long id;

    @Version
    private int version;

    private String description;

    private String status;

    public Order() {

    }

    public Order(long id, int version, String description, String status) {
        this.id = id;
        this.version = version;
        this.description = description;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
