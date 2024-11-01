package me.zhengjie.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.zhengjie.modules.system.domain.Book;
import me.zhengjie.modules.system.domain.Shelf;
import me.zhengjie.modules.system.domain.vo.DictVo;
import me.zhengjie.modules.system.domain.vo.ShelfQueryCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @BelongsProject: eladmin
 * @CreateTime: 2024/8/21 17:30
 * @Author: Zhaoyang Chen
 * @Description: 书架内容管理
 */
@Mapper
public interface ShelfMapper extends BaseMapper<Shelf> {
    List<Book> listBookShelfByUser(ShelfQueryCriteria criteria);
    void deleteFromShelfByBookId(String bookId, String userId);
    int countBooks(ShelfQueryCriteria criteria);
    Long countBooksOnShelf(String userId);
    void deleteBookHistoryOfShelf(String bookId);
    List<DictVo> listAuthors(String userId);
    List<DictVo> listCategories(String userId);
}
