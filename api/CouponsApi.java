package com.dor.coupons.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dor.coupons.beans.Coupon;
import com.dor.coupons.enums.Category;
import com.dor.coupons.exception.ApplicationException;
import com.dor.coupons.logic.CouponsController;

@RestController
@RequestMapping("/coupons")
public class CouponsApi {

	@Autowired
	private CouponsController couponsController;
	
	@PostMapping
	public void createCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		couponsController.createCoupon(coupon);
	}
	
	@GetMapping("/{couponId}")
	public Coupon getCoupon(@PathVariable("couponId") long id) throws ApplicationException {
		return couponsController.getCoupon(id);
	}
	
	@GetMapping
	public List<Coupon> getAllCoupons() throws ApplicationException {
		return this.couponsController.getAllCoupons();
	}
	
	@GetMapping("/byCategory")
	public List<Coupon> getAllCouponsByCategory(@RequestParam("category") Category category) throws ApplicationException {
		return couponsController.getAllCouponsByCategory(category);
	}
	
	@GetMapping("/byMaxPrice")
	public List<Coupon> getAllCouponsByMaxPrice(@RequestParam("maxPrice") double maxPrice) throws ApplicationException {
		return couponsController.getAllCouponsByMaxPrice(maxPrice);
	}
	
	@GetMapping("/byCustomerId")
	public List<Coupon> getCustomerCoupons(@RequestParam("customerId") long customerId) throws ApplicationException {
		return couponsController.getCustomerCoupons(customerId);
	}
	
	@GetMapping("/byCompanyId")
	public List<Coupon> getCompanyCoupons(@RequestParam("companyId") long companyId) throws ApplicationException {
		return couponsController.getCompanyCoupons(companyId);
	}

	@GetMapping("/byCustomerIdAndCategory")
	public List<Coupon> getCustomerCouponsByCategory(@RequestParam("customerId") long customerId, @RequestParam("category") Category category) throws ApplicationException {
		return couponsController.getCustomerCouponsByCategory(customerId, category);
	}
	
	@GetMapping("/byCompanyIdAndCategory")
	public List<Coupon> getCompanyCouponsByCategory(@RequestParam("companyId") long companyId, @RequestParam("category") Category category) throws ApplicationException {
		return couponsController.getCompanyCouponsByCategory(companyId, category);
	}
	
	@GetMapping("/byCustomerIdAndMaxPrice")
	public List<Coupon> getCustomerCouponsByMaxPrice(@RequestParam("customerId") long customerId, @RequestParam("maxPrice") double maxPrice) throws ApplicationException {
		return couponsController.getCustomerCouponsByMaxPrice(customerId, maxPrice);
	}
	
	@GetMapping("/byCompanyIdAndMaxPrice")
	public List<Coupon> getCompanyCouponsByMaxPrice(@RequestParam("companyId") long companyId, @RequestParam("maxPrice") double maxPrice) throws ApplicationException {
		return couponsController.getCompanyCouponsByMaxPrice(companyId, maxPrice);
	}
	
	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		couponsController.updateCoupon(coupon);
	}
	
	@DeleteMapping("/{couponId}")
	public void deleteCoupon(@PathVariable("couponId") long couponId) throws ApplicationException {
		couponsController.deleteCoupon(couponId);
	}
	
	@DeleteMapping("/byCompany/{companyId}")
	public void deleteCouponsByCompanyId(@RequestParam("companyId") long companyId) throws ApplicationException {
		couponsController.deleteCouponsByCompanyId(companyId);
	}
}
