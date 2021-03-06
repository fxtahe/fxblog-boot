package com.fxtahe.fxblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxtahe.fxblog.entity.ArchiveArticle;
import com.fxtahe.fxblog.entity.Article;
import com.fxtahe.fxblog.entity.Tag;
import com.fxtahe.fxblog.vo.ArticleVo;
import com.fxtahe.fxblog.vo.PageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fxtahe
 * @since 2020-04-15
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<ArticleVo> selectFeatureArticles(@Param("authorId") Integer id);

    List<ArticleVo> selectArticleVoPage(PageRequest<ArticleVo> articleVo);

    ArticleVo selectArticleVo(ArticleVo article);

    Long selectCountArticleVoPage(PageRequest<ArticleVo>  articleVo);

    List<Tag> selectRelationTags(Integer id);

    void likeArticle(@Param("id") Integer id,@Param("state") Integer state);

    void viewArticle(@Param("id") Integer id,@Param("state") Integer state);

    List<ArchiveArticle> selectArchiveArticle(@Param("authorId") Integer authorId, @Param("state") Integer state);
}
