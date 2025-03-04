
import java.security.SecureRandom;
import java.lang.reflect.Array;
import java.util.*;

public class smartAr {
    int keyLength;
    int threshold;
    static int orderOfCars;
   
    int size;
    static int noOfCars;
    ArrayList<Car> cars;
    Hashtable<String,Car> hashCar ;
    Hashtable<String,Car> deletedCar;
    static int names = 0;

    public smartAr() {
        cars = new ArrayList<Car>(threshold);
        hashCar = new Hashtable<>();
        deletedCar=new Hashtable<>();
        noOfCars=0;
        orderOfCars=0;
    }

    public void setThreshold() 
    { // Set's the threshold of when to switch from sequence to hashtable
    	Scanner input = new Scanner(System.in);
    	while(true)
    	{
    		System.out.println("Enter the value of the threshold (Range:[100,500000]):");
        	int thresTemp=input.nextInt();
        	if(thresTemp<100||thresTemp>5000000)
        	{
        		System.out.println("Please enter the threshold again");
        		continue;
        	}
        	else
        	{
        		threshold = thresTemp;
        		break;
        	}
    	}
    	
        
    }
    public void setKeyLength()
    {
    	while(true)
    	{
    		System.out.println("Enter the value of keylength");
        	Scanner input =new Scanner(System.in);
        	int keylength=input.nextInt();
        	if(keylength<6||keylength>12)
        	{
        		System.out.println("Please the length again.");
        		continue;
        	}
        	else
        	{
        		keyLength = keylength;
        		break;
     
        	}
    	}
    	
    	
    }
    public void changeSeqToHash()
    {
    		for(int i=0;i<cars.size();i++)
    		{
    			hashCar.put(cars.get(i).getKey(),cars.get(i));
    		}
    		cars.clear();
    	
    }
    public void changeHashToSeq()
    {
    	Set<String> keys = hashCar.keySet();
        for(String tempkey : keys)
        {
            cars.add(hashCar.get(tempkey));
        }
        Collections.sort(cars, new Comparator<Car>() {

			@Override
			public int compare(Car obj1, Car obj2) {
				
				return obj1.order-obj2.order;
			}
        	
        	
        });
    }
    public void generate(int n) 
    {
    		for (int i = 0; i < n; i++) 
            {
                AddKey(generateRandomString(keyLength), "" + names);
            }
    }
    public String nextKey(String key)
    {
    	if(noOfCars>=threshold)
    	{
    		return hashNextKey(key);
    	}
    	else if(noOfCars<threshold)
    	{
    		return seqNextKey(key);
    	}
    	return null;
    }
    public String prevKey(String key)
    {
    	if(noOfCars>=threshold)
    	{
    		return hashPrevKey(key);
    	}
    	else if(noOfCars<threshold)
    	{
    		return seqPrevKey(key);
    	}
    	return null;
    }
    public void printCars()
    {
    	if(noOfCars>=threshold)
    	{
    		System.out.println("hash");
    		printHash();
    		
    	}
    	else if(noOfCars<threshold)
    	{
    		System.out.println("seq");
    		printSeq();
    		
    	}
    }
    public void getValues(String key)
    {
    	if(noOfCars>=threshold)
    	{
    		Set <String> keysHash=hashCar.keySet();
    		for(String tempkey:keysHash)
    		{
    			if(hashCar.get(tempkey).key.equals(key))
    			{
    				System.out.println("Existing user:"+hashCar.get(tempkey).name);
    			}
    		}
    		Set<String> keys=deletedCar.keySet();
    		for(String tempKey:keys)
    		{
    			
    			
    				if(deletedCar.get(tempKey).key.equals(key))
    				{
    					System.out.println("Deleted user :"+deletedCar.get(tempKey).name);
    				}
    			
    		}
    	}
    	else if(noOfCars<threshold)
    	{
    		for(int i=0;i<cars.size();i++)
    		{
    			if(cars.get(i).key.equals(key))
    			{
    				System.out.println("Existing user :"+cars.get(i).name);
    			}
    		}
    		Set<String> keys=deletedCar.keySet();
    		for(String tempKey:keys)
    		{
    			if(deletedCar.contains(tempKey))
    			{
    				if(deletedCar.get(tempKey).key.equals(key))
    				{
    					System.out.println("Deleted user :"+deletedCar.get(tempKey).name);
    				}
    			}
    		}
    		
    		
    	}
    }
    public void printDeletedCars()
    {
        Set<String> keys = deletedCar.keySet();
        for(String tempKey:keys)
        {
            System.out.println("Deleted key:"+tempKey+" and the deleted value:"+deletedCar.get(tempKey).getName()+" and the order:"+deletedCar.get(tempKey).order);
        }
    }
   
    public String hashNextKey(String key)
    {
    	if(hashCar.containsKey(key))
    	{
    		int tempOrder=hashCar.get(key).order;
    		Set<String> keys=hashCar.keySet();
    		for(String tempKeys:keys)
    		{
    			if(hashCar.get(tempKeys).order==(tempOrder+1))
    			{
    				return hashCar.get(tempKeys).getKey();
    			}
    		}
    	}
    	return null;
    }
    public String hashPrevKey(String key)
    {
    	if(hashCar.containsKey(key))
    	{
    		int tempOrder=hashCar.get(key).order;
    		Set<String> keys=hashCar.keySet();
    		for(String tempKeys:keys)
    		{
    			if(hashCar.get(tempKeys).order==(tempOrder-1))
    			{
    				return hashCar.get(tempKeys).getKey();
    			}
    		}
    	}
    	return null;
    }
    public void hashPrevKeys(String key)
    {
    	
    	
    		int order=hashCar.get(key).order;
    		System.out.println("The orederss is:"+order);
    		Set<String> keys=hashCar.keySet();
    		int i=1;
    		for(String tempKeys:keys)
    		{
    			if(hashCar.get(tempKeys).order==i)
    			{
    				System.out.println("The key:"+tempKeys+"  value:"+hashCar.get(tempKeys));
    				i++;
    			}
    			if(i==order)
    				break;
    			
    		}
    	
    }
    public int findByOrder(String key)
    {
    	Set<String> keys=hashCar.keySet();
    	for(String tempKeys:keys)
    	{
    		return hashCar.get(tempKeys).order;
    	}
    	return -1;
    }
    public String seqNextKey(String key) {

        for (Car c : cars) {
            if (c.key.equals(key)) {
                Car n = cars.get(cars.indexOf(c) + 1);
                return n.getKey();
            }
        }
        return "";
    }

