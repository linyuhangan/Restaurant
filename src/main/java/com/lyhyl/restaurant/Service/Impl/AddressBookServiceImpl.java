package com.lyhyl.restaurant.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyhyl.restaurant.Mapper.AddressBookMapper;
import com.lyhyl.restaurant.Service.AddressBookService;
import com.lyhyl.restaurant.entity.AddressBook;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
