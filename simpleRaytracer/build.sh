#!/bin/sh
enerjdir=../../enerj

enerjcargs="-Alint=simulation,mbstatic -verbose"
if [ "$1" = "-nosim" ]
then
enerjcargs=-Alint=mbstatic
fi

rm -f /Plane.class
$enerjdir/bin/enerjc $enerjcargs Plane.java
