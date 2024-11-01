package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Comment;
import me.zhengjie.modules.system.domain.CommentDto;

import java.util.List;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/18 22:23
 * @Author: Zhaoyang Chen
 */
public interface CommentService {
    List<CommentDto> getComments(String bookId);
    void addComment(Comment comment);
}
