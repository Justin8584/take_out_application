package edu.northeastern.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.northeastern.reggie.entity.AddressBook;
import edu.northeastern.reggie.mapper.AddressBookMapper;
import edu.northeastern.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class  AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
