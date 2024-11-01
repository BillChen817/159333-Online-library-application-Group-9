package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/18 22:15
 * @Author: Zhaoyang Chen
 * @Desc: 用户评论
 */
@Getter
@Setter
@TableName("r_comments")
public class Comment extends BaseEntity implements Serializable {
    @Id
    private String id;
    private String bookId;
    private String content;
    private Integer rating;
}
