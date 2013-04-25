#!/bin/sh
enerjdir=../../enerj

enerjargs=-noisy
scimarkargs=
for arg
do
    case "$arg" in
    -nonoise) enerjargs= ;;
    *) scimarkargs="$scimarkargs $arg" ;;
    esac
done

$enerjdir/bin/enerj -Xmx2048m $enerjargs jnt.scimark2.commandline -tiny $scimarkargs
# $enerjdir/bin/enerjstats
