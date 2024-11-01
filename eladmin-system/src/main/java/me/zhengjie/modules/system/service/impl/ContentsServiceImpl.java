package me.zhengjie.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.Book;
import me.zhengjie.modules.system.domain.Contents;
import me.zhengjie.modules.system.mapper.ContentsMapper;
import me.zhengjie.modules.system.service.ContentsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/18 16:32
 * @Author: ZHaoyang Chen
 * @Desc: 书籍目录
 */
@Service
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {
    private final ContentsMapper contentsMapper;

    @Override
    public List<Contents> getContents(String bookId) {
        return contentsMapper.listContents(bookId);
    }

    @Override
    public void addContents(Contents contents) {
        contents.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        contentsMapper.insert(contents);
    }

    @Override
    public void updateContents(Contents contents) {
        contentsMapper.updateById(contents);
    }

    @Override
    public void deleteContents(String contentsId) {
        contentsMapper.deleteById(contentsId);
    }
}
