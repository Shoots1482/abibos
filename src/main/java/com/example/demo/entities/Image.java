package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "\"Image\"")
public class Image {
    private Integer id;

    private Product product;

    private String imageUrl;
    
    private Boolean isPrimary = false;

    @Id
    @Column(name = "\"Image_ID\"", nullable = false)
    public Integer getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "\"Product_ID\"", nullable = false)
    public Product getProduct() {
        return product;
    }

    @NotNull
    @Column(name = "\"Image_URL\"", nullable = false, length = Integer.MAX_VALUE)
    public String getImageUrl() {
        return imageUrl;
    }
    
    @Column(name = "\"Is_Primary\"", nullable = false)
    public Boolean getIsPrimary() {
        return isPrimary;
    }
    
    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public void setPrimary(boolean b) {
        this.isPrimary = b;
    }
}