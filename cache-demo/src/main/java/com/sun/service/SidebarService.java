package com.sun.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunshine
 * @date 2023/5/12 上午11:22
 */
public interface SidebarService {


    public List<String> queryHomeSidebarList();

    public List<String> queryArticleDetailSidebarList(Long author, Long articleId);

    public void updateArticle(Long articleId);

}
