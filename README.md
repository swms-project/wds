# Water distribution scheduling.
We used multi objective optimization for scheduling. For each step, we simulate the network given the propsed schedule and evaluate according to:
- Consumed energy
- Pressure
- Water level in tanks
- Fragmentations of the schedule (Prefer schedule with lesser periods of operating)

## Build using Maven
You need `java 8` and `maven` installed on your computer.

Run the following:
```
mvn jfx:jar
```
Open `target/jfx/app/wds-jfx.jar`

## Used software
- Java 8 
- [Epanet (Java port)](http://github.com/swms-project/Baseform-Epanet-Java-Library)
- [MOEA framework](http://moeaframework.org)
