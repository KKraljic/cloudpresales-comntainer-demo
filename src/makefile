FLAGS = -nowarn
JC = javac -cp "./lib/commons-beanutils.jar:./lib/commons-codec-1.3.jar:./lib/commons-collections.jar:./lib/commons-lang.jar:./lib/commons-logging-1.1.jar:./lib/ezmorph-1.0.3.jar:./lib/httpclient-4.0-beta2.jar:./lib/httpcore-4.0-beta2.jar:./lib/json-lib-2.2.3-MODIFIED-jdk15.jar"
JCR = java -cp "./lib/commons-beanutils.jar:./lib/commons-codec-1.3.jar:./lib/commons-collections.jar:./lib/commons-lang.jar:./lib/commons-logging-1.1.jar:./lib/ezmorph-1.0.3.jar:./lib/httpclient-4.0-beta2.jar:./lib/httpcore-4.0-beta2.jar:./lib/json-lib-2.2.3-MODIFIED-jdk15.jar"
RM = rm

.SUFFIXES: .java

CLASSES = DemoUI.java

DemoUI.class : DemoUI.java
	$(JC) ./com/fourspaces/couchdb/*.java
	$(JC) ./com/fourspaces/couchdb/util/JSONUtils.java
	$(JC) DemoUI.java 
	$(JCR) com.fourspaces.couchdb.*
	$(JCR) com.fourspaces.couchdb.util.JSONUtils
	$(JCR) DemoUI
	$(RM) *.class
