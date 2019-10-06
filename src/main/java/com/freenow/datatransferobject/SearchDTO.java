package com.freenow.datatransferobject;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {

	@Size(min = 4, max = 30, message = "username must be between 4 to 30 character long")
	private String username;

	@Size(min = 6, max = 7, message = "must be online or offline")
	private String onlineStatus;

	@Size(min = 4, max = 30, message = "username must be between 4 to 30 character long")
	private String licensePlate;

	@Min(0)
	@Max(5)
	private Double rating;

	@Size(min = 2, max = 3, message = "Operator must be between 2 and 3 characters e.g. lt, lte, gt, gte, eq, ne")
	private String operator;

	@Min(1)
	@Max(100)
	private Integer seatCount;
}
