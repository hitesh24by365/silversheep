#!/bin/sh
echo "Copiando base de datos a /tmp"
cp {,/tmp/}silversheep.db
echo "Limpiando base de datos"
cp silversheep.db.bak silversheep.db
