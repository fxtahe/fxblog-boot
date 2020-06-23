package com.fxtahe.fxblog.util;

/**
 * @author fxtahe
 * @program fxblog-boot
 * @description 常用枚举
 * @create 2020-04-15
 */
public class Const {


    /*
    文章状态 1.草稿 2.发布 3.已删除
     */
    public final static Integer ARTICLE_DRAFT = 1;
    public final static Integer ARTICLE_POSTED = 2;
    public final static Integer ARTICLE_DELETED = 3;


    /*
     类型： tag:标签 category:分类
     */
    public final static String TAG_TYPE = "tag";
    public final static String CATEGORY_TYPE = "category";

    /*
     Token Header
     */
    public final static String TOKEN_HEADER = "Authorization";
    public final static String TOKEN_PREFIX = "Bearer ";
    /*
    Token Type access:访问Token refresh:刷新
     */
    public final static String ACCESS_TOKEN = "access_token";
    public final static String REFRESH_TOKEN = "refresh_token";


    /*
     new authentication url pattern
     */
    public final static String ADMIN_AUTH_URL = "/**/admin/**";
    public final static String AUTHOR_AUTH_URL = "/**/author/**";

    /*
     Authentication: Admin Author
     */
    public final static String ADMIN_AUTH = "admin";
    public final static String AUTHOR_AUTH = "author";

    /*
      AuthorParameter
     */
    public final static String PARAM_ID = "id";

    public final static String PARAM_NAME = "name";

    public final static String PARAM_AUTHOR = "author";

}
