#!/bin/sh
if [ $# -eq 0 ]; then
    echo "Usage: ./$0 <json_file_name> [<key_to_search>]"
    exit 1
fi

if [ $# -eq 1 ]
then 
	awk ' BEGIN { RS = "," } { gsub(/\s/,""); print $0 } ' $1| sed ' /^$/d ' | sed -e ' s/[{}]//g ' | awk ' BEGIN { RS = "[" } {print $0} '| sed ' s/]// '
else
	awk ' BEGIN { RS = "," } { gsub(/\s/,""); print $0 } ' $1 | sed ' /^$/d ' | sed -e ' s/[{}]//g ' | awk ' BEGIN { RS = "[" } {print $0} '| sed ' s/]// ' | grep $2
fi 