package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.Model.AccountDetails;
import com.example.demo.ModelDto.AccountDto;
import com.example.demo.Repository.AccountRepository;
import com.example.demo.Repository.TransactionRepository;
import com.example.demo.Service.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
	
	@Mock
	private AccountRepository accountRepository;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	@InjectMocks
	private AccountService accountService;
	
	@Test
    void testCreateAccount() {
        // Arrange (Input DTO)
        AccountDto dto = new AccountDto();
        dto.setFullname("John Doe");
        dto.setEmail("john@example.com");
        dto.setPhone("9876543210");

        // Mock generated account number
        String generatedAccNo = "12345678901";

        // Spy on private method if needed OR mock externally:
        // Instead of mocking generateUniqueAccountNo(), 
        // we mock the repository save() after object creation.

        AccountDetails savedAccount = new AccountDetails();
        savedAccount.setFullname(dto.getFullname());
        savedAccount.setEmail(dto.getEmail());
        savedAccount.setPhone(dto.getPhone());
        savedAccount.setAccountnumber(generatedAccNo);
        savedAccount.setBalance(0.0);

        // Mocking repository save()
        Mockito.when(accountRepository.save(Mockito.any(AccountDetails.class)))
               .thenReturn(savedAccount);

        // Act
        AccountDetails result = accountService.createAccount(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.getFullname(), result.getFullname());
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getPhone(), result.getPhone());
        assertEquals(0.0, result.getBalance());
        assertEquals(generatedAccNo, result.getAccountnumber());

        // Verify save() was called exactly once
        Mockito.verify(accountRepository, Mockito.times(1))
               .save(Mockito.any(AccountDetails.class));
	}

}
