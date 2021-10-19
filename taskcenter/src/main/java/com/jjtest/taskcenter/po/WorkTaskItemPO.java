package com.jjtest.taskcenter.po;

import lombok.Data;

/**
 * 工作项
 */
@Data
public class WorkTaskItemPO {

    private Long id;

    /**
     * 1 :
     */
    private int status;
    /**
     * 上一个工作项
     */
    private Long preId;
    /**
     * 是否多派
     */
    private boolean isMore;

    public WorkTaskItemPO() {
    }

    public WorkTaskItemPO(Long id, int status, Long preId, boolean isMore) {
        this.id = id;
        this.status = status;
        this.preId = preId;
        this.isMore = isMore;
    }
}
