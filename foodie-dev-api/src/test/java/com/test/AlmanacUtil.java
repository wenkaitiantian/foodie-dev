package com.test;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AlmanacUtil {

    public AlmanacUtil() {

    }
//获得源码
    public String getdata(String url) {
        String data = null;
        org.apache.commons.httpclient.HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        getMethod.setRequestHeader("User_Agent", "Mozilla/5.0(Windows NT 6.1;Win64;x64;rv:39.0) Gecko/20100101 Firefox/39.0");
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());//系统默认的恢复策略
        try {
            int statusCode = client.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.out.println("Wrong");
            }
            byte[] responseBody = getMethod.getResponseBody();
            data = new String(responseBody);
            return data;

        } catch (HttpException e) {
            System.out.println("Please check your provided http address!");
            data = "";
            e.printStackTrace();

        } catch (IOException e) {
            data = "";
            e.printStackTrace();
        } finally {

            getMethod.releaseConnection();

        }
        return data;
    }

    //想要获取字段部分的分割模式
//    static Pattern proInfo = Pattern.compile("<td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td>" +
//            "<td>(.*?)</td><td>(.*?)</td>", Pattern.DOTALL);
//    static Pattern proInfo = Pattern.compile("<td>(.*?)</td>\\s+<td>(.*?)</td>\\s+<td>(.*?)</td>\\s+<td>(.*?)</td>\\s+<td>(.*?)</td>" +
//            "\\s+<td>(.*?)</td>\\s+<td>(.*?)</td>", Pattern.DOTALL);

        static Pattern proInfo = Pattern.compile("<td(.*?)><a(.*?)><img(.*?)><div(.*?)><div(.*?)><span(.*?)>(.*?)</span>(.*?)</div></div></a></td>+<td>(.*?)</td>+<td>(.*?)</td>+<td><div(.*?)><span(.*?)>(.*?)</span></div></td>+<td>(.*?)</td>" , Pattern.DOTALL);
    private static List<Information> getDataStructure(String str) {
        //想要获取的整段数据的分割模式
        String[] info = str.split("</tr>");
        List<Information> list = new ArrayList<Information>();
        for (String s : info) {
            Matcher m = proInfo.matcher(s);
            Information information = null;
            if (m.find()) {
                information = new Information();
//                System.out.println(m.group(7).trim());
//                System.out.println(m.group(9).trim());
//                System.out.println(m.group(10).trim());
//                System.out.println(m.group(13).trim());
//                System.out.println(m.group(14).trim());
                String ss = m.group(7).trim();
                information.setName(ss);
                information.setPrice(m.group(9).trim());
                information.setFiat(m.group(10).trim());
                information.setGains(m.group(13).trim());
                information.setTransaction(m.group(14).trim());
                list.add(information);
            }
        }
        return list;
    }
@Test
    public static void main(String[] args) throws IOException {
        AlmanacUtil almanacUtil = new AlmanacUtil();
        String ss = almanacUtil.getdata("https://www.mytokencap.com/search?keyword=FDG&category=ticker");
        List<Information> list = getDataStructure(ss);
        String string = "";
        int qw=0;
        int tt=0;
        int cb=0;
        for (int k = 0; k < list.size(); k++) {
            String s ="名称：" + list.get(k).getName() + " " + "价格：" + list.get(k).getPrice() + " " +
                    "法币：" + list.get(k).getFiat() + " " + "今日涨幅：" + list.get(k).getGains() +
                    " " + "24h成交量:" + list.get(k).getTransaction() + "\n";
            string = string + s;
            if ("全网".equals(list.get(k).getName())){
                qw=1;
            }
            if ("Top tank".equals(list.get(k).getName())){
                tt=1;
            }
            if ("COINBIG".equals(list.get(k).getName())){
                cb=1;
            }
        }
        if (qw==0||tt==0||cb==0){
            String errorMytoken="qw:"+ qw +"tt:"+ tt +"cb:" + cb;
            almanacUtil.getdata("https://api.telegram.org/bot1062897512:AAHli0dHNWKGZZCI0K-99MBFYsZmBrnve1s/sendmessage?chat_id=683519846&text=mytoken-"+errorMytoken);
        }
        System.out.println(string);
        File f = new File("./" + File.separator + "gupiao.txt");//存在D盘guipiao.txt里
        OutputStream out = null;
        out = new FileOutputStream(f);
        byte b[] = string.getBytes();
        out.write(b);
        out.close();

}
}