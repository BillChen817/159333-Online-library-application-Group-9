package me.zhengjie.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.Comment;
import me.zhengjie.modules.system.domain.CommentDto;
import me.zhengjie.modules.system.mapper.CommentMapper;
import me.zhengjie.modules.system.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/18 22:24
 * @Author: ZHaoyang Chen
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    @Override
    public List<CommentDto> getComments(String bookId) {
        return commentMapper.listByBookId(bookId);
    }

    @Override
    public void addComment(Comment comment) {
        commentMapper.insert(comment);
    }
}
