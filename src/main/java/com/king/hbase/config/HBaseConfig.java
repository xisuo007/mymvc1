package com.king.hbase.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * Created by ljq on 2020-9-18 16:45
 */
@Data
@Configuration
public class HBaseConfig {

    @Value("${hbase.zookeeper.quorum}")
    private String quorum;

    @Value("${hbase.zookeeper.port}")
    private String port;

}
