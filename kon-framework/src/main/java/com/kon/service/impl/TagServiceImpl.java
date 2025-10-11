package com.kon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kon.domain.entity.Tag;
import com.kon.mapper.TagMapper;
import com.kon.service.TagService;
import org.springframework.stereotype.Service;

/**
 * @author 35238
 * @date 2023/8/2 0002 21:15
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}