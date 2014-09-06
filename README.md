HUDI
=================

HUDI provides one click installs for HUDs hosted on public repos. This is more useful for HUDs with various themes sharing a similar base.

## Usage

### Respository Directory Tree

All shared files should be placed into the directory `base`. Theme specific files should be placed in another directory alongside `base`.. They can be any name, and will be defined in `versions.json` (see [Versions](#versions)).

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
	
	public static final String VERSION = "1.0.0";
	
	public static final String REPO_OWNER = "exammpleuser";
	public static final String REPO_NAME = "examplehud";
	public static final String HUD_DIR = "exampleHUD";
	public static final String DISPLAY_NAME = "Example HUD";
	
	public static final boolean BASE_DIRECTORY = false;
	public static final boolean IGNORE_VERSIONS = true;
	public static final Versions[] VERSIONS = new Versions[] {new Versions("Example HUD", "theme1")};
	
}
```

##### VERSION

Version number that is displayed on the title bar: major.minor.revision

##### REPO_OWNER

This should be the username of the repository owner; the same format as the URL.

##### REPO_NAME

This is the repository name. It should be the same format as the URL.

##### HUD_DIR

This is the directory name to use when installing to "tf/custom".

##### DISPLAY_NAME

This name will appear in various prompt and messages in the installer.

##### BASE_DIRECTORY

This will determine whether to install the base install before the theme. Set this to `false` if the repository does not have the supported directory structure.

##### IGNORE_VERSIONS

If the repository does not include [versions.json](#versions) this can be used to disable the check for it. Be sure to properly populate the embedded VERSIONS constants.

##### VERSIONS

This is an array of the Versions class. Versions accepts two Stings as parameters. The first is the display name of the HUD, and the second is the directory (within the repository) of the theme.

## License

HUDI
Copyright (C) 2014 Rolando Islas

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.