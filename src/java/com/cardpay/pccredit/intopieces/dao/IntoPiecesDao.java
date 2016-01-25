package com.cardpay.pccredit.intopieces.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.model.LocalExcelForm;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface IntoPiecesDao {
	public int checkValue(@Param("userId")String userId,@Param("valueType")String valueType);
	public List<IntoPieces> findintoPiecesByFilterWF(IntoPiecesFilter filter);
	public int findCountintoPiecesByFilterWF(IntoPiecesFilter filter);
	
	public List<IntoPieces> findintoPiecesAllByFilter(IntoPiecesFilter filter);
	public int findintoPiecesAllCountByFilter(IntoPiecesFilter filter);
	
	
	public List<LocalExcelForm> findByProductAndCustomer(AddIntoPiecesFilter filter);
	public int findCountByProductAndCustomer(AddIntoPiecesFilter filter);
	
	public LocalExcel findByApplication(@Param("applicationId") String applicationId);
	
	public List<LocalExcelForm> findLocalExcelForm(AddIntoPiecesFilter filter);
}
