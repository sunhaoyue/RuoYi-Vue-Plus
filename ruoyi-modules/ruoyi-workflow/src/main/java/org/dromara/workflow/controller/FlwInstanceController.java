package org.dromara.workflow.controller;

import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.service.InsService;
import org.dromara.workflow.domain.bo.FlowCancelBo;
import org.dromara.workflow.domain.bo.FlowInstanceBo;
import org.dromara.workflow.domain.bo.FlowInvalidBo;
import org.dromara.workflow.domain.vo.FlowInstanceVo;
import org.dromara.workflow.service.IFlwInstanceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 流程实例管理 控制层
 *
 * @author may
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/instance")
public class FlwInstanceController extends BaseController {

    private final IFlwInstanceService flwInstanceService;
    private final InsService insService;

    /**
     * 分页查询正在运行的流程实例
     *
     * @param instance  参数
     * @param pageQuery 分页
     */
    @GetMapping("/pageByRunning")
    public TableDataInfo<FlowInstanceVo> pageByRunning(Instance instance, PageQuery pageQuery) {
        return flwInstanceService.pageByRunning(instance, pageQuery);
    }

    /**
     * 分页查询已结束的流程实例
     *
     * @param instance  参数
     * @param pageQuery 分页
     */
    @GetMapping("/pageByFinish")
    public TableDataInfo<FlowInstanceVo> pageByFinish(Instance instance, PageQuery pageQuery) {
        return flwInstanceService.pageByFinish(instance, pageQuery);
    }

    /**
     * 按照业务id删除流程实例
     *
     * @param businessIds 业务id
     */
    @DeleteMapping("/deleteByBusinessIds/{businessIds}")
    public R<Void> deleteByBusinessIds(@PathVariable List<Long> businessIds) {
        return toAjax(flwInstanceService.deleteByBusinessIds(businessIds));
    }

    /**
     * 按照实例id删除流程实例
     *
     * @param instanceIds 实例id
     */
    @DeleteMapping("/deleteByInstanceIds/{instanceIds}")
    public R<Void> deleteByInstanceIds(@PathVariable List<Long> instanceIds) {
        return toAjax(flwInstanceService.deleteByInstanceIds(instanceIds));
    }

    /**
     * 撤销流程
     *
     * @param bo 参数
     */
    @PutMapping("/cancelProcessApply")
    public R<Void> cancelProcessApply(@RequestBody FlowCancelBo bo) {
        return toAjax(flwInstanceService.cancelProcessApply(bo));
    }

    /**
     * 激活/挂起流程定义
     *
     * @param id     流程定义id
     * @param active 激活/挂起
     */
    @PutMapping("/active/{id}")
    public R<Boolean> active(@PathVariable Long id, @RequestParam boolean active) {
        if (active) {
            return R.ok(insService.unActive(id));
        } else {
            return R.ok(insService.active(id));
        }
    }


    /**
     * 获取当前登陆人发起的流程实例
     *
     * @param flowInstanceBo 参数
     * @param pageQuery      分页
     */
    @GetMapping("/pageByCurrent")
    public TableDataInfo<FlowInstanceVo> pageByCurrent(FlowInstanceBo flowInstanceBo, PageQuery pageQuery) {
        return flwInstanceService.pageByCurrent(flowInstanceBo, pageQuery);
    }


    /**
     * 获取流程图，流程记录
     *
     * @param businessId 业务id
     */
    @GetMapping("/flowImage/{businessId}")
    public R<Map<String, Object>> flowImage(@PathVariable String businessId) {
        return R.ok(flwInstanceService.flowImage(businessId));
    }

    /**
     * 获取流程变量
     *
     * @param instanceId 流程实例id
     */
    @GetMapping("/instanceVariable/{instanceId}")
    public R<Map<String, Object>> instanceVariable(@PathVariable String instanceId) {
        return R.ok(flwInstanceService.instanceVariable(instanceId));
    }

    /**
     * 作废流程
     *
     * @param bo 参数
     */
    @Log(title = "流程实例管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/invalid")
    public R<Boolean> invalid(@Validated @RequestBody FlowInvalidBo bo) {
        return R.ok(flwInstanceService.processInvalid(bo));
    }

}
