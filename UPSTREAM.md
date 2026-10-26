# EvolutionX integration

Upstream: https://github.com/KieronQuinn/Smartspacer (GPL-3.0), 1.11.4.
EvolutionX changes are ordinary commits on top of upstream in this repository.

## Build

The ROM does not run Gradle. Rebuild the prebuilt after changing the sources:

    ./gradlew :app:assembleRelease
    cp app/build/outputs/apk/release/app-release-unsigned.apk prebuilt/Smartspacer.apk

Requirements: Java 21, Android SDK platform 37, build-tools 36.0.0, and an
ignored `local.properties` with `sdk.dir`. Firebase is applied only when
`app/google-services.json` exists. Keep `versionCode` above the last shipped
build (currently 1000003).

## ROM wiring

- `Android.bp` imports the unsigned APK as a privileged product app, signed
  with the platform key, plus the privapp permission allowlist and sysconfig.
- `config_defaultSmartspaceService` (vendor/pixel-style overlay) points the
  framework Smartspace service to Smartspacer. When the package is disabled,
  frameworks/base falls back to Google ASI.
- The sysconfig allows Smartspacer to bind Google ASI directly, and allows
  ASI to bind Private Compute Services, which stores At a Glance settings.
- The app is not persistent, so `pm disable-user` fully stops it.
