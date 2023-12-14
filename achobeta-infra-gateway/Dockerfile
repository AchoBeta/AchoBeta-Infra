# 以jdk21为基础镜像
FROM openjdk:21
# 描述
LABEL description="infra gateway service"
# 暴露接口
EXPOSE 4399
# 将主机中的jar包添加到镜像中
ADD ./achobeta-infra-gateway/target/achobeta-infra-gateway-0.0.1-SNAPSHOT.jar infra-gateway-service.jar
# 运行jar包
ENTRYPOINT ["java", "--enable-preview", "-jar","infra-gateway-service.jar"]