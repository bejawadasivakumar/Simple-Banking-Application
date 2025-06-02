package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.AccountDetails;

public interface AccountRepository extends JpaRepository<AccountDetails,Long>{

	boolean existsByAccountnumber(String accountnumber);
	AccountDetails findByAccountnumber(String accountnumber);
	
}
