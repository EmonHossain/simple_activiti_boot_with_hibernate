package com.csit.project.loanDaos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.csit.project.enetities.LoanInfo;

import CriteriaQuery;

@Repository
@Transactional
public class LoanInfoDao {
	
	@Autowired
	private SessionFactory _sessionFactory;
	
	@Autowired
	private EntityManager entityManager;
	
	private Session getSession() {
		return _sessionFactory.getCurrentSession();
	}
	
	public void save(LoanInfo loanInfo){
		getSession().save(loanInfo);
	}
	
	public void delete(LoanInfo loanInfo){
		getSession().delete(loanInfo);
	}

	public LoanInfo getLoanInfoByUserId(String taskId) {
		
		return (LoanInfo) getSession().createQuery("from LoanInfo where taskId=:taskId").setParameter("taskId", taskId).uniqueResult();
	}

	public void updateLoanInfo(LoanInfo loanInfo) {
		getSession().update(loanInfo);
		
	}

	public List<LoanInfo> getAllLoanByAssinee(String assignee) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<LoanInfo> = (CriteriaQuery) criteriaBuilder.createQuery(LoanInfo.class);//<LoanInfo> criteria = (CriteriaQuery) criteriaBuilder.createQuery(LoanInfo.class);
		Criteria criteria = getSession().createCriteria(LoanInfo.class);
		Criteria assign = (Criteria) Restrictions.eq("assignee", assignee);
		Criteria approve = (Criteria) Restrictions.eq("approvedBy", null);
		LogicalExpression exp = Restrictions.and(assign, approve);
		criteria.add(exp);
		
		return criteria.list();
	}

}
