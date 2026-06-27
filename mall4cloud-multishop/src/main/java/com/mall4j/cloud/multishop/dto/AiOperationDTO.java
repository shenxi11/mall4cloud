package com.mall4j.cloud.multishop.dto;

/*
 * 模块名: ai_operation_dto
 * 功能概述: 定义运营 AI 占位入口的入参，当前不调用真实模型服务。
 * 对外接口: AiOperationDTO
 * 依赖关系: Swagger Schema
 * 输入输出: 输入运营场景和提示词，输出 Controller 生成占位结果。
 * 异常与错误: 无外部服务错误，必填校验由调用方控制。
 * 维护说明: 接入真实 AI 服务时优先在 Service 层替换生成逻辑，保持接口兼容。
 */

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AI运营DTO
 */
public class AiOperationDTO {

    @Schema(description = "运营场景")
    private String scene;

    @Schema(description = "业务提示词")
    private String prompt;

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
