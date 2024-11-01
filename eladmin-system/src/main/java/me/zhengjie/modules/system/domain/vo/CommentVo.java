package me.zhengjie.modules.system.domain.vo;

import lombok.Data;
import me.zhengjie.modules.system.domain.Comment;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/19 8:40
 * @Author: Zhaoyang Chen
 */
@Data
public class CommentVo {
    private String bookId;
    private Comment comment;
}
