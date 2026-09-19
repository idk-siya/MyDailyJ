# MyDailyJ

A private digital journal Android app: register/login, then create, view, edit, search, and delete journal entries against a backend API.

## Prerequisites

- [Android Studio](https://developer.android.com/studio) (Ladybug or newer) — it bundles the JDK and lets you install Android SDK components through its SDK Manager
- An Android emulator (set up via Android Studio's Device Manager) or a physical device with USB debugging enabled

## 1. Clone the repo

```bash
git clone https://github.com/idk-siya/MyDailyJ.git
cd MyDailyJ
```

## 2. Open in Android Studio

- Open Android Studio → **File > Open** → select the cloned `MyDailyJ` folder
- Let Gradle sync. If prompted, install any missing SDK platforms/build tools it asks for
- `local.properties` (your local SDK path) is not tracked in git — Android Studio generates it automatically on first sync

## 3. Point the app at your backend

The app talks to a REST API for auth and journal entries. Before running, set the base URL in:

`app/src/main/java/com/example/poefn/ui/login/RetrofitClient.kt`

```kotlin
private const val BASE_URL = "http://10.0.2.2:5000/api/"
```

- `10.0.2.2` is the special address the Android **emulator** uses to reach `localhost` on your host machine — keep this if your backend runs locally and you're testing on the emulator.
- If you're using a physical device, replace it with your machine's LAN IP (e.g. `http://192.168.1.10:5000/api/`), or with your deployed API's URL.

Your backend needs to expose these endpoints (see `app/src/main/java/com/example/poefn/network/ApiService.kt` for exact request/response shapes):

| Method | Path             | Purpose            |
|--------|------------------|--------------------|
| POST   | `auth/login`     | Log in             |
| POST   | `auth/register`  | Register account   |
| GET    | `entries`        | List entries       |
| GET    | `entries/search` | Search entries (`q` query param) |
| GET    | `entries/{id}`   | Get one entry      |
| POST   | `entries`        | Create entry       |
| PUT    | `entries/{id}`   | Update entry       |
| DELETE | `entries/{id}`   | Delete entry       |

## 4. Run the app

- Select a device/emulator from the toolbar dropdown
- Click **Run ▶** (or `Shift+F10`)

Or from the command line:

```bash
./gradlew installDebug   # macOS/Linux
gradlew.bat installDebug # Windows
```

## Project structure

- `ui/login/` — all screens: login, register, home, create/edit/view/search entries, settings
- `model/` — data models (`JournalEntry`, `LoginRequest`, `RegisterRequest`, `AuthResponse`)
- `network/` — `ApiService` (Retrofit interface)
- `ui/login/RetrofitClient.kt` — Retrofit client setup (base URL lives here)

## Troubleshooting

- **App crashes immediately on launch** — check `MainActivity` is set as the launcher activity in `AndroidManifest.xml` and that Gradle sync completed without errors.
- **Network calls fail / "Server connection failed" toasts** — confirm `BASE_URL` in `RetrofitClient.kt` is reachable from your device/emulator and your backend is running. (Cleartext `http://` traffic is already allowed via `android:usesCleartextTraffic="true"` in the manifest; switch to HTTPS and remove that flag once you have a real deployed backend.)
