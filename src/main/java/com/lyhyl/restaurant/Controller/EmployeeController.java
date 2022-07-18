package com.lyhyl.restaurant.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyhyl.restaurant.Service.EmployeeService;
import com.lyhyl.restaurant.common.R;
import com.lyhyl.restaurant.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
 /*
 * RequestURL:http://localhost:8080/employee/login
 * login.js
 * 所以RequestMapping设置 /employee
 *
 */
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
@PostMapping("/login")
 public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

    //1、将页面提交的密码password进行md5加密处理
     String password = employee.getPassword();
     password = DigestUtils.md5DigestAsHex(password.getBytes());

   //2、根据页面提交的用户名username查询数据库
     LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<Employee>();
     queryWrapper.eq(Employee::getUsername,employee.getUsername());
     Employee emp = employeeService.getOne(queryWrapper);

     //3、如果没有查询到则返回登录失败结果
     if (emp==null){
        return  R.error("用户名不存在");
     }
     //4、密码比对，如果不一致则返回登录失败结果
     if (!emp.getPassword().equals(password)){
         return R.error("密码错误");
     }
     //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
     if (emp.getStatus() == 0){
         return R.error("账号被禁用");
     }
     // 6、登录成功，将员工id存入Session并返回登录成功结果
     request.getSession().setAttribute("employee",emp.getId());

     return R.success(emp);
}
   @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
    
        request.getSession().removeAttribute("employee");

    return R.success("成功退出登录");
    }

    /**
     * 添加员工
     * @param request
     * @param employee
     * @return
     *
     * 返回json用@requestbody接收
     */
    @PostMapping
    public R<String> adduser(HttpServletRequest request,@RequestBody Employee employee){
        log.info("员工信息",employee.toString());

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //创建时间
        employee.setCreateTime(LocalDateTime.now());
        //修改时间
        employee.setUpdateTime(LocalDateTime.now());

        Long empID =(Long) request.getSession().getAttribute("employee");

        employee.setCreateUser(empID);

        employee.setUpdateUser(empID);

       employeeService.save(employee);


        return R.success("添加成功");
    }

    /**
     * 员工信息查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize, String name){
        //分页构造器
        Page page1 = new Page(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //过滤条件
        lambdaQueryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);

        //排序条件
         lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(page1,lambdaQueryWrapper);

        return R.success(page1);
    }
}
