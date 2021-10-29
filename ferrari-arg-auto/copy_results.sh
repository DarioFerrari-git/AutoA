#!/bin/bash

dir="r10_2x2_d2_a2"

mkdir dataAnalysis/results/$dir
cp performance-0.csv dataAnalysis/results/$dir
cp performance-1.csv dataAnalysis/results/$dir
cp performance-2.csv dataAnalysis/results/$dir
cp performance-3.csv dataAnalysis/results/$dir
cp performance-4.csv dataAnalysis/results/$dir
cp performance-5.csv dataAnalysis/results/$dir
cp performance-6.csv dataAnalysis/results/$dir
cp performance-7.csv dataAnalysis/results/$dir
cp performance-8.csv dataAnalysis/results/$dir
cp performance-9.csv dataAnalysis/results/$dir
cp logFile.log dataAnalysis/results/$dir
cp sim_settings.properties dataAnalysis/results/$dir

rm performance-0.csv
rm performance-1.csv
rm performance-2.csv
rm performance-3.csv
rm performance-4.csv
rm performance-5.csv
rm performance-6.csv
rm performance-7.csv
rm performance-8.csv
rm performance-9.csv
rm logFile.log
