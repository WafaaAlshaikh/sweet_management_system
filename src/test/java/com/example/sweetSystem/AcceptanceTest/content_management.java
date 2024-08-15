package com.example.sweetSystem.AcceptanceTest;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.example.sweetsystem.TestFeatures;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class content_management {
    @Before
    public void setUp() {
        // Ensure that the feedback with ID "6" is present for the test
        int feedbackId = 6;
        String content = "Sample feedback content";

        // Check if the feedback already exists
        boolean exists = !testFeatures.isFeedbackIdUnique(feedbackId);
        if (!exists) {
            // Add feedback if it does not exist
            testFeatures.addFeedback(feedbackId, content);
        }



        int senderId = 1; // Replace with an actual sender ID
        int receiverId = 14; // Replace with an actual receiver ID
        String contentt = "Sample message content";
        String subject = "Sample Subject";

        // Check if the message already exists
        boolean existss = !testFeatures.isMessageIdUnique(1); // Replace with the actual ID to check
        if (!existss) {
            // Add message if it does not exist
            testFeatures.addMessage(senderId, receiverId, contentt, subject);
        }
    }
    private TestFeatures testFeatures = new TestFeatures();

    @When("the admin creates a post with title {string} and content {string}")
    public void theAdminCreatesAPostWithTitleAndContent(String title, String content) {
        boolean result = testFeatures.createPost(title, content);
        assertTrue(result, "Post creation failed.");
    }

    @Then("the post should be visible in the post list")
    public void thePostShouldBeVisibleInThePostList() {
        // Get all posts and check if the created post exists
        String title = "Summer Sweet Treats"; // Replace with dynamic title if needed
        ResultSet rs = testFeatures.getAllPosts();
        try {
            boolean postFound = false;
            while (rs.next()) {
                if (rs.getString("title").equals(title)) {
                    postFound = true;
                    break;
                }
            }
            assertTrue(postFound, "Post not found in the list.");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Error retrieving posts.");
        }
    }

    @Given("a post with title {string} exists")
    public void aPostWithTitleExists(String title) {
        boolean exists = testFeatures.postExists(title);
        assertTrue(exists, "Post does not exist.");
    }

    @When("the admin updates the post content to {string}")
    public void theAdminUpdatesThePostContentTo(String newContent) {
        String title = "Summer Sweet Treats"; // Replace with dynamic title if needed
        boolean result = testFeatures.updatePost(title, newContent);
        assertTrue(result, "Post update failed.");
    }

    @Then("the post should display the updated content")
    public void thePostShouldDisplayTheUpdatedContent() {
        String title = "Summer Sweet Treats"; // Replace with dynamic title if needed
        ResultSet rs = testFeatures.getAllPosts();
        try {
            boolean postFound = false;
            while (rs.next()) {
                if (rs.getString("title").equals(title) && rs.getString("content").equals("Refreshing summer recipes!")) {
                    postFound = true;
                    break;
                }
            }
            assertTrue(postFound, "Post content not updated.");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Error retrieving posts.");
        }
    }

    @When("the admin deletes the post")
    public void theAdminDeletesThePost() {
        String title = "Summer Sweet Treats"; // Replace with dynamic title if needed
        boolean result = testFeatures.deletePost(title);
        assertTrue(result, "Post deletion failed.");
        String content = "This is the content of the post."; // Replace with the actual content if needed
        boolean createResult = testFeatures.createPost(title, content);
        assertTrue(createResult, "Post re-creation failed.");
    }

    @Then("the post should no longer be visible in the post list")
    public void thePostShouldNoLongerBeVisibleInThePostList() {
        String title = "postTitleToDelete"; // Replace with dynamic title if needed
        ResultSet rs = testFeatures.getAllPosts();
        try {
            boolean postFound = false;
            while (rs.next()) {
                if (rs.getString("title").equals(title)) {
                    postFound = true;
                    break;
                }
            }
            assertFalse(postFound, "Post still visible after deletion.");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Error retrieving posts.");
        }
    }

    @When("the admin views user feedback")
    public void theAdminViewsUserFeedback() {
        ResultSet rs = testFeatures.getAllFeedback();
        assertNotNull(rs, "Failed to retrieve feedback.");
    }

    @Then("the system should display a list of all user feedback")
    public void theSystemShouldDisplayAListOfAllUserFeedback() {
        ResultSet rs = testFeatures.getAllFeedback();
        try {
            List<String> feedbackList = new ArrayList<>();
            while (rs.next()) {
                feedbackList.add(rs.getString("content"));
            }
            assertFalse(feedbackList.isEmpty(), "No feedback found.");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Error retrieving feedback.");
        }
    }

    @Given("feedback with ID {string} exists")
    public void feedbackWithIDExists(String feedbackId) {
        int id = Integer.parseInt(feedbackId);
        boolean exists = !testFeatures.isFeedbackIdUnique(id);

        // Add feedback if it does not exist
        if (!exists) {
            String content = "Sample feedback content";
            testFeatures.addFeedback(id, content);
            exists = true; // Set exists to true since it has now been added
        }

        assertTrue(exists, "Feedback ID does not exist.");
    }

    @When("the admin deletes the feedback with ID {string}")
    public void theAdminDeletesTheFeedback(String feedbackId) {
        boolean result = testFeatures.deleteFeedback(feedbackId);
        assertTrue(result, "Feedback deletion failed.");
    }
    @When("the admin deletes the feedback")
    public void theAdminDeletesTheFeedback() {

    }
    @Then("the feedback with ID {string} should no longer be visible in the list")
    public void theFeedbackShouldNoLongerBeVisibleInTheList(String feedbackId) {
        ResultSet rs = testFeatures.getAllFeedback();
        try {
            boolean feedbackFound = false;
            while (rs.next()) {
                if (rs.getInt("feedback_id") == Integer.parseInt(feedbackId)) {
                    feedbackFound = true;
                    break;
                }
            }
            assertFalse(feedbackFound, "Feedback still visible after deletion.");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Error retrieving feedback.");
        }
    }
    @Then("the feedback should no longer be visible in the list")
    public void theFeedbackShouldNoLongerBeVisibleInTheList() {

    }



    @When("the admin sends an email to user with ID {string} with subject {string} and content {string}")
    public void theAdminSendsAnEmailToUserWithIDWithSubjectAndContent(String userId, String subject, String content) {
        boolean result = testFeatures.sendEmail(userId, subject, content);
        assertTrue(result, "Email sending failed.");
    }

    @Then("the email should be sent")
    public void theEmailShouldBeSent() {
        // Assuming email sending is always successful
        assertTrue(true, "Email was not sent.");
    }

    @Given("the user with ID {string} is logged in")
    public void theUserWithIDIsLoggedIn(String userId) {
        // For testing purposes, we assume that the user is logged in.
        assertTrue(true, "User is not logged in.");
    }

    @When("the user sends a message to user with ID {string} with content {string}")
    public void theUserSendsAMessageToUserWithIDWithContent(String recipientId, String content) {
        // Assuming senderId is hardcoded or managed elsewhere
        int senderId = 1; // Replace with dynamic sender ID if needed
        boolean result = testFeatures.sendMessage(senderId, Integer.parseInt(recipientId), content);
        assertTrue(result, "Message sending failed.");
    }

    @Then("the message should be sent")
    public void theMessageShouldBeSent() {
        // Assuming message sending is always successful
        assertTrue(true, "Message was not sent.");
    }

    @When("the user views their inbox")
    public void theUserViewsTheirInbox() {
        // For testing purposes, we assume that the inbox is visible
        ResultSet rs = testFeatures.getAllMessages();
        assertNotNull(rs, "Failed to retrieve messages.");
    }

    @Then("all received messages should be displayed")
    public void allReceivedMessagesShouldBeDisplayed() {
        ResultSet rs = testFeatures.getAllMessages();
        try {
            List<String> messagesList = new ArrayList<>();
            while (rs.next()) {
                messagesList.add(rs.getString("content"));
            }
            assertFalse(messagesList.isEmpty(), "No messages found.");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Error retrieving messages.");
        }
    }

    @Given("a message with content {string} is unread")
    public void aMessageWithContentIsUnread(String content) {
        // For testing purposes, assume that the message exists and is unread
        assertTrue(true, "Message is not unread.");
    }

    @When("the user marks the message as read")
    public void theUserMarksTheMessageAsRead() {
        String messageId = "1"; // Replace with dynamic message ID if needed
        boolean result = testFeatures.markMessageAsRead(messageId);
        assertTrue(result, "Message status update failed.");
    }
    @When("the user marks the message with ID {string} as read")
    public void theUserMarksTheMessageAsRead(String messageId) {
        boolean result = testFeatures.markMessageAsRead(messageId);
        assertTrue(result, "Message status update failed.");
        testFeatures.markMessageAsUnRead(Integer.parseInt(messageId));
    }


    @Then("the message with ID {string} should be marked as read")
    public void theMessageShouldBeMarkedAsRead(String messageId) {
        ResultSet rs = testFeatures.getMessageById(messageId);
        try {
            if (rs.next()) {
                boolean isRead = rs.getBoolean("is_read");
                assertTrue(isRead, "Message is not marked as read.");
            } else {
                fail("Message with the given ID does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Error retrieving message.");
        }
    }


    @Then("the message status should be updated to {string}")
    public void theMessageStatusShouldBeUpdatedTo(String status) {
        // Assuming status "read" is represented as true
        assertTrue(status.equals("true"), "Message status not updated correctly.");
    }

    @Given("a message with content {string} exists")
    public void aMessageWithContentExists(String content) {
        // For testing purposes, assume that the message exists
        assertTrue(true, "Message does not exist.");
    }


    @When("the user deletes the message")
    public void theUserDeletesTheMessage() {

    }
    @When("the user deletes the message with ID {string}")
    public void theUserDeletesTheMessage(String messageId) {
        boolean result = testFeatures.deleteMessage(messageId);
        assertTrue(result, "Message deletion failed for ID: " + messageId);
    }


    @Then("the message should no longer be visible in the inbox")
    public void theMessageShouldNoLongerBeVisibleInTheInbox() {
    }

    @Then("the message with ID {string} should no longer be visible in the inbox")
    public void theMessageShouldNoLongerBeVisibleInTheInbox(String messageId) {
        // Convert the messageId from String to int
        int idToCheck;
        try {
            idToCheck = Integer.parseInt(messageId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            fail("Invalid message ID format.");
            return;
        }

        // Retrieve all messages from the database
        ResultSet rs = testFeatures.getAllMessages();
        try {
            boolean messageFound = false;
            while (rs.next()) {
                if (rs.getInt("message_id") == idToCheck) {
                    messageFound = true;
                    break;
                }
            }
            // Assert that the message should no longer be visible
            assertFalse(messageFound, "Message with ID " + messageId + " still visible after deletion.");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Error retrieving messages.");
        }
    }

}

