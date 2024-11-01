package me.zhengjie.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.zhengjie.modules.system.domain.Comment;
import me.zhengjie.modules.system.domain.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/18 22:24
 * @Author: Zhaoyang Chen
 */
@Mapper
public interface CommentMapper  extends BaseMapper<Comment> {
    List<CommentDto> listByBookId(String bookId);
}
