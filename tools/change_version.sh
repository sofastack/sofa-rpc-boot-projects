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

echo "Change version in root pom.xml ===>"
sed "/<project /,/<name/ s/<version>[^\$].*<\/version>/<version>$1<\/version>/" $shellDir/../pom.xml

echo "Change rpc version in sofa-boot-core(if not same, please modify it) ===>"
sed "s/<rpc.all.version>.*<\/rpc.all.version>/<rpc.all.version>$1<\/rpc.all.version>/" $shellDir/../sofa-boot-core/pom.xml

echo "Change version in sofa-boot-core ===>"
sed "/\/parent/,/<properties/ s/<version>[^\$].*<\/version>/<version>$1<\/version>/" $shellDir/../sofa-boot-core/pom.xml



echo "Change rpc version in sofa-boot-starter ===>"
sed "s/<rpc.starter.version>.*<\/rpc.starter.version>/<rpc.starter.version>$1<\/rpc.starter.version>/" $shellDir/../sofa-boot-starter/pom.xml
sed "/\/parent/,/<properties/ s/<version>[^\$].*<\/version>/<version>$1<\/version>/" $shellDir/../sofa-boot-starter/pom.xml


echo "Change rpc version in sofa-boot-samples ===>"
sed "/<project /,/<properties/ s/<version>[^\$].*<\/version>/<version>$1<\/version>/" $shellDir/../sofa-boot-samples/pom.xml
sed "s/<rpc.starter.version>.*<\/rpc.starter.version>/<rpc.starter.version>$1<\/rpc.starter.version>/" $shellDir/../sofa-boot-samples/pom.xml
