package com.ts.ejb;

import com.ts.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;

@Stateless
public class UserService {

    // The container (TomEE) injects the database manager here automatically.
    // We do not need to open/close connections manually.
    @PersistenceContext(unitName = "coinVaultUnit")
    private EntityManager em;

    public User registerUser(String username, double initialBalance) {
        // 1. Prepare data (Convert double to BigDecimal for money safety)
        User newUser = new User(username, BigDecimal.valueOf(initialBalance));

        // 2. Persist (This generates the INSERT SQL)
        em.persist(newUser);

        return newUser;
    }
}