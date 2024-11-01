
package me.zhengjie.modules.mnt.domain;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;



@Getter
@Setter
@TableName("mnt_deploy")
public class Deploy extends BaseEntity implements Serializable {

	@TableId(value = "deploy_id", type = IdType.AUTO)
	@ApiModelProperty(value = "ID", hidden = true)
    private Long id;

	@ApiModelProperty(value = "应用编号")
	private Long appId;

	@TableField(exist = false)
	@ApiModelProperty(name = "服务器", hidden = true)
	private Set<Server> deploys;

	@TableField(exist = false)
    private App app;

    public void copy(Deploy source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

	public String getServers() {
		if(CollectionUtil.isNotEmpty(deploys)){
			return deploys.stream().map(Server::getName).collect(Collectors.joining(","));
		}
		return "";
	}
}
