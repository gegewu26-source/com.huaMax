# LocationMax

LocationMax is an Android Xposed/LSPosed module for location simulation in authorized testing environments.

[Download LocationMax APK](https://github.com/gegewu26-source/com.huaMax/releases/download/5-0.0.5/LocationMax-release.apk)

The module can make selected apps receive a configured virtual location. It is intended for app testing, device debugging, QA verification, and other lawful scenarios where the device owner has permission to perform location simulation.

## Features

- Pick a virtual location on the map.
- Search for places and jump to matching coordinates.
- Display coordinates and the resolved address of the selected point.
- Save favorite locations.
- Enable or disable spoofing from the in-app switch.
- Configure target apps from the module manager UI.
- Optional external broadcast control for automation.
- 10-day activation code flow for managed distribution.
- Remote control JSON for enable, disable, and force-update states.
- Telegram community link: <https://t.me/LocationMaxx>

## Package

- App name: `LocationMax`
- Application ID: `com.huaMax`
- Namespace: `com.huaMax`
- Xposed entry: `com.huaMax.xposed.ModuleEntry`

## Requirements

- Rooted Android device.
- Android 11 / API 30 or newer.
- LSPosed or another compatible Xposed framework.
- Location permission granted to the app.
- The module enabled in LSPosed, with target apps selected.

## Installation

1. Install the APK.
2. Enable `LocationMax` in LSPosed.
3. Select the target apps that should receive the virtual location.
4. Reboot the device or restart the target apps if required by the framework.
5. Open LocationMax, grant permissions, enter an activation code, choose a point, and start.

## Build

Open the project in Android Studio, or build from the command line:

```sh
./gradlew assembleDebug
```

On Windows:

```powershell
.\gradlew.bat :app:assembleDebug :app:assembleRelease
```

Release APK output:

```text
app/build/outputs/apk/release/app-release.apk
```

## Signing

Local release signing is configured through `keystore.properties`, which is intentionally ignored by Git.

Keep a private backup of these local files:

```text
keystore/locationmax-release.jks
keystore.properties
```

Do not upload signing certificates, keystore passwords, activation private keys, server passwords, or tokens to GitHub.

## External Control

Broadcast actions use the LocationMax package prefix:

- `com.huaMax.action.START`
- `com.huaMax.action.STOP`
- `com.huaMax.action.SET_LOCATION`

See [docs/EXTERNAL_CONTROL.md](docs/EXTERNAL_CONTROL.md) for examples.

## Remote Control

The app can read a JSON control file to support global enable, disable, and force-update states. See [docs/PUBLISHING.md](docs/PUBLISHING.md) before publishing, because the public APK should point to a server path you control.

## Publishing

GitHub and LSPosed publishing notes are in [docs/PUBLISHING.md](docs/PUBLISHING.md).

Current package name for module repository submission:

```text
com.huaMax
```

Current release version:

```text
0.0.5
```

## Legal Notice

This project is provided only for lawful testing, debugging, research, and device-owner-controlled scenarios. Do not use it to bypass platform rules, misrepresent real-world location for fraud, evade compliance checks, invade privacy, or perform illegal activity. Users are responsible for complying with local laws and third-party service terms.

## Open Source

This project is based on [XposedFakeLocation](https://github.com/noobexon1/XposedFakeLocation) by noobexon1 and remains under the MIT License. The original copyright notice is preserved in [LICENSE](LICENSE).
