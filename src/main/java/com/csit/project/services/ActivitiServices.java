package com.csit.project.services;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivitiServices {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	public ProcessInstance startProcess(Map<String, Object> variable) {
		return runtimeService.startProcessInstanceByKey("oneTaskProcess", variable);
	}

	public List<Task> getTasks(String assignee) {
		return taskService.createTaskQuery().taskAssignee(assignee).list();
	}

}
