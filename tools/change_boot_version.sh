#!/bin/bash
shellDir=$(cd "$(dirname "$0")"; pwd)

shopt -s expand_aliases
if [ ! -n "$1" ] ;then
	echo "Please enter a version"
 	exit 1	
else
  	echo "The version is $1 !"
fi

if [ `uname` == "Darwin" ] ;then
 	echo "This is OS X"
 	alias sed='sed -i ""'
else
 	echo "This is Linux"
 	alias sed='sed -i'
fi

echo "Change SOFABoot version in sofa-boot-samples ===>"
sed "s/<sofaboot.version>.*<\/sofaboot.version>/<sofaboot.version>$1<\/sofaboot.version>/" $shellDir/../sofa-boot-samples/pom.xml

echo "Change SOFABoot version in sofa-boot-core ===>"
sed "/<parent>/,/<\/parent>/ s/<version>[^\$].*<\/version>/<version>$1<\/version>/" $shellDir/../sofa-boot-core/pom.xml

echo "Change SOFABoot version in sofa-boot-starter ===>"
sed "/<parent>/,/<\/parent>/ s/<version>[^\$].*<\/version>/<version>$1<\/version>/" $shellDir/../sofa-boot-starter/pom.xml