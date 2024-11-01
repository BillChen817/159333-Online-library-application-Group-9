
package me.zhengjie.modules.mnt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import me.zhengjie.modules.mnt.domain.App;
import me.zhengjie.modules.mnt.domain.vo.AppQueryCriteria;
import me.zhengjie.utils.PageResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface AppService extends IService<App> {

    /**
     * 分页查询
     * @param criteria 条件
     * @param page 分页参数
     * @return /
     */
    PageResult<App> queryAll(AppQueryCriteria criteria, Page<Object> page);

    /**
     * 查询全部数据
     *
     * @param criteria 条件
     * @return /
     */
    List<App> queryAll(AppQueryCriteria criteria);

    /**
     * 创建
     * @param resources /
     */
    void create(App resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(App resources);

    /**
     * 删除
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 导出数据
     * @param apps /
     * @param response /
     * @throws IOException /
     */
    void download(List<App> apps, HttpServletResponse response) throws IOException;
}
