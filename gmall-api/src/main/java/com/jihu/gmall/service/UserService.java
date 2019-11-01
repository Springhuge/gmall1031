package com.jihu.gmall.service;

import com.jihu.gmall.bean.UmsMember;
import com.jihu.gmall.bean.UmsMemberReceiveAddress;
import java.util.List;

public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);
}
