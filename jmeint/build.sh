#!/bin/sh

if [ "$1" = "-nosim" ]
then
export EnerJNoSim=true
fi

rm -rf build
ant
