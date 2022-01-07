package com.jjtest.user.po;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
public class DataHeaderPo {

    @XStreamAlias("servicename")
    private String servicename;
    @XStreamAlias("version")
    private String version;
    @XStreamAlias("timestamp")
    private String timestamp;
    @XStreamAlias("Digest")
    private String digest;
    @XStreamAlias("ConversationId")
    private String conversationId;
}
