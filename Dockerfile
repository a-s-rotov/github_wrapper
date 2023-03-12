FROM maven:3.9-amazoncorretto-19

COPY . /project
RUN  cd /project && mvn package