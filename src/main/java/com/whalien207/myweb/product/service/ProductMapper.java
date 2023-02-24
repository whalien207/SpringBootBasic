package com.whalien207.myweb.product.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.whalien207.myweb.command.CategoryVO;
import com.whalien207.myweb.command.ProductUploadVO;
import com.whalien207.myweb.command.ProductVO;
import com.whalien207.myweb.util.Criteria;

@Mapper
public interface ProductMapper {

	public int regist(ProductVO vo);
	public int registFile(ProductUploadVO vo);

	// 조회 : 특정회원의 정보만 조회
	// !!! 매개변수로 전달되는 데이터가 2개이상이라면 Param어노테이션으로 이름을 붙여주어야 한다. !!! 
	public ArrayList<ProductVO> getList(@Param("user_id") String user_id,
			@Param("cri") Criteria cri); 

	public int getTotal(@Param("user_id") String user_id, 
			@Param("cri") Criteria cri); //전체게시글수

	//카테고리 대분류
	public List<CategoryVO> getCategory();

	//카테고리 중분류 소분류
	public List<CategoryVO> getCategoryChild(CategoryVO vo);
	
	//이미지 데이터 조회
	public List<ProductUploadVO> getProductImg(ProductVO vo);

}
