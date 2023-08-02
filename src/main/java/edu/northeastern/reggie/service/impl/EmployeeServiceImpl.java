package edu.northeastern.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.northeastern.reggie.entity.Employee;
import edu.northeastern.reggie.mapper.EmployeeMapper;
import edu.northeastern.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
