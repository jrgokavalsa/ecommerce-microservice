package com.userregistration.application.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable
{
	@NotNull(message = "{Provide Product Id}")
	@Pattern(regexp = "[0-9]", message = "{Provide valid Product Id}")
	private String productId;
	@NotNull(message = "{Provide Quantity}")
	@Pattern(regexp = "[1-9]", message = "{Provide valid Quantity, Quantity should not be zero}")
	private String quantity;
	@JsonIgnore
	private double totalPrice;

}
