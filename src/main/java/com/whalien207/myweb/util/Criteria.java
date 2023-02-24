package com.whalien207.myweb.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data //getter, setter
@AllArgsConstructor
public class Criteria {
	
	//sql에 전달할 page, amount 를 가지고 다니는 클래스
	private int page; //조회한 페이지 번호
	private int amount; //데이터갯수 (한페이지당 몇개씩 보여줄지)
	
	//검색키워드
	private String searchName; //상품명
	private String searchContent; //상품내용
	private String searchPrice; //정렬방식
	private String startDate; //판매시작일
	private String endDate; //판매종료일
	
	public Criteria() {
		super();
		this.page = 1;
		this.amount = 10;
	}
	
	public Criteria(int page, int amount) {
		super();
		this.page = page;
		this.amount = amount;
	}
	
	//sql문에 전달할 시작값을 구해주는 method
	public int getPageStart() {
		return (page-1) * amount;
	}
	
}
