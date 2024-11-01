package me.zhengjie.modules.system.domain.vo;

import lombok.Data;

/**
 * @BelongsProject: eladmin
 * @CreateTime: 2024/8/23 18:53
 * @Author: Zhaoyang Chen
 * @Description: 书架查询类
 */
@Data
public class ShelfQueryCriteria {
    private String userId;
    private String bookId;
    private String collectFlag;
    private String author;
    private String category;
    private Long page;
    private Long size;
    private Long offset;

    public static final String COLLECT = "COLLECT";
    public static final String REMOVE = "REMOVE";
}
