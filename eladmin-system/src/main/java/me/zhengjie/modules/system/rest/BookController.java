package me.zhengjie.modules.system.rest;

import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.rest.AnonymousGetMapping;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.*;
import me.zhengjie.modules.system.domain.vo.*;
import me.zhengjie.modules.system.mapper.BorrowMapper;
import me.zhengjie.modules.system.service.*;
import me.zhengjie.utils.PageResult;
import me.zhengjie.utils.SecurityUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @BelongsProject: eladmin
 * @CreateTime: 2024/8/21 9:06
 * @Author: Zhaoyang Chen
 * @Description: 图书控制器
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final ContentsService contentsService;
    private final CommentService commentService;
    private final RecommendService recommendService;

    @GetMapping("/list")
    public ResponseEntity<PageResult<Book>> queryAll(BookQueryCriteria param) {
        return new ResponseEntity<>(bookService.listAll(param), HttpStatus.OK);
    }

    @AnonymousGetMapping("/query")
    public ResponseEntity<List<Book>> fuzzyQuery(String key) {
        if (key == null || key.length() == 0) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        return new ResponseEntity<>(bookService.fuzzyQuery(key), HttpStatus.OK);
    }

    @PostMapping(value = "/uploadFolder")
    public ResponseEntity<Object> uploadBookFolder(@RequestParam MultipartFile file){
        return new ResponseEntity<>(bookService.uploadFolder(file), HttpStatus.OK);
    }

    @PostMapping(value = "/uploadBook")
    public ResponseEntity<Object> uploadBook(@RequestParam MultipartFile file){
        return new ResponseEntity<>(bookService.uploadBook(file), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createBook(@RequestBody Book book){
        bookService.create(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/delete")
    public ResponseEntity<Object> deleteBook(String id){
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/collect")
    public ResponseEntity<Object> collectBook(@RequestBody ShelfQueryCriteria queryCriteria){
        bookService.collect(queryCriteria);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/collect/status")
    public ResponseEntity<Object> checkCollectStatus(ShelfQueryCriteria queryCriteria){
        return new ResponseEntity<>(bookService.hasCollectRecord(queryCriteria), HttpStatus.OK);
    }

    @PostMapping("/collect/list")
    public ResponseEntity<Object> collectBookList(@RequestBody ShelfQueryCriteria queryCriteria){
        return new ResponseEntity<>(bookService.listAllCollectedBooks(queryCriteria), HttpStatus.OK);
    }

    @GetMapping("/delete/favorite")
    public ResponseEntity<Object> deleteFavorite(String userId, String bookId){
        bookService.deleteFavorite(userId, bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/browse/history")
    public ResponseEntity<PageResult<Book>> queryBrowseHistory(BookQueryCriteria param) {
        return new ResponseEntity<>(bookService.listBrowseHistory(param), HttpStatus.OK);
    }

    @GetMapping("/browse/add")
    public ResponseEntity<Object> addBrowseRecord(String userId, String bookId){
        bookService.addBrowseRecord(userId, bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list/contents")
    public ResponseEntity<List<Contents>> listContents(String bookId) {
        return new ResponseEntity<>(contentsService.getContents(bookId), HttpStatus.OK);
    }

    @PostMapping("/add/contents")
    public ResponseEntity<Object> addContents(@RequestBody Contents contents){
        contents.setId(UUID.randomUUID().toString());
        contentsService.addContents(contents);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update/contents")
    public ResponseEntity<Object> updateContents(@RequestBody Contents contents){
        contentsService.updateContents(contents);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/delete/contents")
    public ResponseEntity<Object> deleteContents(String contentsId){
        contentsService.deleteContents(contentsId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/borrow")
    public ResponseEntity<Object> borrow(String userId, String bookId){
        Date curDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        // 默认7天到期
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date endTime = calendar.getTime();

        bookService.borrow(userId, bookId, endTime);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/return")
    public ResponseEntity<Object> returnBorrow(String userId, String bookId){
        int row = bookService.updateBorrowHistory(userId, bookId);
        if (row == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (row > 1) {
            throw  new BadRequestException("Found multiple valid borrowing records.");
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/borrow/history")
    public ResponseEntity<Object> borrowHistory(String userId){
        return new ResponseEntity<>(bookService.listBorrowHistory(userId), HttpStatus.OK);
    }

    @GetMapping("/borrow/hasDueRecord")
    public ResponseEntity<Object> hasDueRecord(String userId){
        return new ResponseEntity<>(bookService.hasDueRecord(userId), HttpStatus.OK);
    }

    @AnonymousGetMapping("/detail")
    public ResponseEntity<BookVo> detail(String bookId){
        Book book = bookService.getBook(bookId);
        List<Contents> contents = contentsService.getContents(bookId);
        List<CommentDto> comments = commentService.getComments(bookId);
        BookVo bookVo = new BookVo(book, contents, comments, false);
        try {
            UserDetails currentUser = SecurityUtils.getCurrentUser();
            String username = currentUser.getUsername();
            String status = bookService.queryBorrowStatus(username, bookId);
            if(status == null || "1".equals(status)){
                bookVo.setHasBorrowed(false);
            } else {
                bookVo.setHasBorrowed(true);
            }
        } catch (Exception e) {
            bookVo.setHasBorrowed(false);
        }
        return new ResponseEntity<>(bookVo, HttpStatus.OK);
    }

    @PostMapping("/add/comment")
    public ResponseEntity<Object> addComment(@RequestBody CommentVo commentVo){
        // 封装评论数据
        Comment comment = commentVo.getComment();
        comment.setBookId(commentVo.getBookId());
        comment.setId(UUID.randomUUID().toString());
        commentService.addComment(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/collect/authors")
    public ResponseEntity<List<DictVo>> listAuthors(String userId){
        return new ResponseEntity<>(bookService.listAuthors(userId), HttpStatus.OK);
    }

    @GetMapping("/collect/categories")
    public ResponseEntity<List<DictVo>> listCategories(String userId){
        return new ResponseEntity<>(bookService.listCategories(userId), HttpStatus.OK);
    }
}
