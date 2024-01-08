#!/bin/bash

set -e

#file where every constant is stored
propertiesPath="buildAndTest.cfg"
gradlePropertiesPath=$(grep -oP "gradlePropertiesPath=\K(.*)" "$propertiesPath")

# The path to the internal tags java class
internalTagPath=$(grep -oP "internalTagPath=\K(.*)" "$propertiesPath")

# The variable containing the mod version
internalModVersionPrefix=$(grep -oP "internalModVersionPrefix=\K(.*)" "$propertiesPath")

#The variable containing the GTCEu version
internalGTCEuVersionPrefix=$(grep -oP "internalGTCEuVersionPrefix=\K(.*)" "$propertiesPath")

modVersion=$(grep "$internalModVersionPrefix" "$internalTagPath" | awk -F '[""]' '{print $2}')
gtceuVersion=$(grep "$internalGTCEuVersionPrefix" "$internalTagPath" | awk -F '[:@,);\\[]' '{print $4}')

modName=$(grep -oP "modName=\K(.*)" "$propertiesPath")
prismInstanceModPath=$(grep -oP "prismInstanceModPath=\K(.*)" "$propertiesPath")
prismInstanceName=$(grep -oP "prismInstanceName=\K(.*)" "$propertiesPath")

# Gets the version of the building mod jar
version="$modVersion-GT-$gtceuVersion"

# Gets the full name of the building jar
fileName="$modName-$version.jar"

if [ "$fileName" == "" ]
    then exit 1
fi

# Replaces the modVersion field in gradle.properties by
sed -i "s/modVersion = .*/modVersion = $version/g" "$gradlePropertiesPath"

#remove all TKCYA mods version in the Prism launcher instance mod directory
rm -f "$prismInstanceModPath$modName"* &&

echo "building $fileName..." &&
./gradlew :spotlessApply build &&

#copy paste the jar file to the Prism launcher instance mod directory
cp build/libs/"$fileName" "$prismInstanceModPath$fileName" &&

echo "mod jar has been moved."

#launches the desired Prism Launcher instance
prismlauncher --launch "$prismInstanceName"
