package com.ts.ejb;

import com.ts.model.Transaction;
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

    public User registerUser(String username, String password, double initialBalance) {
        if (findUserByName(username) != null) {
            throw new IllegalArgumentException("Username already exists!");
        }
        String hashedPassword = hashPassword(password);
        String accountNumber = generateAccountNumber();
        User newUser = new User(username, hashedPassword, accountNumber, BigDecimal.valueOf(initialBalance));
        em.persist(newUser);
        return newUser;
    }

    private String generateAccountNumber() {
        // Generate a random 10-digit number
        long number = (long) (Math.random() * 9000000000L) + 1000000000L;
        return String.valueOf(number);
    }

    public User authenticate(String username, String password) {
        User user = findUserByName(username);
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            return user;
        }
        return null;
    }

    public void transfer(String fromUsername, String toUsername, double amountVal, String note) {
        BigDecimal amount = BigDecimal.valueOf(amountVal);
        User sender = findUserByName(fromUsername);
        User receiver = findUserByName(toUsername);

        if (sender == null)
            throw new IllegalArgumentException("Sender not found");
        if (receiver == null)
            throw new IllegalArgumentException("Receiver not found");
        if (sender.getUsername().equals(receiver.getUsername())) {
            throw new IllegalArgumentException("Cannot transfer to yourself");
        }
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient Funds");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        Transaction history = new Transaction(sender, receiver, amount, note);
        em.persist(history);
    }

    public void deposit(Long userId, double amountVal) {
        BigDecimal amount = BigDecimal.valueOf(amountVal);
        User user = em.find(User.class, userId);
        if (user == null)
            throw new IllegalArgumentException("User not found");
        if (amountVal <= 0)
            throw new IllegalArgumentException("Amount must be positive");

        user.setBalance(user.getBalance().add(amount));

        Transaction history = new Transaction(user, user, amount, "Deposit");
        em.persist(history);
    }

    public void withdraw(Long userId, double amountVal) {
        BigDecimal amount = BigDecimal.valueOf(amountVal);
        User user = em.find(User.class, userId);
        if (user == null)
            throw new IllegalArgumentException("User not found");
        if (amountVal <= 0)
            throw new IllegalArgumentException("Amount must be positive");
        if (user.getBalance().compareTo(amount) < 0)
            throw new IllegalArgumentException("Insufficient funds");

        user.setBalance(user.getBalance().subtract(amount));

        Transaction history = new Transaction(user, user, amount.negate(), "Withdrawal");
        em.persist(history);
    }

    public User findUserByName(String username) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :name", User.class)
                    .setParameter("name", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Transaction> getUserHistory(Long userId) {
        return em.createQuery(
                "SELECT t FROM Transaction t " +
                        "WHERE t.sender.id = :uid OR t.receiver.id = :uid " +
                        "ORDER BY t.timestamp DESC",
                Transaction.class)
                .setParameter("uid", userId)
                .getResultList();
    }

    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}