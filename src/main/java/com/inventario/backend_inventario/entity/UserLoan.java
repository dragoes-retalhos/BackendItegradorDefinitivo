package com.inventario.backend_inventario.entity;

import java.util.List;

import com.inventario.backend_inventario.entity.enums.StatusUserAndLoanEnum;
import com.inventario.backend_inventario.entity.enums.TypeUserLoanEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_loan")
public class UserLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser_loan", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "rna", unique = true)
    private String rna;

    @Column(name = "enterprise")
    private String enterprise;

    @Column(name = "identification", unique = true)
    private String identification;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "status")
    private StatusUserAndLoanEnum status;

    @Column(name = "type_user")
    private TypeUserLoanEnum typeUserLoan;

    @OneToMany(mappedBy = "userLoan")
    private List<Loan> loans;

    public UserLoan() {
    }

    public UserLoan(Long id) {
        this.id = id;
    }

    public UserLoan(@NotNull long id, String name, String email, String rna, String enterprise, String identification,
            String phone, StatusUserAndLoanEnum status, TypeUserLoanEnum typeUserLoan) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.rna = rna; // aluno
        this.enterprise = enterprise; // empresa
        this.identification = identification; // chacha
        this.phone = phone;
        this.status = status;
        this.typeUserLoan = typeUserLoan;
    }

    @PrePersist
    protected void initializeStatus() {
        if (status == null) {
            status = StatusUserAndLoanEnum.ACTIVE;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        UserLoan other = (UserLoan) obj;
        return id == other.id;
    }

}
