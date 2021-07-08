package com.king.kettle;

//import org.pentaho.di.core.KettleEnvironment;
//import org.pentaho.di.core.database.DatabaseMeta;
//import org.pentaho.di.core.logging.LogLevel;
//import org.pentaho.di.core.util.EnvUtil;
//import org.pentaho.di.job.Job;
//import org.pentaho.di.job.JobMeta;
//import org.pentaho.di.repository.RepositoryDirectoryInterface;
//import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
//import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
//import org.pentaho.di.trans.Trans;
//import org.pentaho.di.trans.TransMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ljq
 * @Date 2019/11/12 11:36
 */
public class RunKettleJob {
    public static void main(String[] args){
        Map<String,String> params = new HashMap<>();
        params.put("filename","runjob");
        params.put("extend","txt");

        String kjbPath = "D:/kettle/jobs/Kettledoc/常规作业示例.kjb";
        String ktrPath = "G:/kettle/test01.ktr";

        //runRepoJob(params);

        runTrans(params,ktrPath);
    }

    /**
     * 运行资源库中的作业
     *
     * @param params 作业参数
     */
    public static void runRepoJob(Map<String, String> params) {
        //try {
        //    KettleEnvironment.init();
        //    KettleDatabaseRepository repository = new KettleDatabaseRepository();
        //    // 配置资源库数据库连接信息
        //    DatabaseMeta databaseMeta = new DatabaseMeta(
        //            "kettle",
        //            "mysql",
        //            "jdbc",
        //            "127.0.0.1",
        //            "test",
        //            "3306",
        //            "root",
        //            "root"
        //    );
        //    // 配置连接参数，指定连接编码为UTF8，若不指定则不能读取中文目录或者中文名作业
        //    databaseMeta.getAttributes().put("EXTRA_OPTION_MYSQL.characterEncoding", "utf8");
        //    // 连接测试
        //    if (databaseMeta.testConnection().startsWith("正确")) {
        //        System.out.println("数据库连接成功");
        //    } else {
        //        System.out.println("数据库连接失败");
        //        return;
        //    }
        //    // 配置资源库
        //    KettleDatabaseRepositoryMeta repositoryMeta = new KettleDatabaseRepositoryMeta(
        //            "kettle",
        //            "kettle",
        //            "Kettle Repository",
        //            databaseMeta
        //    );
        //    repository.init(repositoryMeta);
        //    // 连接资源库
        //    repository.connect("admin", "admin");
        //    // 指定job或者trans所在的目录
        //    RepositoryDirectoryInterface dir = repository.findDirectory("/批处理/");
        //    // 选择资源库中的作业
        //    JobMeta jobMeta = repository.loadJob("资源库作业示例", dir, null, null);
        //    // 配置作业参数
        //    for (String param : params.keySet()) {
        //        jobMeta.setParameterValue(param, params.get(param));
        //    }
        //    Job job = new Job(repository, jobMeta);
        //    job.setLogLevel(LogLevel.DEBUG);
        //    //执行作业
        //    job.start();
        //    //等待作业执行结束
        //    job.waitUntilFinished();
        //    if (job.getErrors() > 0) {
        //        throw new Exception("作业执行出错");
        //    }
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }
    /**
     * 运行转换文件
     *
     * @param params  转换参数
     * @param ktrPath 转换文件的路径，后缀ktr
     */
    public static void runTrans(Map<String, String> params, String ktrPath) {
        //try {
        //    // 初始化
        //    KettleEnvironment.init();
        //    EnvUtil.environmentInit();
        //    //创建ktr元对象  参数是生成的ktr脚本文件
        //    TransMeta transMeta = new TransMeta(ktrPath);
        //    // 配置参数
        //    //for (String param : params.keySet()) {
        //    //    transMeta.setParameterValue(param, params.get(param));
        //    //}
        //    //创建ktr
        //    Trans trans = new Trans(transMeta);
        //    // 设置日志级别
        //    trans.setLogLevel(LogLevel.DEBUG);
        //    // 执行转换
        //    trans.execute(null);
        //    // 等待转换执行结束
        //    trans.waitUntilFinished();
        //    // 抛出异常
        //    if (trans.getErrors() > 0) {
        //        throw new Exception("转换执行出错");
        //    }
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }
    /**
     * 运行作业文件
     *
     * @param params  作业参数
     * @param kjbPath 作业文件路径，后缀kjb
     */
    public static void runJob(Map<String, String> params, String kjbPath) {
        //try {
        //    KettleEnvironment.init();
        //    JobMeta jobMeta = new JobMeta(kjbPath, null);
        //    // 配置作业参数
        //    for (String param : params.keySet()) {
        //        jobMeta.setParameterValue(param, params.get(param));
        //    }
        //    // 配置变量
        //    // jobMeta.setVariable("name","value");
        //    Job job = new Job(null, jobMeta);
        //    // 设置日志级别
        //    job.setLogLevel(LogLevel.DEBUG);
        //    // 启动作业
        //    job.start();
        //    // 等待作业执行完毕
        //    job.waitUntilFinished();
        //    if (job.getErrors() > 0) {
        //        throw new Exception("作业执行出错");
        //    }
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }
}
