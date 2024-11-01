package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Contents;

import java.util.List;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/18 16:32
 * @Author: Zhaoyang Chen
 */
public interface ContentsService {
    List<Contents> getContents(String bookId);

    void addContents(Contents contents);

    void updateContents(Contents contents);
    void deleteContents(String contentsId);
}
