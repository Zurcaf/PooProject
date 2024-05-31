cd src
javac user_interface\*.java patrol_allocation\*.java evolution_simulation\*.java discrete_stochastic_simulation\*.java solver_comparator\*.java && jar cmf MANIFEST.MF ..\project.jar user_interface\*.class patrol_allocation\*.class evolution_simulation\*.class discrete_stochastic_simulation\*.class && jar cmf MANIFEST_comparator.MF ..\comparator.jar solver_comparator\*.class patrol_allocation\*.class evolution_simulation\*.class discrete_stochastic_simulation\*.class
cd ..
