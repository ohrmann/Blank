Blank App

Blank App is an Android application that focuses on creating and managing notes securely. The project is built with Kotlin and Jetpack Compose and is planned to be further expanded with additional features.

Current Features
	1.	Pattern Lock Setup
	•	On first launch, the user is prompted to draw a pattern lock.
	•	For all subsequent launches, the same pattern must be drawn to unlock and access the notes.
	2.	Notes with Title and Dates
	•	The main screen displays a (currently dummy) list of notes, each with a title, creation date, and modification date.
	•	A search field at the top allows filtering notes by title.
	•	Two floating action buttons are located at the bottom:
	•	Filter: Shows a menu with “By creation date” and “By edit date” options (plans to integrate a calendar for date filtering in the future).
	•	New: Navigates to a “Create Note” screen, which is currently a placeholder.
	3.	Secure Lock Screen
	•	Every time the app restarts, the user must redraw the pattern lock to regain access. An incorrect pattern denies access to all data.

Planned Enhancements
	•	Date Filtering
	•	Integrate a calendar-based system to allow filtering notes by a date range (either creation or modification date).
	•	Full-Fledged Note Editing
	•	Provide a detailed note editing screen with text fields for title and content, and potentially attachments or images.
	•	Biometric Authentication (Optional)
	•	After entering the correct pattern lock, users may confirm their identity with biometrics (fingerprint or face recognition) if the device supports it.
	•	Data Storage and Encryption
	•	Replace dummy data with a local database (for example, Room).
	•	Encrypt notes on-device to maintain privacy and protect sensitive information.

Technologies Used (So Far)
	•	Kotlin
	•	Jetpack Compose (for UI components)
	•	Gesture API (for pattern lock)
	•	Material 3 (basic UI components, such as AlertDialog and FloatingActionButton)

	Note: Other libraries (e.g., Room, BiometricPrompt, etc.) are planned for future integration but not yet implemented.

Getting Started
	1.	Clone the repository.
  2.  Open the project in Android Studio.
	3.	Check build.gradle (in the app module) to ensure all dependencies are correct.
	4.	Run the project on an emulator or a physical device with Android 6.0 (API 23) or higher.

Usage
	1.	First Launch
	•	The user is prompted to set a pattern lock by drawing a gesture on screen.
	2.	Subsequent Launches
	•	The user must redraw the saved pattern to unlock and view notes. An incorrect pattern denies access.
	3.	Main Screen
	•	Displays a list of notes with titles, creation dates, and modification dates (currently sample data).
	•	A search bar at the top to filter notes by title.
	•	Two floating action buttons:
	•	Filter (menu options for date filtering — placeholder).
	•	New (navigates to a basic “Create Note” screen).
	4.	Create Note Screen
	•	Currently a placeholder screen showing a simple text label. In the future, it will allow users to enter a title and content, and possibly attach images.
