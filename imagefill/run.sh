#!/bin/sh
enerjdir=../../enerj
classpath=src
mainclass=FFTest

enerjargs=-noisy
if [ "$1" = "-nonoise" ]
then
enerjargs=
fi

# Run zxing.
$enerjdir/bin/enerj $enerjargs -cp $classpath $mainclass 32 32 input.txt

# Output stats.
# $enerjdir/bin/enerjstats
