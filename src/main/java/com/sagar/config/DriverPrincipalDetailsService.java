package com.sagar.config;

import com.sagar.dataaccessobject.DriverRepository;
import com.sagar.domainobject.DriverDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DriverPrincipalDetailsService implements UserDetailsService {

    private final DriverRepository driverRepository;

    @Autowired
    public DriverPrincipalDetailsService(final DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DriverDO driver = driverRepository.findByUsername(username);
        if(null == driver){
            throw  new UsernameNotFoundException("Not found user");
        }
        return new DriverPrincipal(driver);
    }
}
