# 以jdk21为基础镜像
FROM openjdk:21
# 描述
LABEL description="infra oauth service"
# 暴露接口
EXPOSE 4396
# 将主机中的jar包添加到镜像中
ADD ./achobeta-infra-oauth/target/achobeta-infra-oauth-0.0.1-SNAPSHOT.jar infra-oauth-service-0.0.1-SNAPSHOT.jar
# 运行jar包
ENTRYPOINT ["java", "--enable-preview", "-jar","infra-oauth-service-0.0.1-SNAPSHOT.jar"]