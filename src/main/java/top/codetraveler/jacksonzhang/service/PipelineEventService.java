package top.codetraveler.jacksonzhang.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.codetraveler.jacksonzhang.constant.DingTalkMsgType;
import top.codetraveler.jacksonzhang.dto.PipelineEventDTO;

@Service
public class PipelineEventService {

    @Autowired
    private Environment env;

    public void processPipelineEvent(PipelineEventDTO pipelineEvent) {

        // 获取流水线事件数据
        String pipelineName = pipelineEvent.getProject().getName() + "流水线";
        String pipelineStatus = pipelineEvent.getObjectAttributes().getStatus();
        String trigger = pipelineEvent.getUser().getName();
        String commitUrl = pipelineEvent.getCommit().getUrl();
        String webUrl = pipelineEvent.getProject().getDescription();

        // 组装钉钉机器人所需的消息
        String dingTalkMessage = createDingTalkMessage(pipelineName, pipelineStatus, trigger, commitUrl, webUrl);

        // 将消息推送到钉钉机器人
        sendDingTalkMessage(dingTalkMessage);
    }

    private String createDingTalkMessage(String pipelineName, String pipelineStatus, String trigger, String commitUrl, String webUrl) {

        JSONObject message = new JSONObject();
        message.put("msgtype", DingTalkMsgType.MARKDOWN);

        JSONObject markdown = new JSONObject();
        markdown.put("title", pipelineName);
        markdown.put("text", generateMarkdownTemplate(pipelineName, pipelineStatus, trigger, commitUrl, webUrl));

        message.put("markdown", markdown);

        return message.toJSONString();
    }

    // 生成钉钉机器人所需的markdown格式消息
    public String generateMarkdownTemplate(String pipelineName, String pipelineStatus, String trigger, String commitUrl, String webUrl) {

        StringBuilder msg = new StringBuilder();
        msg.append("### ").append(pipelineName).append("\n\n");
        msg.append("- 状态: ").append(formatStatusText(pipelineStatus)).append("\n");
        msg.append("- 触发者: ").append(trigger).append("\n");
        msg.append("- [").append("相关commit信息").append("](").append(commitUrl).append(")\n");
        msg.append("- [").append("访问链接").append("](").append(webUrl).append(")\n");

        return msg.toString();
    }

    // 根据流水线状态格式化状态文本
    private String formatStatusText(String status) {

        String color = switch (status) {
            case "success" -> "green";
            case "failed" -> "red";
            case "running" -> "orange";
            case "pending" -> "dodgerblue";
            default -> "default";
        };
        return "<font color='" + color + "'>" + status + "</font>";
    }

    private void sendDingTalkMessage(String message) {
        String dingTalkWebhook = env.getProperty("dingTalk.webhook");
        if (dingTalkWebhook == null || dingTalkWebhook.isEmpty()) {
            throw new RuntimeException("钉钉机器人webhook地址为空，请检查配置文件！");
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(message, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(dingTalkWebhook, entity, String.class);
        // TODO 根据请求结果做处理
    }
}
