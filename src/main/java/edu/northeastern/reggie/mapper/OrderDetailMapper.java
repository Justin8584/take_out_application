package edu.northeastern.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.northeastern.reggie.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
