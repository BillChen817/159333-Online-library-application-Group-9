package me.zhengjie.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.zhengjie.modules.system.domain.Book;
import me.zhengjie.modules.system.domain.BorrowHistory;
import me.zhengjie.modules.system.domain.vo.BookQueryCriteria;
import me.zhengjie.modules.system.domain.vo.BorrowVo;
import me.zhengjie.modules.system.domain.vo.DictVo;
import me.zhengjie.modules.system.domain.vo.ShelfQueryCriteria;
import me.zhengjie.utils.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: eladmin
 * @CreateTime: 2024/8/21 8:56
 * @Author: Zhaoyang Chen
 * @Description: 图书服务接口
 */
public interface BookService {
    PageResult<Book> listAll(BookQueryCriteria params);

    Map<String, String> uploadFolder(MultipartFile avatar);

    Map<String, String> uploadBook(MultipartFile book);

    void create(Book book);

    void deleteById(String id);

    void collect(ShelfQueryCriteria queryCriteria);

    int hasCollectRecord(ShelfQueryCriteria queryCriteria);

    PageResult<Book> listAllCollectedBooks(ShelfQueryCriteria queryCriteria);

    void deleteFavorite(String userId, String bookId);

    PageResult<Book> listBrowseHistory(BookQueryCriteria param);

    void addBrowseRecord(String userId, String bookId);

    Book getBook(String bookId);

    List<DictVo> listAuthors(String userId);
    List<DictVo> listCategories(String userId);

    List<Book> fuzzyQuery(String key);

    String queryBorrowStatus(String username, String bookId);

    void borrow(String userId, String bookId, Date endTime);

    int updateBorrowHistory(String userId, String bookId);

    List<BorrowVo> listBorrowHistory(String userId);
    boolean hasDueRecord(String userId);
}
