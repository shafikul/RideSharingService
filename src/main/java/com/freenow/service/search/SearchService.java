package com.freenow.service.search;

import com.freenow.datatransferobject.SearchDTO;
import com.freenow.domainobject.DriverDO;

import java.util.List;

public interface SearchService {

    List<DriverDO> searchDriverInfo(SearchDTO searchDTO);
}
