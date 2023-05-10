package com.charsmart.data.cases.service.impl;

import com.charsmart.data.cases.entity.Name;
import com.charsmart.data.cases.mapper.NameMapper;
import com.charsmart.data.cases.service.NameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wonder@charsmart.com
 * @since 2022-01-31
 */
@Service
public class NameServiceImpl extends ServiceImpl<NameMapper, Name> implements NameService {

}
