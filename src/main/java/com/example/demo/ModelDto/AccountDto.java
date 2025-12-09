package com.example.demo.ModelDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AccountDto {

	@NotBlank(message = "Full Name is mandatory")
	private String fullname;
	@Email(message = "Invalid Email")
	@NotBlank
	private String email;
	@NotBlank(message = "Phone number is required")
	private String phone;
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
