package com.king.hbase.config;

import com.alibaba.fastjson.JSONObject;
import com.king.util.log.LogUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ljq on 2020-9-18 17:00
 */
//@DependsOn("springContextHolder")    //控制依赖顺序，保证SpringContextHolder类在之前已经加载
//@Component
public class HBaseUtils {

    //private static HBaseConfig hBaseConfig = SpringContextHolder.getBean("HBaseConfig");

    private static Configuration conf = HBaseConfiguration.create();
    private static ExecutorService pool = Executors.newScheduledThreadPool(20);//设置连接池
    private static Connection connection = null;
    //private static HbaseUtils instance = null;
    private static Admin admin = null;

    private HBaseUtils(){
        if (connection == null) {
            try {
                //将HBase配置加载到连接池
                //conf.set("hbase.zookeeper.quorum",hBaseConfig.getQuorum());
                //conf.set("hbase.zookeeper.port",hBaseConfig.getPort());
                conf.set(HConstants.ZOOKEEPER_QUORUM,"192.168.1.237");
                conf.set("hbase.zookeeper.port","2181");
                connection = ConnectionFactory.createConnection(conf);
                admin = connection.getAdmin();
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.error("HbaseUtils实例初始化失败，错误信息{}",e.getMessage());
            }
        }
    }

    /**
     * 创建命名空间
     */
    public void createNamespace(String namespace) throws IOException {
        NamespaceDescriptor ccl = NamespaceDescriptor.create(namespace).build();
        admin.createNamespace(ccl);
        System.out.println("命名空间创建成功！");
    }

    /**
     * 查询所有表的表名
     */
    public List<String> getAllTableNames() {
        List<String> result = new ArrayList<>();
        try {
            TableName[] tableNames = admin.listTableNames();
            for (TableName tableName : tableNames) {
                result.add(tableName.getNameAsString());
            }
        } catch (IOException e) {
            LogUtil.error("获取所有表的表名失败", e);
        }
        return result;
    }

    /**
     * 创建表
     * @param tableName     表名
     * @param columnFamily  列族(数组)
     */
    public void createTable(String tableName,String[] columnFamily) throws IOException {
        TableName name = TableName.valueOf(tableName);
        //如果存在则删除
        if (admin.tableExists(name)) {
            admin.disableTable(name);
            admin.deleteTable(name);
            LogUtil.error("创建表:{}失败，表名称已存在！",tableName);
        }else {
            HTableDescriptor desc = new HTableDescriptor(name);
            for (String s : columnFamily) {
                desc.addFamily(new HColumnDescriptor(s));
            }
            admin.createTable(desc);
        }
    }

    /**
     * 插入记录(单行单列族-多列多值)
     * @param tableName         表名
     * @param row               行名
     * @param columnFamilys     列族名
     * @param columns           列名(数组)
     * @param values            值(数组) (需要和列一一对应)
     */
    public void insertRecords(String tableName,String row,String columnFamilys,String[] columns,String[] values) throws IOException {
        TableName name = TableName.valueOf(tableName);
        Table table = connection.getTable(name);
        Put put = new Put(Bytes.toBytes(row));
        for (int i = 0; i < columns.length; i++) {
            put.addColumn(Bytes.toBytes(columnFamilys), Bytes.toBytes(columns[i]), Bytes.toBytes(values[i]));
            table.put(put);
        }
    }

