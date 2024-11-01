package me.zhengjie.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: eladmin
 * @CreateTime: 2024/8/21 8:42
 * @Author: Zhaoyang Chen
 * @Description: 浏览记录
 */
@Getter
@Setter
@TableName("r_browser_records")
public class BrowseRecord extends BaseEntity implements Serializable {
    @Id
    private String id;
    private String userId;
    private String bookId;
    private Date browseTime;
}
