
package me.zhengjie.modules.mnt.mapper;

import me.zhengjie.modules.mnt.domain.Server;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Set;


@Mapper
public interface DeployServerMapper {

    void insertData(@Param("deployId") Long deployId, @Param("servers") Set<Server> servers);

    void deleteByDeployId(@Param("deployId") Long deployId);

    void deleteByDeployIds(@Param("deployIds") Set<Long> deployIds);

    void deleteByServerIds(@Param("serverIds") Set<Long> serverIds);
}
