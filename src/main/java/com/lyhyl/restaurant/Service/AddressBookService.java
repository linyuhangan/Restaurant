package com.lyhyl.restaurant.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyhyl.restaurant.entity.AddressBook;

import java.util.List;


public interface AddressBookService extends IService<AddressBook> {
//查询地址
    List<AddressBook> selectAdderss(AddressBook addressBook);

    AddressBook updateDefault(AddressBook addressBook,Long id);
}
