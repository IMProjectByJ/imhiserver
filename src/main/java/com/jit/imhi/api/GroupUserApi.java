package com.jit.imhi.api;


import com.jit.imhi.model.Groupchat;
import com.jit.imhi.model.Groupuser;
import com.jit.imhi.service.GroupOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/groupuser")

public class GroupUserApi {
    GroupOperateService groupOperateService ;
    @Autowired
    public  GroupUserApi(GroupOperateService groupOperateService) {

        this.groupOperateService = groupOperateService;

    }

    @GetMapping("findGroupList/{memberId}")
    public Object findGrouplist(@PathVariable Integer memberId){
        List<Groupchat> list = new ArrayList<>();
        list = groupOperateService.findgroupList(memberId);
        return  list;
    }
}
