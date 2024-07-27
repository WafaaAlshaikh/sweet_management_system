package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestFeatures {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1482003";

    public boolean loginClicked(String email, String passw) {
        // Initialize database connection
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Query to check if the email and password exist
            String sql = "SELECT COUNT(*) FROM sweet_system.users WHERE email = ? AND password = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, email);
                stmt.setString(2, passw);

                // Execute query and check if user exists
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0; // Return true if count is greater than 0
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions properly
        }
        return false; // Return false if there's an exception or no user is found
    }





    //signup
    public static boolean isValidEmail(String email) {
        return email.contains("@") && email.length() >= 11;
    }

    public static boolean isValidName(String name) {
        return name != null && name.trim().length() >= 2;
    }


    public static boolean isValidPassword(String password) {
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        boolean hasDigit = false;
        if (password.length() < 4) return false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) hasLowerCase = true;
            if (Character.isUpperCase(password.charAt(i))) hasUpperCase = true;
            if (Character.isDigit(password.charAt(i))) hasDigit = true;
        }
        return hasLowerCase && hasUpperCase && hasDigit;
    }



    public static boolean isValidId(String id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT COUNT(*) FROM sweet_system.users WHERE CAST(user_id AS TEXT) = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        if (count > 0) {
                            return false;
                        }
                        return id.length() > 2;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean isValidRole(String role) {
        return role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("Customer") || role.equalsIgnoreCase("Store Owner") || role.equalsIgnoreCase("Supplier") || role.equalsIgnoreCase("Beneficiary User");
    }

    private static boolean isEmailAlreadyRegistered(String email) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT COUNT(*) FROM sweet_system.users WHERE email = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean registerUser(String username, String password, String email, String role) {
        if (!isValidEmail(email)) {
            return false; // Invalid email format
        }
        if (!isValidName(username)) {
            return false; // Invalid name
        }
        if (!isValidPassword(password)) {
            return false; // Invalid password
        }
        if (!isValidRole(role)) {
            return false;
        }
        if (isEmailAlreadyRegistered(email)) {
            return false;
        }
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO sweet_system.users (username, password, email, role) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, email);
                stmt.setString(4, role);
                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
}
