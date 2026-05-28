# **XposedFakeLocation**

![GitHub License](https://img.shields.io/github/license/noobexon1/XposedFakeLocation?color=blue)
![GitHub Release Date](https://img.shields.io/github/release-date/noobexon1/XposedFakeLocation?color=violet)
![GitHub Downloads (all assets, all releases)](https://img.shields.io/github/downloads/noobexon1/XposedFakeLocation/total)
![GitHub repo size](https://img.shields.io/github/repo-size/noobexon1/XposedFakeLocation)
![GitHub Repo stars](https://img.shields.io/github/stars/noobexon1/XposedFakeLocation)
![GitHub Release](https://img.shields.io/github/v/release/noobexon1/XposedFakeLocation?color=red)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)]()

**XposedFakeLocation** is an Android application and Xposed module that allows you to spoof your device's location globally or for specific apps without using "mock location" from the developer options. Customize your location with precision, including sensor data, and add randomization within a specified radius for enhanced privacy.


<div align="center">
    <img src="images/xposedfakelocation.webp" alt="App Logo" width="256" />
</div>


---

## **Table of Contents**

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Development](#development)
- [License](#license)
- [Disclaimer](#disclaimer)
- [Acknowledgements](#acknowledgements)

---

## **Features**

- **Global or Per-App Location Spoofing**: Override your device's location data system-wide or on a per-app basis.
- **Custom Coordinates**: Set precise GPS latitude and longitude coordinates.
- **Fine-Tuned spoofing settings**: Customize other custom sensor values such as speed, speed accuracy, vertical accuracy, mean sea level, mean sea level accuracy, GPS noise, etc.
- **Randomization**: Set a radius for location randomization to mimic real-world movement patterns.
- **Intuitive UI Navigation**: Easy access to maps, favorite locations, and settings.
- **Spoof per-app templates**: Concurrently spoof multiple apps with different locations and settings at the same time using templates.
- **Community Integration**: Direct links to Telegram, Discord, and GitHub communities for support and updates.
- **Headless Mode (off by default)**: Once enabled in Settings, you can start/stop spoofing and set the active fake location from another app or `adb shell` via broadcast intents, no need to open the UI. See [`docs/EXTERNAL_CONTROL.md`](docs/EXTERNAL_CONTROL.md) for more details.

---

## **Prerequisites**

- **Rooted Android Device**: The app requires root access to function properly.
- **Xposed Framework**: Install the `Xposed Framework` compatible with your Android version. It is recommended to use [LSPosed](https://github.com/LSPosed/LSPosed) as it is the most popular `Xposed Framework` manager app.

---

## **Installation**

You can always install the latest stable version of `XposedFakeLocation` from the [releases](https://github.com/noobexon1/XposedFakeLocation/releases) page. 

If you want to build by yourself:

1. **Clone or Download the Repository**

   ```shell
   git clone https://github.com/noobexon1/XposedFakeLocation.git
   ```

2. **Build the Application**

   - Open the project in `Android Studio`.
   - Build the APK using `Build > Build Bundle(s) / APK(s) > Build APK(s)`.
   - Alternatively, use `Gradle`:

     ```shell
     ./gradlew assembleDebug
     ```

3. **Install the APK**

   - Install the APK via `adb`:
   
     ```shell
     adb install app/build/outputs/apk/debug/app-debug.apk
     ```

4. **Activate the Xposed Module**

   - Open `LSPosed Manager`.
   - Enable the `XposedFakeLocation` module.
   - In `LSPosed` scope settings, select `Android System Framework ('android')` and `Phone Services ('com.android.phone')`.
   - Reboot your device to apply the scope change.
   - Select target apps directly inside `XposedFakeLocation` instead of selecting each target app in LSPosed.

   **Alternative (pre-v0.0.7 behavior):** If the in-app target selector does not work for you, open `Settings` inside `XposedFakeLocation` and turn `"Use built-in target app selection"` OFF. In that mode the in-app list is ignored and the `android` / `com.android.phone` system-wide hooks are skipped at boot. You then pick target apps directly in the `LSPosed` manager app (just like in v0.0.6) — add each target app to the module's scope there, remove `android` and `com.android.phone` if you no longer need them. Reboot your device to apply the scope change.

---

## **Usage**

1. **Launch the App**

   - Open `XposedFakeLocation` from your apps menu.

2. **Navigate the Interface**

   - Use the navigation menu to access different sections:
     - **Map**: Primary interface for location selection
     - **Favorites**: Saved locations for quick access
     - **Target Apps**: Apps that should receive spoofed locations.
     - **Templates**: Concurrently spoof multiple apps with different locations and settings at the same time using templates.
     - **Settings**: Configure application behavior
     - **About**: View application information

3. **Select Target Apps**

   - Open `Target Apps` from the navigation menu.
   - Search for and select the apps that should receive spoofed locations.
   - Apps not selected here will keep receiving their normal location data.

4. **Select a Location**

   - Use the integrated map to select your desired location by tapping on the map.

5. **Configure Settings**

   - Optionally, access the `Settings` screen to fine-tune your spoofing settings.

6. **Start Spoofing**

   - Toggle the `Play/Stop` button to begin location spoofing.
   - `XposedFakeLocation` will override location data only for apps selected in `Target Apps`.
   - If the target app was already running, it should be force stopped and reopened to apply the spoofing.

7. **Stop Spoofing**

   - Toggle the `Play/Stop` button to cease location spoofing.

8. **Headless Mode (Optional, off by default)**

   - You can control `XposedFakeLocation` from another app or from `adb shell` using broadcast intents (useful for automation or integrating with your own tools). No additional permissions or signing requirements on the caller side are needed.
   - See [`docs/EXTERNAL_CONTROL.md`](docs/EXTERNAL_CONTROL.md) for more details.

---

## **Development**

### **Building from Source**

1. **Clone the Repository**

   ```shell
   git clone https://github.com/noobexon1/XposedFakeLocation.git
   ```

2. **Open in Android Studio**

   - Navigate to the project directory.
   - Open the project with `Android Studio`.

3. **Sync Gradle**

   - Allow Gradle to download all dependencies.

4. **Build and Run**

   - Connect your rooted device.
   - Run the app from `Android Studio`.

---

## **License**

Distributed under the `MIT License`. See [LICENSE](LICENSE) for more information.

---

## **Disclaimer**

This application is intended for **development and testing purposes only**. Misuse of location spoofing can violate terms of service of other applications and services. Use at your own risk. There is no responsibility whatsoever for any damage to the device.

---

## **Acknowledgements**

- [GpsSetter](https://github.com/Android1500/GpsSetter) - Highly inspired by this amazing project!
- [Xposed Framework](https://repo.xposed.info/) - Java hooks
- [LSPosed](https://github.com/LSPosed/LSPosed) - The go-to Xposed framework manager app.
- [OSMDroid](https://github.com/osmdroid/osmdroid) - Open-source offline map interface.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern UI toolkit for Android.
- [Material Design 3](https://m3.material.io/) - Latest design system from Google.
- [Line Awesome Icons](https://icons8.com/line-awesome) - Beautiful icon set used in the app.
- [FuckLocation](https://github.com/Mikotwa/FuckLocation) - Reference for additional Android location hook handling.


