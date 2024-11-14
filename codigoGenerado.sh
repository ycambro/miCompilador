#!/bin/bash

t0=a
t1=42
t0=$t1
t2=b
t3=$t0
t4=2
t5=$((t3 - t4))
t2=$t5
t6=c
t7=$t0
t8=$t2
t9=2
t10=$((t8 * t9))
t11=$((t7 + t10))
t6=$t11
echo "El valor de a es: $t0"
echo "El valor de b es: $t2"
echo "El valor de c es: $t6"
echo "El valor de c es: $t6"