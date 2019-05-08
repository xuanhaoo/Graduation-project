package com.abc.webservice;

import com.abc.service.ContentService;
import com.abc.service.ViewSpotInfoService;
import com.abc.vo.IndexPugVo;
import com.abc.vo.IndexViewSpotVo;
import com.abc.vo.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年05月04 11:06
 */
@RestController
@RequestMapping("webservice/indexWebservice")
public class IndexWebService {
    private static final Logger log = LoggerFactory.getLogger(IndexWebService.class);

    @Autowired
    private ContentService contentService;

    @Autowired
    private ViewSpotInfoService viewSpotInfoService;


    /**
     * 推荐首页足迹内容
     * @return
     */
    @RequestMapping("/getIndexRecommendPug")
    public Json getIndexRecommendPug() {
        List<IndexPugVo> list = contentService.recommendIndexPug();
        return Json.succ().data("recommendList", list);

    }

    /**
     * 推荐首页景点内容
     * @return
     */
    @RequestMapping("/getIndexRecommendView")
    public Json getIndexRecommendView() {
        List<IndexViewSpotVo> list = viewSpotInfoService.indexRecommendList();
        return Json.succ().data("recommendViewList", list);
    }

}
