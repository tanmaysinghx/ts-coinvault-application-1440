package com.ts.ejb;

import com.ts.model.Transaction; // Import your new Entity
import com.ts.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "coinVaultUnit")
    private EntityManager em;

    public User registerUser(String username, double initialBalance) {
        // Check if user exists to prevent ugly SQL errors
        if (findUserByName(username) != null) {
            throw new IllegalArgumentException("Username already exists!");
        }
        User newUser = new User(username, BigDecimal.valueOf(initialBalance));
        em.persist(newUser);
        return newUser;
    }

    public void transfer(String fromUsername, String toUsername, double amountVal) {
        // 1. Setup Data
        BigDecimal amount = BigDecimal.valueOf(amountVal);

        // 2. Find both users
        User sender = findUserByName(fromUsername);
        User receiver = findUserByName(toUsername);

        // 3. Validation Checks
        if (sender == null) throw new IllegalArgumentException("Sender not found");
        if (receiver == null) throw new IllegalArgumentException("Receiver not found");
        if (sender.getUsername().equals(receiver.getUsername())) {
            throw new IllegalArgumentException("Cannot transfer to yourself");
        }
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient Funds");
        }

        // 4. The Transfer (Update Balances)
        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        // 5. Create History Record
        Transaction history = new Transaction(sender, receiver, amount);

        // 6. Save Changes
        // JPA detects the changes to 'sender' and 'receiver' automatically!
        // We only need to explicitly save the new history record.
        em.persist(history);
    }

    // Helper method to find a user by name
    public User findUserByName(String username) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :name", User.class)
                    .setParameter("name", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // User not found
        }
    }
}