#!/bin/bash
if ! [ $1 ]
then
	echo "error: please specify the source file"
	exit 1
fi

if [ "${1:0,-5}" = .java ]
then jsrc=$1
else jsrc=$1.java
fi

javac $jsrc && java $jsrc
