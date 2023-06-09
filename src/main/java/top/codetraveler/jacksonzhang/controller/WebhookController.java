package top.codetraveler.jacksonzhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.codetraveler.jacksonzhang.dto.PipelineEventDTO;
import top.codetraveler.jacksonzhang.service.PipelineEventService;

@RestController
public class WebhookController {

    private static final String basicPath = "/webhook";

    @Autowired
    private PipelineEventService pipelineEventService;

    // 处理流水线事件消息
    @PostMapping(basicPath + "/pipeline")
    public String handlePipelineEvent(@RequestBody PipelineEventDTO pipelineEvent) {

        // 处理流水线事件消息
        pipelineEventService.processPipelineEvent(pipelineEvent);

        // 根据你的需要返回一个响应
        return "Handle pipeline event successfully!";
    }

    // TODO: 处理其他事件
}
