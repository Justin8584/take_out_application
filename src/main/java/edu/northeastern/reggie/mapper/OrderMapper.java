package edu.northeastern.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.northeastern.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
