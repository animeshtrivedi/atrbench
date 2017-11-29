#Basic example with 

java -jar ./target/atrbench-uber.jar copyMethod -wi 0 -f 2 -i 2 -bm avgt -r 1s -gc true 

# Another example which passes JVM flags, between -Xint and w/o there is 2 orders of magnitude difference 
java -jar ./target/atrbench-uber.jar testMethod -wi 0 -f 2 -i 2 -bm avgt -r 10s -gc true -jvmArgs "-XX:CompileThreshold=1 -Xint"

# Uses -t 2 - but i am not sure why it prints 4 times. Also show the full JVM settings 
java -jar ./target/atrbench-uber.jar testCopyFromByteBuffer -wi 0 -f 1 -i 1 -bm thrpt  -r 5s -gc true -jvmArgs "-XX:CompileThreshold=1 -Xms1024m -Xmx16g -XshowSettings:all" -t 2 -h 

November 29,
java -jar ./target/atrbench-uber.jar CLASSNAME

More details:
-wi : number of warmup iterations, not counted toward the benchmark
-f : says number of forks (perhaps new runs)
-i : numbers of iteration per fork
-bm : Benchmarking mode, which is also mentioned with @BenchmarkMode(Mode.XYZ)
-gc : Do garbage collection in between
-r  : Run each iteration for these many seconds

## Run system copy benchmark
java -jar ./atrbench-uber.jar SystemCopy  -f 3 -i 3 -r 5s -gc true -wi 5


