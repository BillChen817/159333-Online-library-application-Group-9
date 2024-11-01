package me.zhengjie.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.zhengjie.modules.system.domain.BorrowHistory;
import me.zhengjie.modules.system.domain.vo.BorrowVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @BelongsProject: reader-admin
 * @CreateTime: 2024/10/8 17:46
 * @Author: Zhaoyang Chen
 */
@Mapper
public interface BorrowMapper extends BaseMapper<BorrowHistory> {
    String queryBorrowStatus(String username, String bookId);

    int updateByUser(String userId, String bookId);

    List<BorrowVo> selectVo(String userId);

    List<BorrowHistory> listDueRecord(String userId);
}
