#!/bin/sh
cd src
javac "*.java" && jar cmf MANIFEST.MF ../project.jar *.class */*.class
cd ..
