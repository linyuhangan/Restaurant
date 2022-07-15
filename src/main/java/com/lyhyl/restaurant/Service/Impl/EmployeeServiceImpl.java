package com.lyhyl.restaurant.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyhyl.restaurant.Mapper.EmployeeMapper;
import com.lyhyl.restaurant.Service.EmployeeService;
import com.lyhyl.restaurant.entity.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl  extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
