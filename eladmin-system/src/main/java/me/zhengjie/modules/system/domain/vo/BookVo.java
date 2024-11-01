package me.zhengjie.modules.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.zhengjie.modules.system.domain.Book;
import me.zhengjie.modules.system.domain.CommentDto;
import me.zhengjie.modules.system.domain.Contents;

import java.util.List;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/18 22:14
 * @Author: Zhaoyang Chen
 * @Desc: 图书详情（包括基本信息、目录、评论信息）
 */
@Data
@AllArgsConstructor
public class BookVo {
    private Book book;
    private List<Contents> contents;
    private List<CommentDto> comments;
    private boolean hasBorrowed;
}
