package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/9/20 15:10
 * @Author: Zhaoyang Chen
 */
@Data
@TableName("r_recommend")
public class Recommend {
    @Id
    private String id;
    private String userId;
    private String bookId;
}
