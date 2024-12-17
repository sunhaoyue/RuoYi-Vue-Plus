package org.dromara.workflow.controller;

import cn.hutool.core.lang.tree.Tree;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.warm.flow.core.entity.Definition;
import org.dromara.warm.flow.core.service.DefService;
import org.dromara.warm.flow.orm.entity.FlowDefinition;
import org.dromara.workflow.domain.bo.FlowCategoryBo;
import org.dromara.workflow.domain.vo.FlowDefinitionVo;
import org.dromara.workflow.service.IFlwCategoryService;
import org.dromara.workflow.service.IFlwDefinitionService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 流程定义管理 控制层
 *
 * @author may
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/definition")
public class FlwDefinitionController extends BaseController {

    private final IFlwCategoryService flwCategoryService;
    private final IFlwDefinitionService flwDefinitionService;
    private final DefService defService;

    /**
     * 查询流程定义列表
     *
     * @param flowDefinition 参数
     */
    @GetMapping("/list")
    public TableDataInfo<FlowDefinitionVo> list(FlowDefinition flowDefinition, PageQuery pageQuery) {
        return flwDefinitionService.queryList(flowDefinition, pageQuery);
    }

    /**
     * 获取流程分类树列表
     *
     * @param categoryBo 参数
     */
    @GetMapping("/categoryTree")
    public R<List<Tree<Long>>> categoryTree(FlowCategoryBo categoryBo) {
        return R.ok(flwCategoryService.selectCategoryTreeList(categoryBo));
    }

    /**
     * 获取历史流程定义列表
     *
     * @param flowCode 参数
     */
    @GetMapping("/getHisListByKey/{flowCode}")
    public R<List<FlowDefinitionVo>> getHisListByKey(@PathVariable String flowCode) {
        return R.ok(flwDefinitionService.getHisListByKey(flowCode));
    }

    /**
     * 获取流程定义详细信息
     *
     * @param id 流程定义id
     */
    @GetMapping(value = "/{id}")
    public R<Definition> getInfo(@PathVariable Long id) {
        return R.ok(defService.getById(id));
    }

    /**
     * 新增流程定义
     *
     * @param flowDefinition 参数
     */
    @Log(title = "流程定义", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit()
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> add(@RequestBody FlowDefinition flowDefinition) {
        return R.ok(defService.checkAndSave(flowDefinition));
    }

    /**
     * 修改流程定义
     *
     * @param flowDefinition 参数
     */
    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @PutMapping
    @RepeatSubmit()
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> edit(@RequestBody FlowDefinition flowDefinition) {
        return R.ok(defService.updateById(flowDefinition));
    }

    /**
     * 发布流程定义
     *
     * @param id 流程定义id
     */
    @Log(title = "流程定义", businessType = BusinessType.INSERT)
    @PutMapping("/publish/{id}")
    @RepeatSubmit()
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> publish(@PathVariable Long id) {
        return R.ok(defService.publish(id));
    }

    /**
     * 取消发布流程定义
     *
     * @param id 流程定义id
     */
    @Log(title = "流程定义", businessType = BusinessType.INSERT)
    @PutMapping("/unPublish/{id}")
    @RepeatSubmit()
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> unPublish(@PathVariable Long id) {
        return R.ok(defService.unPublish(id));
    }

    /**
     * 删除流程定义
     */
    @Log(title = "流程定义", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Transactional(rollbackFor = Exception.class)
    public R<Void> remove(@PathVariable List<Long> ids) {
        return toAjax(flwDefinitionService.removeDef(ids));
    }

    /**
     * 复制流程定义
     *
     * @param id 流程定义id
     */
    @Log(title = "流程定义", businessType = BusinessType.INSERT)
    @PostMapping("/copy/{id}")
    @RepeatSubmit()
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> copy(@PathVariable Long id) {
        return R.ok(defService.copyDef(id));
    }

    /**
     * 导入流程定义
     *
     * @param file 文件
     */
    @Log(title = "流程定义", businessType = BusinessType.IMPORT)
    @PostMapping("/importDef")
    @RepeatSubmit()
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> importDef(MultipartFile file, String category) {
        return R.ok(flwDefinitionService.importXml(file, category));
    }

    /**
     * 导出流程定义
     *
     * @param id       流程定义id
     * @param response 响应
     * @throws IOException 异常
     */
    @Log(title = "流程定义", businessType = BusinessType.EXPORT)
    @PostMapping("/exportDef/{id}")
    public void exportDef(@PathVariable Long id, HttpServletResponse response) throws IOException {
        flwDefinitionService.exportDef(id, response);
    }

    /**
     * 获取流程定义xml字符串
     *
     * @param id 流程定义id
     */
    @GetMapping("/xmlString/{id}")
    public R<String> xmlString(@PathVariable Long id) {
        return R.ok("操作成功", defService.xmlString(id));
    }

    /**
     * 激活/挂起流程定义
     *
     * @param id     流程定义id
     * @param active 激活/挂起
     */
    @RepeatSubmit()
    @PutMapping("/active/{id}")
    public R<Boolean> active(@PathVariable Long id, @RequestParam boolean active) {
        return R.ok(active ? defService.active(id) : defService.unActive(id));
    }

}
