rm -rf classes/*.class*
javac -encoding UTF8 -sourcepath sources -classpath ./../gs/postgresql-42.1.4.jar:./../gs/gs-ui-1.3.jar:./../gs/gs-core-1.3.jar:./../gs/gs-algo-1.3.jar -d classes sources/unite/*.java

javac -encoding UTF8 -sourcepath sources -classpath ./../gs/postgresql-42.1.4.jar:./../gs/gs-ui-1.3.jar:./../gs/gs-core-1.3.jar:./../gs/gs-algo-1.3.jar -d classes sources/*.java

java -classpath  ./../gs/postgresql-42.1.4.jar:./../gs/gs-ui-1.3.jar:./../gs/gs-core-1.3.jar:./../gs/gs-algo-1.3.jar:classes/ Main
