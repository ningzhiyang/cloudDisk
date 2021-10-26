package com.imnu.cloudDisk.hadoop;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <Description> <br>
 *
 * @author 宁志洋
 * @CreateDate 2021/7/29 <br>
 */
@Data
@Component
public class HdfsConfig {

    @Value("${hadoop.name-node}")
    private String NameNode;
    @Value("${hadoop.namespace}")
    private String NameSpace;

}
