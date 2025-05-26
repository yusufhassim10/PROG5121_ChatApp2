package chatapp;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Arrays for Part 3
        ArrayList<Message> sentMessages = new ArrayList<>();
        ArrayList<Message> storedMessages = new ArrayList<>();
        ArrayList<Message> disregardedMessages = new ArrayList<>();
        ArrayList<String> messageHashes = new ArrayList<>();
        ArrayList<String> messageIDs = new ArrayList<>();

        // === USER REGISTRATION ===
        System.out.println("=== USER REGISTRATION ===");

        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        System.out.print("Enter SA phone number (e.g. +27831234567): ");
        String cell = input.nextLine();

        Login user = new Login(username, password, cell);

        String registrationMessage = user.registerUser();
        System.out.println(registrationMessage);

        if (!registrationMessage.equals("Registration successful.")) {
            System.out.println("Exiting due to failed registration.");
            return;
        }

        // === LOGIN ===
        System.out.println("\n=== LOGIN ===");

        System.out.print("Enter username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = input.nextLine();

        boolean isLoggedIn = user.loginUser(loginUsername, loginPassword);

        String loginMessage = user.returnLoginStatus(isLoggedIn, "User", "One");
        System.out.println(loginMessage);

        if (!isLoggedIn) {
            System.out.println("Exiting due to failed login.");
            return;
        }

        // === MAIN MENU ===
        int choice;
        do {
            System.out.println("\nWelcome to QuickChat.");
            System.out.println("1) Send Message");
            System.out.println("2) Reports & Tools");
            System.out.println("3) Quit");
            System.out.print("Enter choice: ");
            choice = input.nextInt();
            input.nextLine(); // Clear newline

            if (choice == 1) {
                System.out.print("How many messages do you want to send? ");
                int numMessages = input.nextInt();
                input.nextLine();

                for (int i = 0; i < numMessages; i++) {
                    System.out.println("\nEnter message " + (i + 1) + " details:");
                    System.out.print("Recipient (+27...): ");
                    String recipient = input.nextLine();

                    System.out.print("Message (max 250 characters): ");
                    String messageText = input.nextLine();

                    Message msg = new Message(i + 1, recipient, messageText);

                    if (!msg.checkRecipientCell()) {
                        System.out.println("❌ Invalid recipient number format. Message not sent.");
                        continue;
                    }

                    if (!msg.checkMessageLength()) {
                        int over = messageText.length() - 250;
                        System.out.println("❌ Message exceeds 250 characters by " + over + ". Please shorten it.");
                        continue;
                    }

                    System.out.print("Do you want to Send, Store, or Discard this message? ");
                    String action = input.nextLine();

                    String result = msg.sendMessageOption(action);
                    System.out.println(result);
                    msg.displayMessageDetails();

                    // Store message in the appropriate array
                    switch (action.toLowerCase()) {
                        case "send":
                            sentMessages.add(msg);
                            break;
                        case "store":
                            storedMessages.add(msg);
                            break;
                        case "discard":
                            disregardedMessages.add(msg);
                            break;
                    }

                    messageHashes.add(msg.createMessageHash());
                    messageIDs.add(msg.createMessageHash());
                }

                System.out.println("✅ Total messages sent: " + Message.returnTotalMessages());
            }

            // === REPORTS & TOOLS MENU ===
            else if (choice == 2) {
                int subChoice;
                do {
                    System.out.println("\n--- Reports & Tools ---");
                    System.out.println("1) View All Sent Messages");
                    System.out.println("2) View Longest Sent Message");
                    System.out.println("3) Search by Message ID");
                    System.out.println("4) Search by Recipient");
                    System.out.println("5) Delete a Message by Hash");
                    System.out.println("6) Back to Main Menu");
                    System.out.print("Enter choice: ");
                    subChoice = input.nextInt();
                    input.nextLine();

                    switch (subChoice) {
                        case 1:
                            System.out.println("\nAll Sent Messages:");
                            for (Message m : sentMessages) {
                                System.out.println("To: " + m.recipient + " | Message: " + m.messageText);
                            }
                            break;

                        case 2:
                            Message longest = null;
                            for (Message m : sentMessages) {
                                if (longest == null || m.messageText.length() > longest.messageText.length()) {
                                    longest = m;
                                }
                            }
                            if (longest != null) {
                                System.out.println("\nLongest Message Sent:");
                                System.out.println("To: " + longest.recipient);
                                System.out.println("Message: " + longest.messageText);
                            } else {
                                System.out.println("No messages found.");
                            }
                            break;

                        case 3:
                            System.out.print("Enter message hash/ID to search: ");
                            String searchID = input.nextLine();
                            boolean found = false;
                            for (Message m : sentMessages) {
                                if (m.createMessageHash().equalsIgnoreCase(searchID)) {
                                    System.out.println("Match Found: " + m.messageText);
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                System.out.println("Message not found.");
                            }
                            break;

                        case 4:
                            System.out.print("Enter recipient number to search: ");
                            String searchRecipient = input.nextLine();
                            for (Message m : sentMessages) {
                                if (m.recipient.equals(searchRecipient)) {
                                    System.out.println("Message: " + m.messageText);
                                }
                            }
                            break;

                        case 5:
                            System.out.print("Enter message hash to delete: ");
                            String hashToDelete = input.nextLine();
                            Message toRemove = null;
                            for (Message m : sentMessages) {
                                if (m.createMessageHash().equals(hashToDelete)) {
                                    toRemove = m;
                                    break;
                                }
                            }
                            if (toRemove != null) {
                                sentMessages.remove(toRemove);
                                System.out.println("Message deleted successfully.");
                            } else {
                                System.out.println("No message found with that hash.");
                            }
                            break;
                    }

                } while (subChoice != 6);
            }

        } while (choice != 3);

        System.out.println("👋 Exiting program. Goodbye!");
        input.close();
    }
}
