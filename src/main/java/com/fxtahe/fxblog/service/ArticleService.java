package com.fxtahe.fxblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fxtahe.fxblog.entity.Article;
import com.fxtahe.fxblog.vo.ArticleVo;
import com.fxtahe.fxblog.vo.PageRequest;
import com.fxtahe.fxblog.vo.PageResponse;

import java.util.List;

/**
 * <p>
 *  文章服务接口
 * </p>
 *
 * @author fxtahe
 * @since 2020-04-15
 */
public interface ArticleService extends IService<Article> {

    /**
     * 新建文章
     * @param articleVo
     */
    void saveArticleVo(ArticleVo articleVo);

    /**
     * 查询推荐文章
     * @return List<ArticleVo>
     */
    List<ArticleVo> getFeatureArticle();

    /**
     *
     * @param pageRequest 查询条件
     * @return IPage<ArticleVo>
     */
    PageResponse<ArticleVo> getArticleVoPage(PageRequest<ArticleVo> pageRequest);

    /**
     * 删除文章及关联数据
     * @param id 文章主键
     */
    void deleteArticle(Integer id);

    /**
     * 更新文章及关联数据
     * @param articleVo 文章数据
     */
    void updateArticleVo(ArticleVo articleVo);


}
