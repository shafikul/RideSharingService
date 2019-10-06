package com.freenow.datatransferobject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.domainvalue.OnlineStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {

	private Long id;

	@NotNull(message = "Username can not be null!")
	@Size(min=3, max=20, message="username should be between 3 to 20 charecter long")
	private String username;

	@NotNull(message = "Password can not be null!")
	@Size(min=3, max=20, message="password should be between 3 to 20 charecter long")
	private String password;

	private GeoCoordinate coordinate;

	private CarDTO carDTO;

	private OnlineStatus onlineStatus;
}
