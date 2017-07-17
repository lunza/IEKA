package com.ieka.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.common.utils.CookieUtils;
import com.ieka.common.utils.HttpClientUtil;
import com.ieka.common.utils.JsonUtils;
import com.ieka.pojo.TbItem;
import com.ieka.portal.pojo.CartItem;
import com.ieka.portal.service.CartService;



/**
 * 购物车service
 * 
 * @author fx50j
 *
 */
@Service
public class CartServiceImpl implements CartService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;

	/**
	 * 添加购物车商品
	 */
	@Override
	public IEKAResult addCart(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		CartItem cartItem = null;
		// 取购物车列表
		List<CartItem> cartItemList = getCartItemList(request);
		// 判断购物车商品列表中是否存在此商品
		for (CartItem cItem : cartItemList) {
			// 存在此商品,只增加数量即可
			if (cItem.getId() == itemId) {
				cartItem.setNum(cItem.getNum()+1);
				cartItem = cItem;
				break;
			}
		}
		if (cartItem == null) {
			cartItem = new CartItem();
		// 根据商品id传递商品基本信息
		String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
		IEKAResult iekaResult = IEKAResult.formatToPojo(json, TbItem.class);
		if (iekaResult.getStatus() == 200) {
			TbItem item = (TbItem) iekaResult.getData();
			cartItem.setId(item.getId());
			cartItem.setTitle(item.getTitle());
			cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
			cartItem.setNum(num);
			cartItem.setPrice(item.getPrice());
		}
		cartItemList.add(cartItem);
		}
		//把购物车写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItemList),true);
		return IEKAResult.ok();
	}

	/**
	 * 从cookie中取商品列表
	 * 
	 * @return
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request) {
		// 取得商品列表字符串
		String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);
		if(cartJson==null){
			return new ArrayList<>();
		}
		// 转换成商品列表
		List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
		return list;
	}
	/**
	 * 展示购物车列表
	 */
	@Override
	public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> itemList = getCartItemList(request);
		return itemList;
	}

}
