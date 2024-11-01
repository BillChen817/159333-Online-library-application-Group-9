package me.zhengjie.modules.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import me.zhengjie.config.FileProperties;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.*;
import me.zhengjie.modules.system.domain.vo.BookQueryCriteria;
import me.zhengjie.modules.system.domain.vo.BorrowVo;
import me.zhengjie.modules.system.domain.vo.DictVo;
import me.zhengjie.modules.system.domain.vo.ShelfQueryCriteria;
import me.zhengjie.modules.system.mapper.*;
import me.zhengjie.modules.system.service.BookService;
import me.zhengjie.utils.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.*;

/**
 * @BelongsProject: eladmin
 * @CreateTime: 2024/8/21 8:57
 * @Author: Zhaoyang Chen
 * @Description: TODO
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final UserMapper userMapper;
    private final FileProperties properties;
    private final ShelfMapper shelfMapper;
    private final RecordMapper browseMapper;
    private final BorrowMapper borrowMapper;

    @Override
    public PageResult<Book> listAll(BookQueryCriteria params) {
        Long offset = Math.max((params.getPage() - 1) * params.getSize(), 0);
        params.setOffset(offset);
        List<Book> books = bookMapper.listAll(params);
        Long total = bookMapper.countAll(params);
        return PageUtil.toPage(books, total);
    }

    @Override
    public Map<String, String> uploadFolder(MultipartFile folder) {
        File file = FileUtil.upload(folder, properties.getPath().getAvatar());
        return new HashMap<String, String>(1) {{
            assert file != null;
            put("folder", file.getName());
        }};
    }

    @Override
    public Map<String, String> uploadBook(MultipartFile book) {
        File file = FileUtil.upload(book, properties.getPath().getPath());
        return new HashMap<String, String>(1) {{
            assert file != null;
            put("url", file.getName());
        }};
    }

    @Override
    public void create(Book book) {
        book.setUploadTime(new Date());
        if (StringUtils.isEmpty(book.getId())) {
            book.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            bookMapper.insert(book);
        } else {
            bookMapper.updateById(book);
        }
    }

    @Override
    public void deleteById(String id) {
        bookMapper.deleteById(id);
        // 删除所有书架
        shelfMapper.deleteBookHistoryOfShelf(id);
    }

    @Override
    public void collect(ShelfQueryCriteria queryCriteria) {
        String flag = queryCriteria.getCollectFlag();
        String userId = queryCriteria.getUserId();
        String bookId = queryCriteria.getBookId();
        if (ShelfQueryCriteria.COLLECT.equals(flag) && hasNoRecord(queryCriteria)) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            Shelf shelf = new Shelf();
            shelf.setId(uuid);
            shelf.setUserId(userId);
            shelf.setBookId(bookId);
            shelfMapper.insert(shelf);
        } else if (ShelfQueryCriteria.REMOVE.equals(flag) && !hasNoRecord(queryCriteria)) {
            shelfMapper.deleteFromShelfByBookId(bookId, userId);
        }
    }

    @Override
    public int hasCollectRecord(ShelfQueryCriteria queryCriteria) {
        return shelfMapper.countBooks(queryCriteria);
    }

    @Override
    public PageResult<Book> listAllCollectedBooks(ShelfQueryCriteria params) {
        Long offset = Math.max((params.getPage() - 1) * params.getSize(), 0);
        params.setOffset(offset);
        List<Book> books = shelfMapper.listBookShelfByUser(params);
        Long total = shelfMapper.countBooksOnShelf(params.getUserId());
        return PageUtil.toPage(books, total);
    }

    @Override
    public void deleteFavorite(String userId, String bookId) {
        shelfMapper.deleteFromShelfByBookId(bookId, userId);
    }

    @Override
    public PageResult<Book> listBrowseHistory(BookQueryCriteria params) {
        Long offset = Math.max((params.getPage() - 1) * params.getSize(), 0);
        params.setOffset(offset);
        List<Book> books = browseMapper.listBrowseHistory(params);
        Long total = browseMapper.countBrowseAll(params.getUserId());
        return PageUtil.toPage(books, total);
    }

    @Override
    public void addBrowseRecord(String userId, String bookId) {
        // 先检查是否已经登记过
        BrowseRecord existRecord = browseMapper.getBrowseRecord(userId, bookId);
        if (existRecord != null) {
            existRecord.setBrowseTime(new Date());
            browseMapper.updateById(existRecord);
        } else {
            BrowseRecord browseRecord = new BrowseRecord();
            browseRecord.setBookId(bookId);
            browseRecord.setUserId(userId);
            browseRecord.setBrowseTime(new Date());
            browseMapper.insert(browseRecord);
        }
    }

    @Override
    public Book getBook(String bookId) {
        return bookMapper.selectById(bookId);
    }

    @Override
    public List<DictVo> listAuthors(String userId) {
        return shelfMapper.listAuthors(userId);
    }

    @Override
    public List<DictVo> listCategories(String userId) {
        return shelfMapper.listCategories(userId);
    }

    @Override
    public List<Book> fuzzyQuery(String key) {
        List<Book> books = bookMapper.fuzzyQuery(key);
        return books;
    }

    @Override
    public String queryBorrowStatus(String username, String bookId) {
        return borrowMapper.queryBorrowStatus(username, bookId);
    }

    @Override
    public void borrow(String userId, String bookId, Date endTime) {
        UserDetails currentUser;
        try {
            currentUser = SecurityUtils.getCurrentUser();
        } catch (Exception e) {
            throw new BadRequestException("please login first.");
        }

        String username = currentUser.getUsername();
        String status = borrowMapper.queryBorrowStatus(username, bookId);
        // 没有借阅记录或者已归还时，新增一条借阅记录
        if(status == null || "1".equals(status)){
            BorrowHistory history = new BorrowHistory();
            history.setUserId(userId);
            history.setBookId(bookId);
            history.setEndTime(endTime);
            history.setStatus("0"); //未归还
            history.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            borrowMapper.insert(history);
        } else {
            throw new BadRequestException("The book has been borrowed.");
        }





    }

    @Override
    public int updateBorrowHistory(String userId, String bookId) {
        return borrowMapper.updateByUser(userId, bookId);
    }

    @Override
    public List<BorrowVo> listBorrowHistory(String userId) {
        return borrowMapper.selectVo(userId);
    }

    @Override
    public boolean hasDueRecord(String userId) {
        List<BorrowHistory> dueRecords = borrowMapper.listDueRecord(userId);
        return dueRecords.size() > 0;
    }

    private boolean hasNoRecord(ShelfQueryCriteria queryCriteria) {
        return shelfMapper.countBooks(queryCriteria) == 0;
    }
}
