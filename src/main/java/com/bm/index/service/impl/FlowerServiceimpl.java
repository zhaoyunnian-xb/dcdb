package com.bm.index.service.impl;

import com.bm.index.dao.source1.FlowerDao;
import com.bm.index.model.DcdbDjlbYbParam;
import com.bm.index.model.Flow;
import com.bm.index.service.FlowerService;
import com.bm.index.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlowerServiceimpl implements FlowerService {
    @Autowired
    private FlowerDao f;
    @Override
    public Map<String,Object> getFlower(String ywtype,String id,String preid){
        Map<String,Object> map=new HashMap<String,Object>();
        List<Flow> list= f.selectFlower(ywtype,id,preid);
        map.put("list",list);
        map.put("code","202");
        map.put("msg","");
        return map;
    }
    @Override
    public List<Flow> getFlowList(String ywtype,String id,String preid){
        if (id.contains(",")){
            id = id.split(",")[0];
        }
        List<Flow> list= f.selectFlower(ywtype,id,preid);//查找到数据
        boolean b = list.stream().anyMatch((flow -> "01021".equals(flow.getNodeId())));
        if(b){
            String wh = list.get(0).getWh();
            //查询分支id
            List<String> idList = f.selectIdByWh(wh);
            List<Flow> children = new ArrayList<>();
            Flow flow1 = new Flow();
            List<Flow> listChild = new ArrayList<>();
            for (String s : idList){
                flow1 = new Flow();
                children = new ArrayList<>();
                //根据id查询出所有的分支数据
                List<DcdbDjlbYbParam> flows = f.selectById(s);
                for (DcdbDjlbYbParam f : flows){
                    Flow flow = new Flow();
                    flow.setNodeName(f.getNodeName());
                    flow.setApproveTime(f.getCjrq());
                    flow.setUserName(f.getUsername());
                    flow.setCbbmmc(f.getCbbmmc());
                    children.add(flow);
                    flow1.setNodeName(f.getCbbmmc());
                    flow1.setUserName("");
                    flow1.setApproveTime("");
                    flow1.setId(UUIDUtils.getUUID());
                    flow1.setNodeId("");
                    flow1.setUserId("");
                    flow1.setIstf("");
                    flow1.setWh(f.getWh());
                }
                flow1.setChildren(children);
                listChild.add(flow1);
            }
            List<Flow> lists = new ArrayList<>();
            for (Flow f:list){
                if("0103".equals(f.getNodeId())||"0104".equals(f.getNodeId())||"0105".equals(f.getNodeId())||"0106".equals(f.getNodeId())
                        ||"01061".equals(f.getNodeId())||"8888".equals(f.getNodeId())){
                    lists.add(f);
                }
                if ("01021".equals(f.getNodeId())){
                    f.setChildren(listChild);
                }
            }
            list.removeAll(lists);
            return list;
        }else {
            return list;
        }

    }
}
