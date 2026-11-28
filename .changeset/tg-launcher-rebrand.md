---
"oneclient_app": minor
---

Rebrand the launcher from OneClient/OneLauncher to TG Launcher: window title, bundle identifier (`org.tg.TGLauncher`), URL scheme (`tglauncher://`, with legacy `oneclient://` links still parsing), IPC pipe, data directory (`TG/TG Launcher`, falling back to the existing `Polyfrost/OneClient` folder), installer/packaging metadata, docs, and CI release workflows.
