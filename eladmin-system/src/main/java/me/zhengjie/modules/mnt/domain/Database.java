
package me.zhengjie.modules.mnt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;
import java.io.Serializable;


@Getter
@Setter
@TableName("mnt_database")
public class Database extends BaseEntity implements Serializable {

    @TableId(value = "db_id", type = IdType.AUTO)
	@ApiModelProperty(value = "ID", hidden = true)
    private String id;

	@ApiModelProperty(value = "数据库名称")
    private String name;

	@ApiModelProperty(value = "数据库连接地址")
    private String jdbcUrl;

	@ApiModelProperty(value = "数据库密码")
    private String pwd;

	@ApiModelProperty(value = "用户名")
    private String userName;

    public void copy(Database source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
