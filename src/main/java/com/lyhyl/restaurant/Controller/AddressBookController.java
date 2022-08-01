package com.lyhyl.restaurant.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lyhyl.restaurant.Service.AddressBookService;
import com.lyhyl.restaurant.common.BaseContext;
import com.lyhyl.restaurant.common.R;
import com.lyhyl.restaurant.entity.AddressBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/addressBook")
@RestController
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 添加收货地址
     * @param addressBook
     * @return
     */
    @PostMapping
    public R<String> add(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.save(addressBook);
        return R.success("地址添加成功");
    }

    /**
     *查询指定用户地址
     * @param addressBook
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.selectAdderss(addressBook);
        return R.success(list);
    }

    /**
     * 修改默认地址
     * @param addressBook
     * @return
     */
    @Transactional
    @PutMapping("/default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        log.info("address:{}",addressBook);
        Long id = BaseContext.getCurrentId();
        System.out.println("------------------"+id+"----------------------");
        AddressBook book = addressBookService.updateDefault(addressBook,id);
        return R.success(book);

    }


}
