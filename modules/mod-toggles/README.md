# TGOneConfig Mod Toggles

A single interface that lets a mod put its own on/off switch on its card in TGOneConfig, without depending on
TGOneConfig at runtime.

```kotlin
dependencies {
    compileOnly("org.tg.tgoneconfig:mod-toggles:1.0.0")
}
```

```java
public class MyModToggle implements ModToggle {
    @Override
    public boolean isEnabled() {
        return MyConfig.enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        MyConfig.enabled = enabled;
        MyConfig.save();
    }
}
```

```json
"entrypoints": {
  "tgoneconfig:toggle": ["com.example.MyModToggle"]
}
```

The class is only loaded when TGOneConfig asks for the entrypoint, so nothing breaks when TGOneConfig is not installed.
Keep it out of classes the mod loads on its own.
