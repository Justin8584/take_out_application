package edu.northeastern.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.northeastern.reggie.common.R;
import edu.northeastern.reggie.entity.Employee;
import edu.northeastern.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    /*
    @Autowired
    private EmployeeService employeeService;
    */
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * employee login
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        /*
        1. Encrypt the password from the webpage
         */
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        /*
        2. check in database, according to username
         */
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);  // getOne(), for username 唯一约束 Unique

        /*
        3. return login fail, if cannot find the username
         */
        if (emp == null) {
            return R.error("Login failed, cannot find Username in database. ");
        }

        /*
        4. compare the password, return fail if password is wrong
         */
        if (!emp.getPassword().equals(password)) {
            return R.error("Wrong password, try to enter again. ");
        }

        /*
        5. check status of employee, forbidden or not? 0==forbid, 1==active
         */
        if (emp.getStatus() == 0) {
            return R.error("Login failed, account has been deactivated. ");
        }

        /*
        6. successfully login, save employee ID into Session
         */
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * employee logout
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {

        /*
        1. clear the current login employee, saved in session
         */
        request.getSession().removeAttribute("employee");
        return R.success("Successfully Logout");  // success, code=1
    }

    /**
     * add new employee
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
//        log.info("Add new employee, employee info, {} ... !!!", employee.toString());

        /*
         * set the default password is 123456, with md5 encrypt
         */
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        /*
         * get the current login employee ID
         * No need anymore, common data from MetaObjectHandler
         */
//        Long empID = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empID);
//        employee.setUpdateUser(empID);

        employeeService.save(employee);

        return R.success("Successfully Add New Employee! ");
    }

    /**
     * employee info search function
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
//        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);

        // constructor for page info
        Page pageInfo = new Page(page, pageSize);
        // constructor with conditions
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        // conditions
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        // sort by update time
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        // make the search
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * change employee info, by ID
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());

        /*
         * get the current login employee ID, for in used updating method
         * No need anymore, common data from MetaObjectHandler
         */
//        Long empId = (Long) request.getSession().getAttribute("employee");  // current login user, make the changes and save change time
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empId);

        employeeService.updateById(employee);
        return R.success("Successfully Edit employee INFO! ");
    }

    /**
     * searching employee INFO by ID
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("Searching employee INFO by ID ... !!!");

        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        } else {
            return R.error("Employee Not Found! ");
        }

    }
}
