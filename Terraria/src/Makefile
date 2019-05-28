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
	BlockType.java \
	GameRunner.java \
	Game.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class