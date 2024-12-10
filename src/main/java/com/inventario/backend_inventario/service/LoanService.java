package com.inventario.backend_inventario.service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventario.backend_inventario.entity.LaboratoryItem;
import com.inventario.backend_inventario.entity.Loan;
import com.inventario.backend_inventario.entity.User;
import com.inventario.backend_inventario.entity.UserLoan;
import com.inventario.backend_inventario.entity.dto.ItemLoanDTO;
import com.inventario.backend_inventario.entity.dto.LoanDTO;
import com.inventario.backend_inventario.entity.dto.LoanSumaryViewDto;
import com.inventario.backend_inventario.entity.enums.StatusUserAndLoanEnum;
import com.inventario.backend_inventario.repository.LaboratoryItemRepository;
import com.inventario.backend_inventario.repository.LoanRepository;
import com.inventario.backend_inventario.repository.UserLoanRepository;
import com.inventario.backend_inventario.repository.UserRepository;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LaboratoryItemRepository laboratoryItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoanRepository userLoanRepository;

    public LoanDTO getLoanById(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
        return LoanDTO.fromEntity(loan);
    }

    public List<LoanSumaryViewDto> getLoansDinamic() {
        List<Object[]> rawResults = loanRepository.findAllLoanSummary();

        return rawResults.stream().map(row -> new LoanSumaryViewDto(
                ((Number) row[0]).longValue(),
                StatusUserAndLoanEnum.fromValue((Integer) row[1]),
                ((java.sql.Timestamp) row[2]).toLocalDateTime(),
                ((java.sql.Date) row[3]).toLocalDate(),
                (String) row[4],
                (String) row[5])).collect(Collectors.toList());
    }

    public List<ItemLoanDTO> getLoansByLaboratoryItem(Long itemId) {
        List<Loan> loans = loanRepository.findByLaboratoryItemsId(itemId);
        return loans.stream()
                .map(ItemLoanDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public Loan createLoan(LoanDTO loanDTO) {

        User user = userRepository.findById(loanDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        UserLoan userLoan = userLoanRepository.findById(loanDTO.getUserLoanId())
                .orElseThrow(() -> new RuntimeException("Empréstimo de usuário não encontrado"));

        Loan loan = new Loan();
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setExpectedReturnDate(loanDTO.getExpectedReturnDate());
        loan.setReturnDate(loanDTO.getReturnDate());
        loan.setStatus(loanDTO.getStatus());
        loan.setUser(user);
        loan.setUserLoan(userLoan);

        List<LaboratoryItem> laboratoryItems = laboratoryItemRepository.findAllById(loanDTO.getLaboratoryItemIds());
        if (laboratoryItems.isEmpty()) {
            throw new RuntimeException("Itens de laboratório não encontrados");
        }
        loan.setLaboratoryItems(new HashSet<>(laboratoryItems));
        return loanRepository.save(loan);
    }

    @Transactional
    public LoanDTO updateLoanStatus(Long loanId, StatusUserAndLoanEnum newStatus) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        loan.setStatus(newStatus);

        loan = loanRepository.save(loan);

        return LoanDTO.fromEntity(loan);
    }

}
