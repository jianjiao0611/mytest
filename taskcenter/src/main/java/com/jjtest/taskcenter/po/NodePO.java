package com.jjtest.taskcenter.po;

import lombok.Data;

@Data
public class NodePO {

    private NodePO t;
    private boolean head;

    private Long id;

    private Long preId;

    private Long nextId;

    private NodePO preNode;

    private NodePO nextNode;

    public NodePO() {
    }

    public NodePO(boolean head, Long id, Long preId, Long nextId) {
        this.head = head;
        this.id = id;
        this.preId = preId;
        this.nextId = nextId;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(" id = " + this.getId());
        if (nextNode != null) {
            s.append("nextId = " + nextNode.getId());
        }
        return s.toString();
    }
}
