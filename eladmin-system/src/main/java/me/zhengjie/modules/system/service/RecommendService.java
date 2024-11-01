package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Book;

import java.util.List;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/20 14:27
 * @Author: Zhaoyang Chen
 */
public interface RecommendService {
    void recommend(String userId);
    List<Book> listBooks(String userId);
    List<Book> randomBooks();
}
