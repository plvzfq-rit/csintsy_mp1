#!bin/bash

find src -name "*.class" -exec rm -rf {} \;
javac src/main/Driver.java -cp src;
java -classpath src main.Driver $1 bot;