package com.lyhyl.restaurant.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyhyl.restaurant.Mapper.AddressBookMapper;
import com.lyhyl.restaurant.Service.AddressBookService;
import com.lyhyl.restaurant.common.BaseContext;
import com.lyhyl.restaurant.entity.AddressBook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
    /**
     * 查询地址
     * @param addressBook
     * @return
     */
    @Override
    public List<AddressBook> selectAdderss(AddressBook addressBook) {
        LambdaQueryWrapper<AddressBook> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(addressBook.getUserId() != null,AddressBook::getUserId,addressBook.getUserId());
        lambdaQueryWrapper.orderByAsc(AddressBook::getUpdateTime);
        List<AddressBook> list = this.list(lambdaQueryWrapper);
        return list;
    }

    /**
     * 修改默认地址
     * @param addressBook
     * @return
     */
    @Override
    public AddressBook updateDefault(AddressBook addressBook,Long id) {
        LambdaUpdateWrapper<AddressBook> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        System.out.println(BaseContext.getCurrentId());
        lambdaUpdateWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        lambdaUpdateWrapper.set(AddressBook::getIsDefault,0);
        this.update(lambdaUpdateWrapper);

        addressBook.setIsDefault(1);
        this.updateById(addressBook);
        return addressBook;
    }

}
