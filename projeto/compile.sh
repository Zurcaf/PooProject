#!/bin/sh
cd src
rm user_interface\*.class patrol_allocation\*.class evolution_simulation\*.class discrete_stochastic_simulation\*.class && javac -encoding UTF-8 user_interface/*.java patrol_allocation/*.java evolution_simulation/*.java discrete_stochastic_simulation/*.java && jar cmf MANIFEST.MF ../project.jar user_interface/* patrol_allocation/* evolution_simulation/* discrete_stochastic_simulation/*
cd ..

