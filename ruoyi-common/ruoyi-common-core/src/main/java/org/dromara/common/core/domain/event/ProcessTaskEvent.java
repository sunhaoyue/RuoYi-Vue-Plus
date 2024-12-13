package org.dromara.common.core.domain.event;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程办理监听
 *
 * @author may
 */

@Data
public class ProcessTaskEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程定义编码
     */
    private String flowCode;

    /**
     * 审批节点编码
     */
    private String nodeCode;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 业务id
     */
    private String businessKey;

}
