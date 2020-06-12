package com.wx_shop.servicetest.utils;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class xmlUtils {
    /**xml格式字符串与map集合之间互相转换的工具
     */

    //map集合转xml字符串
    public static String toXml(Map<String, Object> params) {
        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version='1.0' encoding='ISO8859-1' standalone='yes' ?><xml>");

        ArrayList<String> arr = new ArrayList<String>();
        for (String key : params.keySet()) {
            if (params.get(key) != null && !params.get(key).equals("")) {
                arr.add(key);
            }
        }
        Collections.sort(arr);
        for (int i = 0; i < arr.size(); i++) {
            String k = arr.get(i);
            if (params.get(k) != null) {
                String v = params.get(k).toString();
                xml.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            }
        }

        xml.append("</xml>");
        String xml2 = "";
        try {
            xml2 = new String(xml.toString().getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return xml2;
    }
    //xml字符串转map集合
    public static Map<String, Object> toMap(String xml) {


        Map<String, Object> result = new HashMap<String, Object>();

        if(xml.equals("")){
            return result;
        }

        try {
            StringReader read = new StringReader(xml);
            InputSource source = new InputSource(read);
            SAXBuilder sb = new SAXBuilder();

            Document doc = (Document) sb.build(source);
            Element root = doc.getRootElement();
            result.put(root.getName(), root.getText());
            result = parse(root, result);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.remove("xml");
        return result;
    }

    private static Map<String, Object> parse(Element root,
                                             Map<String, Object> result) {
        List<Element> nodes = root.getChildren();
        int len = nodes.size();
        if (len == 0) {
            result.put(root.getName(), root.getText());
        } else {
            for (int i = 0; i < len; i++) {
                Element element = (Element) nodes.get(i);// 循环依次得到子元素
                result.put(element.getName(), element.getText());
                // parse(element,result);
            }
        }
        return result;
    }

    public static String getRequestXml(SortedMap<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if("sign".equalsIgnoreCase(k)){

            }
            else if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)) {
                sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
            }
            else {
                sb.append("<"+k+">"+v+"</"+k+">");
            }
        }
        sb.append("<"+"sign"+">"+"<![CDATA["+parameters.get("sign")+"]]></"+"sign"+">");
        sb.append("</xml>");
        return sb.toString();
    }
}


