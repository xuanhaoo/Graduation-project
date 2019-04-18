package com.abc.controller;

import com.abc.entity.ArtSubject;
import com.abc.entity.Customer;
import com.abc.entity.SysRole;
import com.abc.service.ContentService;
import com.abc.service.SubjectService;
import com.abc.service.ViewSpotInfoService;
import com.abc.vo.Json;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description: 主题管理控制器
 * @Date: 2019年03月01 15:04
 */
@RestController
@RequestMapping("/sys/subject")
public class SubjectController {


    private static final Logger log = LoggerFactory.getLogger(SubjectController.class);


    @Autowired
    private SubjectService subjectService;


    /**
     * 基本查询
     * @param body
     * @return
     */
    @PostMapping("/query")
    public Json query(@RequestBody String body) {

        String oper = "query subject";
        log.info("{}, body: {}", oper, body);

        JSONObject json = JSON.parseObject(body);
        // 名称模糊查询
        String subName = json.getString("name");
        int current = json.getIntValue("current");
        int size = json.getIntValue("size");
        if (current == 0) current = 1;
        if (size == 0) size = 10;

        Wrapper<ArtSubject> queryParams = new EntityWrapper<>();
        queryParams.orderBy("createDate", true);
        if (StringUtils.isNotBlank(subName)) {
            queryParams.like("name", subName);
        }
        Page<ArtSubject> page = subjectService.selectPage(new Page<>(current, size), queryParams);
        return Json.succ(oper).data("page", page);
    }

    /**
     * 添加
     * @param body
     * @return
     */
    @PostMapping
    public Json add(@RequestBody String body) {
        String oper = "add subject";
        ArtSubject artSubject = JSON.parseObject(body, ArtSubject.class);

        if (StringUtils.isBlank(artSubject.getName())) {
            return Json.fail(oper, "主题名不能为空");
        }

        ArtSubject artSubjectDB = subjectService.selectOne(new EntityWrapper<ArtSubject>().eq("name", artSubject.getName()));
        if (artSubjectDB != null) {
            return Json.fail(oper, "主题名已经存在：" +  artSubject.getName());
        }

        //保存新用户数据
        artSubject.setCreateDate(new Date());
        boolean success = subjectService.insert(artSubject);
        return Json.result(oper, success)
                .data("id", artSubject.getId())
                .data("created", artSubject.getCreateDate());
    }


    /**
     * 基本更新
     * @param body
     * @return
     */
    @PatchMapping
    public Json update(@RequestBody String body) {
        String oper = "update subject";
        log.info("{}, body: {}", oper, body);

        ArtSubject artSubject = JSON.parseObject(body, ArtSubject.class);
        if (null == artSubject.getId()) {
            return Json.fail(oper, "无法更新主题：参数为空（id）");
        }
        artSubject.setUpdateDate(new Date());
        boolean success = subjectService.updateById(artSubject);
        return Json.result(oper, success).data("updated", artSubject.getUpdateDate());
    }


    /**
     * 基本删除
     * @param body
     * @return
     */
    @DeleteMapping
    public Json delete(@RequestBody String body) {
        String oper = "delete subject";
        log.info("{}, body: {}", oper, body);

        JSONObject jsonObj = JSON.parseObject(body);
        String id = jsonObj.getString("id");

        if (StringUtils.isBlank(id)) {
            return Json.fail(oper, "无法删除主题：参数为空（id）");
        }

        boolean success = subjectService.deleteById(Integer.parseInt(id));
        return Json.result(oper, success);
    }

    /**
     * 查询所有的主题项
     * @return
     */
    @GetMapping("/queryAllNoPage")
    public Json queryAllNoPage() {

        Wrapper<ArtSubject> queryParams = new EntityWrapper<>();
        List<ArtSubject> subjectList =  subjectService.selectList(queryParams);
        return Json.succ().data(subjectList);
    }
}
