package com.mall4j.cloud.multishop.controller.admin;

/*
 * 模块名: admin_ai_operation_controller
 * 功能概述: 提供运营 AI 占位入口，返回场景清单和未配置状态的模拟生成结果。
 * 对外接口: /admin/ai_operation
 * 依赖关系: AiOperationDTO, AiOperationVO, ServerResponseEntity
 * 输入输出: 输入运营场景和提示词，输出统一 AI 占位响应。
 * 异常与错误: 当前不调用外部 AI 服务，因此不产生第三方异常。
 * 维护说明: 接入真实 AI 服务时将生成逻辑迁移到 Service 层并保留返回结构。
 */

import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.multishop.dto.AiOperationDTO;
import com.mall4j.cloud.multishop.vo.AiOperationVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 运营AI
 */
@RestController("adminAiOperationController")
@RequestMapping("/admin/ai_operation")
@Tag(name = "admin-运营AI")
public class AiOperationController {

    @GetMapping("/scenes")
    @Operation(summary = "获取AI运营场景", description = "获取AI运营场景")
    public ServerResponseEntity<List<AiOperationVO>> scenes() {
        List<AiOperationVO> scenes = new ArrayList<>();
        scenes.add(buildScene("product_title", "商品标题/卖点", "根据商品资料生成更适合搜索和转化的标题、卖点。"));
        scenes.add(buildScene("short_video_copy", "短视频脚本", "根据商品和活动信息生成短视频口播与画面脚本。"));
        scenes.add(buildScene("live_script", "直播话术", "根据直播主题生成开场、卖点讲解和促单话术。"));
        return ServerResponseEntity.success(scenes);
    }

    @PostMapping("/generate")
    @Operation(summary = "生成AI运营内容", description = "生成AI运营内容占位结果")
    public ServerResponseEntity<AiOperationVO> generate(@RequestBody AiOperationDTO aiOperationDTO) {
        AiOperationVO aiOperationVO = new AiOperationVO();
        aiOperationVO.setConfigured(Boolean.FALSE);
        aiOperationVO.setProvider("not_configured");
        aiOperationVO.setScene(aiOperationDTO.getScene());
        aiOperationVO.setMessage("AI服务尚未配置，当前返回运营占位结果。");
        aiOperationVO.setSuggestedPrompt(defaultPrompt(aiOperationDTO.getScene()));
        aiOperationVO.setResult("待配置AI服务后生成。当前输入：" + (aiOperationDTO.getPrompt() == null ? "" : aiOperationDTO.getPrompt()));
        return ServerResponseEntity.success(aiOperationVO);
    }

    private AiOperationVO buildScene(String scene, String result, String prompt) {
        AiOperationVO aiOperationVO = new AiOperationVO();
        aiOperationVO.setConfigured(Boolean.FALSE);
        aiOperationVO.setProvider("not_configured");
        aiOperationVO.setScene(scene);
        aiOperationVO.setResult(result);
        aiOperationVO.setSuggestedPrompt(prompt);
        aiOperationVO.setMessage("待配置AI服务");
        return aiOperationVO;
    }

    private String defaultPrompt(String scene) {
        if ("short_video_copy".equals(scene)) {
            return "生成一段短视频口播脚本，包含前三秒钩子、商品卖点和行动引导。";
        }
        if ("live_script".equals(scene)) {
            return "生成一段直播开场和商品讲解话术，包含互动提问和下单引导。";
        }
        return "生成商品标题、核心卖点和适合搜索的关键词。";
    }
}
