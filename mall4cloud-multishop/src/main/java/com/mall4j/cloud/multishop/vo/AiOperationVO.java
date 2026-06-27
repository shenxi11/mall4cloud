package com.mall4j.cloud.multishop.vo;

/*
 * 模块名: ai_operation_vo
 * 功能概述: 定义运营 AI 占位入口的返回结构，明确当前 AI 服务未真实接入。
 * 对外接口: AiOperationVO
 * 依赖关系: Swagger Schema
 * 输入输出: 输入运营场景和提示词，输出配置状态、建议提示词和占位结果。
 * 异常与错误: 无外部服务异常。
 * 维护说明: 接入真实 AI 服务后保持 configured/provider/result 字段语义稳定。
 */

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AI运营VO
 */
public class AiOperationVO {

    @Schema(description = "是否已配置真实AI服务")
    private Boolean configured;

    @Schema(description = "运营场景")
    private String scene;

    @Schema(description = "服务商")
    private String provider;

    @Schema(description = "提示消息")
    private String message;

    @Schema(description = "建议提示词")
    private String suggestedPrompt;

    @Schema(description = "生成结果")
    private String result;

    public Boolean getConfigured() {
        return configured;
    }

    public void setConfigured(Boolean configured) {
        this.configured = configured;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuggestedPrompt() {
        return suggestedPrompt;
    }

    public void setSuggestedPrompt(String suggestedPrompt) {
        this.suggestedPrompt = suggestedPrompt;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
