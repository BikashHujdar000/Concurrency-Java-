package com.example.bikash.SpringTest.Auditors;

import com.example.bikash.SpringTest.Model.User;
import io.jsonwebtoken.security.Jwks;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class AuditAwareImplementation implements AuditorAware {
    @Override
    public Optional getCurrentAuditor() {

      Authentication authentication  =  SecurityContextHolder.getContext().getAuthentication();

      if (authentication == null) {
          return Optional.of("Anonymous");
      }
        return  Optional.of(authentication.getName());

    }
}
