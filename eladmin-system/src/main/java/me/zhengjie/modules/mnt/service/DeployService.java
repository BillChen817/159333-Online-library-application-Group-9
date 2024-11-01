
package me.zhengjie.modules.mnt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.zhengjie.modules.mnt.domain.Deploy;
import me.zhengjie.modules.mnt.domain.DeployHistory;
import me.zhengjie.modules.mnt.domain.vo.DeployQueryCriteria;
import me.zhengjie.utils.PageResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;


public interface DeployService extends IService<Deploy> {

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param page     分页参数
     * @return /
     */
    PageResult<Deploy> queryAll(DeployQueryCriteria criteria, Page<Object> page);

    /**
     * 查询全部数据
     * @param criteria 条件
     * @return /
     */
    List<Deploy> queryAll(DeployQueryCriteria criteria);

    /**
     * 创建
     * @param resources /
     */
    void create(Deploy resources);


    /**
     * 编辑
     * @param resources /
     */
    void update(Deploy resources);

    /**
     * 删除
     * @param ids /
     */
    void delete(Set<Long> ids);

	/**
	 * 部署文件到服务器
	 * @param fileSavePath 文件路径
	 * @param appId 应用ID
     */
	void deploy(String fileSavePath, Long appId);

    /**
     * 查询部署状态
     * @param resources /
     * @return /
     */
    String serverStatus(Deploy resources);
    /**
     * 启动服务
     * @param resources /
     * @return /
     */
    String startServer(Deploy resources);
    /**
     * 停止服务
     * @param resources /
     * @return /
     */
    String stopServer(Deploy resources);

    /**
     * 停止服务
     * @param resources /
     * @return /
     */
    String serverReduction(DeployHistory resources);

    /**
     * 导出数据
     * @param queryAll /
     * @param response /
     * @throws IOException /
     */
    void download(List<Deploy> queryAll, HttpServletResponse response) throws IOException;
}
