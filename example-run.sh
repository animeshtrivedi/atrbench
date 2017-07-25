#Basic example with 

java -jar ./target/atrbench-uber.jar copyMethod -wi 0 -f 2 -i 2 -bm avgt -r 1s -gc true 

# Another example which passes JVM flags, between -Xint and w/o there is 2 orders of magnitude difference 
java -jar ./target/atrbench-uber.jar testMethod -wi 0 -f 2 -i 2 -bm avgt -r 10s -gc true -jvmArgs "-XX:CompileThreshold=1 -Xint"

