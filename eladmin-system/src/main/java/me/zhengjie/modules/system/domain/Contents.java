package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/18 9:12
 * @Author: Zhaoyang Chen
 * @Desc: 书籍目录
 */
@Data
@TableName("r_contents")
public class Contents {
    @Id
    private String id;
    private String bookId;
    private String chapterTitle;
    private int page;
}
