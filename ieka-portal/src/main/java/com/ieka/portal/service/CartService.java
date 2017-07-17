package com.ieka.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.portal.pojo.CartItem;

public interface CartService {

	IEKAResult addCart(long itemId,int num,HttpServletRequest request,HttpServletResponse response);

	List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);
}
