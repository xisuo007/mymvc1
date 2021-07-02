package com.king.controller.hbase;

import com.king.hbase.config.HBaseUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

//@RestController
//@RequestMapping("hbase")
public class HBaseController {

    @Resource
    HBaseUtils hbaseUtils;

    @RequestMapping("/createNamespace")
    public void createNamespace(String namespace) throws IOException {
        hbaseUtils.createNamespace(namespace);
    }

    @RequestMapping("/createTable")
    public void createTable(String name) throws IOException {
        String[] columns = {"name","age"};
        hbaseUtils.createTable(name,columns);
    }

    @RequestMapping("/getAllTableNames")
    public List<String> getAllTableNames() throws IOException {
        return hbaseUtils.getAllTableNames();
    }

    @RequestMapping("/insertOneRecord")
    public void insertOneRecord(String name, String row, String columnFamily, String column, String value) throws IOException {
        hbaseUtils.insertOneRecord(name,row,columnFamily,column,value);
    }

    @RequestMapping("/scanAllRecord")
    public String scanAllRecord(String name) throws IOException {
        String s = hbaseUtils.scanAllRecord(name);
        return "查询结果："+s;
    }


    @RequestMapping("/testRequest")
    public void testRequest(HttpServletRequest request,String name, String row, String columnFamily, String column, String value) throws IOException {
        Enumeration<String> attributeNames = request.getAttributeNames();
        if (attributeNames.hasMoreElements()) {
            String element = attributeNames.nextElement();
            System.out.println(element);
        }
    }
}
