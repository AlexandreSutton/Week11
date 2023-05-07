package projects.service;

import projects.dao.ProjectsDao;

import projects.entity.Project;


public class ProjectService {
	
	private ProjectsDao projectDao = new ProjectsDao();
	
/*This has the Dao insert a row into prject*/
	
	public Project addProject(Project project) {
		return projectDao.insertProject(project);
	}
}
