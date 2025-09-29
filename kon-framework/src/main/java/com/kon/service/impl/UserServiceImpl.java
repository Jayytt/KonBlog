package com.kon.service.impl;

import com.kon.domain.entity.User;
import com.kon.domain.vo.UserInfoVo;
import com.kon.mapper.UserMapper;
import com.kon.result.ResponseResult;
import com.kon.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kon.utils.BeanCopyUtils;
import com.kon.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 平泽唯
 * @since 2025-09-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public ResponseResult UserInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);

        if (user == null) {
            return ResponseResult.errorResult(404, "用户不存在");
        }

        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

}
