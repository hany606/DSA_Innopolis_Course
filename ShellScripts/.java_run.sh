#!/bin/sh
path=$PWD
name=$1
name=${name%.*}
#echo $result
#echo $dir
#echo $name
echo "########## SUCCESSFULLY STARTING RUNNING ##########"
echo java -classpath ../out/production/*/ $name
java -classpath ../out/production/*/ $name
