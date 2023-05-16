echo "cd src/main/java/"
cd src/main/java/jni

target=HelloWorldJNI
echo "javac -h . ${target}.java"
javac -h . ${target}.java

g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin $target.cpp -o $target.o
g++ -dynamiclib -o libnative.dylib $target.o -lc

java -cp .././ -Djava.library.path=. $target