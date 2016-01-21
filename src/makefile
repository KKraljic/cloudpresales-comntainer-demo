FLAGS = -nowarn
JC = javac -cp "./lib/commons-beanutils.jar:commons-codec-1.3.jar:commons-collections.jar:commons-lang.jar:commons-logging-1.1.jar:ezmorph-1.0.3.jar:httpclient-4.0-beta2.jar:httpcore-4.0-beta2.jar:json-lib-2.2.3-MODIFIED-jdk15.jar"
JCR = java -cp "./lib/commons-beanutils.jar:commons-codec-1.3.jar:commons-collections.jar:commons-lang.jar:commons-logging-1.1.jar:ezmorph-1.0.3.jar:httpclient-4.0-beta2.jar:httpcore-4.0-beta2.jar:json-lib-2.2.3-MODIFIED-jdk15.jar"
RM = rm

.SUFFIXES: .java
CLASSES = DemoUI.java
DemoUI.class : DemoUI.java
	$(JC) DemoUI.java
	$(JCR) DemoUI
	$(RM) *.class
