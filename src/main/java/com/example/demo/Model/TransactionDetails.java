package com.example.demo.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="transactions")
public class TransactionDetails {

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String accountnumber;
	private String type;// type means deposit or withdraw
	private double amount;
	private LocalDateTime timestamp;
	
	
	public TransactionDetails() {
		
	}
	public TransactionDetails(String accountnumber, String type, double amount, LocalDateTime timestamp) {
		this.accountnumber = accountnumber;
		this.type = type;
		this.amount = amount;
		this.timestamp = timestamp;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
}
