# Publishing LocationMax

This document is for publishing LocationMax to GitHub and submitting it to the LSPosed/Xposed module repository.

## Before Uploading

Check these points before pushing the repository:

- Keep `LICENSE` in the repository.
- Keep the original MIT license notice.
- Do not upload `keystore.properties`.
- Do not upload anything under `keystore/`.
- Do not upload `.jks`, `.keystore`, `.p12`, `.pem`, `.key`, passwords, tokens, or server credentials.
- Do not put the server SSH password in README, scripts, issues, release notes, or screenshots.
- If a password has been pasted into chat, rotate it on the server.

## Recommended GitHub Repository

For your own public source repository, use the package name as the GitHub repository name:

```text
com.huaMax
```

Suggested public GitHub repository description:

```text
LocationMax - Android Xposed/LSPosed location simulation module for lawful testing
```

## First GitHub Upload

After creating an empty GitHub repository, run these commands from the project directory:

```powershell
git remote add origin https://github.com/YOUR_NAME/com.huaMax.git
git branch -M main
git add .
git commit -m "Prepare LocationMax public release"
git push -u origin main
```

Replace `YOUR_NAME` with your GitHub username.

If `git remote add origin` says the remote already exists, use:

```powershell
git remote set-url origin https://github.com/YOUR_NAME/com.huaMax.git
```

## GitHub Release

The current local version is:

```text
versionCode: 15
versionName: 0.0.15
```

For the LSPosed module repository, the safest release tag is:

```text
15-0.0.15
```

Upload this APK as a release asset:

```text
LocationMax-release.apk
```

Also keep a versioned copy in the repository:

```text
releases/LocationMax-release-0.0.15.apk
```

Publishing rule:

- `releases/LocationMax-release.apk` is the latest APK and may be overwritten for force updates.
- `releases/LocationMax-release-X.Y.Z.apk` is a permanent rollback copy and must not be overwritten or deleted.
- Add each new version to the top of the download tables in `README.md` and `releases/README.md`.

The release title can be:

```text
LocationMax 0.0.15
```

Suggested release notes:

```text
LocationMax 0.0.15

- Added the `system` LSPosed scope for system-server location hooks.
- Stabilized the mock provider service with a background ticker and wake lock.
- Added repository-hosted APK files for force-update downloads.

For lawful testing, debugging, and device-owner-controlled use only.
```

## GitHub Actions Signing

The included release workflow can build and attach a signed APK automatically when a GitHub Release is published.

Before using it, add these repository secrets in GitHub:

```text
LOCATIONMAX_KEYSTORE_BASE64
LOCATIONMAX_KEYSTORE_PASSWORD
LOCATIONMAX_KEY_ALIAS
LOCATIONMAX_KEY_PASSWORD
```

If you do not know how to add these secrets yet, manually upload the already built APK to the GitHub Release instead.

## LSPosed/Xposed Module Repository

After the GitHub repository and release are ready, submit the module here:

```text
https://modules.lsposed.org/submission/
```

Use:

```text
Package name: com.huaMax
Description: LocationMax - Android Xposed/LSPosed location simulation module for lawful testing
```

The official submission flow creates a GitHub issue titled:

```text
[submission] com.huaMax
```

The repository bot can create a package repository under `Xposed-Modules-Repo` and invite you as admin. If you later want to move your existing GitHub repository into that organization, use the transfer flow:

```text
[transfer] com.huaMax
```

The public module repository expects a valid GitHub release with an APK asset. The release tag should be:

```text
15-0.0.15
```

The repository should include:

```text
SUMMARY.md
README.md
LICENSE
```

## Public Description

Short description:

```text
Android Xposed/LSPosed location simulation module for lawful testing.
```

Long description:

```text
LocationMax lets a rooted Android device owner configure a virtual location for selected apps through an Xposed/LSPosed module. It supports map selection, place search, address display, favorites, activation-code distribution, and remote enable/disable/force-update control. It is intended only for lawful testing, debugging, and device-owner-controlled use.
```

## Important Notice

Publishing the project as open source does not remove legal responsibility. Do not advertise it for fraud, platform abuse, evasion, privacy invasion, or other unlawful use.
