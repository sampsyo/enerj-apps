#!/bin/sh
stfile=enerjlog.txt
stdest=../stats
fext=txt

if [ -e stats ]; then
    mv stats stats_`date "+%s"`
fi
mkdir stats

cd imagefill
./run.sh -nonoise > /dev/null
mv $stfile $stdest/imagefill.$fext
cd ..

cd jmeint
./run.sh -nonoise > /dev/null
mv $stfile $stdest/jmeint.$fext
cd ..

cd simpleRaytracer
./run.sh -nonoise > /dev/null
mv $stfile $stdest/raytracer.$fext
cd ..

cd scimark2
./run.sh -nonoise fft > /dev/null
mv $stfile $stdest/fft.$fext
./run.sh -nonoise lu > /dev/null
mv $stfile $stdest/lu.$fext
./run.sh -nonoise mc > /dev/null
mv $stfile $stdest/mc.$fext
./run.sh -nonoise smm > /dev/null
mv $stfile $stdest/smm.$fext
./run.sh -nonoise sor > /dev/null
mv $stfile $stdest/sor.$fext
cd ..
