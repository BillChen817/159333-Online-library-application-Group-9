package me.zhengjie.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.Book;
import me.zhengjie.modules.system.domain.Recommend;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.mapper.BookMapper;
import me.zhengjie.modules.system.mapper.RecommendMapper;
import me.zhengjie.modules.system.mapper.UserMapper;
import me.zhengjie.modules.system.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/20 14:28
 * @Author: Zhaoyang Chen
 */
@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {
    private static final Logger log = LoggerFactory.getLogger(RecommendServiceImpl.class);
    private final UserMapper userMapper;
    private final BookMapper bookMapper;
    private final RecommendMapper recommendMapper;
    // 推荐条数
    private final int recommendCount = 8;

    @Async
    @Override
    public void recommend(String userId) {
        String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        log.info("recommend started at:{}", startTime);
        User user = userMapper.selectById(userId);
        String favoriteGenres = user.getFavoriteGenres();
        List<String> bookIds = new ArrayList<>();
        if (favoriteGenres != null && !favoriteGenres.isEmpty()) {
            String[] genres = favoriteGenres.split(",");
            bookIds.addAll(bookMapper.listByCategory(userId, genres));
        }
        int remainCount = recommendCount - bookIds.size();
        if (remainCount > 0) {
            List<String> randomIds = bookMapper.randomSelect(userId, remainCount, bookIds);
            bookIds.addAll(randomIds);
        }
        if (!bookIds.isEmpty()) {
            recommendMapper.deleteByUserId(userId);
        }
        // 登记到推荐表
        for (String bookId : bookIds) {
            Recommend recommend = new Recommend();
            recommend.setId(UUID.randomUUID().toString());
            recommend.setUserId(userId);
            recommend.setBookId(bookId);
            recommendMapper.insert(recommend);
        }
        String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        log.info("recommend ended userId:{}", endTime);
    }

    @Override
    public List<Book> listBooks(String userId) {
        List<String> bookIds = recommendMapper.listBookIds(userId);
        if (bookIds != null && !bookIds.isEmpty()) {
            return bookMapper.listBookByIds(bookIds);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Book> randomBooks() {
        return bookMapper.randomReturn(recommendCount);
    }
}
