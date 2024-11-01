package me.zhengjie.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.zhengjie.modules.system.domain.Contents;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/18 16:34
 * @Author: Zhaoyang Chen
 */
@Mapper
public interface ContentsMapper extends BaseMapper<Contents> {
    List<Contents> listContents(String bookId);
    void deleteContents(String id);
}
