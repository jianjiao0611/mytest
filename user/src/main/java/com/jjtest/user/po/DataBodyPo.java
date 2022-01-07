package com.jjtest.user.po;

import lombok.Data;

import java.util.List;

@Data
public class DataBodyPo {

    private List<DataPo> datalist;

    private String returnCode;

    private String description;

    private String retCode;

    private String retMsg;

    private String total;
}
