package com.sun.service.impl;

import com.sun.service.SidebarService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author sunshine
 * @date 2023/5/12 上午11:25
 */
public class SidebarServiceImpl implements SidebarService {
    /**
     * cacheManager: 注册的缓存管理器
     * cacheNames: 缓存前缀
     * key: SpEL表达式，基于此方法生成对应的缓存key,若是常量字符串，用单引号包裹
     * @return
     */
    @Override
    @Cacheable(key = "'homeSidebar'", cacheManager = "caffeineCacheManager", cacheNames = "home")
    public List<String> queryHomeSidebarList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("AAAA");
        list.add("BBBB");
        list.add("CCCC");
        return list;
    }

    @Override
    @Cacheable(key = "'sideBar_' + #articleId", cacheManager = "caffeineCacheManager", cacheNames = "article")
    public List<String> queryArticleDetailSidebarList(Long author, Long articleId) {
        ArrayList<String> list = new ArrayList<>();
        list.add("AAAA");
        list.add("BBBB");
        list.add("DDDD");
        return list;
    }

    /**
     * 失效缓存注解 CacheEvict
     * @param articleId
     */
    @Override
    @CacheEvict(key = "'sideBar_' + #articleId", cacheManager = "caffeineCacheManager", cacheNames = "article")
    public void updateArticle(Long articleId) {


    }

    /**
     * CachePut: 方法执行完毕之后，主动将对应的结果写入缓存
     * Caching: 可以组合多个缓存注解使用
     * @param age
     * @return
     */

    @Caching(cacheable = @Cacheable(cacheNames = "caching", key = "#age"),
       evict = @CacheEvict(cacheNames = "t4", key = "#age"))
    public String cahing(int age) {
        return "cahing: " + age + "-->" + UUID.randomUUID().toString();
    }

}
