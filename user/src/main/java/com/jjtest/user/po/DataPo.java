package com.jjtest.user.po;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("data")
public class DataPo {

    private String chargeTypeName;

    private String provName;

    private String productId;

    private String useBeginTime;

    private String netTypeName;

    private String termProdName;

    private String productName;

    private String flowKB;

    private String programName;

    private String durationSec;

    private String programId;

    private String channelId;

    private String broadcastTypeName;

    @Override
    public String toString() {
        return "DataPo{" +
                "chargeTypeName='" + chargeTypeName + '\'' +
                ", provName='" + provName + '\'' +
                ", productId='" + productId + '\'' +
                ", useBeginTime='" + useBeginTime + '\'' +
                ", netTypeName='" + netTypeName + '\'' +
                ", termProdName='" + termProdName + '\'' +
                ", productName='" + productName + '\'' +
                ", flowKB='" + flowKB + '\'' +
                ", programName='" + programName + '\'' +
                ", durationSec='" + durationSec + '\'' +
                ", programId='" + programId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", broadcastTypeName='" + broadcastTypeName + '\'' +
                '}';
    }
}
