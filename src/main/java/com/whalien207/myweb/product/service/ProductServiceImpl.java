package com.whalien207.myweb.product.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.whalien207.myweb.command.CategoryVO;
import com.whalien207.myweb.command.ProductUploadVO;
import com.whalien207.myweb.command.ProductVO;
import com.whalien207.myweb.util.Criteria;

@Service("productService")
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductMapper productMapper;
	
	@Value("${project.uploadpath}")
	private String uploadpath;

	//날짜별로 폴더 생성
	public String makeDir() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String now = sdf.format(date);

		String path = uploadpath + "\\" + now;
		File file = new File(path);

		if(!file.exists()) { //false, 파일이 이미 있다면 true
			file.mkdir(); //폴더생성
		}

		return now; //년월일 폴더위치
	}
	
	//글등록
	//한 프로세스 안에서 예외가 발생하면, 기존에 진행했던 CRUD작업을 rollback시켜준다.
	//조건 - catch를 통해서 예외처리가 진행되면 Transaction 어노테이션이 에러를 잡지 못한다.
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int regist(ProductVO vo, List<MultipartFile> list) {
		
		//1. 글 등록 처리 -> 
		int result = productMapper.regist(vo);
		
		//2. 파일 인서트 -> 
		for(MultipartFile file : list) {
			//for문 안에서는 단일 파일 업로드와 동작이 같다.			
			String origin = file.getOriginalFilename();
			//브라우저마다 경로가 포함돼서 올라오는 경우가 있어서 간단하게 처리해준다.
			String filename = origin.substring(origin.lastIndexOf("\\") + 1);

			//폴더생성
			String filepath = makeDir();

			//중복파일의 처리 (랜덤한 UUID를 생성하여 파일명 앞에 붙여준다.)
			String uuid = UUID.randomUUID().toString();
			//최종저장경로
			String savename = uploadpath + "\\" + filepath + "\\" + uuid + "_" + filename;
			
			try {
				File save = new File(savename); //세이브경로
				file.transferTo(save); //업로드 실행
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			
			//insert작업
			ProductUploadVO prodVO = ProductUploadVO.builder().filename(filename)
									 .filepath(filepath)
									 .uuid(uuid)
									 .prod_writer(vo.getProd_writer())
									 .build();
			productMapper.registFile(prodVO);
			
		}
		
		return result;
	}
	
	@Override
	public ArrayList<ProductVO> getList(String user_id, Criteria cri) {
		return productMapper.getList(user_id, cri);
	}
	
	@Override
	public int getTotal(String user_id, Criteria cri) {
		return productMapper.getTotal(user_id, cri);
	}
	
	@Override
	public List<CategoryVO> getCategory() {
		return productMapper.getCategory();
	}
	
	@Override
	public List<CategoryVO> getCategoryChild(CategoryVO vo) {
		return productMapper.getCategoryChild(vo);
	}
	
	@Override
	public List<ProductUploadVO> getProductImg(ProductVO vo) {
		return productMapper.getProductImg(vo);
	}

}
