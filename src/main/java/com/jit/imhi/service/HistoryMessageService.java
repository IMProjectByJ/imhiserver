package com.jit.imhi.service;

import com.jit.imhi.mapper.HistoryMessageMapper;
import com.jit.imhi.model.HistoryMessage;
import com.jit.imhi.model.Numinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class HistoryMessageService {
    private HistoryMessageMapper historyMessageMapper;

    public HistoryMessageService () {
    }
    @Autowired

    public  HistoryMessageService(HistoryMessageMapper historyMessageMapper){
        this.historyMessageMapper = historyMessageMapper;
    }
    public int SelectTheNum(Numinfo numinfo){
      return   historyMessageMapper.selectTheNum(numinfo);
    }
    public  int selectNoticNum(Numinfo numinfo){
        return  historyMessageMapper.selectNoticNum(numinfo);
    }
    public List<HistoryMessage>   selectNotic(Numinfo numinfo){return historyMessageMapper.selectNotic(numinfo); }

}
