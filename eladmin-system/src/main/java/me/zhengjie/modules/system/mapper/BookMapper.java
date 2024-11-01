package me.zhengjie.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.zhengjie.modules.system.domain.Book;
import me.zhengjie.modules.system.domain.vo.BookQueryCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: eladmin
 * @CreateTime: 2024/8/21 8:47
 * @Author: Zhaoyang Chen
 * @Description: 图书映射类
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {
    List<Book> listAll(BookQueryCriteria params);
    Long countAll(BookQueryCriteria params);
    List<String> listByCategory(String userId, String[] genres);
    List<String> randomSelect(String userId, int remainCount, List<String> excludeIds);
    List<Book> listBookByIds(List<String> ids);
    List<Book> randomReturn(int total);
    List<Book> fuzzyQuery(String key);
}
