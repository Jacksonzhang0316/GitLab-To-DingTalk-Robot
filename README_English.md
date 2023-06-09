# GitLab-To-DingTalk-Robot

### Project Introduction

Due to the lack of comprehensive support for GitLab from the official DingTalk group chat robot, and because my company had a requirement for such message push notifications, I decided to write one myself. I aim to support as many GitLab Webhook events as possible, including push, merge, request, issue, pipeline, and more. However, currently, it only supports the pipeline event.

GitLab-To-DingTalk-Robot, upon receiving a Webhook request from GitLab, will transform the request data into a format that the DingTalk custom robot supports, and then sends it to the DingTalk robot. At present, it only supports messages in Markdown style. Support for messages in other styles will be added in the future.

---

### Compilation and Deployment

This project is based on JDK 17, built with Maven, and deployed with Spring Boot, which makes the compilation and deployment process extremely simple!

1. Clone this project to your local machine.
2. Open the project in IntelliJ IDEA and wait for Maven to automatically download the dependencies.
3. Right-click on `GitLabRobotApplication.java` in IDEA and select Run to start the project and verify that there are no issues with the project compilation.
4. Package the project by using the Maven tool bar in IDEA. Click on `GitLabRobot` -> `Lifecycle` -> `package` and wait for Maven to finish the packaging.
5. After successful packaging, you can find the `GitLabRobot-1.0.0.jar` file in the `target` directory. Upload this file to your server.
6. Create a custom robot in DingTalk. For the security settings, select `Custom Keywords`. See the API details below for keyword rules.
7. Start the service. You will need to include the DingTalk robot's Webhook URL in the command, as shown below:
    ```shell
    nohup java -DDING_TALK_WEBHOOK_URL=https://oapi.dingtalk.com/robot/send?access_token=xxx -jar GitLabRobot-1.0.0.jar &
    ```
8. After the service starts successfully, fill in the service address in the Webhook settings of GitLab, as shown below:
    ```text
    http://xxx.xxx.xxx.xxx:8989/webhook/pipline/
    ```
9. Save the settings and test if the Webhook is working.
