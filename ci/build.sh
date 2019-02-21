#!/bin/bash

# 设置脚本遇到任何异常即停止执行
set -e

project=$(cat projectname)
echo "当前项目${project}"

environment=$(cat environment)
echo "当前环境${environment}"

# 拉取tomcat基础镜像
docker pull registry-vpc.cn-beijing.aliyuncs.com/xal/tomcat
# 构建应用镜像
docker build -t registry-vpc.cn-beijing.aliyuncs.com/xal/${project}:${environment} .
# 推送应用镜像
docker push registry-vpc.cn-beijing.aliyuncs.com/xal/${project}:${environment}
# 删除本地镜像文件
docker rmi registry-vpc.cn-beijing.aliyuncs.com/xal/${project}:${environment}