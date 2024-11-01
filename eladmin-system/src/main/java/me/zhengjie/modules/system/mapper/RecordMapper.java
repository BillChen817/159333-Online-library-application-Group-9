package me.zhengjie.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.zhengjie.modules.system.domain.Book;
import me.zhengjie.modules.system.domain.BrowseRecord;
import me.zhengjie.modules.system.domain.vo.BookQueryCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @BelongsProject: eladmin
 * @CreateTime: 2024/8/21 17:45
 * @Author: Zhaoyang Chen
 * @Description: 浏览记录映射类
 */
@Mapper
public interface RecordMapper extends BaseMapper<BrowseRecord> {
    List<Book> listBrowseHistory(BookQueryCriteria params);
    Long countBrowseAll(String userId);
    BrowseRecord getBrowseRecord(String userId, String bookId);
    void deleteBookBrowseHistory(String bookId);
}
