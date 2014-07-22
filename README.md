TF2 Hud Installer
=================

TF2 Hud Installer provides one click installs for HUDs hosted on public repos. This is more useful for HUDs with various themes sharing a similar base.

## Usage

### Respository Directory Tree

All shared files should be placed into the directory `base`. Theme specific files should be placed in another directory alongside `base`.. They can be any name, and will be defined in `versions.json` (see [Versions](#Versions)).

```
-base
  -shared_asset.txt
-theme1
  -theme1_specific_aspect.txt
-theme2
  -theme2_specific_asset.txt
```

### Versions

For the installer to know what versions of HUD there are and to know which directory contains the version the file `versions.json` must be placed in the root of the repository. The file should contain an array element filled with an object elemnet for each version. `versionDirectory` corresponds to the directory that version's file are placed.

```JSON
[
  {
    "displayName": "Example HUD",
    "versionDirectory": "theme1"
  }
]
```

### Dependencies

- google-gson https://code.google.com/p/google-gson/ 
- Commons-IO http://commons.apache.org/proper/commons-io/

### Compiling

Before compiling you will need to include a `Constants.java` in the package `com.rolandoislas.hudinstaller.data`.

#### Constants.java

```Java
public class Constants {
	
	public static final String VERSION = "1.0.0.0";
	
	public static final String REPO_OWNER = "exammpleuser";
	public static final String REPO_NAME = "examplehud";
	public static final String HUD_DIR = "exampleHUD";
	public static final String DISPLAY_NAME = "Example HUD";
	
}
```