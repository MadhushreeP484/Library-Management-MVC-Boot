package org.jsp.library.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class PaymentDetails {
	
	private int id;
	private String amount;
	private String currency;
	private boolean status;
	private String keyDetails;
	private int orderId;
	private int paymentId;

}
