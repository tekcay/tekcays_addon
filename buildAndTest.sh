#!/bin/bash

#file where every constant is stored
propertiesPath="buildAndTest.ini"

gradlePropertiesPath=$(grep -oP "gradlePropertiesPath=\K(.*)" "$propertiesPath")

modName=$(grep -oP "modName=\K(.*)" "$propertiesPath")

#Prism launcher instance mod directory
instanceModPath=$(grep -oP "instanceModPath=\K(.*)" "$propertiesPath")

#mod version
version=$(grep -A 1 "$modName\$" "$gradlePropertiesPath" | grep -oP 'version=\K[0-9.]+')

#output file mod name
fileName="$modName-1.12.2-$version.jar"

#remove all TKCYA mods version in the Prism launcher instance mod directory
rm "$instanceModPath$modName"*

echo "mod jars in $instanceModPath have been deleted."
echo "building $fileName..."

#build the jar mod
./gradlew build &&

#copy paste the jar file to the Prism launcher instance mod directory
cp build/libs/"$fileName" "$instanceModPath$fileName" &&

echo "mod jar has been moved."

#launches the desired instance
prismlauncher --launch TKCYA
