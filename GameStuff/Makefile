JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Moveable.java \
	MovingThing.java \
	Player.java \
	Block.java \
	BlockList.java \
	Game.java \
	GameRunner.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class