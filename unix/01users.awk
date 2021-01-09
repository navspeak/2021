BEGIN { FS=":" ; print "username" }
{ print $1 }
END { print "Total User = " NR }
