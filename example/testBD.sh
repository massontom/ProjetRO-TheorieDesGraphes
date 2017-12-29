rm classes/GestionBD.class
rm classes/TestGestionBD.class
rm classes/Point.class
javac -sourcepath sources -classpath librairies/postgresql-42.1.4.jar -d classes sources/GestionBD.java
javac -sourcepath sources -classpath librairies/postgresql-42.1.4.jar -d classes sources/TestGestionBD.java
javac -sourcepath sources -classpath librairies/postgresql-42.1.4.jar -d classes sources/Point.java
java -classpath librairies/postgresql-42.1.4.jar:classes/ TestGestionBD
