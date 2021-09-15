package com.userregistration.application.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO implements Serializable
{
	private static final long serialVersionUID = 3994192272098493497L;
	
	@NotEmpty(message = "{user.name.not.blank}")
	@Size(min = 5, max = 50)
	@Pattern(regexp = "[a-zA-Z0-9]+")
	private String userName;

	@NotEmpty(message = "{user.firstname.not.blank}")
	@Size(min = 2, max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9_ ]*$")
	private String firstName;

	@NotEmpty(message = "{user.secondname.not.blank}")
	@Size(min = 2, max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9_ ]*$")
	private String lastName;
	
	@NotNull(message = "{user.mobileno.not.null}")
	@Pattern(regexp = "[0-9]{10}", message = "{user.mobileno.invalid}")
	private String mobileNo;

	@NotEmpty(message = "{user.email.not.blank}")
	@Email
	private String emailId;

	@NotEmpty(message = "{user.aadhaarno.not.null}")
	@Pattern(regexp = "[0-9]{12}", message = "{user.aadhaarno.invalid}")
	private String aadhaarNo;

	@NotNull(message = "{user.pancardno.not.null} ")
	@Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "{user.pancardno.invalid}")	
	private String panCardNo;
	
	
}
