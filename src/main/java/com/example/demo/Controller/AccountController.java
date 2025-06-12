package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.AccountDetails;
import com.example.demo.Model.TransactionDetails;
import com.example.demo.ModelDto.AccountDto;
import com.example.demo.Repository.TransactionRepository;
import com.example.demo.Service.AccountService;

@RestController
@RequestMapping("/api")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	
	@PostMapping("/create")
		public AccountDetails create(@RequestBody AccountDto dto) {
			return accountService.createAccount(dto);
		}
	@GetMapping("/getDetails")
	public List<AccountDetails> find(){
		return accountService.getAll();
	}
	 
	@GetMapping("/AccountDetails/{accountnumber}")
	public ResponseEntity<?> getDetailsByAccountNumber(@PathVariable String accountnumber) {
	    AccountDetails details = accountService.getAccountDetailsByAccountnumber(accountnumber);
	    
	    if (details != null) {
	        return ResponseEntity.ok(details);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body("Account details not found for account number: " + accountnumber);
	    }
	}
	/*@PostMapping("/transaction")
	public String depositAndWithdraw(@RequestParam String accountnumber,@RequestParam double amount, @RequestParam String type) {
		boolean result = accountService.transactions(accountnumber, amount, type);
		AccountDetails acc = accountService.getAccountDetailsByAccountnumber(accountnumber);
		if (acc == null) {
		    return "Transaction failed: Account not found.";
		}
		double balance  = acc.getBalance();
		if(result) {
		if(type.equals("deposit") && result == true) {
			return "Deposit : Transaction Successful !!! \nCurrent Balance: " + balance;
		}
		else if(type.equals("withdraw")) {
			return "Withdraw: Transaction Successful !!! \nCurrent Balance: " + balance;
		}
		}
		return "Transaction failed: insufficient balance or invalid type";
	}
	*/
	@PostMapping("/transaction")
	public ResponseEntity<String> depositAndWithdraw(@RequestParam String accountnumber,@RequestParam double amount,@RequestParam String type) {
	    boolean result = accountService.transactions(accountnumber, amount, type);
	    AccountDetails acc = accountService.getAccountDetailsByAccountnumber(accountnumber);

	    if (acc == null) {
	        return new ResponseEntity<>("Transaction failed: Account not found.", HttpStatus.NOT_FOUND);
	    }

	    double balance = acc.getBalance();

	    if (result) {
	        if (type.equalsIgnoreCase("deposit")) {//.equalsIgnoreCase used to handle case-insensitive matching of the transaction type.
	            return new ResponseEntity<>("Deposit: Transaction Successful !!! \nCurrent Balance: " + balance, HttpStatus.OK);
	        }
	        else if (type.equalsIgnoreCase("withdraw")) {
	            return new ResponseEntity<>("Withdraw: Transaction Successful !!! \nCurrent Balance: " + balance, HttpStatus.OK);
	        }
	    }

	    return new ResponseEntity<>("Transaction failed: insufficient balance or invalid type", HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/CurrentBalance/{accountnumber}")
	public String currentBalance(@PathVariable String accountnumber) {
		AccountDetails acc = accountService.getAccountDetailsByAccountnumber(accountnumber);
		if(acc == null) {
			return "Account not found with the given AccountNumber";
		}
		double currentAmount = accountService.currentBalanceByAccountNumber(accountnumber);
		return "Current Balance :" + currentAmount;
	}
	
	/*@GetMapping("/mini-statement/{accountnumber}")
	public List<TransactionDetails>transactions(@PathVariable String accountnumber){
		AccountDetails acc = accountService.getAccountDetailsByAccountnumber(accountnumber);
		List<TransactionDetails> alltransactions = transactionRepo.findByAccountnumberOrderByTimestampDesc(accountnumber);
		return alltransactions;
	}
	*/
	@GetMapping("/mini-statement/{accountnumber}")
	public ResponseEntity<?> getTransactions(@PathVariable String accountnumber) {
	    AccountDetails acc = accountService.getAccountDetailsByAccountnumber(accountnumber);
	    
	    if (acc == null) {
	        return new ResponseEntity<>("Transaction History not found with the given AccountNumber", HttpStatus.NOT_FOUND);
	    }
	    List<TransactionDetails> transactions = transactionRepo.findByAccountnumberOrderByTimestampDesc(accountnumber);
	    return ResponseEntity.ok(transactions);
	    /*
	       Why the transaction list is returned in descending order:
	       transactionRepo.findByAccountnumberOrderByTimestampDesc(accountnumber);
           This method name follows Spring Data JPA's method naming convention, and it tells Spring to:
           Fetch all transactions for a given account number, ordered by the timestamp field in 
           descending order (i.e., most recent transactions first).
	     */
		
	}
	@GetMapping("/deleteAccount/{accountnumber}")
	public ResponseEntity<String> delete(@PathVariable String accountnumber){
		AccountDetails account = accountService.getAccountDetailsByAccountnumber(accountnumber);
		accountService.deleteAccount(accountnumber);
		if(account != null) {
		return new ResponseEntity<>("Account deleted successfully!!!", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>("Account doesn't exist in the Database",HttpStatus.NOT_FOUND);
	}
}

