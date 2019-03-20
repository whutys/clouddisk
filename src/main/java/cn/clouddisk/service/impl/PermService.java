package cn.clouddisk.service.impl;

import cn.clouddisk.mapper.PermMapper;
import cn.clouddisk.service.IPermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermService implements IPermService {
    @Autowired
    private PermMapper permMapper;
    @Override
    public List<String> selectPermsByUserId(int userId) {
        List<String> perms = permMapper.selectPermByUserId(userId);
//        List<String> permsList = new ArrayList<>();
//        for (Perm perm : perms) {
//            permsList.add(perm.getPermKey());
//        }
        return perms;
    }
}
