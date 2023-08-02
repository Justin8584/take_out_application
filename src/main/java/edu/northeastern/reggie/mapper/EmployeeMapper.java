package edu.northeastern.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.northeastern.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
