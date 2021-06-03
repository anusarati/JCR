#!/bin/bash
src() {
	if ! [ $1 ]
	then
	        return 1
	fi
	
	if [ "${1:0,-5}" = .$2 ]
	then echo $1
	else echo $1.$2
	fi
}

jsrc() { echo src $1 java; }

jcr() {
	jsrc=$(jsrc $1) &&
	{ echo "javac $jsrc"; javac $jsrc; } && { echo "java $jsrc"; java $jsrc $2; }
}
