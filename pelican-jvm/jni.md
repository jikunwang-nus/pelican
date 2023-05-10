
```shell
echo "cd src/main/java/"
cd src/main/java/ 
target = HelloWorldJNI
javac -h . $target

g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin $target.cpp -o $target.o


g++ -dynamiclib -o libnative.dylib $target.o -lc

```

