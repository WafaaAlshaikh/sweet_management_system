package com.example.sweetsystem;

import java.sql.*;

public class TestFeatures {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1482003";

    private Connection connection;

    public TestFeatures() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loginClicked(String email, String passw) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT COUNT(*) FROM sweet_system.users WHERE email = ? AND password = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, email);
                stmt.setString(2, passw);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    public static boolean isEmailAlreadyRegistered(String email) {
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
        System.out.println("Attempting to register user: " + username + ", " + email + ", " + role);

        if (!isValidEmail(email)) {
            System.out.println("Invalid email format: " + email);
            return false;
        }
        if (!isValidName(username)) {
            System.out.println("Invalid username: " + username);
            return false;
        }
        if (!isValidPassword(password)) {
            System.out.println("Invalid password: " + password);
            return false;
        }
        if (!isValidRole(role)) {
            System.out.println("Invalid role: " + role);
            return false;
        }
        if (isEmailAlreadyRegistered(email)) {
            System.out.println("Email already registered: " + email);
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
                System.out.println("Rows inserted: " + rowsInserted);
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    public boolean updateUserRole(String username, String newRole) {
        if (!isValidRole(newRole)) {
            return false;
        }
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "UPDATE sweet_system.users SET role = ? WHERE username = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, newRole);
                stmt.setString(2, username);
                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String username) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "DELETE FROM sweet_system.users WHERE username = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                int rowsDeleted = stmt.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public boolean createPost(String title, String content) {
        String sql = "INSERT INTO sweet_system.posts (title, content, date_posted) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, content);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet getAllPosts() {
        String sql = "SELECT title, content FROM sweet_system.posts";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean postExists(String title) {
        String sql = "SELECT COUNT(*) FROM sweet_system.posts WHERE title = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePost(String title, String newContent) {
        String sql = "UPDATE sweet_system.posts SET content = ? WHERE title = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newContent);
            stmt.setString(2, title);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletePost(String title) {
        String sql = "DELETE FROM sweet_system.posts WHERE title = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet getAllMessages() {
        String query = "SELECT * FROM sweet_system.messages";
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean sendEmail(String recipientId, String subject, String content) {
        System.out.println("Sending email to user ID: " + recipientId + " with subject: " + subject);
        return true;
    }

    public ResultSet getAllFeedback() {
        String sql = "SELECT * FROM sweet_system.feedback";
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isFeedbackIdUnique(int feedbackId) {
        String sql = "SELECT COUNT(*) FROM sweet_system.feedback WHERE feedback_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, feedbackId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isValidFeedbackContent(String content) {
        return content != null && content.trim().length() > 10;
    }

    public boolean addFeedback(int feedbackId, String content) {
        if (!isFeedbackIdUnique(feedbackId)) {
            return false;
        }
        if (!isValidFeedbackContent(content)) {
            return false;
        }

        String sql = "INSERT INTO sweet_system.feedback (feedback_id, content) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, feedbackId);
            stmt.setString(2, content);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteFeedback(int feedbackId) {
        String sql = "DELETE FROM sweet_system.feedback WHERE feedback_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, feedbackId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteFeedback(String feedbackId) {
        try {
            int id = Integer.parseInt(feedbackId);
            return deleteFeedback(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendMessage(int senderId, int receiverId, String content) {
        String query = "INSERT INTO sweet_system.messages (sender_id, receiver_id, content, date) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setString(3, content);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean markMessageAsRead(int messageId) {
        String sql = "UPDATE sweet_system.messages SET is_read = TRUE WHERE message_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, messageId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean markMessageAsUnRead(int messageId) {
        String sql = "UPDATE sweet_system.messages SET is_read = FALSE WHERE message_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, messageId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean markMessageAsRead(String messageId) {
        try {
            int id = Integer.parseInt(messageId);
            return markMessageAsRead(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet getMessageById(String messageId) {
        ResultSet resultSet = null;
        try {
            int id = Integer.parseInt(messageId);
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM messages WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
        } }catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }



    public boolean deleteMessage(String messageId) {
        try {
            int id = Integer.parseInt(messageId);
            return deleteMessage(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }







    public boolean addMessage(int senderId, int receiverId, String content, String subject) {
        String query = "INSERT INTO sweet_system.messages (sender_id, receiver_id, content, date, subject) VALUES (?, ?, ?, NOW(), ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, senderId);
            pstmt.setInt(2, receiverId);
            pstmt.setString(3, content);
            pstmt.setString(4, subject);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteMessage(int messageId) {
        String query = "DELETE FROM sweet_system.messages WHERE message_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, messageId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean isMessageIdUnique(int messageId) {
        String query = "SELECT COUNT(*) FROM sweet_system.messages WHERE message_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, messageId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




    private boolean isRecipeAdded;
    private boolean isRecipeUpdated;
    private boolean isRecipeDeleted;
    private boolean isUserRecipeAdded;

    public void addRecipe(int userId, String name, String content) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO sweet_system.recipes (user_id, recipe_name, instructions) VALUES (?, ?, ?)")) {
            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.setString(3, content);
            int rowsInserted = stmt.executeUpdate();
            isRecipeAdded = rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            isRecipeAdded = false;
        }
    }

    public boolean isRecipeAdded() {
        return isRecipeAdded;
    }

    public boolean isRecipeVisible(String name) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT COUNT(*) FROM sweet_system.recipes WHERE recipe_name = ?")) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateRecipe(String name, String content) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE sweet_system.recipes SET instructions = ? WHERE recipe_name = ?")) {
            stmt.setString(1, content);
            stmt.setString(2, name);
            int rowsUpdated = stmt.executeUpdate();
            isRecipeUpdated = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            isRecipeUpdated = false;
        }
    }

    public boolean isRecipeUpdated() {
        return isRecipeUpdated;
    }

    public void deleteRecipe(String name) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM sweet_system.recipes WHERE recipe_name = ?")) {
            stmt.setString(1, name);
            int rowsDeleted = stmt.executeUpdate();
            isRecipeDeleted = rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            isRecipeDeleted = false;
        }
    }

    public boolean isRecipeDeleted() {
        return isRecipeDeleted;
    }

    public String getRecipeContent(String name) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT instructions FROM sweet_system.recipes WHERE recipe_name = ?")) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("instructions");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUserRecipe(int userId, String name, String content) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO sweet_system.recipes (user_id, recipe_name, instructions) VALUES (?, ?, ?)")) {
            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.setString(3, content);
            int rowsInserted = stmt.executeUpdate();
            isUserRecipeAdded = rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            isUserRecipeAdded = false;
        }
    }

    public boolean isUserRecipeAdded() {
        return isUserRecipeAdded;
    }

    public boolean isUserRecipeVisible(String name, int userId) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT COUNT(*) FROM sweet_system.recipes WHERE recipe_name = ? AND user_id = ?")) {
            stmt.setString(1, name);
            stmt.setInt(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createRecipe(int userId, String name, String content) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO sweet_system.recipes (user_id, recipe_name, instructions) VALUES (?, ?, ?)")) {
            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.setString(3, content);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String viewRecipe(String recipeName) {
        String content = null;
        String query = "SELECT instructions FROM sweet_system.recipes WHERE recipe_name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, recipeName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    content = rs.getString("instructions");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return content;
    }
}


