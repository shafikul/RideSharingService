package com.sagar.service.search;

import com.sagar.datatransferobject.SearchDTO;
import com.sagar.domainobject.DriverDO;

import java.util.List;

public interface SearchService {

    List<DriverDO> searchDriverInfo(SearchDTO searchDTO);
}
