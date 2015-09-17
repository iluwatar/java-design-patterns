#!/bin/bash
for file in $(find -name *.dot); 
do 
    dot -Tpng $file -o $file.png; 
done

