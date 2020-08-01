package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.dto.CouponDTO;
import io.github.talelin.latticy.model.CouponDO;

import java.util.List;

public interface CouponService extends IService<CouponDO> {

    void create(CouponDTO dto);

    void delete(Integer id);

    List<CouponDO> getListByActivityId(Integer id);
}
