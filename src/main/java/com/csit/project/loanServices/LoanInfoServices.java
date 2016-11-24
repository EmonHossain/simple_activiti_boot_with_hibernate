package com.csit.project.loanServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csit.project.enetities.LoanInfo;
import com.csit.project.loanDaos.LoanInfoDao;

@Service("loanInfoService")
public class LoanInfoServices {
	
	@Autowired
	private LoanInfoDao loanInfoDao;

	public void saveClientLoanInfo(LoanInfo loanInfo) {
		loanInfoDao.save(loanInfo);
	}

	public LoanInfo getClientLoanInfoByTaskId(String taskId) {
		return loanInfoDao.getLoanInfoByUserId(taskId);		
	}

	public void updateClientLoanInfo(LoanInfo loanInfo) {
		loanInfoDao.updateLoanInfo(loanInfo);		
	}

	public List<LoanInfo> getLoansByAssignee(String assignee) {
		return loanInfoDao.getAllLoanByAssinee(assignee);
	}

	
	
	
}
