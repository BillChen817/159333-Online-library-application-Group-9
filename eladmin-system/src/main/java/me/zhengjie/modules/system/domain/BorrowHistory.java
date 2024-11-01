package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/10/8 17:47
 * @Author: Zhaoyang Chen
 */
@Getter
@Setter
@TableName("r_borrow_history")
public class BorrowHistory {
    @Id
    private String id;
    private String userId;
    private String bookId;
    private Date endTime;
    private String status;
}
