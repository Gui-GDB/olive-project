# 基础镜像
FROM openjdk:17
#设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 拷贝项目
COPY olive-0.1.jar /olive.jar
# 入口
ENTRYPOINT ["java", "-jar", "/olive.jar"]