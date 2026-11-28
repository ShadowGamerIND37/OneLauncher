![TGOneConfig_banner_wide](https://user-images.githubusercontent.com/62163840/226811571-ffe7a524-c96a-4b6e-9ca9-d29439f5174f.png)

# TG OneConfig
[![Build status](https://img.shields.io/github/actions/workflow/status/TG/TGOneConfig/build.yml?style=flat&label=build&branch=v1)](https://github.com/ShadowGamerIND37/OneLauncher/tree/mods/tgoneconfig/actions/workflows/build.yml)
[![Modrinth downloads](https://img.shields.io/modrinth/dt/AibBIVmj?style=flat&logo=modrinth&label=Modrinth&color=1bd96a)](https://modrinth.com/mod/tgoneconfig)
[![CurseForge downloads](https://img.shields.io/curseforge/dt/1148006?style=flat&logo=curseforge&label=CurseForge&color=f16436)](https://www.curseforge.com/minecraft/mc-mods/tgoneconfig)

TG OneConfig is a free, open source configuration library by [TG](https://github.com/ShadowGamerIND37). It gives developers a config API, and it gives players one screen where they change the settings of every mod that uses it.

## ❔ General information

### Is it a client?
TGOneConfig may look like a client, but it is a standalone mod. You can [see for yourself](https://github.com/ShadowGamerIND37/OneLauncher/tree/mods/tgoneconfig-Bootstrap). It ships no mods of its own, so you decide which mods you use with it.

### What do players get?
- A settings screen with one card per mod, search, and a keybinds page. Right Shift opens it.
- A HUD editor for moving, scaling and restyling the HUD elements of your mods.
- Profiles, which each keep a separate copy of your mod settings.

### Which versions does it support?
- Minecraft 1.8.9 on Ornithe.
- Minecraft 1.21.1-26.3 on Fabric.

TGOneConfig needs [PolyCompose](https://modrinth.com/mod/polycompose) and Fabric Language Kotlin. It also needs Fabric API on Fabric and OSL on Ornithe.

## ⚡️ Getting started
Almost everything you need to start using TGOneConfig is in [our documentation](https://docsv1.polyfrost.org). For further questions, make a developer ticket in [our Discord](https://github.com/ShadowGamerIND37/discord).

To build TGOneConfig yourself, run the command below with JDK 25 installed. Replace `26.3-fabric` with any supported version, such as `1.8.9-ornithe`.

```sh
./gradlew ":minecraft:26.3-fabric:build"
```

The mod jar ends up in `minecraft/versions/26.3-fabric/build/dist`.

## ℹ️ Example mod
Please check out our [example mod](https://github.com/ShadowGamerIND37/OneLauncher/tree/mods/tgoneconfigExampleMod/).

## 📄 Licenses and Supporting Projects
<details>
  <summary>License</summary>
  TGOneConfig is free and open-source. Except where noted otherwise (below and/or in individual files), all code is licensed under BOTH 
- version 3 of the GNU Lesser General Public License, **AND**
- the Additional Terms Applicable to TGOneConfig, version 1.0 of the Additional Terms or (at your option) any later version.

Copies of version 3 of the GNU Lesser General Public License, version 1.0 of the Additional Terms Applicable to TGOneConfig, and version 3 of the GNU General Public License (which the GNU LGPLv3 supplements) can be found in the [**LICENSE**](LICENSE) file.

TG acts as the specified proxy when deciding whether future versions of the GNU Lesser General Public License shall apply. If TG publicly states the acceptance of a later version of the GNU Lesser General Public License, this is permanent authorization for you to choose that version for TGOneConfig. However, you **must still** be compliant with the Additional Terms Applicable to TGOneConfig.
</details>

<details>
  <summary>Supporting Projects</summary>

![YourKitLogo|50](https://www.yourkit.com/images/yklogo.png)

YourKit supports open source projects with innovative and intelligent tools
for monitoring and profiling Java and .NET applications. <br>
YourKit is the creator of [YourKit Java Profiler](https://www.yourkit.com/java/profiler/),
[YourKit .NET Profiler](https://www.yourkit.com/.net/profiler/),
and [YourKit YouMonitor](https://www.yourkit.com/youmonitor/).
</details>
