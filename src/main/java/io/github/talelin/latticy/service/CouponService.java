package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.dto.CouponDTO;
import io.github.talelin.latticy.model.CouponDO;

public interface CouponService extends IService<CouponDO> {

    void create(CouponDTO dto);
}