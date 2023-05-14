package projects.service;

import projects.dao.ProjectsDao;
import projects.entity.Project;
import java.util.List;
import java.util.NoSuchElementException;


public class ProjectService {
	
	private ProjectsDao projectDao = new ProjectsDao();
	
/*This has the Dao insert a row into prject*/
	
	public Project addProject(Project project) {
		return projectDao.insertProject(project);
	}
	
	public List<Project> fetchAllProjects() {
		return projectDao.fetchAllProjects();
	}
	
	public Project fetchProjectById(Integer projectId) {
		return projectDao.fetchProjectById(projectId).orElseThrow(() -> new NoSuchElementException("Project with project ID=" + projectId + " does not exist."));
		
	}
}
