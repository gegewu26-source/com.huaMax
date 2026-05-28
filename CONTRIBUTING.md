# Contributing to XposedFakeLocation

Thanks for your interest in contributing to `XposedFakeLocation`! This document covers the project structure, development setup, and the process for submitting changes.

---

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Ways to Contribute](#ways-to-contribute)
- [Development Setup](#development-setup)
- [Coding Guidelines](#coding-guidelines)
- [Submitting a Pull Request](#submitting-a-pull-request)
- [Reporting Bugs & Requesting Features](#reporting-bugs--requesting-features)

---

## Code of Conduct

Be respectful and constructive. Issues and pull requests that are abusive, off-topic, or otherwise disruptive will be closed without further discussion.

---

## Ways to Contribute

1. **Fix bugs** 
   - Visit the [issue tracker](https://github.com/noobexon1/XposedFakeLocation/issues) for known issues or open a new one.
2. **Implement new features** 
   - It is recommended to open an `enhancement` issue at the [issue tracker](https://github.com/noobexon1/XposedFakeLocation/issues) page first to discuss the idea before spending time on an implementation and getting it rejected.
3. **Translate the app** 
   - Help translate the app to your language by adding new translations to the `app/src/main/res/values-<language>/strings.xml` file.
4. **Improve the documentation** 
   - Help improve the documentation by adding new documentation to the `docs/` directory, or by rephrasing the existing documentation to make it more clear and concise.

---

## Development Setup

1. **Clone the repository**

   ```shell
   git clone https://github.com/noobexon1/XposedFakeLocation.git
   ```

2. **Open in Android Studio and sync the project** 

   - Open the project in `Android Studio`.
   - Sync the project with Gradle.

---

## Coding Guidelines

- **Language**: Use Kotlin.
- **UI**: Jetpack Compose + Material 3.
- **State management**: Every screen has a dedicated `ViewModel`. Keep UI code in the screen file and all logic and state in the `ViewModel`.

---

## Submitting a Pull Request

1. **Fork** 

   - Fork the repository and create a branch from `master`.

2. **Make your changes** 

   - Follow the coding guidelines above.

3. **Test on a device**

   - Test the changes on a device to ensure they work as expected.

4. **Keep commits focused** 

   - One logical change per commit with a clear message.

5. **Open a pull request** 

   - Open a pull request against the `master` branch.
   - In the PR description, include:
     - What the change does and why.
     - How you tested it (device model, Android version).
     - Any known limitations or follow-up work.

6. **Wait for review**
   - Wait for review and iterate on the changes if needed.

---

## Reporting Bugs & Requesting Features

Please use the [GitHub issue tracker](https://github.com/noobexon1/XposedFakeLocation/issues). When reporting a bug, include:

- Device model and Android version
- `XposedFakeLocation` version
- Steps to reproduce
- Expected vs. actual behavior
- Relevant log output (from the module's log tab)
