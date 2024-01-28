package io.github.erictowns.infrastructure.dal.mapper.mysql.mysql01;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.erictowns.infrastructure.dal.po.UserInfoPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * desc: user mapper
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 22:49
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoPo> {

    /**
     * 批量插入
     *
     * @param data 数据
     * @return 插入数量
     */
    int insertIgnoreBatchSomeColumn(List<UserInfoPo> data);

}
