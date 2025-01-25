#!/bin/bash

echo "Content-type: text/html; charset=utf-8"
echo ""
echo "<html>"
echo "<head>"
echo "<title>query req get/post ok</title>"
echo "</head>"
echo "<body>"

if [ "$REQUEST_METHOD" = "GET" ] ; then
    echo "hit GET"
    qqns=$(echo "$QUERY_STRING" | awk '{split($0,array,"&")} END{print array[1]}')
    qqqs=$(echo "$QUERY_STRING" | awk '{split($0,array,"&")} END{print array[2]}')
    export REQUEST_METHOD="get" 
elif [ "$REQUEST_METHOD" = "POST" ] ; then
    echo "hit POST"    
    OIFS="$IFS"
    IFS=\&
    read qqns qqqs go
    IFS="$OIFS"
fi
echo qqns: $qqns
echo qqqs: $qqqs
qqns=${qqns#qqns=}
qqqs=${qqqs#qqqs=}

echo "<br>qqnums: $qqns" 
echo "<br>groups: $qqqs"

qqns1=$(echo $qqns | sed -e 's/[^0-9]/ /g' | tr " " "\n" | sort | uniq | grep -v "^$" |
        awk '{ if ($1>10000 && $1<999999999999) print($1)}'| tail -100 | tr "\n" " ")
echo "<br>qqnums refined: $qqns1"
qqqs1=$(echo $qqqs | sed -e 's/[^0-9]/ /g' | tr " " "\n" | sort | uniq | grep -v "^$" |
        awk '{ if ($1>10000 && $1<999999999999) print($1)}'| tail -100 | tr "\n" " ")
echo "<br>groups refined: $qqqs1"

echo "<br>exec: py-mssql.py qq $qqns1 qqq $qqqs1"
echo "<pre>"
python3 /usr/lib/cgi-bin/py-mssql.py qq $qqns1 qqq $qqqs1
echo "</pre>"
echo "</body>"
echo "</html>"
