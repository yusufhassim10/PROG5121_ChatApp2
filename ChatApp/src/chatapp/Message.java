package chatapp;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Message {
    // === Fields ===
    public String messageID;
    public int messageNumber;
    public String recipient;
    public String messageText;
    public String messageHash;
    public String status;

    private static int totalMessages = 0;

    // === Constructor ===
    public Message(int number, String recipient, String messageText) {
        this.messageNumber = number;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // === Generate 10-digit ID ===
    private String generateMessageID() {
        Random rand = new Random();
        int id = rand.nextInt(900000000) + 100000000;  // 9-digit random
        return String.valueOf(id);
    }

    // === Check recipient cell number format ===
    public boolean checkRecipientCell() {
        return recipient.matches("^\\+27\\d{9}$");
    }

    // === Check message character limit ===
    public boolean checkMessageLength() {
        return messageText.length() <= 250;
    }

    // === Create hash: XX:N:FIRSTLAST ===
    public String createMessageHash() {
        String[] words = messageText.trim().split(" ");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : "";
        return messageID.substring(0, 2) + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase();
    }

    // === Send, Store, or Discard the message ===
    public String sendMessageOption(String choice) {
        switch (choice.toLowerCase()) {
            case "send":
                status = "Sent";
                totalMessages++;
                return "Message successfully sent.";
            case "store":
                status = "Stored";
                storeMessage();  // ✅ Write to JSON file
                return "Message successfully stored.";
            case "discard":
                status = "Disregarded";
                return "Press 0 to delete message.";
            default:
                return "Invalid option.";
        }
    }

    // === Display message popup ===
    public void displayMessageDetails() {
        JOptionPane.showMessageDialog(null,
                "Message ID: " + messageID +
                "\nMessage Hash: " + messageHash +
                "\nRecipient: " + recipient +
                "\nMessage: " + messageText);
    }

    // === Save stored message to file ===
    public void storeMessage() {
        try (FileWriter file = new FileWriter("storedMessages.json", true)) {
            file.write("{\n\t\"id\": \"" + messageID + "\",\n\t\"recipient\": \"" + recipient +
                       "\",\n\t\"message\": \"" + messageText + "\"\n},\n");
        } catch (IOException e) {
            System.out.println("⚠️ Error storing message: " + e.getMessage());
        }
    }

    // === Return total sent messages ===
    public static int returnTotalMessages() {
        return totalMessages;
    }
}
