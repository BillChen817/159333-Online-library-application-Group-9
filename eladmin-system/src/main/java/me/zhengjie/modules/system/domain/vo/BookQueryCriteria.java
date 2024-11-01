package me.zhengjie.modules.system.domain.vo;

import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * @BelongsProject: eladmin
 * @CreateTime: 2024/8/21 9:39
 * @Author: Zhaoyang Chen
 */
@Data
public class BookQueryCriteria implements Serializable {
    private String userId;
    private String key;
    private String type;
    private Long page;
    private Long size;
    private Long offset;
}
