---
layout: pattern
title: Serverless
folder: serverless
permalink: /patterns/serverless/zh
categories: Architectural
language: zh
tags:
 - Cloud distributed
---

## Serverless

无服务器消除了规划基础设施的需要，让您专注于您的
应用程序。

以下是构建无服务器型时应该注意的优化型
应用程序

* 精益功能
  * 简洁的逻辑-使用函数转换，而不是传输(利用一些
集成可用从提供者传输)，并确保您只读
你需要的东西
  * 高效/单一目的代码-避免条件/路由逻辑和崩溃
进入个体功能，避免“胖”/单片功能和控制
功能部署包中的依赖项减少了您的加载时间
函数
  * 临时环境-利用容器启动进行昂贵的初始化
* 多事的调用
  * 简洁的有效载荷-仔细检查事件尽可能多，并观察
有效载荷约束(async - 128K)
  * 弹性路由-了解重试策略并利用死信队列
(SQS或SNS的重播)和记住重试计数为调用
  * 并发执行- lambda考虑的是并发和
它不是基于请求/持续时间的。Lambda将增加实例的数量
根据要求。
* 协调调用
  * 通过API解耦-设置你的应用程序的最佳实践是有API的as
确保关注点分离的契约
  * 规模匹配下游-确保Lambda调用下游
组件，你匹配规模配置到它(通过指定Max
基于下游服务的并发性)
  * 安全—经常问这样的问题，我需要VPC吗?
* Serviceful操作
  *自动化-使用自动化工具来管理和维护堆栈
  *监控应用程序-使用监控服务，以获得您的整体视图
  serverless应用程序

## 目的

无论是降低你的基础设施成本，减少你花在运营任务上的时间，
简化您的部署过程，达到无限的可伸缩性，无服务器节省时间
市场份额减半。
 
## 解释

无服务器计算是一种云计算执行模型，其中云提供商
动态管理机器资源的分配。定价是基于
应用程序消耗的实际资源量，而不是预先购买的
单位的能力。

## 类图
![alt text](./etc/serverless.urm.png "Serverless pattern class diagram")

## Serverless框架

术语“无服务器”是令人困惑的，因为这类应用程序都有服务器 硬件和服务器进程在某处运行，但区别在于正常
方法是组织构建和支持一个“无服务器”应用程序不关心硬件或流程，他们外包给供应商。

一些无服务器云提供商是这样的

[Serverless](https://serverless.com/) 是用于部署和操作无服务器架构的工具包。
## (Function as a Service or "FaaS")


![https://serverless.com/framework/docs/providers/aws/](./etc/aws-black.png "aws")
![https://serverless.com/framework/docs/providers/azure/](./etc/azure-black.png "azure")
![https://serverless.com/framework/docs/providers/openwhisk/](./etc/openwhisk-black.png "openwhisk")
![https://serverless.com/framework/docs/providers/google/](./etc/gcf-black.png "google")
![https://serverless.com/framework/docs/providers/kubeless/](./etc/kubeless-logos-black.png "kubeless")
![https://serverless.com/framework/docs/providers/spotinst/](./etc/spotinst-logos-black-small.png "spotinst")
![https://serverless.com/framework/docs/providers/webtasks/](./etc/webtask-small-grayscale.png "webtask")
...

任何触发Lambda函数执行的操作都会被框架视为 一个事件。大多数无服务器云提供商支持以下事件
- Http
- PubSub Events
- scheduled


AWS支持处理由AWS服务(S3/Cloudwatch/等)生成的事件
使用aws作为计算引擎是我们的首选。

## (Backend as a Service or "BaaS")
这个例子使用DynamoDB NoSQL创建了' persons '集合的后端
数据库服务也由Amazon提供。

## AWS lambda函数实现

[AWS Lambda SDK](https://aws.amazon.com/sdk-for-java/) 提供了预定义的接口
`com.amazonaws.services.lambda.runtime.RequestHandler` 实现的 

```java
public class LambdaInfoApiHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

  private static final Logger LOG = Logger.getLogger(LambdaInfoApiHandler.class);
  private static final Integer SUCCESS_STATUS_CODE = 200;


  @Override
  public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    
  }
}
```


handleRequest方法是实现函数代码的地方。上下文提供了
关于Lambda执行环境的有用信息。AWS Lambda函数需要一个
部署包。这个包是一个.zip或.jar文件，包含所有
函数的依赖关系。

“serverless.Yml包含了管理功能部署的配置。

## 在本地运行示例

## Pre-requisites

* Node.js v6.5.0或更高版本。
* 无服务器CLI v1.9.0或更高版本。你可以运行npm install -g serverless来安装它。
* AWS帐户。如果你还没有，你可以注册一个免费试用，每个月有100万个免费Lambda请求。
* [设置](https://serverless.com/framework/docs/providers/aws/guide/credentials/)您的提供商证书。观看关于设置凭据的视频

# 构建和部署

* `cd serverless`
* `mvn clean package`
* `serverless deploy --stage=dev --verbose`

基于“无服务器”中的配置。Yml的无服务器框架创建如下资源

* CloudFormation stack for S3 (ServerlessDeploymentBucket)
* IAM Role (IamRoleLambdaExecution)
* CloudWatch (log groups)
* API Gateway (ApiGatewayRestApi) 
* Lambda function
* DynamoDB collection


该命令将打印出如下所示的Stack Outputs
```yaml
endpoints:
  GET - https://xxxxxxxxx.execute-api.us-east-1.amazonaws.com/dev/info
  POST - https://xxxxxxxxx.execute-api.us-east-1.amazonaws.com/dev/api/person
  GET - https://xxxxxxxxx.execute-api.us-east-1.amazonaws.com/dev/api/person/{id}
  
```

```yaml
CurrentTimeLambdaFunctionQualifiedArn: arn:aws:lambda:us-east-1:xxxxxxxxxxx:function:lambda-info-http-endpoint-dev-currentTime:4
ServiceEndpoint: https://xxxxxxxxx.execute-api.us-east-1.amazonaws.com/dev
ServerlessDeploymentBucketName: lambda-info-http-endpoin-serverlessdeploymentbuck-2u8uz2i7cap2
```
access the endpoint to invoke the function.

Use the following cURL commands to test the endpoints

```cURL
curl -X GET \
  https://xxxxxxxxx.execute-api.us-east-1.amazonaws.com/dev/info \
  -H 'cache-control: no-cache'
```

```cURL
curl -X POST \
  https://xxxxxxxxx.execute-api.us-east-1.amazonaws.com/dev/api/person \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
	"firstName": "Thor",
	"lastName": "Odinson",
	"address": {
		"addressLineOne": "1 Odin ln",
		"addressLineTwo": "100",
		"city": "Asgard",
		"state": "country of the Gods",
		"zipCode": "00001"
	}
}'
```

```cURL
curl -X GET \
  https://xxxxxxxxx.execute-api.us-east-1.amazonaws.com/dev/api/person/{id} \
  -H 'cache-control: no-cache'
```

## 鸣谢

* [serverless docs](https://serverless.com/framework/docs/)
* [Serverless Architectures](https://martinfowler.com/articles/serverless.html)
* [Serverless Black Belt](https://youtu.be/oQFORsso2go)
