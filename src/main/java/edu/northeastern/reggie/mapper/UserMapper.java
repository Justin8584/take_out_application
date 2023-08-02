package edu.northeastern.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.northeastern.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
