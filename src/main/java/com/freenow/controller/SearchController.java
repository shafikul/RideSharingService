package com.freenow.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freenow.controller.mapper.DriverMapper;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.datatransferobject.SearchDTO;
import com.freenow.service.search.SearchService;

/**
 * All search operations for driver & car related information will be routed by
 * this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/a/search")
public class SearchController {

	private final SearchService searchService;

	@Autowired
	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}

	@PostMapping
	public List<DriverDTO> search(@Valid @RequestBody SearchDTO searchDTO) {
		return DriverMapper.makeDriverDTOList(searchService.searchDriverInfo(searchDTO));
	}
}
