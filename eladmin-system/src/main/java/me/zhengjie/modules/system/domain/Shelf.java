package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;

import java.io.Serializable;


/**
 * @BelongsProject: eladmin
 * @CreateTime: 2024/8/21 8:43
 * @Author: Zhaoyang Chen
 * @Description: 书架实体类
 */
@Getter
@Setter
@TableName("r_book_shelf")
public class Shelf extends BaseEntity implements Serializable {
    private String id;
    private String userId;
    private String bookId;
}
