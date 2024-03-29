# Chat System Implementation

This project is a submission for the programming assignment by **Christof Sobotta** from **Harz University of Applied Sciences**. The goal of the assignment is to implement a complete software design for a functional single-topic chat system.

## Project Overview

The project consists of two Java programs implementing a simple single-topic chat system - a client and a server. The client allows users to identify themselves, connect to a configurable server, and participate in chat sessions. The server brings multiple clients together, manages chat history, and appropriately saves and reloads chat history on reconnects and server restarts.

## Assignment Details

- **Submission Deadline:** February 23, 2024, 23:59
- **Submission Folder:** Specified folder in the Stud.IP event
- **Maximum File Size Limitation:** Adhere to the specified file size limit

## Project Features

- User-friendly client allowing easy identification and connection to a configurable server.
- Multi-client chat sessions with message transmission between clients and the server.
- Chat history tracking on both clients and a status window on the server.
- Server appropriately saves and reloads chat history on reconnects and restarts.
- New clients receive all new messages, and known clients receive the complete chat history.
- Use of either Sockets, RMI, or CORBA for data transmission.

## Project Structure

### Classes

- [ChatTabPageView](https://github.com/swrcvendetta/middleware_assignment/blob/master/docs/Views/TabPages/ChatTabPageView.html)
- [LoginTabPageView](https://github.com/swrcvendetta/middleware_assignment/blob/master/docs/Views/TabPages/LoginTabPageView.html)
- [SettingsTabPageView](https://github.com/swrcvendetta/middleware_assignment/blob/master/docs/Views/TabPages/SettingsTabPageView.html)
- [Client](https://github.com/swrcvendetta/middleware_assignment/blob/master/docs/Client.html)
- [ChatController](https://github.com/swrcvendetta/middleware_assignment/blob/master/docs/Controller/ChatController.html)
- [LoginController](https://github.com/swrcvendetta/middleware_assignment/blob/master/docs/Controller/LoginController.html)
- [SettingsController](https://github.com/swrcvendetta/middleware_assignment/blob/master/docs/Controller/SettingsController.html)
- [MessageRecord](https://github.com/swrcvendetta/middleware_assignment/blob/master/docs/Primitives/MessageRecord.html)
- [MessageParser](https://github.com/swrcvendetta/middleware_assignment/blob/master/docs/MessageParser.html)
- [ClientHandler](https://github.com/swrcvendetta/middleware_assignment/blob/master/docs/ClientHandler.html)
- [Server](https://github.com/swrcvendetta/middleware_assignment/blob/master/docs/Server.html)

## Submission Contents

- Runnable JAR files for executable programs
- Source code of the developed programs (excluding the entire Eclipse tree)
- Design documentation, including various UML diagrams and process descriptions
- Technical design decisions and justifications for technology selection
- Files showing saved chat histories on the server side
- Operation of the system, including all known error cases/exceptions
- Screenshots proving system operation on both Windows and Linux

## Additional Information

- The program is OS-independent, running on both Windows and Linux.
- Include an installation guide or platform independence conditions if necessary.

## Contribution Guidelines

Thank you for considering contributing to this project. Please note that contributions are not currently accepted. This project is developed as part of a programming assignment and is not open for external contributions.

If you have any questions or need further clarification, feel free to contact the project owner.

## License

This project is licensed under the [Creative Commons Attribution-NoDerivatives 4.0 International License](https://github.com/swrcvendetta/middleware_assignment/blob/master/LICENSE).
