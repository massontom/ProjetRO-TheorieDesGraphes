rm -rf classes/*.class*
javac -encoding UTF8 -sourcepath sources -classpath librairies/postgresql-42.1.4.jar:librairies/gs-ui-1.3/gs-ui-1.3.jar:librairies/gs-core-1.3/gs-core-1.3.jar:librairies/gs-algo-1.3/gs-algo-1.3.jar -d classes sources/unite/*.java
javac -encoding UTF8 -sourcepath sources -classpath librairies/postgresql-42.1.4.jar:librairies/gs-ui-1.3/gs-ui-1.3.jar:librairies/gs-core-1.3/gs-core-1.3.jar:librairies/gs-algo-1.3/gs-algo-1.3.jar -d classes sources/*.java
java -classpath  librairies/postgresql-42.1.4.jar:librairies/gs-ui-1.3/gs-ui-1.3.jar:librairies/gs-core-1.3/gs-core-1.3.jar:librairies/gs-algo-1.3/gs-algo-1.3.jar:classes/ Main
