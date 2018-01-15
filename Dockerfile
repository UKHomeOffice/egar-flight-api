FROM quay.io/ukhomeofficedigital/openjdk8-jre:v0.2.0

# Add application artefacts
RUN mkdir /var/lib/flight
ENV app_deploy_path=/var/lib/flight
RUN yum -y install wget
RUN wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
RUN yum -y install apache-maven
RUN mkdir /tmp/working
COPY . /tmp/working
WORKDIR /tmp/working
RUN mvn -B package -DskipTests
RUN cp /tmp/working/target/egar-flight-api*.jar /var/lib/flight/egar-flight-api.jar
WORKDIR /tmp
RUN rm -rf working
WORKDIR /root/.m2
RUN rm -rf repository
WORKDIR /var/lib/flight
CMD ["/usr/bin/java", "-jar", "/var/lib/flight/egar-flight-api.jar"]
