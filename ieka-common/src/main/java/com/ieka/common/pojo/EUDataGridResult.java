package com.ieka.common.pojo;

import java.util.List;

/**
 * 分页实体对象
 * 
 * @author 孙菁
 *
 */
public class EUDataGridResult {
	// 拥有属性:总条数,每页条数
	private long total;
	private List<?> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
