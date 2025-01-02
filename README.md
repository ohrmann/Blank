# Blank App

**Blank App** is an Android application that focuses on creating and managing notes securely. The project is built with Kotlin and Jetpack Compose and is planned to be further expanded with additional features.

---

## Current Features

1. **Pattern Lock Setup**
	- On first launch, the user is prompted to draw a pattern lock.
	- For all subsequent launches, the same pattern must be drawn to unlock and access the notes.

2. **Notes with Title and Dates**
	- The main screen displays a (currently dummy) list of notes, each with a title, creation date, and modification date.
	- A search field at the top allows filtering notes by title.
	- Two floating action buttons are located at the bottom:
		- **Filter**: Shows a menu with “By creation date” and “By edit date” options (plans to integrate a calendar for date filtering in the future).
		- **New**: Navigates to a “Create Note” screen, which is currently a placeholder.

3. **Secure Lock Screen**
	- Every time the app restarts, the user must redraw the pattern lock to regain access. An incorrect pattern denies access to all data.

---

## Planned Enhancements

- **Date Filtering**  
  Integrate a calendar-based system to allow filtering notes by a date range (either creation or modification date).

- **Full-Fledged Note Editing**  
  Provide a detailed note editing screen with text fields for title and content, and potentially attachments or images.

- **Biometric Authentication (Optional)**  
  After entering the correct pattern lock, users may confirm their identity with biometrics (fingerprint or face recognition) if the device supports it.

- **Data Storage and Encryption**  
  Replace dummy data with a local database (for example, Room).  
  Encrypt notes on-device to maintain privacy and protect sensitive information.

---

## Technologies Used (So Far)

- **Kotlin**
- **Jetpack Compose** (for UI components)
- **Gesture API** (for pattern lock)
- **Material 3** (basic UI components, such as `AlertDialog` and `FloatingActionButton`)

> **Note**: Other libraries (e.g., Room, Encryption libraries, etc.) are planned for future integration but not yet implemented.

---

## Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/ohrmann/Blank.git

## License
**This project is licensed under the MIT License.**
MIT License

Copyright (c) 2025 Vitalii Kolosov

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.