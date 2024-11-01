
package me.zhengjie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.zhengjie.domain.SysLog;
import me.zhengjie.domain.vo.SysLogQueryCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

    List<SysLog> queryAll(@Param("criteria") SysLogQueryCriteria criteria);

    IPage<SysLog> queryAll(@Param("criteria") SysLogQueryCriteria criteria, Page<SysLog> page);
    IPage<SysLog> queryAllByUser(@Param("criteria") SysLogQueryCriteria criteria, Page<SysLog> page);
    String getExceptionDetails(@Param("id") Long id);
    void deleteByLevel(@Param("logType") String logType);
}