    public String seqPrevKey(String key) {

        for (Car c : cars) {
            if (c.key.equals(key)) {
                Car n = cars.get(cars.indexOf(c) - 1);
                return n.getKey();
            }
        }
        return "";
    }

    public String seqGetValues(String key) {

        for (Car c : cars) {
            if (c.key.equals(key)) {
                return c.getName();
            }
        }
        return "";

    }

    public void AddKey(String key, String value) {

        if(noOfCars>threshold)
        {
            hashAddKey(key,value);
        }
        else if (noOfCars<threshold) 
        {
            Car newCar = new Car(key, "" + (names),orderOfCars++);
            names++;
            noOfCars++;
            cars.add(newCar);
        }
        else if(noOfCars==threshold)
        {
        	changeSeqToHash();
        	hashAddKey(key,value);
        }
    }
    public void hashAddKey(String key,String value){

        Car newCar = new Car(key,value,orderOfCars++);
        names++;
        noOfCars++;
        hashCar.put(key,newCar);

    }
    public void addDeletedCar(String key ,String value,int order)
    {
        deletedCar.put(key,new Car(key,value,order));
    }
    public void Remove(String key) {

    	if(noOfCars>threshold)
    	{
    		hashRemove(key);
    	}
    	else if(noOfCars<threshold)
    	{
    		for (int i = 0; i < cars.size(); i++) {
                Car temp = cars.get(i);
                if (temp.key.equals(key)) {
                    cars.remove(temp);
                    addDeletedCar(key,temp.name,temp.order);
                    noOfCars--;
                }
            }
    	}
    	else if(noOfCars==threshold)
    	{
    		changeHashToSeq();
    		for (int i = 0; i < cars.size(); i++) {
                Car temp = cars.get(i);
                if (temp.key.equals(key)) {
                    cars.remove(temp);
                    addDeletedCar(key,temp.name,temp.order);
                    noOfCars--;
                }
            }
    	}
        

    }
    public void hashRemove(String key)
    {
        Set<String> keys = hashCar.keySet();
        for(String tempkey: keys){
            if(tempkey.equals(key)){
                deletedCar.put(tempkey,new Car(tempkey,hashCar.get(tempkey).name,hashCar.get(tempkey).order));
                hashCar.remove(key);
                noOfCars--;
               
                break;
            }
        }

    }
    public ArrayList<String> previousCars(String key)
    {
    	
    	int index=0;
        if(noOfCars<threshold)
        {
        	ArrayList<String> temp=new ArrayList<String>();
        	for(int i =0; i< cars.size();i++){
                Car t = cars.get(i);
                    if(t.key.equals(key)){
                        index = cars.indexOf(t);
                    }
            }
            for (int i = 0; i < index; i++) 
            {
                temp.add(cars.get(i).getKey());
            }
            return temp;
                       
        }
        else if(noOfCars>=threshold)
        {
        	
        	if(hashCar.containsKey(key))
        	{
        		int order=hashCar.get(key).order;
        		ArrayList<String> temp=new ArrayList<String>(order);
        		String[] Prevkeys=new String[order];
        		System.out.println("the order is"+order);
        		Set<String> keys=hashCar.keySet();
        		for(String tempKeys:keys)
        		{
        			if(hashCar.get(tempKeys).order<order)
        			{
        				Prevkeys[hashCar.get(tempKeys).order]=hashCar.get(tempKeys).key;
        			}
        			
        		}
        		for(int i=0;i<Prevkeys.length;i++)
        		{
        			temp.add(Prevkeys[i]);
        		}
        		return temp;
        	}
        	
        }
            return null;
        	
     }
    public void printSeq() {

        for (int i = 0; i < cars.size(); i++) {
            System.out.println(cars.get(i).toString());
        }
    }
    public void printHash() {
        Set<String> keys = hashCar.keySet();
        for(String key: keys) {
            System.out.println("This car has key of " + key + " and value is: " + hashCar.get(key).name+" the order is :"+hashCar.get(key).order);
        }
    }



    public String generateRandomString(int length) {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";

        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();

        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }
        if(isPresent(sb.toString())==false)
        {
        	return sb.toString();
        }
        else
        {
        	generateRandomString(length);
        }
        return null;
    }
    public boolean isPresent(String key)
    {
    	if(noOfCars<threshold)
    	{
    		return cars.contains(key);
    	}
    	else if(noOfCars>=threshold)
    	{
    		return hashCar.containsKey(key);
    	}
    	return false;
    }
    public ArrayList<String> allKeys() {
		if (noOfCars<threshold) 
		{
			ArrayList<String> keys = new ArrayList<String>();

			for (int i = 0; i < cars.size(); i++) {
				keys.add(cars.get(i).getKey());
			}
			Collections.sort(keys);
			return keys;
		}
		else if (noOfCars>=threshold) 
		{
			ArrayList<String> keys = new ArrayList<String>(hashCar.keySet());
			Collections.sort(keys);
			return keys;
		}
		return null;
	}
   
    

}

