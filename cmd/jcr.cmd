@ECHO off
SETLOCAL
IF "%1"=="" (
ECHO error: please specify the source file
EXIT /B 1
)
SET jsrc=%1
IF NOT "%jsrc:~-5%"==".java" SET jsrc=%1.java
javac %jsrc% && java %jsrc%
