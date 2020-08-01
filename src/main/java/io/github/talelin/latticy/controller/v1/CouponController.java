package io.github.talelin.latticy.controller.v1;

import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.dto.CouponDTO;
import io.github.talelin.latticy.model.CouponDO;
import io.github.talelin.latticy.service.CouponService;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/coupon")
@Validated
@PermissionModule("优惠券")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("")
    @PermissionMeta("创建优惠券")
    @GroupRequired
    public CreatedVO create(@Validated @RequestBody CouponDTO couponDTO) {
        this.couponService.create(couponDTO);
        return new CreatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除优惠券")
    @GroupRequired
    public DeletedVO delete(@PathVariable @Positive(message = "{id}") Integer id) {
        this.couponService.delete(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public CouponDO get(@PathVariable @Positive(message = "{id}") Integer id) {
        CouponDO couponDO = this.couponService.getBaseMapper().selectById(id);
        if (couponDO == null) {
            throw new NotFoundException(100000);
        }
        return couponDO;
    }
}
