package com.abc.controller;

import com.abc.entity.ArtComment;
import com.abc.service.CommentService;
import com.abc.util.PageUtils;
import com.abc.vo.CommentCountData;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月07 17:20
 */
@RestController
@RequestMapping("/sys/comment")
public class CommentController {

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);


    @Autowired
    private CommentService  commentService;



    @PostMapping("/queryCommentByContent")
    public Json queryCommentByContent(@RequestBody String body) {
        String oper = "query comment by content";

        JSONObject json = JSON.parseObject(body);
        int artId = json.getIntValue("artId");
        Page<ArtComment> page = commentService.queryContentComment(PageUtils.getPageParam(json), artId);
        return Json.succ().data("page", page);
    }

    /**
     * 删除评论
     * @param body
     * @return
     */
    @DeleteMapping
    public Json delete(@RequestBody String body) {
        String oper = "delete comment";
        log.info("{}, body: {}", oper, body);

        JSONObject jsonObj = JSON.parseObject(body);
        String id = jsonObj.getString("id");

        if (StringUtils.isBlank(id)) {
            return Json.fail(oper, "无法删除：参数为空（逻辑id）");
        }
        boolean success = commentService.deleteById(Integer.parseInt(id));
        return Json.result(oper, success);
    }

    /**
     * 评论分析
     * @return
     */
    @GetMapping("/commentAnalysis")
    public Json commentAnalysis() {
        String oper = "commentAnalysis";

        List<ArtComment> commentList = commentService.queryTheLastComment();
        CommentCountData commentCountData = commentService.packageData();
        return Json.succ()
                .data("commentList", commentList)
                .data("commentCountData", commentCountData);
    }


}
