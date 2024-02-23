/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package Controller;

import Events.ValidationChangedEventArgs;
import Interfaces.INotifyValidationChanged;
import Models.ChatModel;
import Primitives.MessageRecord;

import java.sql.Timestamp;

/**
 * The ChatController class manages the interaction between the user interface and the ChatModel
 * in the context of a chat application. It extends the ControllerBase class and implements specific
 * functionality for sending and validating messages.
 */
public class ChatController extends ControllerBase {

    /**
     * The ChatModel associated with this controller.
     */
    private ChatModel chatModel;

    /**
     * Constructs a new ChatController instance with the specified ChatModel and sets up
     * listeners to handle incoming messages.
     *
     * @param chatModel The ChatModel instance to be associated with this controller.
     */
    public ChatController(ChatModel chatModel) {
        this.chatModel = chatModel;
        this.chatModel.listenForMessage();
    }

    /**
     * Validates the provided message based on specific rules.
     *
     * @param message The message to be validated.
     * @return A validation message or null if the message is valid.
     */
    private String validateMessage(String message) {
        // Some validation rules for the message
        if (message.isEmpty())
            return "Message can not be empty.";
        if (message.length() > 200)
            return "Message can not contain more than 200 characters.";
        return null;
    }

    /**
     * Sends a message to the chat, validates it, and notifies the sender about the validation result.
     * If the message is valid, it is printed to the console, and the ChatModel is updated.
     *
     * @param sender   The sender of the message, implementing INotifyValidationChanged.
     * @param username The username associated with the message.
     * @param message  The message content.
     */
    public void sendMessage(INotifyValidationChanged sender, String username, String message) {
        String messageValidationResult = validateMessage(message);
        sender.onValidationChanged(this, new ValidationChangedEventArgs("message", messageValidationResult == null, messageValidationResult));

        if (messageValidationResult == null) {
            System.out.println(message);
            Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
            MessageRecord msg = new MessageRecord(username, message, timestamp);
            this.chatModel.sendMessage(msg);
        }
    }
}
