package me.zhengjie.modules.system.domain;

import lombok.Data;

import java.util.Date;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/19 9:04
 * @Author: Zhaoyang Chen
 * @Desc: 传送给前端的评论信息
 */
@Data
public class CommentDto {
    private String id;
    private String content;
    private Integer rating;
    private Date createTime;
    private String nickName;
    private String avatarName;
}
