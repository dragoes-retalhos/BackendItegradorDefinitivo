package com.inventario.backend_inventario.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.inventario.backend_inventario.entity.enums.StatusUserAndLoanEnum;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_loan")
    private long id;

    @Column(name = "loan_date", nullable = true)
    private LocalDateTime loanDate;

    @Column(name = "expected_return_date", nullable = false)
    private LocalDate expectedReturnDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private StatusUserAndLoanEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_loan_iduser_loan", nullable = false)
    private UserLoan userLoan;

    @ManyToMany
    @JoinTable(name = "loan_has_laboratory_item", joinColumns = @JoinColumn(name = "loan_id_loan"), inverseJoinColumns = @JoinColumn(name = "laboratory_item_id_laboratory_item"))
    private Set<LaboratoryItem> laboratoryItems;

    public Loan() {
    }

    public Loan(long id, LocalDateTime loanDate, LocalDate expectedReturnDate, LocalDateTime returnDate,
            StatusUserAndLoanEnum status, User user, UserLoan userLoan, Set<LaboratoryItem> laboratoryItems) {
        this.id = id;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.returnDate = returnDate;
        this.status = status;
        this.user = user;
        this.userLoan = userLoan;
        this.laboratoryItems = laboratoryItems;
    }

    @PrePersist
    protected void onCreate() {
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
        Loan other = (Loan) obj;
        return id == other.id;
    }

}
