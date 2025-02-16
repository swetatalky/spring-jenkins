package com.example.sweta.onetomanymapping.model;

import java.util.Map;

public class ApiData {
 private String	status;
 private String status_code;
	 private String  version;
private  String access;
private int total;
private int offset;
private int limit;
	private Map<String,Map<String, Map<String,String>>> data; //"data": {
//	    "DZ": {
//	      "country": "Algeria",
//	      "region": "Africa"
//	    },
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public Map<String, Map<String, Map<String, String>>> getData() {
		return data;
	}
	public void setData(Map<String, Map<String, Map<String, String>>> data) {
		this.data = data;
	}


	
	
}
