#!/bin/bash

# 设置脚本遇到任何异常即停止执行
set -e

project=$(cat projectname)
echo "当前项目${project}"

environment=$(cat environment)
echo "当前环境${environment}"

appport=$(cat appport)
echo "应用端口${appport}"

echo "拉取应用镜像"
echo "............"
docker pull registry-vpc.cn-beijing.aliyuncs.com/xal/${project}:${environment}

echo "检查是否存在正运行的应用容器"
echo "............"
containerid=$(docker ps -a | grep ${environment}_${project} | awk '{print $1}')

if [ ${containerid} ]
then
  echo "存在正运行的应用容器，现开始删除正运行应用容器"
  docker rm -f ${containerid}
  echo "已删除正运行应用容器"
else
  echo "没有正运行的应用容器"
fi

echo "使用最新拉取的镜像重新启动应用容器"
echo "............"
docker run -dit --restart always --name ${environment}_${project} --hostname ${environment}_${project} -p ${appport}:8080 registry-vpc.cn-beijing.aliyuncs.com/xal/${project}:${environment}
echo "删除已过期镜像"
for imageid in $(docker images | grep "${project}" | grep "<none>" | awk '{print $3}')
do
  docker rmi ${imageid}
done
