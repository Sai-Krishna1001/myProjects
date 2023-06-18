package com.virtusa.ars.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDTO {

	private String userId;
	private int accountNumber;
	private String bankName;
	private BigDecimal balance;
}
