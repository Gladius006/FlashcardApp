# 📚 FlashcardApp

A clean and simple Android flashcard quiz app built with Kotlin to help you study and memorize information effectively.

---

## 📱 Screenshots

> Add your screenshots here after running the app on a device or emulator.

---

## ✨ Features

- 📖 **Quiz Mode** — Flip through flashcards one at a time with a clean card-based interface
- 👁️ **Show/Hide Answer** — Test yourself by revealing the answer only when ready
- ⬅️➡️ **Card Navigation** — Move forward and backward through your deck freely
- ➕ **Add Cards** — Create new flashcards with a question and answer
- ✏️ **Edit Cards** — Update any existing flashcard anytime
- 🗑️ **Delete Cards** — Remove individual cards with confirmation dialog
- 📋 **Manage Deck** — View and manage all your cards in one screen
- 💾 **Persistent Storage** — All cards saved locally using Room database, data is never lost
- 🃏 **Sample Cards** — 5 pre-loaded cards on first launch to get you started

---

## 🏗️ Architecture

This app follows the **MVVM (Model-View-ViewModel)** architecture pattern with a clean separation of concerns:

```
UI (Fragments) ──► ViewModel ──► Repository ──► Room DAO ──► SQLite DB
                     LiveData ◄──────────────────────────────────────
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| Architecture | MVVM |
| Database | Room (SQLite) |
| UI | Material Design 3 |
| Navigation | Jetpack Navigation Component |
| Async | Kotlin Coroutines + LiveData |
| View Binding | Android ViewBinding |

---

## 📁 Project Structure

```
app/src/main/java/com/example/flashcardapp/
├── MainActivity.kt
├── model/
│   └── Flashcard.kt
├── database/
│   ├── FlashcardDao.kt
│   └── FlashcardDatabase.kt
├── repository/
│   └── FlashcardRepository.kt
├── viewmodel/
│   └── FlashcardViewModel.kt
├── adapter/
│   └── FlashcardAdapter.kt
└── ui/
    ├── home/
    │   ├── HomeFragment.kt
    │   └── ManageFragment.kt
    └── add_edit/
        └── AddEditFragment.kt
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or newer
- Android device or emulator running **API 26+**

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Gladius006/FlashcardApp.git
   ```

2. **Open in Android Studio**
   - File → Open → Select the `FlashcardApp` folder

3. **Sync Gradle**
   - Click "Sync Now" when prompted

4. **Run the app**
   - Press `Shift+F10` or click the ▶️ Run button

---

## 📖 How to Use

1. **Launch the app** — 5 sample flashcards are preloaded
2. **Quiz yourself** — Tap "Show Answer" to reveal the answer
3. **Navigate** — Use Previous/Next buttons to move between cards
4. **Add a card** — Tap the ➕ FAB button
5. **Edit/Delete** — Use the Edit and Delete buttons on the card
6. **Manage all cards** — Tap the list icon in the top toolbar

---

## 🔮 Future Improvements

- [ ] Shuffle / random card order
- [ ] Quiz score tracking (correct / incorrect)
- [ ] Card categories and decks
- [ ] Dark mode toggle
- [ ] Export / import cards
- [ ] Search cards

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 👨‍💻 Author

**Gladius006** — [github.com/Gladius006](https://github.com/Gladius006)
