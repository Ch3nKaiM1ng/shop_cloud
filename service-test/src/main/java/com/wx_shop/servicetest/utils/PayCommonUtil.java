package com.wx_shop.servicetest.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.jdom2.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class PayCommonUtil {
    public static String getPostStr(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        try {
            InputStream is = request.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String s = "";

            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String xml = sb.toString(); //次即为接收到微信端发送过来的xml数据
        return xml;

    }

    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
        org.w3c.dom.Document doc = documentBuilder.parse(stream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int idx=0; idx<nodeList.getLength(); ++idx) {
            Node node = nodeList.item(idx);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                data.put(element.getNodeName(), element.getTextContent());
            }
        }
        try {
            stream.close();
        }
        catch (Exception ex) {

        }
        return data;
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }

//    /**
//     * 生成带有 sign 的 XML 格式字符串
//     *
//     * @param data Map类型数据
//     * @param key API密钥
//     * @return 含有sign字段的XML
//     */
//    public static String generateSignedXml(final Map<String, String> data, String key) throws Exception {
//        return generateSignedXml(data, key, SignType.MD5);
//    }
//
//    /**
//     * 生成带有 sign 的 XML 格式字符串
//     *
//     * @param data Map类型数据
//     * @param key API密钥
//     * @param signType 签名类型
//     * @return 含有sign字段的XML
//     */
//    public static String generateSignedXml(final Map<String, String> data, String key, SignType signType) throws Exception {
//        String sign = generateSignature(data, key, signType);
//        data.put(WXPayConstants.FIELD_SIGN, sign);
//        return mapToXml(data);
//    }

    //随机字符串生成
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    //请求xml组装
    public static String getRequestXml(SortedMap<String,Object> parameters){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();
            if ("attach".equalsIgnoreCase(key)||"body".equalsIgnoreCase(key)||"sign".equalsIgnoreCase(key)) {
                sb.append("<"+key+">"+"<![CDATA["+value+"]]></"+key+">");
            }else {
                sb.append("<"+key+">"+value+"</"+key+">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }


    /**
     * 验证回调签名
     * @return
     */
    public static boolean isTenpaySign(Map<String, String> map,String payKey) {
        String characterEncoding="utf-8";
        String charset = "utf-8";
        String signFromAPIResponse = map.get("sign");
        if (signFromAPIResponse == null || signFromAPIResponse.equals("")) {
            System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);
        //过滤空 设置 TreeMap
        SortedMap<String,String> packageParams = new TreeMap();

        for (String parameter : map.keySet()) {
            String parameterValue = map.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }

        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();

        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + payKey);

        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        //算出签名
        String resultSign = "";
        String tobesign = sb.toString();

        if (null == charset || "".equals(charset)) {
            resultSign = MD5Util.MD5Encode(tobesign, characterEncoding).toUpperCase();
        }else{
            try{
                resultSign = MD5Util.MD5Encode(tobesign, characterEncoding).toUpperCase();
            }catch (Exception e) {
                resultSign = MD5Util.MD5Encode(tobesign, characterEncoding).toUpperCase();
            }
        }

        String tenpaySign = ((String)packageParams.get("sign")).toUpperCase();
        return tenpaySign.equals(resultSign);
    }

    //请求方法
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {

            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            System.out.println("连接超时：{}"+ ce);
        } catch (Exception e) {
            System.out.println("https请求异常：{}"+ e);
        }
        return null;
    }





    public static String getRemoteHost(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }


      /**
         * 签名字符串
         * @param text 需要签名的字符串
         * @param key 密钥
         * @param input_charset 编码格式
         * @return 签名结果
         */
              public static String sign(String text, String key, String input_charset) {
            text = text + "&key=" + key;
            return DigestUtils.md5Hex(getContentBytes(text, input_charset));
      }
      /**
         * 签名字符串
         *   @param text 需要签名的字符串
         * @param sign 签名结果
         * @param key 密钥
         * @param input_charset 编码格式
         * @return 签名结果
         */
              public static boolean verify(String text, String sign, String key, String input_charset) {
            text = text + key;
            String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
            if (mysign.equals(sign)) {
                  return true;
            } else {
                  return false;
            }
      }
              public static byte[] getContentBytes(String content, String charset) {
            if (charset == null || "".equals(charset)) {
                  return content.getBytes();
            }
            try {
                  return content.getBytes(charset);
            } catch (UnsupportedEncodingException e) {
                  throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
            }
      }
 
      private static boolean isValidChar(char ch) {
            if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
                  return true;
            if ((ch >= 0x4e00 && ch <= 0x7fff) || (ch >= 0x8000 && ch <= 0x952f))
                  return true;// 简体中文汉字编码
            return false;
      }
      /**
         * 除去数组中的空值和签名参数
         * @param sArray 签名参数组
         * @return 去掉空值与签名参数后的新签名参数组
         */
              public static Map<String, String> paraFilter(Map<String, String> sArray) {
            Map<String, String> result = new HashMap<String, String>();
            if (sArray == null || sArray.size() <= 0) {
                  return result;
            }
            for (String key : sArray.keySet()) {
                  String value = sArray.get(key);
                  if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                              || key.equalsIgnoreCase("sign_type")) {
                        continue;
                  }
                  result.put(key, value);
            }
            return result;
      }
      /**
         * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
         * @param params 需要排序并参与字符拼接的参数组
         * @return 拼接后字符串
         */
              public static String createLinkString(Map<String, String> params) {
            List<String> keys = new ArrayList<String>(params.keySet());
            Collections.sort(keys);
            String prestr = "";
            for (int i = 0; i < keys.size(); i++) {
                  String key = keys.get(i);
                  String value = params.get(key);
                  if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                        prestr = prestr + key + "=" + value;
                  } else {
                        prestr = prestr + key + "=" + value + "&";
                  }
            }
            return prestr;
      }
      /**
         *
         * @param requestUrl 请求地址
         * @param requestMethod 请求方法
         * @param outputStr 参数
         */
              public static String httpRequest(String requestUrl,String requestMethod,String outputStr) {
            // 创建SSLContext
            StringBuffer buffer = null;
            try {
                  URL url = new URL(requestUrl);
                  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                  conn.setRequestMethod(requestMethod);
                  conn.setDoOutput(true);
                  conn.setDoInput(true);
                  conn.connect();
                  //往服务器端写内容
                  if (null != outputStr) {
                        OutputStream os = conn.getOutputStream();
                        os.write(outputStr.getBytes("utf-8"));
                        os.close();
                  }
                  // 读取服务器端返回的内容
                  InputStream is = conn.getInputStream();
                  InputStreamReader isr = new InputStreamReader(is, "utf-8");
                  BufferedReader br = new BufferedReader(isr);
                  buffer = new StringBuffer();
                  String line = null;
                  while ((line = br.readLine()) != null) {
                        buffer.append(line);
                  }
                  br.close();
            }catch(Exception e){
                  e.printStackTrace();
            }
            return buffer.toString();
      }
      public static String urlEncodeUTF8(String source){
            String result=source;
            try {
                  result=java.net.URLEncoder.encode(source, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
            }
            return result;
      }

      /**
         * 获取子结点的xml
         * @param children
         * @return String
         */
              public static String getChildrenText(List children) {
            StringBuffer sb = new StringBuffer();
            if(!children.isEmpty()) {
                  Iterator it = children.iterator();
                  while(it.hasNext()) {
                        Element e = (Element) it.next();
                        String name = e.getName();
                        String value = e.getTextNormalize();
                        List list = e.getChildren();
                        sb.append("<" + name + ">");
                        if(!list.isEmpty()) {
                              sb.append(getChildrenText(list));
                        }
                        sb.append(value);
                        sb.append("</" + name + ">");
                  }
            }
 
            return sb.toString();
      }
      public static InputStream String2Inputstream(String str) {
            return new ByteArrayInputStream(str.getBytes());
      }
    
    
    
}