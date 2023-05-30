#!/bin/bash

# get IP address and subnet mask as arguments
#ip=$1
#subnet_mask=$2

# get current IP address and netmask
ip=$(hostname -I | cut -d ' ' -f 1)
netmask=$(ifconfig eth0 | grep netmask | awk '{print $4}')
broadcast=$(ifconfig | grep broadcast | awk '{print $6}' )


# calculate network address
IFS=. read -r i1 i2 i3 i4 <<< "$ip"
IFS=. read -r m1 m2 m3 m4 <<< "$netmask"
network1=$((i1 & m1)).$((i2 & m2)).$((i3 & m3)).$((i4 & m4))
network=$(( (i1 & m1) << 24 | (i2 & m2) << 16 | (i3 & m3) << 8 | (i4 & m4) ))

# calculate number of bits in subnet mask
#a=$(echo "obase=2;$m1" | bc |tr -dc '1'| awk '{print length}')
a=$(echo "obase=2;$m1" | bc | tr -d '\n' | sed 's/0*$//' | wc -c)
b=$(echo "obase=2;$m2" | bc | tr -d '\n' | sed 's/0*$//' | wc -c)
c=$(echo "obase=2;$m3" | bc | tr -d '\n' | sed 's/0*$//' | wc -c)
d=$(echo "obase=2;$m4" | bc | tr -d '\n' | sed 's/0*$//' | wc -c)

subnet_bits=$(($a+ $b+ $c+ $d))


#echo $subnet_bits

# calculate number of hosts in subnet
hosts=$((2 ** (32 - $subnet_bits) - 2))

#echo "Network address: $network1/$subnet_bits"
#echo "Number of hosts: $hosts"

# Loop through all the IP addresses in the network and display them
for (( i=1; i<=$hosts; i++ )); do
  current_address=$(( network + i ))
  ip1=($(printf "%d.%d.%d.%d\n" $((current_address>>24)) $((current_address>>16&255)) $((current_address>>8&255)) $((current_address&255))))
#echo $ip1
p1=($(printf "%d.%d\n"  $((current_address>>8&255)) $((current_address&255))))
#echo $p1

 ping -c 1 -W 0.1 "$ip1" >/dev/null 2>&1
    if [ $? -ne 0 ]; then

  echo "$ip1"
count=$(($count+1))
#else
#echo "$ip1 is not available"  
  fi
done
#echo "Total available IPs: $count"



