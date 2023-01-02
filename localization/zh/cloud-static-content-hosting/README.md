---
title: Static Content Hosting
category: Cloud
language: zh
tag:
- Cloud distributed
---

## 意图

将静态内容部署到基于云的存储服务，该服务可以将它们直接交付给客户端。 这可以减少对昂贵计算实例的需求。

## 解释

真实世界例子

> 全球性的营销网站（静态内容）需要快速的部署以开始吸引潜在的客户。为了将托管费用和维护成本降至最低，使用云托管存储服务和内容交付网络。

通俗地说

> 静态内容托管模式利用云原生存储服务来存储内容和全球内容交付网络，将其缓存在世界各地的多个数据中心。 在静态网站上，单个网页包含静态内容。 它们还可能包含客户端脚本，例如 Javascript。相比之下，动态网站依赖于服务器端处理，包括服务器端脚本，如 PHP、JSP 或 ASP.NET。

维基百科说

> 与由 Web 应用程序生成的动态网页相反，静态网页（有时称为平面网页或固定网页）是完全按照存储的方式传送到用户的网页浏览器的网页。静态网页适用于从不或很少需要更新的内容，尽管现代
> Web 模板系统正在改变这一点。可以将大量静态页面作为文件进行维护，没有自动化工具（例如静态站点生成器）是不切实际的。

**示例**

![alt text](./etc/static-content-hosting.png "Static Content Hosting")

在这个例子中我们使用AWS S3创建一个静态网站，并利用 AWS Cloudfront 在全球范围内分发内容。

1. 首先你需要一个AWS账户，你可以在这个创建一个免费的：[AWS Free Tier](https://aws.amazon.com/free/free-tier/)

2. 登陆 [AWS控制台](https://console.aws.amazon.com/console/home?nc2=h_ct&src=header-signin)

3. 进入身份和接入管理服务 (IAM) .

4. 创建一个仅具有此应用程序必要权限的IAM用户。

   * 点击 `用户`
   * 点击 `添加用户`. 选择你想要的 `用户名`， `接入类型`应该是 `编程式接入`. 点击 `下一步: 权限`.
   * 选择 `直接附加已存在的策略`. 选择 `AmazonS3FullAccess` 和 `CloudFrontFullAccess`. Click `下一步: 标签`.
   * 没有需要的标签, 所以直接点击 `下一步: 回顾`.
   * 检查呈现的信息，没问题的话点击`创建用户`
   * 完成这个示例所需要的`访问秘钥Id`和`访问秘钥密码`将会呈现在你面前，请妥善保管。
   * 点击 `关闭`.

5. [安装AWS 命令行工具 (AWS CLI)](https://docs.aws.amazon.com/cli/latest/userguide/install-cliv1.html) 来获得编程式访问AWS云。

6. 使用`aws configure`命令来配置AWS CLI [说明书](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-quickstart.html#cli-configure-quickstart-config)

7. 为web站点创建AWS S3 bucket。 注意S3 bucket名字必须要在全球范围内唯一。


   *  语法是 `aws s3 mb <bucket name>`  [说明书](https://docs.aws.amazon.com/cli/latest/userguide/cli-services-s3-commands.html#using-s3-commands-managing-buckets-creating)
   * 比如 `aws s3 mb s3://my-static-website-jh34jsjmg`
   * 使用列出现有存储桶的命令`aws s3 ls`验证存储桶是否已成功创建

8. 使用命令`aws s3 website`来配置bucket作为web站点。  [说明书](https://docs.aws.amazon.com/cli/latest/reference/s3/website.html).

   * 比如`aws s3 website s3://my-static-website-jh34jsjmg --index-document index.html --error-document error.html`

9. 上传内容到bucket中。
   * 首先创建内容，至少包含`index.html`和`error.html`文档。
   * 上传内容到你的bucket中。 [说明书](https://docs.aws.amazon.com/cli/latest/userguide/cli-services-s3-commands.html#using-s3-commands-managing-objects-copy)
   * 比如`aws s3 cp index.html s3://my-static-website-jh34jsjmg` and `aws s3 cp error.html s3://my-static-website-jh34jsjmg`

10. 然后我们需要设置bucket的策略以允许读取访问。

    * 使用以下内容创建`policy.json`（注意需要将bucket名称替换为自己的）。

    ```json
    {
        "Version": "2012-10-17",
        "Statement": [
            {
                "Sid": "PublicReadGetObject",
                "Effect": "Allow",
                "Principal": "*",
                "Action": "s3:GetObject",
                "Resource": "arn:aws:s3:::my-static-website-jh34jsjmg/*"
            }
        ]
    }
    ```

    * 根据这些设置桶策略[说明书](https://docs.aws.amazon.com/cli/latest/reference/s3api/put-bucket-policy.html)
    * 比如 `aws s3api put-bucket-policy --bucket my-static-website-jh34jsjmg --policy file://policy.json`

11. 使用浏览器测试web站点。

    * web站点的URL格式是 `http://<bucket-name>.s3-website-<region-name>.amazonaws.com`
    * 比如 这个站点创建在 `eu-west-1` 区域 ,名字是 `my-static-website-jh34jsjmg` 所以它可以通过 `http://my-static-website-jh34jsjmg.s3-website-eu-west-1.amazonaws.com`来访问。

12. 为web站点创建CloudFormation 分发。

    * 语法文档在这里 [this reference](https://docs.aws.amazon.com/cli/latest/reference/cloudfront/create-distribution.html)
    * 比如，最简单的方式是使用命令l `aws cloudfront create-distribution --origin-domain-name my-static-website-jh34jsjmg.s3.amazonaws.com --default-root-object index.html`
    * 也支持JSON格式的配置 比如使用 `--distribution-config file://dist-config.json` 来传递分发的配置文件参数
    * 命令的舒勇将显示准确的分配配置项，包括包括可用于测试的生成的 CloudFront 域名，例如 `d2k3xwnaqa8nqx.cloudfront.net`
    * CloudFormation 分发部署需要一些时间，但一旦完成，您的网站就会从全球各地的数据中心提供服务！

13. 就是这样！ 您已经实现了一个静态网站，其内容分发网络以闪电般的速度在世界各地提供服务。

    * 要更新网站，您需要更新 S3 存储桶中的对象并使 CloudFront 分配中的对象无效
    * 要从 AWS CLI 执行此操作，请参阅 [this reference](https://docs.aws.amazon.com/cli/latest/reference/cloudfront/create-invalidation.html)
    * 您可能想要做的进一步开发是通过 https 提供内容并为您的站点添加域名

## 适用性

当您想要执行以下操作时，请使用静态内容托管模式：

* 最小化包含一些静态资源的网站和应用程序的托管成本。
* 使用静态内容构建全球可用的网站
* 监控网站流量、带宽使用、成本等。

## 典型用例

* 具有全球影响力的网站
* 静态网站生成器生成的内容
* 没有动态内容要求的网站

## 真实世界例子

* [Java Design Patterns web site](https://java-design-patterns.com)

## 鸣谢

* [Static Content Hosting pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/static-content-hosting)
