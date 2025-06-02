package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.AccountDetails;
import com.example.demo.Model.TransactionDetails;
import com.example.demo.ModelDto.AccountDto;
import com.example.demo.Repository.AccountRepository;
import com.example.demo.Repository.TransactionRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	
	//Creating the Account
	public AccountDetails createAccount(AccountDto dto) {
		AccountDetails account = new AccountDetails();
		account.setFullname(dto.getFullname());
		account.setEmail(dto.getEmail());
		account.setPhone(dto.getPhone());
		account.setAccountnumber(generateUniqueAccountNo());
		account.setBalance(0.0);
		return accountRepository.save(account);
	}

	//Fetching the details
	public List<AccountDetails> getAll(){
		return accountRepository.findAll();
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
}
