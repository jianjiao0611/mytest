package com.jjtest.user.po;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("CustomServiceInterfaceResponse")
public class DataBasePo {

    private DataBodyPo body;

    private DataHeaderPo header;
}
