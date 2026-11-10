💊 Meditaken - Medication Management & Reminder Application

A secure Android application developed using Kotlin, Jetpack Compose, Room, SQLite, and SQLCipher. Designed to help users manage their medications, create personalised reminder schedules, and maintain their daily medication routines.

The application combines medication management, custom alarm scheduling, interactive notifications, biometric authentication, and AES-256 encrypted local database storage to provide a convenient and secure medication tracking experience.

### Screenshots

### Screenshots

<table>
  <tr>
    <td align="center">
      <img src="./screenshots/lockscreen%20of%20meditaken%20with%20message.png" width="180">
      <br>Lockscreen
    </td>
    <td align="center">
      <img src="./screenshots/home%20screen%20diplaying%20next%20alarm.png" width="180">
      <br>Home Screen
    </td>
    <td align="center">
      <img src="./screenshots/home%20screen%20in%20dark%20mode%20.png" width="180">
      <br>Home Screen – Dark Mode
    </td>
    <td align="center">
      <img src="./screenshots/addmedication%20screen%20dark%20mode%20empty.png" width="180">
      <br>Medication Screen – Dark Mode
    </td>
  </tr>
 
📱 About the Project

This project is a native Android medication management application developed using Kotlin and Android Studio.

The primary objective was to create an application that helps users organise their medications, manage personalised reminder schedules, and receive timely notifications when medication is due.

The application provides full medication management functionality, allowing users to add, edit, delete, and retrieve medication records. It also incorporates a custom-built alarm system using Android AlarmManager, with interactive notification functionality that allows users to stop or snooze reminders.

Security was an important consideration throughout development. The application uses SQLCipher with AES-256 encryption to protect its local SQLite database and incorporates Android BiometricPrompt and device lock-screen authentication to restrict unauthorised application access.

The user interface was developed primarily using Jetpack Compose, providing a modern, responsive, and accessible user experience with support for both light and dark modes.

 <tr>
    <td align="center">
      <img src="./screenshots/Meditaken%20LockScreen%20on%20S22%20ultra.jpg" width="180">
      <br>S22 Ultra – LockScreen
    </td>
    <td align="center">
      <img src="./screenshots/s22%20ultra%20home%20screen%20with%20a%20record%20white.jpg" width="180">
      <br>S22 Ultra – Medication Screen
    </td>
    <td align="center">
      <img src="./screenshots/s22%20ultra%20meditaken%20homescreen%20with%20added%20record%20dark%20mode.jpg" width="180">
      <br>S22 Ultra – Home Screen Dark Mode
    </td>
    <td align="center">
      <img src="./screenshots/s22%20ultra%20addmedicationscreen%20with%20data%20conjured%20dark%20mode.jpg" width="180">
      <br>S22 Ultra – Medication Screen Dark Mode
    </td>
  </tr>
</table>


✨ Features

💊 Medication Management

Add new medication records.

Edit existing medication information.

Delete medication records.

Retrieve and display stored medication information.

Full Create, Read, Update and Delete (CRUD) functionality.

Persistent local medication storage.

⏰ Custom Reminder Scheduling

Create personalised medication reminder schedules.

Schedule reminders according to individual medication routines.

Android AlarmManager integration.

Custom-built alarm management system.

Scheduled reminders that can trigger outside normal application usage.

🔔 Interactive Notifications & Alarms

Local notifications for scheduled medication reminders.

Custom alarm functionality.

Interactive notification actions.

Snooze: Temporarily postpone an active reminder.

Stop: Dismiss the current alarm.

Integrated alarm and notification management.

🔐 Security & Authentication

SQLCipher AES-256 database encryption.

Encrypted local SQLite database.

Android BiometricPrompt integration.

Biometric authentication before accessing the application.

Device lock-screen authentication.

Additional protection for sensitive medication information.

🎨 User Interface & Experience

Modern Android interface built using Jetpack Compose.

Declarative UI development using composable functions.

Simple and accessible navigation.

Light and dark mode support.

Reusable UI components.

User-friendly medication management screens.

💾 Database & Local Storage

SQLite relational database.

Room persistence library.

SQLCipher encrypted database integration.

Full database CRUD operations.

Local data persistence.

No remote database required for core medication management functionality.

🛠️ Technologies & Tools

The application was developed using the following technologies:

Technology

Implementation

Kotlin

Primary programming language

Android Studio

Android development environment

Jetpack Compose

UI development

SQLite

Local relational database

Room

Database abstraction and persistence

SQLCipher

AES-256 database encryption

Android AlarmManager

Alarm scheduling and triggering

Android BiometricPrompt

Biometric authentication

Kotlin Serialization

Kotlin serialization support

Android Notifications

Local notifications and interactive actions

Gradle

Project build and dependency management

🏗️ Technical Implementation

1. User Interface – Jetpack Compose

The application interface was developed primarily using Jetpack Compose, Android's modern declarative UI toolkit.

Composable functions are used to construct the application's screens and reusable interface components. This approach helps maintain a modular UI structure and separates interface elements from the underlying application functionality.

The application also includes dark mode support to provide an alternative viewing experience.

2. Database Management – Room & SQLite

Room is used as the persistence layer for the application's SQLite database.

Database operations support the complete CRUD lifecycle:

