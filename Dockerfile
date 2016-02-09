FROM ubuntu:14.04

RUN apt-get update
RUN apt-get install software-properties-common -y
RUN apt-get install build-essential -y
RUN add-apt-repository ppa:webupd8team/java -y
RUN apt-get update
RUN echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections
RUN apt-get install xorg openbox -y
RUN apt-get install oracle-java8-installer -y
RUN apt-get install oracle-java8-set-default
RUN apt-get install libxext6 -y
RUN apt-get install libxrender-dev -y
RUN apt-get install libxtst6 -y
RUN apt-get install libxi-dev -y
RUN apt-get install xauth -y
RUN apt-get update
RUN apt-get install maven -y
RUN export JAVA_HOME=/usr/java/jdk1.8.0
RUN export PATH=${PATH}:${JAVA_HOME}/bin
COPY pom.xml .
COPY /src/. /src/
COPY makefile .
RUN mvn package
CMD java -jar ./target/Cloudpresales-Container-Demo-1.0.jar