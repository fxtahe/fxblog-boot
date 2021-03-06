package com.fxtahe.fxblog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxtahe.fxblog.entity.*;
import com.fxtahe.fxblog.mapper.ArticleMapper;
import com.fxtahe.fxblog.service.ArticleService;
import com.fxtahe.fxblog.service.RelationshipService;
import com.fxtahe.fxblog.util.Const;
import com.fxtahe.fxblog.vo.ArticleVo;
import com.fxtahe.fxblog.vo.PageRequest;
import com.fxtahe.fxblog.vo.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fxtahe
 * @since 2020-04-15
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private RelationshipService relationshipService;

    @Override
    public void saveArticleVo(ArticleVo articleVo,Integer userId) {
        articleVo.setAuthorId(userId);
        LocalDateTime now = LocalDateTime.now();
        articleVo.setCreateDate(now);
        articleVo.setUpdateDate(now);
        articleVo.insert();
        saveOrUpdateRelation(articleVo,userId);
    }

    @Override
    public ArticleVo getArticleVo(ArticleVo article) {
        return baseMapper.selectArticleVo(article);
    }

    @Override
    public List<ArticleVo> getFeatureArticle(Integer userId) {
        return baseMapper.selectFeatureArticles(userId);
    }

    @Override
    public Map<Integer,List<ArchiveArticle>> getArchiveArticle(Integer userId,Integer state) {

        List<ArchiveArticle>  list = baseMapper.selectArchiveArticle(userId, state);

        LinkedHashMap<Integer,List<ArchiveArticle>> map = new LinkedHashMap<>(16);
        for(ArchiveArticle archiveArticle:list){
            List<ArchiveArticle> articles = map.get(archiveArticle.getYear());
            if(CollectionUtils.isEmpty(articles)){
                List<ArchiveArticle> archiveArticles = new LinkedList<>();
                archiveArticles.add(archiveArticle);
                map.put(archiveArticle.getYear(), archiveArticles);
            }else{
                articles.add(archiveArticle);
            }
        }
        return map;
    }

    @Override
    public PageResponse<ArticleVo> getArticleVoPage(PageRequest<ArticleVo> pageRequest) {
        //pageRequest.setData((ArticleVo) pageRequest.getData().setAuthorId(userId));
        Long current = pageRequest.getCurrent();
        Long size = pageRequest.getSize();
        if(current == null||current <=0){
            current = 1L;
            pageRequest.setCurrent(current);
        }
        if(size == null || size <=0){
            size = 10L;
            pageRequest.setSize(size);
        }

        pageRequest.setLimit((current-1)*size);
        List<ArticleVo> result =  baseMapper.selectArticleVoPage(pageRequest);
        Long total = baseMapper.selectCountArticleVoPage(pageRequest);
        return new PageResponse<ArticleVo>().setCurrent(current).setSize(size).setData(result).setTotal(total);
    }

    @Override
    public void deleteArticle(Integer id,Integer userId) {
        baseMapper.delete(new QueryWrapper<Article>().eq("id",id).eq(userId!=null,"author_id",userId));
        relationshipService.deleteByCondition(new Relationship().setArticleId(id));
    }

    @Override
    public void updateArticleVo(ArticleVo articleVo,Integer userId) {
        articleVo.setUpdateDate(LocalDateTime.now());
        articleVo.updateById();
        relationshipService.deleteByCondition(new Relationship().setArticleId(articleVo.getId()));
        saveOrUpdateRelation(articleVo,userId);
    }

    @Override
    public void likeArticle(Integer id) {
        baseMapper.likeArticle(id,Const.ARTICLE_POSTED);
    }

    @Override
    public void viewArticle(Integer id) {
        baseMapper.viewArticle(id,Const.ARTICLE_POSTED);
    }

    public void saveOrUpdateRelation(ArticleVo articleVo,Integer userId){
        Integer id = articleVo.getId();
        List<Tag> tags = articleVo.getTags();
        Relationship relationship = new Relationship();
        if (!CollectionUtils.isEmpty(tags)) {
            for (Tag tag : tags) {
                Integer tId = tag.getId();
                if (tId != null) {
                    relationship.setRId(tId).setArticleId(id)
                            .setRType(Const.TAG_TYPE).insert();
                } else {
                    tag.setAuthorId(userId);
                    tag.insert();
                    relationship.setRId(tag.getId()).setArticleId(id)
                            .setRType(Const.TAG_TYPE).insert();
                }
            }
        }
        Category category = articleVo.getCategory();
        if(category != null){
            if(category.getId() == null){
                category.setAuthorId(userId);
                category.insert();
            }
            relationship.setRId(category.getId()).setArticleId(id).setRType(Const.CATEGORY_TYPE);
            relationship.insert();
        }
    }


}
