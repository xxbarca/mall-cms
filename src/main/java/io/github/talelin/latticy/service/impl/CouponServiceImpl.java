package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.common.enumeration.CouponTypeEnum;
import io.github.talelin.latticy.dto.CouponDTO;
import io.github.talelin.latticy.dto.CouponTemplateDTO;
import io.github.talelin.latticy.mapper.CouponMapper;
import io.github.talelin.latticy.mapper.CouponTemplateMapper;
import io.github.talelin.latticy.model.CouponDO;
import io.github.talelin.latticy.model.CouponTemplateDO;
import io.github.talelin.latticy.service.CouponService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponDO> implements CouponService {
    @Autowired
    private CouponTemplateMapper couponTemplateMapper;

    @Override
    public void create(CouponDTO dto) {
        boolean ok = checkCouponType(dto);
        if (!ok) {
            throw new ParameterException(100001);
        }
        CouponDO couponDO = new CouponDO();
        BeanUtils.copyProperties(dto, couponDO);
        this.getBaseMapper().insert(couponDO);
    }

    @Override
    public void delete(Integer id) {
        CouponDO couponDO = this.getBaseMapper().selectById(id);
        if (couponDO == null) {
            throw new NotFoundException(100000);
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public List<CouponDO> getListByActivityId(Integer id) {
        QueryWrapper<CouponDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CouponDO::getActivityId, id);
        return this.getBaseMapper().selectList(wrapper);
    }

    public List<CouponTemplateDO> getTemplates() {
        return couponTemplateMapper.selectList(null);
    }

    @Override
    public void update(CouponDTO dto, Integer id) {
        CouponDO coupon = this.getById(id);
        if (coupon == null) {
            throw new NotFoundException(100000);
        }
        boolean ok = checkCouponType(dto);
        if (!ok) {
            throw new ParameterException(100001);
        }
        BeanUtils.copyProperties(dto, coupon);
        this.updateById(coupon);

    }

    /**
     * 校验优惠卷数据是否满足优惠卷类型
     */
    private boolean checkCouponType(CouponTemplateDTO dto) {
        if (dto.getType() == CouponTypeEnum.FULL_MONEY_CUT.getValue()) {
            return (dto.getFullMoney() != null && dto.getMinus() != null);
        } else if (dto.getType() == CouponTypeEnum.DISCOUNT.getValue()) {
            return dto.getRate() != null;
//            return dto.getDiscount() != null;
        } else if (dto.getType() == CouponTypeEnum.ALL.getValue()) {
            return true;
        } else if (dto.getType() == CouponTypeEnum.FULL_MONEY_DISCOUNT.getValue()) {
            return (dto.getFullMoney() != null && dto.getDiscount() != null);
        } else {
            return false;
        }
    }
}
