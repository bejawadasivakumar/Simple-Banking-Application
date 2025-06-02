package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.TransactionDetails;


public interface TransactionRepository extends JpaRepository<TransactionDetails,Long>{

	 List<TransactionDetails> findByAccountnumberOrderByTimestampDesc(String accountNumber);
}
