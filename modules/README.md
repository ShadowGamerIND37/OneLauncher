# TGOneConfig Modules

This folder contains the many modules that TGOneConfig offers to developers.
Each is designed to be an independent package, though some are dependent on other packages for their usage.
They each are under the namespace `org.tg.tgoneconfig.api.*`.

Here is a list of the modules, and a brief description of what they do (For more information on how to use each module, see the README in the module's folder):

- [config](config/README.md) - The tree based configuration system used in TGOneConfig.
- [config-impl](config-impl/README.md) - The default implementation of the configuration system used in TGOneConfig.
- [events](events/README.md) - The event system used in TGOneConfig.
- [hud](hud/README.md) - The HUD system used in TGOneConfig.
- [mod-toggles](mod-toggles/README.md) - A standalone interface for giving a mod a switch on its TGOneConfig card.
- [ui](ui/README.md) - The UI system used in TGOneConfig.
- [utils](utils/README.md) - Various utilities used in TGOneConfig.

Each module has the design philosophy of being decoupled from the internal structure, so that the developer can use the modules with separate front-facing APIs
depending on preference. For example, the config system has two different ways to collect configuration data.
This also helps to reduce interdependence and reduce the complexity of the codebase.

> For Minecraft dependant code, see the `versions` directory. Some utilities and classes are found in this directory, under the same package name as the module.