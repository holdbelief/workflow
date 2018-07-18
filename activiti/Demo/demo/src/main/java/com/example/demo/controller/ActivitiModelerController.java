package com.example.demo.controller;

import java.io.UnsupportedEncodingException;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
@RequestMapping("activiti")
public class ActivitiModelerController {
	
	@Autowired
	private RepositoryService repositoryService;
  
	@Autowired
	private ObjectMapper objectMapper;
	
	
    @RequestMapping("/test")
    @ResponseBody
	public String newMode() {
    	return "spring-boot";
    }
    
	
    /**
     * 新建模型
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/create")
	public ModelAndView newModel() throws UnsupportedEncodingException {
//    	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//    	RepositoryService repositoryService = processEngine.getRepositoryService();
//    	ObjectMapper objectMapper = new ObjectMapper();
        //初始化一个空模型
        Model model = repositoryService.newModel();

        //设置一些默认信息，可以用参数接收
        int revision = 1;
        String key = "process";
        String name = "process";
        String description = "新建流程模型";
        
        //ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace","http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());
        
        repositoryService.saveModel(model);

        String id = model.getId();
        
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
        return new ModelAndView("redirect:/modeler.html?modelId=" + id);
    }
}
