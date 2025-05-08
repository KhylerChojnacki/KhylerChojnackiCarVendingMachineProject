/*
Name: Khyler Chojnacki
Class: Computer Science 2/CS2050
Description: Project 01 Iteration 02 Abstract classes, ArrayLists, and Linear Data Structures
and Classes

This code/project displays a vending machine for cars 
that has multiple floors and spaces for cars. It's designed 
to read from a file and be able to show the user what cars 
are in the tower, adding cars to the tower, retrieveing a
car from the tower, printing cars from lowest to highest 
(price),printing cars from oldest to newest (year), searching 
by the make (manfacturer) and car type whether that be basic or
premium, adding a car from the tower to a car wash, processing 
cars that are in the queue into the car wash, and selling a car. 
Since this is the second iteration of this project, this project 
is designed to use abstract classes, ArrayLists, queues, and HashMaps

*/
//These are to import information from the computer, read a file, and make sure the file exists
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
/* The main class is what is displayed to the user. It's a menu that asks the user 
 * for how many floors and spaces their tower has, It will then show the user
 * the menu will all the options that the user can select from.
 * 
 */
public class CarTowerKhylerChojnacki {
	public static void main (String[]args) {
	Scanner scanner = new Scanner(System.in);
	//This gets the information from the user (from there keyboard) for the number of floors and spaces
	System.out.print("Enter the number of floors for the vending machine: ");
	int floors = scanner.nextInt();
	System.out.print("Enter the number of spaces for the car vending machine: ");
	int spaces = scanner.nextInt();
	
	//creates a VendingMachine object for the number of floors and spaces based on the input from keyboard
	VendingMachine vendingMachine = new VendingMachine(floors,spaces);
	boolean running = true;
	//while loop to display the menu to the user
	while (running) {
		System.out.println("Car Vending Machine Menu");
		System.out.println("1) Load Car Data");
		System.out.println("2) Display Vending Machine");
		System.out.println("3) Retrieve a Car");
		System.out.println("4) Print Sorted Inventory Lowest to Highest (Price)");
		System.out.println("5) Print Sorted Inventory Oldest to Newest (Year) ");
		System.out.println("6) Search by the make (manufacturer) of the car and the type of car (basic or premium)");
		System.out.println("7) Add car to car wash queue");
		System.out.println("8) Process car wash queue");
		System.out.println("9) Sell car");
		System.out.println("10) Exit the Program");
		//Reading from what the user selected
		System.out.println("Enter the corresponding number to what you'd like to choose:");
		int selection = scanner.nextInt();
		scanner.nextLine();
		//Focuses on the selection the user made using cases
		switch (selection) {
			case 1:
				//loads data of a car from a file
				System.out.print("Enter the file here: "); 
				String fileFinder = scanner.nextLine();
				try {
					vendingMachine.loadCarsFromFile(fileFinder);
					
				} catch (FileNotFoundException e) {
					System.out.println("The file you have entered was not found, try again");
				}
				break;
			case 2:
				//Displays the tower
				vendingMachine.displayCarTower();
				break;
			case 3:
				//Retrieves a car from the tower and from the location
				System.out.print("Please enter the floor number starting from 1: ");
				int floor = scanner.nextInt();
				System.out.print("Please enter the space number starting from 1: ");
				int space = scanner.nextInt();
				vendingMachine.retrieveCar(floor, space);
				break;
			case 4:
				//Sorts and displays the inventory by the price (lowest to highest)
				vendingMachine.sortedInventory("price");
			
				break;
			case 5:
				//Sorts and displays the inventory by year (oldest to newest)
				vendingMachine.sortedInventory("year");
				
				break;
			case 6:
				//Searches for cars by both make and type
				System.out.println("Please enter a make of a car");
				String make = scanner.nextLine();
				System.out.println("Please enter a car type");
				String carType = scanner.nextLine();
				vendingMachine.searchByMakeAndTypeOfCar(make,carType);
				break;
			case 7:
				//Adding a car to the car wash queue
				System.out.print("Please enter a floor number: ");
				int floorWash = scanner.nextInt();
				System.out.print("Please enter a space number: ");
				int spaceWash = scanner.nextInt();
				scanner.nextLine();
				vendingMachine.addCarToCarWashQueue(floorWash,spaceWash);
				break;
			case 8:
				//process the car wash queue
				vendingMachine.processCarWash();
				break;
			case 9:
				//Sell a car
				System.out.print("Please enter a floor number: ");
				int floorSell = scanner.nextInt();
				System.out.print("Please enter a space number: ");
				int spaceSell = scanner.nextInt();
				vendingMachine.sellCar(floorSell, spaceSell);
				break;
			case 10:
				//Exits the program and stops loop (menu from appearing)
				System.out.println("Exiting program. Goodbye!");
				
				return;
			default:
				//This is when one of the six cases is not selected
				System.out.print("Invalid selection. Please select a valid selection from the menu ");
	
				scanner.close();
			}
	}
	}
}
/* 
 * This is the abstract car class which takes 
 * information from the file and creates a
 * new car for each car in the file. It 
 * will have the make, model, price and year 
 * of the car as well as taking the floor and space. 
 * It will also take note of the car type whether that be 
 * basic or premium
 * 
 */
