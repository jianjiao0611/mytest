package com.jjtest.tool.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * xml工具类
 */
public class XmlUtils {

    public static <T> T xmlToBean(String xmlStr, Class<T> cls) {
        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStream.processAnnotations(cls);
        T t = (T) xStream.fromXML(xmlStr);
        return t;
    }


 }
