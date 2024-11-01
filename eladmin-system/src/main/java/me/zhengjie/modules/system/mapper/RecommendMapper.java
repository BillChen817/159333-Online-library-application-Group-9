package me.zhengjie.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.zhengjie.modules.system.domain.Recommend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/20 15:12
 * @Author: Zhaoyang Chen
 */
@Mapper
public interface RecommendMapper extends BaseMapper<Recommend> {
    void deleteByUserId(String userId);
    List<String> listBookIds(String userId);
}