abstract class Car {
	private String make;
	private String model;
	private double price;
	private int year;
	private int floor;
	private int space;
	/*this is where a car gets made (the object)
	 * @param make: This is the make of the car
	 * @param model: This is the model of the car 
	 * @param price: What the car costs
	 * @param year: When the car was made
	 * @param floor: Which floor the car is located in the tower
	 * @param space: Which space the car is located on in the tower
	 * @return It will return a string of the car
	 */
	public Car(String make, String model, double price, int year, int floor, int space) {
		this.make = make;
		this.model = model;
		this.price = price;
		this.year = year;
		this.floor = floor;
		this.space = space;
	}
	public String toString() {
		return getCarType() + " " + make + " " + model + " " + price + " " + year + " " + " (Floor: " + floor + ", Space: " + space + ")";
	}
	/* 
	 * These two methods below will get the price 
	 * (top method) and the the year (bottom method)
	 * @return The price of the car
	 * @return The year of the car
	 */
	public double getPrice() {
		return price;
		
	}
	public int getYear() {
		return year;
	}
	/*
	 * To get the make of the car for the search
	 * for make and get car type which is
	 * for whether the car is basic or premium
	 */
	public String getMake() {
		return make;
	}
	public abstract String getCarType();
	/*
	 * Some more getters for the floor and space
	 */
	public int getFloor() {
		return floor;
	}	
	public int getSpace() {
			return space;
		
	
	}	
	
	}
/*
 * This a subclass. It's a BasicCar which extends from Car
 * and pulls data from the abstract car class
 */
class BasicCar extends Car{
	public BasicCar (String make,String model,double price,int year,int floor, int space) {
		super(make,model,price,year,floor,space);
	}
	@Override
	public String getCarType() {
		return "Basic";
	}
}
/*
 * This a subclass. It's a PremiumCar which extends from Car
 * and pulls data from the abstract car class
 */
class PremiumCar extends Car{
	public PremiumCar (String make,String model,double price,int year,int floor, int space) {
		super(make,model,price,year,floor,space);
}
	@Override
	public String getCarType() {
		return "Premium";
	}
}
/*
 * This is the VendingMachine class which holds
 * all of the methods for the menu such as adding a
 * car, retrieving a car, sorting and printing cars by 
 * either price or year, loading car data from a file, adding
 * cars to the car wash, processing the car wash, selling a car,
 * searching for a car by the make and type, and 
 * displaying what the tower is currently holding
 */

