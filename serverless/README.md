---
layout: pattern
title: serverless
folder: serverless
permalink: /patterns/serverless/
categories: Architectural
tags:
 - Java
 - Difficulty-Intermittent
---

## Serverless

Serverless eliminates the need to plan for infrastructure and let's you focus on your 
application. 

## Intent

Whether to reduce your infrastructure costs, shrink the time you spend on ops tasks, 
simplify your deployment processes, reach infinite scalability, serverless cuts time 
to market in half.
 
## Explanation

Serverless computing is a cloud computing execution model in which the cloud provider 
dynamically manages the allocation of machine resources. Pricing is based on the 
actual amount of resources consumed by an application, rather than on pre-purchased 
units of capacity. 

## Serverless framework

[Serverless](https://serverless.com/) is a toolkit for deploying and operating serverless architectures. 

## (Function as a Service or "FaaS")

The term ‘Serverless’ is confusing since with such applications there are both server 
hardware and server processes running somewhere, but the difference to normal 
approaches is that the organization building and supporting a ‘Serverless’ application
 is not looking after the hardware or the processes - they are outsourcing this to a vendor.

Some of the Serverless Cloud Providers are 

![https://serverless.com/framework/docs/providers/aws/](./etc/aws-black.png "aws")
![https://serverless.com/framework/docs/providers/azure/](./etc/azure-black.png "azure")
![https://serverless.com/framework/docs/providers/openwhisk/](./etc/openwhisk-black.png "openwhisk")
![https://serverless.com/framework/docs/providers/google/](./etc/gcf-black.png "google")
![https://serverless.com/framework/docs/providers/kubeless/](./etc/kubeless-logos-black.png "kubeless")
![https://serverless.com/framework/docs/providers/spotinst/](./etc/spotinst-logos-black-small.png "spotinst")
![https://serverless.com/framework/docs/providers/webtasks/](./etc/webtask-small-grayscale.png "webtask")
...

Anything that triggers an Lambda Function to execute is regarded by the Framework as 
an Event. Most of the Serverless Cloud Providers support following Events
- Http
- PubSub Events
- scheduled

AWS supports processing event generated from AWS Services (S3/Cloudwatch/etc) and 
using aws as a compute engine is our first choice.

## AWS lambda function implementation

AWS lambda SDK provides pre-defined interface `com.amazonaws.services.lambda.runtime
.RequestHandler` to implement our lambda function. 

```java
public class LambdaInfoApiHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

  private static final Logger LOG = Logger.getLogger(LambdaInfoApiHandler.class);
  private static final Integer SUCCESS_STATUS_CODE = 200;


  @Override
  public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    
  }
}
```
handleRequest method is where the function code is implemented. Context provides 
useful information about Lambda execution environment. AWS Lambda function needs a 
deployment package. This package is either a .zip or .jar file that contains all the 
dependencies of the function.

`serverless.yml` contains configuration to manage deployments for your functions.

## Run example in local

# Pre-requisites
* Node.js v6.5.0 or later.
* Serverless CLI v1.9.0 or later. You can run npm install -g serverless to install it.
* An AWS account. If you don't already have one, you can sign up for a free trial that includes 1 million free Lambda requests per month.
* [Set-up](https://serverless.com/framework/docs/providers/aws/guide/credentials/) your Provider Credentials. Watch the video on setting up credentials

# build and deploy

* `cd serverless`
* `mvn clean package`
* `serverless deploy --stage=dev --verbose`

Based on the configuration in `serverless.yml` serverless framework creates a
cloud formation stack for S3 (ServerlessDeploymentBucket), IAM Role 
(IamRoleLambdaExecution), cloud watch (log groups), API Gateway (ApiGatewayRestApi) 
and the Lambda function. 

The command will print out Stack Outputs which looks something like this

```yaml
endpoints:
  GET - https://xxxxxxxxx.execute-api.us-east-1.amazonaws.com/dev/info
```

```yaml
CurrentTimeLambdaFunctionQualifiedArn: arn:aws:lambda:us-east-1:xxxxxxxxxxx:function:lambda-info-http-endpoint-dev-currentTime:4
ServiceEndpoint: https://xxxxxxxxx.execute-api.us-east-1.amazonaws.com/dev
ServerlessDeploymentBucketName: lambda-info-http-endpoin-serverlessdeploymentbuck-2u8uz2i7cap2
```
access the endpoint to invoke the function.

## Credits

* [serverless docs](https://serverless.com/framework/docs/)
* [Serverless Architectures](https://martinfowler.com/articles/serverless.html)