#!/bin/sh
enerjdir=../../enerj

enerjcargs="-Alint=mbstatic,simulation -verbose"
if [ "$1" = "-nosim" ]
then
enerjcargs="-Alint=mbstatic"
fi

rm -f jnt/*/*.class
$enerjdir/bin/enerjc $enerjcargs jnt/*/*.java