class VendingMachine{
	private LinkedList<Car> carInventory = new LinkedList<>();
	private HashMap<String, Car> towerMap = new HashMap<>();
	private Queue<Car> carWashQueue = new LinkedList<>();
	private int floors;
	private int spaces;
	/*
	 * This is constructing the VendingMachine object 
	 * depending on what was inputed in from the user
	 * @param floors: The number of floors in the tower
	 * @param spaces: The number of spaces on each floor within the tower
	 * It also has the HashMap, LinkedList, and Queue
	 */
	public VendingMachine(int floors, int spaces) {
		this.floors = floors;
		this.spaces = spaces;
		towerMap = new HashMap<>();
		carInventory = new LinkedList<>();
		carWashQueue = new LinkedList<>();
		
	}
	/*
	 * This method adds a car to the tower depending on
	 * the floor and space given in the file.
	 * @param car: the car being added to the tower
	 * @param floor: what floor the car will be placed on
	 * @param space: what space the car will be placed
	 */
	public void addCar(Car car, int floor, int space) {
		/* 
		 * Statement checks to see if car is valid
		 */
		if(car == null) {
			System.out.println("Car cannot be added");
		}
		/* 
		 * Statement checks to see if the floor is valid (from the file) 
		 */
		if (floor < 1 || floor > floors) {
			System.out.println("Car cannot be placed at location, floor does not exist");
			return;
		}
		/*
		 * Statement checks to see if the space is valid (from file)
		 */
		if (space < 1 || space > spaces) {
			System.out.println("Car cannot be placed at location, space does not exist");
			return;
		}
		String slotKey = floor + "-" + space;
		if (towerMap.containsKey(slotKey)) {
			System.out.println("Location is occupied by another car");
			return;
		}
		System.out.println("Car added: " + car.toString() + " at " + floor + " - " + space);
		carInventory.add(car);
		towerMap.put(slotKey,car);
		System.out.println("Car has been added to location" + slotKey);
		System.out.println("Inventory size: " + carInventory.size());
		System.out.println("Map size: " + towerMap.size());
	}	
	/*
	 * Method to retrieve car from tower depending on floor and space
	 * @param floor: Floor where car is located
	 * @param space: Space number where car is located
	 */
	public void retrieveCar (int floor, int space) {
		/* 
		 * Statement checks to see if the floor is valid (from the file) 
		 */
		if (floor < 1 || floor > floors) {
			System.out.println("Car is unable to be retrieved, floor does not exist");
			return;
		}
		/*
		 * Statement checks to see if the space is valid (from file)
		 */
		if (space < 1 || space > spaces) {
			System.out.println("Car is unable to be retrieved, space does not exist");
			return;
	}
		String slotKey = floor + "-" + space;
		
		if (towerMap.containsKey(slotKey)) {
			Car car = towerMap.get(slotKey);
			System.out.println("Car has been retrieved: " + car.toString() );
			towerMap.remove(slotKey);
			carInventory.remove(car);
		
		} else {
			System.out.println("Location is empty");
		}
	}
	/*
	 * Displays the car tower with the information is currently has.
	 * This can change with adding and retrieving cars
	 */
	public void displayCarTower() {
		if(towerMap.isEmpty()||carInventory.isEmpty()) {
			System.out.println("There are no cars in the vending machine");
			return;
		}
		for (int floor = 1; floor <= floors; floor++) {
			for(int space =1; space <= spaces; space++) {
				String slotKey = floor + "-" + space;
				
				 
				if (towerMap.containsKey(slotKey)) {
					System.out.println("Slot " + slotKey + towerMap.get(slotKey));
				}
			}
			System.out.println();
		}
		}
	
