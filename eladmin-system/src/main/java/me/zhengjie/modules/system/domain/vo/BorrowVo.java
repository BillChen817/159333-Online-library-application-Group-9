package me.zhengjie.modules.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/10/10 15:19
 * @Author: Zhaoyang Chen
 */
@Data
@AllArgsConstructor
public class BorrowVo {
    private String bookId;
    private String bookName;
    private Date dueTime;
    private String overStatus;
    private String loanStatus;
}
