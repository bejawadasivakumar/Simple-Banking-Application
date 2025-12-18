package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.AccountDetails;
import com.example.demo.Model.TransactionDetails;
import com.example.demo.ModelDto.AccountDto;
import com.example.demo.Repository.AccountRepository;
import com.example.demo.Repository.TransactionRepository;

@Service
public class AccountService {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	
	//Creating the Account
	public AccountDetails createAccount(AccountDto dto) {
		try {
		AccountDetails account = new AccountDetails();
		account.setFullname(dto.getFullname());
		account.setEmail(dto.getEmail());
		account.setPhone(dto.getPhone());
		account.setAccountnumber(generateUniqueAccountNo());
		account.setBalance(0.0);
		AccountDetails saved = accountRepository.save(account);
		logger.info("Account created successfully with account number: {}", saved.getAccountnumber());
		return accountRepository.save(account);
	}
	catch(Exception e) {
		logger.error("Error creating account", e);
		throw e;
	}
	}

	//Fetching the details
	public List<AccountDetails> getAll(){
		try {
			List<AccountDetails> accounts = accountRepository.findAll();
			logger.info("Retrieved {} accounts from database", accounts.size());
		return accounts;
		}
		catch(Exception e) {
			logger.error("Error fetching all accounts", e);
			throw e;
		}
	}
	//Generating the AccountNumber
	private String generateUniqueAccountNo() {
		String accountNo;
		do {
			accountNo = String.format("%011d", new Random().nextLong() % 1_000_000_00000L);
			if (accountNo.startsWith("-")) {
				accountNo = accountNo.substring(1);
			}
		} while (accountRepository.existsByAccountnumber(accountNo));
		return accountNo;
	}
	
	// Finding the AccountDetails by accountNumber
	public AccountDetails getAccountDetailsByAccountnumber(String accountnumber) {
		AccountDetails repoDetails = accountRepository.findByAccountnumber(accountnumber);
		if(repoDetails != null) {
		return repoDetails;
		}
		return null;
	}
	//Transaction(Deposit/Withdraw)
	public boolean transactions(String accountnumber, double amount, String type) {
		AccountDetails acc = accountRepository.findByAccountnumber(accountnumber);
		if(type.equals("deposit")) {
			acc.setBalance(acc.getBalance() + amount);
			accountRepository.save(acc);
			TransactionDetails trans = new TransactionDetails(accountnumber,type,amount,LocalDateTime.now());
			transactionRepository.save(trans);
			return true;
		}
		else if(type.equals("withdraw")) {
			if(acc.getBalance() >= amount) {
			acc.setBalance(acc.getBalance() - amount);
			accountRepository.save(acc);
			TransactionDetails trans = new TransactionDetails(accountnumber,type,amount,LocalDateTime.now());
			transactionRepository.save(trans);
			return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
	
	//find the current Balance by Account number
	public double currentBalanceByAccountNumber(String accountnumber) {
		AccountDetails account = accountRepository.findByAccountnumber(accountnumber);
		return account.getBalance();
	}
	
	// delete the Account details in the DB through accountnumber
	public void deleteAccount(String accountnumber) {
		AccountDetails accountDetails = accountRepository.findByAccountnumber(accountnumber);
		accountRepository.delete(accountDetails);
		
		
		
	}
}
