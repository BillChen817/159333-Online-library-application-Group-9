
package me.zhengjie.modules.quartz.domain.vo;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;


@Data
public class QuartzJobQueryCriteria {

    private String jobName;

    private Boolean isSuccess;

    private List<Timestamp> createTime;
}
