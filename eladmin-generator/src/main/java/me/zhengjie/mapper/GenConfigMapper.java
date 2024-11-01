
package me.zhengjie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.zhengjie.domain.GenConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface GenConfigMapper extends BaseMapper<GenConfig> {

    GenConfig findByTableName(@Param("tableName") String tableName);
}
