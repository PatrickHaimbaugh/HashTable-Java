


import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;



public class HashTableTest {
    public static void testClassStructure() {
		Constructor<HashTable> constructor = null;
		Method load;
		Method put;
		Method print; 
		Method get_collisions;
		Method resize;
		
		System.out.println("Testing Class Structure....");
		
		try {
			constructor = HashTable.class.getConstructor();
		}catch (NoSuchMethodException e){
			System.out.println("Ensure that HashTable has a constructor!");
		}
		
		try {
			load = HashTable.class.getDeclaredMethod("load", String.class);
		}catch (NoSuchMethodException e ) {
			System.out.println("Ensure that HashTable has a method load(String filename) that is declare public!");
		}
		
		try {
			put = HashTable.class.getDeclaredMethod("put", String.class, Product.class);
		}catch (NoSuchMethodException e) {
			System.out.println("Ensure that HashTable has a method put(String vendor, Product value) that is declare public!");
		}
		
		try {
			print = HashTable.class.getDeclaredMethod("print");
		}catch (NoSuchMethodException e) {
			System.out.println("Ensure that HashTable has a method print() that is declare public!");
		}
		
		try {
			get_collisions = HashTable.class.getDeclaredMethod("getCollisions");
		}catch (NoSuchMethodException e) {
			System.out.println("Ensure that HashTable has a method getCollisions() that is declare public!");
		}
		
		try {
			resize = HashTable.class.getDeclaredMethod("resize");
		}catch (NoSuchMethodException e) {
			System.out.println("Ensure that HashTable has a method resize() that is declare public!");
		}
	}
    
    
    /**
	 * Test put value from zero.
	 * */
	private static void zeroPut(String file) throws Exception{
		//String add_file = file_location + "_add.txt";
        
        Product case_product = loadProduct(file)[0]; 
		
		HashTable hash = new HashTable();
		
		hash.put(case_product.getName(), case_product);
		
        hash.print();
	}
   
    
    /**
	 * Load a file and transfer the file to product array
	 * @param filename the file name of the test file
	 * */
	private static Product[] loadProduct(String filename) {
		ArrayList <Product> product_array = new ArrayList<Product>();
		
		try {
			Scanner in = new Scanner(new File(filename));
			String line = null;
			while(in.hasNextLine()) {
				line = in.nextLine();
	            String[] arr = line.split("; ");
	            String name = arr[0];
	            String vendor = arr[1];
	            double price = Double.valueOf(arr[2].split("\\$")[1]); 
	            String[] depRateString = arr[3].split("\\[")[1].split("\\]")[0].split(", ");
	            int[] depRating = new int[depRateString.length];
	            
	            for (int i = 0; i<depRating.length; i++) {
	            	depRating[i] = Integer.parseInt(depRateString[i]);
	            }
	        
	            Product new_product = new Product(name, vendor, depRating, price);
	            product_array.add(new_product);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 
		return product_array.toArray(new Product[0]);
	}
    
    
    /**
	 * Test serially put value from zero.
	 * */
	private static void zeroSeriallyPut(String file) throws Exception{
			
		Product case_product[] = loadProduct(file); 
		
		HashTable hash = new HashTable();
		for (int i = 0; i < case_product.length; i++) {
			hash.put(case_product[i].getName(), case_product[i]);
		}
		hash.print();
	}
    
    /**
	 * Test put single value to the table.
	 * */
	private static void standardPut(String file_location) throws Exception{
		String load_file = file_location + ".txt";
		String add_file = file_location + "_add.txt";
		
		Product case_product = loadProduct(add_file)[0]; 
		
		HashTable hash = new HashTable();
		
		hash.load(load_file);
		
		hash.put(case_product.getName(), case_product);
        
	    hash.print();
	}
    
    
    /**
	 * Test put multiple values to the table.
	 * */
	private static void seriallyPut(String file_location) throws Exception {
		String load_file = file_location + ".txt";
		String add_file = file_location + "_add.txt";
		
		Product[] case_product = loadProduct(add_file); 
		
		HashTable hash = new HashTable();
		
		hash.load(load_file);
		
		for (int i = 0; i < case_product.length; i++) {
			hash.put(case_product[i].getName(), case_product[i]);
		}
		hash.print();
	}
    
    /**
	 * Test remove value to the table, and the value is in the table.
	 * */
	private static void standardRemove(String file_location) throws Exception{
		String load_file = file_location + ".txt";
		String remove_file = file_location + "_remove.txt";
		
		Product case_product = loadProduct(remove_file)[0]; 
		String product_name = case_product.getName();
		
		HashTable hash = new HashTable();
				
		hash.load(load_file);
				
		hash.remove(product_name);
		        
		hash.print();

		//System.out.println("testing this standard remove");
	}
    
    
    /**
	 * Test remove value to the table, and the value is in the table.
	 * */
	private static void seriallyRemove(String file_location) throws Exception{
		String load_file = file_location + ".txt";
		String remove_file = file_location + "_remove.txt";
		
		Product[] case_product = loadProduct(remove_file); 
		
		HashTable hash = new HashTable();		
		hash.load(load_file);
		
		for (int i = 0; i < case_product.length; i++) {
			hash.remove(case_product[i].getName());
		}
        
		hash.print();
		//System.out.println("serially removed this piece of shit");
	}
    
    
    /**
	 * Test remove value to the table, and the value is not in the table.
	 * */
	private static void zeroRemove(String file_location) throws Exception{
		String load_file = file_location + ".txt";
		String remove_file = file_location + "_zero_remove.txt";
		
		Product case_product = loadProduct(remove_file)[0];
		
		String product_name = case_product.getName();
		
		HashTable hash = new HashTable();
		
		hash.load(load_file);
		
		hash.remove(product_name);
		
		hash.print();
	}
    
    
    /**
	 * Examine resize function, it should return the resized HashTable.
	 * */
	private static void testResize(String file_location) throws Exception{
		
		String load_file = file_location + ".txt";
		String add_file = file_location + "_add.txt";
		
		Product case_product[] = loadProduct(add_file); 
		
		HashTable hash = new HashTable();
        
        System.out.println("Size before Resize: " + hash.getCapacity());
		
		hash.load(load_file);
			
		
		for (int i = 0; i < case_product.length; i++) {
			hash.put(case_product[i].getName(), case_product[i]);
		}
				
		System.out.println("Size after Resize: " + hash.getCapacity());
	} 
    
    
    /**
	 * Examine get function, it should return a product.
	 * the test file should be two string the first string should exists in the hashtable, but the second one should not in the hashtable.
	 * */
	private static void get(String file_location) throws Exception{
		String load_file = file_location + ".txt";
		String get_file = file_location + "_get.txt";
		String e_name = null;
		String ne_name = null;
		
		try {
			Scanner in = new Scanner(new File(get_file));
			if (in.hasNextLine()) {
				String line = in.nextLine();
				String[] arr = line.split(",");
				e_name = arr[0];
				ne_name = arr[1];
			}
		}catch (Exception e) {
			System.out.println("reading oops");
		}
	
		HashTable hash = new HashTable();
		
		hash.load(load_file);
		
        System.out.println(hash.get(e_name).toString());		
	}

    
    /**
	 * Test load a file to the hash table.
	 * */
	private static void load(String file_location) throws Exception{
		String load_file = file_location + ".txt";
		
		HashTable hash = new HashTable();
		
		hash.load(load_file);
        
		hash.print();
	}
    
    /**
	 * Test getCollisions function, the function should shows in which 
	 * will print the collisions and the index of the array that they occur.
	 * */ 
	private static void collision(String file_location) throws Exception{
		String load_file = file_location + ".txt";
		
		HashTable hash = new HashTable();
		
		hash.load(load_file);
		
		PrintStream old = System.out;
		
		// The output of test code
		ByteArrayOutputStream test_output = new ByteArrayOutputStream();
		PrintStream topt = new PrintStream(test_output);
		System.setOut(topt);
		
		hash.getCollisions();
		
		System.out.flush();
		System.setOut(old);
		String test_string = test_output.toString();
		
		
		System.out.println(test_string);
	}
    
    /**
	 * Test the hashTable by loading the file
	 * @param filename the file name of the test file, the format should be "./test1". Noted that there is not file format
	 * */
	private static void testTable(String file_location){	
		try {
			zeroPut("test_add.txt");
			zeroSeriallyPut("test_add.txt");
			load(file_location);
			standardPut(file_location);
			seriallyPut(file_location);
			standardRemove(file_location);
			seriallyRemove(file_location);
			zeroRemove(file_location);
			collision(file_location);
			testResize(file_location);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
    
    public static void main(String args[]){
        System.out.println("-----------------------------------------------");      
        System.out.println("Start of Hash Table tests");
        System.out.println("-----------------------------------------------");
 
        testClassStructure(); 
        
        System.out.println("\nTesting Put....");
        try {
			zeroPut("test_add.txt");
			zeroSeriallyPut("test_add.txt");
			standardPut("test");
			seriallyPut("test");
		}catch (Exception e) {
			e.printStackTrace();
		}
        
        System.out.println("\nTesting Remove....");
		try {
			standardRemove("test");
			seriallyRemove("test");
			zeroRemove("test");
		}catch (Exception e) {
			e.printStackTrace();
		}
        
        System.out.println("\nTesting get....");
		try {
			get("test");
		}catch (Exception e) {
			e.printStackTrace();
		}
        
        System.out.println("\nTesting Resize....");
		try {
			testResize("test");
		}catch (Exception e) {
			e.printStackTrace();
		}
        
        
        System.out.println("\nTesting Collision....");
		try {
			collision("test");
		}catch (Exception e) {
			e.printStackTrace();
		}
        
        
        System.out.println("\nRunning testcase....");
		testTable("test");

		try {
			Recommender rec = new Recommender();
			String[] test = new String[7];
			test[0] = "pPhone1";
			test[1] = "kPhone2";
			test[2] = "dPhone3";
			test[3] = "bPhone2";
			test[4] = "ePhone3";
			test[5] = "gPhone1";
			test[6] = "nPhone2";
			rec.inversionCounts("test.txt", test);
			System.out.println("test length is " + test.length);
			for(int i = 0; i < test.length; i++) {
				System.out.print(test[i] + " ");
			}
			System.out.println();
			test = rec.recommend("test.txt", "tPhone3", test);
			for(int i = 0; i < test.length; i++) {
				System.out.print(test[i] + " ");
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
    
    }
    
    
}