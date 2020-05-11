package com.fxtahe.fxblog.service.Impl;

import com.fxtahe.fxblog.entity.Author;
import com.fxtahe.fxblog.mapper.AuthorMapper;
import com.fxtahe.fxblog.service.AuthorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fxtahe
 * @since 2020-05-10
 */
@Service
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements AuthorService {

}