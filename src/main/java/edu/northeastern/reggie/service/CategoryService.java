package edu.northeastern.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.northeastern.reggie.entity.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
