package org.dromara.workflow.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.warm.flow.core.entity.HisTask;
import org.dromara.warm.flow.orm.entity.FlowTask;
import org.dromara.workflow.domain.bo.*;
import org.dromara.workflow.domain.vo.FlowHisTaskVo;
import org.dromara.workflow.domain.vo.FlowTaskVo;

import java.util.List;
import java.util.Map;

/**
 * 任务 服务层
 *
 * @author may
 */
public interface IFlwTaskService {
    /**
     * 启动任务
     *
     * @param startProcessBo 启动流程参数
     * @return 结果
     */
    Map<String, Object> startWorkFlow(StartProcessBo startProcessBo);


    /**
     * 办理任务
     *
     * @param completeTaskBo 办理任务参数
     * @return 结果
     */
    boolean completeTask(CompleteTaskBo completeTaskBo);

    /**
     * 查询当前用户的待办任务
     *
     * @param flowTaskBo 参数
     * @param pageQuery  分页
     * @return 结果
     */
    TableDataInfo<FlowTaskVo> getPageByTaskWait(FlowTaskBo flowTaskBo, PageQuery pageQuery);

    /**
     * 查询当前租户所有待办任务
     *
     * @param flowTaskBo 参数
     * @param pageQuery  分页
     * @return 结果
     */
    TableDataInfo<FlowHisTaskVo> getPageByTaskFinish(FlowTaskBo flowTaskBo, PageQuery pageQuery);


    /**
     * 查询待办任务
     *
     * @param flowTaskBo 参数
     * @param pageQuery  分页
     * @return 结果
     */
    TableDataInfo<FlowTaskVo> getPageByAllTaskWait(FlowTaskBo flowTaskBo, PageQuery pageQuery);

    /**
     * 查询已办任务
     *
     * @param flowTaskBo 参数
     * @param pageQuery  分页
     * @return 结果
     */
    TableDataInfo<FlowHisTaskVo> getPageByAllTaskFinish(FlowTaskBo flowTaskBo, PageQuery pageQuery);

    /**
     * 查询当前用户的抄送
     *
     * @param flowTaskBo 参数
     * @param pageQuery  分页
     * @return 结果
     */
    TableDataInfo<FlowTaskVo> getPageByTaskCopy(FlowTaskBo flowTaskBo, PageQuery pageQuery);

    /**
     * 修改任务办理人
     *
     * @param taskIdList 任务id
     * @param userId     用户id
     * @return 结果
     */
    boolean updateAssignee(List<Long> taskIdList, String userId);

    /**
     * 驳回审批
     *
     * @param bo 参数
     * @return 结果
     */
    boolean backProcess(BackProcessBo bo);

    /**
     * 获取可驳回节点
     *
     * @param instanceId 实例id
     * @return 结果
     */
    List<HisTask> getBackTaskNode(String instanceId);

    /**
     * 终止任务
     *
     * @param bo 参数
     * @return 结果
     */
    boolean terminationTask(FlowTerminationBo bo);

    /**
     * 按照任务id查询任务
     *
     * @param taskIdList 任务id
     * @return 结果
     */
    List<FlowTask> selectByIdList(List<Long> taskIdList);

    /**
     * 按照任务id查询任务
     *
     * @param taskId 任务id
     * @return 结果
     */
    FlowTask selectByIdList(Long taskId);

    /**
     * 按照实例id查询任务
     *
     * @param instanceIdList 流程实例id
     * @return 结果
     */
    List<FlowTask> selectByInstIdList(List<Long> instanceIdList);

    /**
     * 按照实例id查询任务
     *
     * @param instanceId 流程实例id
     * @return 结果
     */
    List<FlowTask> selectByInstId(Long instanceId);

    /**
     * 任务操作
     *
     * @param bo            参数
     * @param taskOperation 操作类型，区分委派、转办、加签、减签、修改办理人
     * @return 结果
     */
    boolean taskOperation(TaskOperationBo bo, String taskOperation);
}
