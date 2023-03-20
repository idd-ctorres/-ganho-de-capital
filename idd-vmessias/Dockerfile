FROM eclipse-temurin:11-jdk
COPY . /idd_vmessias
WORKDIR /idd_vmessias
RUN ./gradlew clean test shadowJar

FROM eclipse-temurin:11-jre
COPY --from=0 /idd_vmessias/build/libs/idd-vmessias-1.0-SNAPSHOT-all.jar /idd_vmessias/app.jar

ENTRYPOINT [ "java", "-jar", "/idd_vmessias/app.jar" ]
