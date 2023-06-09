# GitLab-To-DingTalk-Robot

### 项目介绍

由于钉钉官方中的GitLab群聊机器人对GitLab的支持不够完善，恰好公司有这方面的消息推送需求，所以自己写了一个。我将支持尽可能多的GitLab Webhook事件，包括push、merge
request、issue、pipeline等等，目前还只支持pipeline。
GitLab-To-DingTalk-Robot接收到来自GitLab的Webhook请求后，会将请求中的数据转换成钉钉自定义机器人支持的格式，然后发送给钉钉机器人。
目前还只支持Markdown风格的消息，后续会支持其他风格的消息。
---

### 编译与部署

本项目基于JDK 17开发，使用Maven进行构建，Spring Boot进行部署，所以编译与部署是非常简单的！

1. 克隆本项目到本地。
2. 使用IntelliJ IDEA打开项目工程，等待Maven自动下载依赖。
3. 在IDEA中右击试运行`GitLabRobotApplication.java`，即可启动项目，验证所有项目编译不存在问题。
4. 打包项目，使用IDEA自带的Maven工具栏，点击`GitLabRobot` -> `Lifecycle` -> `package`，等待Maven打包完成。
5. 打包成功后即可在`target`目录下找到`GitLabRobot-1.0.0.jar`文件，将其上传到服务器。
6. 在钉钉中创建一个自定义机器人，安全设置选择`自定义关键词`，关键词填写规则见下方的接口详情。
7. 启动服务，其中需要在命令中携带钉钉机器人的Webhook地址，如下所示：
    ```shell
    nohup java -DDING_TALK_WEBHOOK_URL=https://oapi.dingtalk.com/robot/send?access_token=xxx -jar GitLabRobot-1.0.0.jar &
    ```
8. 服务启动成功后，在GitLab的Webhook设置中填写服务的地址，如下所示：
   ```text
   http://xxx.xxx.xxx.xxx:8989/webhook/pipline/
   ```
9. 保存设置，测试Webhook是否生效

---

### 接口详情
流水线事件：
```text
http://xxx.xxx.xxx.xxx:8989/webhook/pipline/
```