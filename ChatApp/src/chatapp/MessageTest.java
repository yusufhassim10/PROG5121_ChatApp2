package chatapp;

import org.junit.jupiter.api.Test;

public class MessageTest {

    @Test
    void testCheckRecipientCell_Valid() {
        Message msg = new Message(1, "+27831234567", "Hello there!");
        assertTrue(msg.checkRecipientCell());
    }

    @Test
    void testCheckRecipientCell_Invalid() {
        Message msg = new Message(2, "0831234567", "Invalid number format");
        assertFalse(msg.checkRecipientCell());
    }

    @Test
    void testCheckMessageLength_Valid() {
        String shortMsg = "A".repeat(250);  // Exactly 250 characters
        Message msg = new Message(3, "+27831234567", shortMsg);
        assertTrue(msg.checkMessageLength());
    }

    @Test
    void testCheckMessageLength_TooLong() {
        String longMsg = "B".repeat(251);  // 251 characters
        Message msg = new Message(4, "+27831234567", longMsg);
        assertFalse(msg.checkMessageLength());
    }

    @Test
    void testCreateMessageHash_Format() {
        Message msg = new Message(5, "+27831234567", "Hello again");
        String hash = msg.createMessageHash();
        assertTrue(hash.matches("\\d{2}:5:[A-Z]+"));
    }

    @Test
    void testSendMessageOption_Send() {
        Message msg = new Message(6, "+27831234567", "Send this message");
        String result = msg.sendMessageOption("send");
        assertEquals("Message successfully sent.", result);
    }

    @Test
    void testSendMessageOption_Store() {
        Message msg = new Message(7, "+27831234567", "Store this message");
        String result = msg.sendMessageOption("store");
        assertEquals("Message successfully stored.", result);
    }

    @Test
    void testSendMessageOption_Discard() {
        Message msg = new Message(8, "+27831234567", "Discard this message");
        String result = msg.sendMessageOption("discard");
        assertEquals("Press 0 to delete message.", result);
    }

    @Test
    void testSendMessageOption_Invalid() {
        Message msg = new Message(9, "+27831234567", "Unknown action");
        String result = msg.sendMessageOption("invalid");
        assertEquals("Invalid option.", result);
    }

    private void assertTrue(boolean checkRecipientCell) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertFalse(boolean checkRecipientCell) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertEquals(String message_successfully_sent, String result) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
