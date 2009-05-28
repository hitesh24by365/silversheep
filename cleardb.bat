@echo off
echo "Copiando base de datos a c:/temp"

copy silversheep.db c:\temp

echo "Limpiando base de datos"

copy silversheep.db.bak silversheep.db