Create: Insert new medication records.

Read: Retrieve and display stored medication information.

Update: Modify existing medication records.

Delete: Remove medication records from the database.

This allows medication information to persist locally between application sessions.

3. Database Encryption – SQLCipher AES-256

SQLCipher is integrated to provide AES-256 encryption for the local SQLite database.

This implementation adds a layer of protection to stored medication information, helping prevent unauthorised access to the database outside the application's intended access mechanisms.

4. Alarm Management – Android AlarmManager

A custom alarm system was implemented using Android AlarmManager to manage medication reminder scheduling and triggering.

The alarm functionality supports scheduled reminders and integrates with the application's notification system.

Custom stop and snooze functionality was also implemented, allowing users to interact with reminders rather than simply receiving passive notifications.

5. Authentication – BiometricPrompt

Android's BiometricPrompt API is used to implement biometric authentication.

The application also incorporates device lock-screen authentication as an additional authentication mechanism before allowing access to the application's contents.

This provides an additional security measure for protecting personal medication information.

🔒 Privacy & Security

The application was designed with an emphasis on local data storage and protecting sensitive user information.

Security-related functionality includes:

AES-256 encrypted SQLite database using SQLCipher.

Local database persistence through Room.

Biometric authentication.

Device lock-screen authentication.

Authentication before accessing the application.

Medication information is stored locally on the device. Core application functionality does not require a remote database or cloud-based medication storage service.

🧪 Testing

The application can be explored through its main functional areas:

Medication creation, editing, retrieval, and deletion.

Custom reminder scheduling.

Alarm triggering and dismissal.

Notification snooze functionality.

Biometric authentication.

Device lock-screen authentication.

Database persistence.

Light and dark mode functionality.

The application was tested across multiple Android device configurations to evaluate its functionality, compatibility, reliability, and performance under different operating conditions.

Testing included both Android Studio emulators and a physical Android device, allowing the application to be evaluated in simulated and real-world environments.

Test Devices

Testing was conducted using the following devices:

Google Pixel 6 Pro

Android Studio Emulator

Application functionality and compatibility testing

Google Pixel 7 Pro

Android Studio Emulator

Application functionality and compatibility testing

Samsung Galaxy S22 Ultra

Physical Android Device

Real-device functionality and performance testing

Functional Testing

The application's main features were tested to verify that they operated as expected, including:

Medication creation, editing, deletion, and retrieval.

Database persistence and data access.

Custom medication reminder scheduling.

Alarm triggering at scheduled times.

Interactive notification functionality.

Snooze and stop alarm actions.

Biometric authentication.

Device lock-screen authentication.

Light and dark mode functionality.

Performance Testing

Performance testing was conducted on a physical Samsung Galaxy S22 Ultra using Android Developer Options and CPU monitoring applications.

The application was evaluated under several operating conditions to observe its behaviour and resource usage.

Testing scenarios included:

1. Standard Application Operation

Monitoring CPU usage during normal application operation.

Observing resource consumption while navigating the application.

Evaluating performance during medication management and database operations.

2. Alarm Activation

Monitoring CPU usage while medication alarms were actively ringing.

Observing application behaviour during alarm activation.

Evaluating resource usage while interacting with alarm functionality.

3. Different Application Configurations

Testing under different reminder and application settings.

Observing performance under different operational configurations.

Evaluating alarm behaviour across different scheduling scenarios.

4. Background and Non-Running Application Scenarios

Testing scheduled alarms when the application was not actively running.

Observing whether scheduled reminders triggered as expected without the application being open.

Monitoring device behaviour and CPU usage during alarm activation in these scenarios.

Testing Objectives

The testing process focused on evaluating:

Application stability and responsiveness.

Reliability of scheduled medication reminders.

Alarm functionality when the application was not actively running.

Database operation and data persistence.

CPU resource usage during normal operation and alarm activation.

Behaviour on both emulated and physical Android devices.

The combination of emulator-based testing, physical device testing, and performance monitoring provided an opportunity to evaluate the application beyond basic functional verification.

🔮 Potential Future Enhancements

Potential areas for further development include:

Medication adherence tracking and history.

Medication intake statistics and reporting.

Enhanced reminder customisation.

Medication history and missed-dose tracking.

Database backup and restore functionality.

Additional accessibility improvements.

🎓 Project Information

Project: Android Medication Management & Reminder Application

Platform: Android

Language: Kotlin

Development Environment: Android Studio

UI Framework: Jetpack Compose

Database: Room / SQLite with SQLCipher encryption

Project Status: Completed – Source code available in this repository.

👨‍💻 Developer

Developed as an academic Android application development project, demonstrating practical implementation of:

Native Android application development.

Kotlin programming.

Declarative UI development with Jetpack Compose.

Relational database management.

AES-256 database encryption.

Biometric authentication.

Background alarm scheduling.

Local notifications and interactive reminder functionality.

📄 Copyright & Usage

Copyright © 2026 Laszlo Major. All rights reserved.

This repository is publicly available for portfolio and educational demonstration purposes.

The source code is provided for viewing and evaluation. No licence is granted for copying, modifying, redistributing, or commercially using this project without prior written permission from the copyright holder.

Third-party libraries and dependencies remain subject to their respective licences.
