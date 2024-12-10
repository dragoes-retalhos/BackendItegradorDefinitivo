package com.inventario.backend_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventario.backend_inventario.entity.Loan;
import com.inventario.backend_inventario.entity.dto.ItemLoanDTO;
import com.inventario.backend_inventario.entity.dto.LoanDTO;
import com.inventario.backend_inventario.entity.dto.LoanSumaryViewDto;
import com.inventario.backend_inventario.entity.dto.UpdateLoanStatusDTO;
import com.inventario.backend_inventario.service.LoanService;
import com.inventario.backend_inventario.util.ApiErrorResponse;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/dinamic")
    public ResponseEntity<Object> getLoansDinamic() {
        try {
            List<LoanSumaryViewDto> loanSumaryViewDto = loanService.getLoansDinamic();
            return ResponseEntity.ok(loanSumaryViewDto);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    e.getMessage(),
                    "/api/loan/dinamic");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<Object> getLoanById(@PathVariable Long loanId) {
        try {
            LoanDTO loanDTO = loanService.getLoanById(loanId);
            return ResponseEntity.ok(loanDTO);
        } catch (RuntimeException e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Not Found",
                    e.getMessage(),
                    "/api/loan/" + loanId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    e.getMessage(),
                    "/api/loan/");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/LoanItemHistory/{itemId}") /* retorna historico de emprestimo */
    public ResponseEntity<Object> getLoanIntemHistory(@PathVariable Long itemId) {
        try {
            List<ItemLoanDTO> loans = loanService.getLoansByLaboratoryItem(itemId);
            return ResponseEntity.ok(loans);
        } catch (RuntimeException e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Not Found",
                    e.getMessage(),
                    "/api/loan/LoanItemHistory/" + itemId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    e.getMessage(),
                    "/api/loan/LoanItemHistory/");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createLoan(@RequestBody LoanDTO loanDTO) {
        try {
            Loan newLoan = loanService.createLoan(loanDTO);
            LoanDTO newLoanDTO = LoanDTO.fromEntity(newLoan);
            return ResponseEntity.status(HttpStatus.CREATED).body(newLoanDTO);
        } catch (RuntimeException e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Bad Request",
                    e.getMessage(),
                    "/api/loan/");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Erro ao criar o empréstimo",
                    "/api/loan/");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @PutMapping("/{loanId}/status")
    public ResponseEntity<Object>updateLoanStatus(
        @PathVariable Long loanId, 
        @RequestBody UpdateLoanStatusDTO updateLoanStatusDTO)  {
        try {
            LoanDTO updatedLoan = loanService.updateLoanStatus(loanId, updateLoanStatusDTO.getStatus());
            return ResponseEntity.ok(updatedLoan);
        } catch (RuntimeException e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Not Found",
                    e.getMessage(),
                    "/api/loan/" + loanId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    e.getMessage(),
                    "/api/loan/");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
