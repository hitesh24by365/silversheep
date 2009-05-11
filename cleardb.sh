#!/bin/sh
echo "Copiando base de datos a /tmp"
cp silversheep.db /tmp
echo "Limpiando base de datos"
cp silversheep.db.bak silversheep.db
