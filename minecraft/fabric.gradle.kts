plugins {
    id("net.fabricmc.fabric-loom")
    id("tgoneconfig-bridge") // creates the modImplementation and friends configurations
    id("tgoneconfig-fabric")
}

dependencies {
    if (versionedCatalog.has("modmenu"))
    implementation(versionedCatalog["modmenu"])
}