
package me.zhengjie.modules.mnt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.zhengjie.modules.mnt.domain.Deploy;
import me.zhengjie.modules.mnt.domain.vo.DeployQueryCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Set;


@Mapper
public interface DeployMapper extends BaseMapper<Deploy> {

    Long countAll(@Param("criteria") DeployQueryCriteria criteria);

    List<Deploy> findAll(@Param("criteria") DeployQueryCriteria criteria);
    
    Set<Long> getIdByAppIds(@Param("appIds") Set<Long> appIds);

    Deploy getDeployById(@Param("deployId") Long deployId);
}
