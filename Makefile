JFLAGS = -d .
JC = javac
OBJS = src/*

default:
	$(JC) $(OBJS) $(JFLAGS)
