FROM frolvlad/alpine-oraclejdk8:slim
WORKDIR /build-env
ADD . /build-env
RUN apk update &&  apk add bash && apk add libstdc++
ENV LC_ALL=C
COPY target/feednews-0.0.1-SNAPSHOT.jar /var/tmp/
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/var/tmp/feednews-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080