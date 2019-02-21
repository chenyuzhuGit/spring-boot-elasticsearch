#!/bin/bash

# 设置脚本遇到任何异常即停止执行
set -e

project=$(cat projectname)
echo "当前项目${project}"

environment=$(cat environment)
echo "当前环境${environment}"

# 拉取maven镜像
docker pull maven
# 使用最新的配置文件
\cp -rf application.properties ../src/main/resources/application.properties
# 使用maven将代码打成war包
docker run -i --rm -v /data/build/maven:/root/.m2 -v "$(pwd)/..":/usr/src/mymaven -w /usr/src/mymaven maven mvn clean package spring-boot:repackage
# 将目标war包移动到构建目录
mv ../target/esquery.war ROOT.war
# 拉取tomcat基础镜像
docker pull registry-vpc.cn-beijing.aliyuncs.com/xal/tomcat
# 构建应用镜像
docker build -t registry-vpc.cn-beijing.aliyuncs.com/xal/${project}:${environment} .
# 推送应用镜像
docker push registry-vpc.cn-beijing.aliyuncs.com/xal/${project}:${environment}
# 删除本地镜像文件
docker rmi registry-vpc.cn-beijing.aliyuncs.com/xal/${project}:${environment}