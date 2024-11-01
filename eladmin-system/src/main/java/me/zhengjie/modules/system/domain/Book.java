package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;
import nonapi.io.github.classgraph.json.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: gridadmin
 * @CreateTime: 2024/8/20 23:22
 * @Author: Zhaoyang Chen
 * @Description: 图书类
 */
@Getter
@Setter
@TableName("r_books")
public class Book extends BaseEntity implements Serializable {

    @Id
    private String id;

    private String title;

    private String category;

    private String author;

    private String nation;

    private String publisher;

    private Date publishTime;

    private String folder;

    private String mediaType;

    private String sample;

    private String url;

    private Date uploadTime;

}
