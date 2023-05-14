package projects;

import java.math.BigDecimal;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {
	
/*This is where the application takes the user input and performs the CRUD operation*/
	
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();
	private Project curProject;
	
	// @formatter:off
	private List<String> operations = List.of(
		"1) Add a project",
		"2) List projects",
		"3) Select a project"
	);
	// @formatter:on


	public static void main(String[] args) {
		new ProjectsApp().processUserSelections();
	}
	/*This shows the user a selection and performs the users request until said user requests it to stop*/
	
	private void processUserSelections() {
		boolean done = false;
		
		while(!done) {
			try {
			int selection = getUserSelection();
				
			switch(selection) {
			case -1:
					done = exitMenu();
					break;
					
			case 1:
				createProject();
				break;
				
			case 2:
				listProjects();
				break;
				
			case 3:
				selectProject();
				break;
				
			default:
				System.out.println("\n" + selection + " is not valid. Try again.");
				break;
				}
			
			}
			catch(Exception e) {
				System.out.println("\nError" + e  + " Try again.");
			}
		}
	}
	
	private void selectProject() {
		listProjects();
		Integer projectId = getIntInput("Enter a project ID to select a project");
		curProject = null;
		
		curProject = projectService.fetchProjectById(projectId);
	}
	
	private void listProjects() {
		List<Project> projects = projectService.fetchAllProjects();
		System.out.println("\nProjects: ");
		projects.forEach(project -> System.out.println(" " + project.getProjectId() + ": " + project.getProjectName()));
		
	}
	
	/*gets user input for the row then calls to service to create a row */
	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");
		
		Project project = new Project();
		
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully create project: " + dbProject);
	}
	/*gets the users input and turns it into a BigDecimal */
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		
		try {
			/*this is creating the BigDecimal and setting it to 2 decimal spots */
			return new BigDecimal(input).setScale(2);
			
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
	}
	/*When the user wants to end the app it calls upon this code. */
	private boolean exitMenu() {
		System.out.println("Exiting the menu.");
		return true;
	}
	/*Prints the available menu options and allows the user to select one*/
	private int getUserSelection() {
		printOperations();
		Integer input = getIntInput("Enter a menu selection");
		return Objects.isNull(input) ? -1 : input;
	}
	/*shows the prompt and gets the users input then turns it into an integer*/
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			return Integer.valueOf(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}
	}
	/*shows prompt on the screen and gets the users selection. if nothing is entered it shows null, if correct shows the users input*/
	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();
		return input.isBlank() ? null : input.trim();
	}
	/*Shows the menu selections, one options per line*/
	private void printOperations() {
		System.out.println("\nThese are the available selections. Press the Enter key to quit:");
		
		operations.forEach(line -> System.out.println(" " + line));
		
		if(Objects.isNull(curProject)) {
			System.out.println("\nYou are not working with a project.");
		}
		else {
			System.out.println("\nYou are working with project: " + curProject);
		}
	}
}
