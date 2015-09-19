#!/bin/bash

set -x

for file in $(find -name *.dot); 
do 
    dot -Tpng $file -o $file.png; 
done