    /**
     * 插入记录(单行单列-单列单值)
     * @param tableName         表名
     * @param row               行名
     * @param columnFamily      列族名
     * @param column            列名
     * @param value             值
     */
    public void insertOneRecord(String tableName,String row,String columnFamily,String column,String value) throws IOException {
        TableName name = TableName.valueOf(tableName);
        Table table = connection.getTable(name);
        Put put = new Put(Bytes.toBytes(row));
        put.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(column),Bytes.toBytes(value));
        table.put(put);
    }

    /**
     * 删除表
     * @param tableName
     */
    public void deleteTable(String tableName) throws IOException {
        TableName name = TableName.valueOf(tableName);
        if (admin.tableExists(name)) {
            admin.disableTable(name);
            admin.deleteTable(name);
        }
    }

    /**
     * 删除一行记录
     * @param tableName         表名
     * @param rowKey            行名
     */
    public void deleteRow(String tableName,String rowKey) throws IOException {
        TableName name = TableName.valueOf(tableName);
        Table table = connection.getTable(name);
        Delete delete = new Delete(rowKey.getBytes());
        table.delete(delete);
    }

    /**
     * 删除单行单列族记录
     * @param tableName         表名
     * @param rowKey            行名
     * @param columnFamily      列族名
     */
    public void deleteColumnFamily(String tableName,String rowKey,String columnFamily) throws IOException {
        TableName name = TableName.valueOf(tableName);
        Table table = connection.getTable(name);
        Delete delete = new Delete(rowKey.getBytes()).deleteFamily(Bytes.toBytes(columnFamily));
        table.delete(delete);
    }

    /**
     * 删除单行单列族单列记录
     * @param tableName         表名
     * @param rowKey            行名
     * @param columnFamily      列族名
     * @param column            列名
     */
    public void deleteColumn(String tableName,String rowKey,String columnFamily,String column) throws IOException {
        TableName name = TableName.valueOf(tableName);
        Table table = connection.getTable(name);
        Delete delete = new Delete(rowKey.getBytes()).deleteColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        table.delete(delete);
    }

    /**
     * 查找一行记录
     * @param tableName         表名
     * @param rowKey            行名
     * @return
     */
    public static String selectRow(String tableName,String rowKey) throws IOException {
        String record = "";
        TableName name = TableName.valueOf(tableName);
        Table table = connection.getTable(name);
        Get get = new Get(rowKey.getBytes());
        Result result = table.get(get);
        NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = result.getMap();
        for (Cell cell : result.rawCells()) {
            StringBuffer buffer = new StringBuffer().append(Bytes.toString(cell.getRow())).append("\t")
                    .append(Bytes.toString(cell.getFamily())).append("\t")
                    .append(Bytes.toString(cell.getQualifier())).append("\t")
                    .append(Bytes.toString(cell.getValue())).append("\n");
            String str = buffer.toString();
            record += str;
        }
        return record;
    }

    /**
     * 查找单行单列族单列记录
     * @param tableName         表名
     * @param rowKey            行名
     * @param columnFamily      列族名
     * @param column            列名
     * @return
     */
    public static String selectValue(String tableName,String rowKey,String columnFamily,String column) throws IOException {
        TableName name = TableName.valueOf(tableName);
        Table table = connection.getTable(name);
        Get get = new Get(rowKey.getBytes());
        get.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(column));
        Result result = table.get(get);
        return Bytes.toString(result.value());
    }

    /**
     * 查询表中所有行(scan方式)
     * @param tableName
     * @return
     */
    public String scanAllRecord(String tableName) {
        Map<String,Map<String,String>> map = new HashMap<>();
        String ret = "";
        ResultScanner scanner = null;
        try {
            TableName name = TableName.valueOf(tableName);
            Table table = connection.getTable(name);
            Scan scan = new Scan();//scan中可设置过滤条件，过滤器等
            scanner = table.getScanner(scan);
            for (Result result : scanner) {
                Map<String,String> rawMap = new HashMap<>();
                for (Cell cell : result.rawCells()) {
                    rawMap.put(Bytes.toString(CellUtil.cloneQualifier(cell)),Bytes.toString(CellUtil.cloneValue(cell)));
                }
                map.put(Bytes.toString(CellUtil.cloneRow(result.rawCells()[0])),rawMap);
            }
            ret = JSONObject.toJSONString(map);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return ret;
    }

    /**
     * 根据rowKey关键字查询报告记录
     * @param tableName         表名
     * @param rowKeyword        关键字
     * @return
     */
    public List scanReportDataByRowKeyword(String tableName,String rowKeyword) throws IOException {
        ArrayList<Object> list = new ArrayList<>();

        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        //添加行键过滤器，根据关键字匹配
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(rowKeyword));
        scan.setFilter(rowFilter);

        ResultScanner scanner = table.getScanner(scan);
        try {
            for (Result result : scanner) {
                //根据业务来处理返回数据
                result.toString();
                list.add(null);
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return list;
    }

    /**
     * 根据rowKey关键字和时间戳范围查询
     * @param tableName
     * @param rowKeyword
     * @param minStamp
     * @param maxStamp
     * @return
     */
    public List scanReportDataByRowKeywordTimestamp(String tableName,String rowKeyword,Long minStamp,Long maxStamp) throws IOException {
        ArrayList<Object> list = new ArrayList<>();
        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        //添加scan的时间范围
        scan.setTimeRange(minStamp,maxStamp);

        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(rowKeyword));
        scan.setFilter(rowFilter);
        ResultScanner scanner = table.getScanner(scan);
        try {
            for (Result result : scanner) {
                //根据业务处理数据
                result.toString();
                list.add(null);
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return list;
    }

    /**
     * 利用协处理器进行全表count统计
     * @param tableName
     * @return
     */
    public Long countRowWithCoprocessor(String tableName) throws Throwable {
        TableName name = TableName.valueOf(tableName);
        HTableDescriptor descriptor = admin.getTableDescriptor(name);
        String coprocessorClass = "org.apache.hadoop.hbase.coprocessor.AggregateImplementation";
        if (!descriptor.hasCoprocessor(coprocessorClass)) {
            admin.disableTable(name);
            descriptor.addCoprocessor(coprocessorClass);
            admin.modifyTable(name,descriptor);
            admin.enableTable(name);
        }
        //计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Scan scan = new Scan();
        AggregationClient aggregationClient = new AggregationClient(conf);
        long count = aggregationClient.rowCount(name, new LongColumnInterpreter(), scan);

        stopWatch.stop();
        System.out.println("RowCount:"+count+",全表count统计耗时："+stopWatch.getTotalTimeMillis());
        return count;
    }

}
