package com.csit.project.controllers;

import java.util.Collections;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.csit.project.LoanInfoEntity;
import com.csit.project.enetities.LoanInfo;
import com.csit.project.loanServices.LoanInfoServices;
import com.csit.project.services.ActivitiServices;

@Controller
public class ApproveController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private ActivitiServices activitiServices;
	
	@Autowired
	private LoanInfoServices loanInfoServices;

	/*
	 * @RequestMapping("/approverA") public String showApvrA(Model model) {
	 * System.out.println("hello there"); List<Task> tasks =
	 * getApprovertask("apvrA"); model.addAttribute("tasks", tasks);
	 * 
	 * return "apvrA"; }
	 */

	@RequestMapping(value = "/approvedA/{taskId}", method = RequestMethod.GET)
	public String taskApprovedForApvrA(Model model, @PathVariable String taskId) {
		boolean approvedTaskA = true;
		try {
			taskService.complete(taskId, Collections.singletonMap("approvedTaskA", approvedTaskA));
			System.out.println("Task for A is approved....!!!");
			LoanInfo loanInfo =  loanInfoServices.getClientLoanInfoByTaskId(taskId);
			loanInfo.setApproved(true);
			loanInfo.setCurrentStatus("approved");
			loanInfo.setApprovedBy(taskId);
			
			loanInfoServices.updateClientLoanInfo(loanInfo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * List<Task> tasks = getApprovertask("apvrC");
		 * model.addAttribute("tasks", tasks);
		 */

		return "redirect:/approverA";
	}

	@RequestMapping(value = "/approverB/{taskId}", method = RequestMethod.GET)
	public String delegateToApvrB(Model model, @PathVariable String taskId) {

		boolean reviewRequestToB = true;

		try {

			// taskService.complete(taskId,
			// Collections.singletonMap("reviewRequestToB", reviewRequestToB));
			taskService.delegateTask(taskId, "apvrB");
			System.out.println("Task delegated from Approver A to Approver B");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * List<Task> tasks = getApprovertask("apvrA");
		 * model.addAttribute("tasks", tasks);
		 */

		return "redirect:/approverA";
	}

	@RequestMapping("/approverB")
	public String showApvrB(Model model) {

		// User approver =
		// identityService.createUserQuery().userId("apvrB").singleResult();
		List<Task> tasks = getApprovertask("apvrB");
		model.addAttribute("tasks", tasks);

		return "apvrB";
	}

	@RequestMapping(value = "/approvedB/{taskId}", method = RequestMethod.GET)
	public String taskApprovedForApvrB(Model model, @PathVariable String taskId) {
		boolean approvedTaskB = true;
		try {
			// taskService.complete(taskId,
			// Collections.singletonMap("approvedTaskB", approvedTaskB));
			taskService.resolveTask(taskId);
			System.out.println("Task for B is approved....!!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * List<Task> tasks = getApprovertask("apvrC");
		 * model.addAttribute("tasks", tasks);
		 */

		return "redirect:/approverB";
	}

	@RequestMapping(value = "/approverC/{taskId}", method = RequestMethod.GET)
	public String delegateToApvrC(Model model, @PathVariable String taskId) {

		try {
			taskService.delegateTask(taskId, "apvrC");
			System.out.println("Task delegated from Approver B to Approver C");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * List<Task> tasks = getApprovertask("apvrC");
		 * model.addAttribute("tasks", tasks);
		 */

		return "redirect:/approverB";
	}

	@RequestMapping("/approverC")
	public String showApvrC(Model model) {

		// User approver =
		// identityService.createUserQuery().userId("apvrA").singleResult();
		List<Task> tasks = getApprovertask("apvrC");
		model.addAttribute("tasks", tasks);

		return "apvrC";
	}

	@RequestMapping(value = "/approvedC/{taskId}", method = RequestMethod.GET)
	public String taskApprovedForApvrC(Model model, @PathVariable String taskId) {
		boolean approvedTaskC = true;
		try {
			// taskService.complete(taskId,
			// Collections.singletonMap("approvedTaskC", approvedTaskC));
			taskService.resolveTask(taskId);
			System.out.println("Task Approved by C");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * List<Task> tasks = getApprovertask("apvrC");
		 * model.addAttribute("tasks", tasks);
		 */

		return "redirect:/approverC";
	}

	@RequestMapping("/approverA")
	public String showApvrA(Model model) {
		System.out.println("hello there");
		List<Task> tasks = getApprovertask("apvrA");
		// User user =
		// identityService.createUserQuery().userId("apvrA").singleResult();

		/*
		 * List<LoanInfoEntity> loanInfo = new ArrayList<>(); for (Task task :
		 * tasks) {
		 * 
		 * loanInfo.add(new LoanInfoEntity(,)); }
		 */
		//List<LoanInfoEntity> loans = loanInfoRepository.findAll();
		/*
		 * List<LoanInfoEntity> loans = new ArrayList<LoanInfoEntity>(); for
		 * (LoanInfoEntity loanInfoEntity : loanInfo) { //Task task =
		 * tasks.iterator().next(); //loanInfoEntity.setTaskId("152");
		 * //loanInfo.add(loanInfoEntity);
		 * 
		 * loanInfoEntity.setTaskId("1212"); loans.add(loanInfoEntity);
		 * 
		 * }
		 */
		List<LoanInfo> listOfLoan = loanInfoServices.getAllLoanByAssignee("apvrA");
		
		model.addAttribute("loans", loans);

		return "apvrA";
	}

	@Transactional
	private List<Task> getApprovertask(String apvrName) {
		return activitiServices.getTasks(apvrName);
	}
}
