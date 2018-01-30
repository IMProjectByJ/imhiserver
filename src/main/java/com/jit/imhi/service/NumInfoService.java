package com.jit.imhi.service;

import com.jit.imhi.mapper.NuminfoMapper;
import com.jit.imhi.model.Numinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class NumInfoService {
    NuminfoMapper numinfoMapper;

    public NumInfoService() {
    }
    @Autowired

    public NumInfoService( NuminfoMapper numinfoMapper) {
        this.numinfoMapper = numinfoMapper;
    }

    public Numinfo selectByPrimaryKey(Integer userId, Integer friendId,String friendType){
        return  numinfoMapper.selectByPrimaryKey(userId,friendId,friendType);
    }

    public String SelectNumOne(Numinfo numinfo){
        return  numinfoMapper.selectNumOne(numinfo);
    }

    public String SelectNumTwo(Numinfo numinfo){
        return  numinfoMapper.selectNumTwo(numinfo);
    }

    public int delNumInfo(Integer userId,  Integer friendId,  String friendType) {
        return numinfoMapper.deleteByPrimaryKey(userId, friendId, friendType);
    }


}
