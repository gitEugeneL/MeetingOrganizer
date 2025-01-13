make text:
	mvn clean package && mvn exec:java -Dexec.args="text"

make gui:
	mvn clean package && mvn exec:java