	/*
	 * This sorts the inventory depending on what was
	 * selected in the menu. If 4 was selected, the code will
	 * sort the information by price (lowest to highest) and if 5 
	 * was selected then the information will be sorted by the year
	 * (oldest to newest).
	 * @param price: Sorts cars by price
	 * @param year: sorts cars by year
	 */
	public void sortedInventory (String searchBy) {
		ArrayList<Car> carList = new ArrayList<>(carInventory);
		System.out.println("Sorted Inventory by " + searchBy + ":");
		
		if (searchBy.equalsIgnoreCase("price")) {
			carList.sort(Comparator.comparingDouble(Car::getPrice));
		}else if(searchBy.equalsIgnoreCase("year")) {
			carList.sort(Comparator.comparingInt(Car::getYear));	
		}else {
			System.out.println("Please select either price or year");
		}
		for (Car car : carList) {
			System.out.println(car);
		}
		
			}
/*
 * This is the method that searches the tower by a make and type 
 * depending on what the user inputs in.
 * @param make: The make of the car being searched for
 * @parma carType: The type of car (Basic or Premium)
 */
	public void searchByMakeAndTypeOfCar(String make, String carType) {
		boolean foundCar = false;
		
		for (Car car : carInventory){
			if (car.getMake().equalsIgnoreCase(make) && car.getCarType().equalsIgnoreCase(carType)) {
				System.out.print(car);
				foundCar = true;
			}
		}
		if(!foundCar) {
			System.out.println("No cars were found with " + make + " and " + carType );
		}
	}
	/*
	 * Method to sell a car. It searches for the car and then
	 * removes the car from both the inventory and tower
	 * @param floor: Which floor car is located
	 * @param space: Which space car is located
	 */
	public void sellCar (int floor, int space) {
		if (floor < 1 || floor > floors) {
			System.out.println("Floor does not exist");
			return;
		}
		if (space < 1 || space > spaces) {
			System.out.println("Space does not exist");
			return;
	}
		String slotKey = floor + "-" + space;
		
		if(towerMap.containsKey(slotKey)) {
			Car car = towerMap.get(slotKey);
			towerMap.remove(slotKey);
			if(carInventory.contains(car)) {
			carInventory.remove(car);
			System.out.println("Car has been sold: " + car.toString());	
		}else {
			System.out.println("The car that is trying to be sold does not exist");
		}
		}
}
	/*
	 * Method where car from tower can get added to car
	 * was queue depending on the floor and space the 
	 * user inputs
	 * @param floor: Which floor car is located
	 * @param space: Which space car is located
	 */
	public void addCarToCarWashQueue(int floor, int space) {
		String slotKey = floor + "-" + space;
		
		
		if(towerMap.containsKey(slotKey)) {
			Car carWash = towerMap.get(slotKey);
			carWashQueue.add(carWash);
			System.out.println("Car has been added to the car wash queue: " + carWash);
			
		}else {
		System.out.println("Car cannot be added to the car wash queue");
		}

	}
	/*
	 * Method that processes the car wash queue by removing the
	 * car from the queue (the first car in line as this is a queue
	 * which follows FIFO rule) and puts them into the car wash to
	 * get washed.
	 */
	public void processCarWash() {
		if(carWashQueue.isEmpty()) {
			System.out.println("There are no cars in the car wash queue");
		}else {	
			Car car = carWashQueue.remove();
			System.out.println("Car wash is proccessing: " + car);
		}
	}
	/*
	 * This loads the car data from the file and adds the 
	 * cars to the tower
	 * @param vendingMachine: The cars getting added to the tower
	 * @param FileNotFoundException: If the file doesn't work or can't be found
	 * @param filename: The path where the file is that contains the car data.
	 */
	public void loadCarsFromFile(String filename) throws FileNotFoundException {
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(new File(filename));
	
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine().trim();
				if (line.isEmpty()) continue;
	
				String[] parts = line.split(" ");
				if (parts.length < 7) {
					System.out.println("Invalid line: " + line);
					continue;
				}
	
				char carType = parts[0].charAt(0);
				int floor = Integer.parseInt(parts[1]);
				int space = Integer.parseInt(parts[2]);
				int year = Integer.parseInt(parts[3]);
				double price = Double.parseDouble(parts[4]);
				String make = parts[5];
				String model = String.join(" ", java.util.Arrays.copyOfRange(parts, 6, parts.length));
	
				Car car = null;
				if (carType == 'B') {
					car = new BasicCar(make, model, price, year, floor, space);
				} else if (carType == 'P') {
					car = new PremiumCar(make, model, price, year, floor, space);
				} else {
					System.out.println("Unknown car type: " + carType);
					continue;
				}
	
				addCar(car, floor, space);
			}
		} finally {
			if (fileScanner != null) {
				fileScanner.close();
			}
		}
	}
	

	}
	



