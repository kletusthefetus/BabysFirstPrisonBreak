default: Driver.java AdventureMap.java Room.java 
	javac Driver.java AdventureMap.java Room.java 

run: Driver.class AdventureMap.class Room.class 
	java Driver

clean:
	rm -f *.class