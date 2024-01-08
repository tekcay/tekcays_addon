### INSTALLATION:

Run `./gradlew setupDecompWorkspace`.

### Build and test:

* Rename `buildAndTestBlank.cfg` to `buildAndTest.cfg`
* Fill the `prismInstanceModPath` and the `prismInstanceModPath` paths
* Run `buildAndTest.sh`.

### Change GTCEu gradle dependency version:

- Edit the line containing `curse.maven:gregtech-ce-unofficial` in [dependencies.gradle](dependencies.gradle).
- Reload all gradle projects 

### Change mod version
Increment the value of the `VERSION` field in the `TKCYAInternalTags` class.
