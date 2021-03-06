import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Name: Karthik Ravindra Rao
// USC loginid: raokarth@usc.edu
// Created on Mar 11, 2017 12:45:14 PM
/**
   Project CrowdSource
   Class MajorityVoting.java 
*/

public class MajorityVoting {
	static int k;
	static int q;
	public static Map<Integer, List<List<Double>>> prepareWorkers(List<Double> workersReputation){
		q = workersReputation.size();
		k = q/2;
		k++;
		Map<Integer, List<List<Double>>> map = new HashMap<>();
		List<Double> list;
		for(int i=0;i<Math.pow(2,q);i++)
		{
			list = new ArrayList<>();
			for(int j=0;j<q;j++)
			{
				if((i & (1<<j)) > 0){
						list.add(workersReputation.get(j));
					}
			}
			if(map.containsKey(list.size())){
				List temp = map.get(list.size());
				temp.add(list);
				map.put(list.size(), temp);
			}
			else{
				List<List<Double>> tempList = new ArrayList<>();
				tempList.add(list);
				map.put(list.size(), tempList);
			}
		}
//		for(int i=k; i<=q; i++){
//			System.out.println(map.get(i));
//		}
		return map;
	}
	
	public static double calculateARS(Map<Integer, List<List<Double>>> map, List<Double>workersReputation){
		Double ARS = 0.0;
		for(int i=k; i<=q; i++){
			List<List<Double>> list =  map.get(i);
			for(int j=0; j<list.size(); j++){
				List<Double> temp = list.get(j);
//				System.out.println(temp);
				Double rj = 1.0;
				Double remainingRj = 1.0;
				List<Double> remaining = new ArrayList<>();
				remaining.addAll(workersReputation);
//				System.out.println(remaining);
				for(int l=0; l<temp.size(); l++){
					rj = rj*temp.get(l);
					remaining.remove(temp.get(l));
				}
//				System.out.println(rj);
				for(int z=0; z<remaining.size(); z++){
					remainingRj = remainingRj*(1-remaining.get(z));
				}
//				System.out.println(remainingRj);
				ARS = ARS + (rj * remainingRj);
//				System.out.println((rj * remainingRj));
//				System.out.println(ARS);
			}
		}
		return ARS;
	}
	
	
	public static void main (String args[]){
		workersWithRandomReputationScore();
//		fixedReputationScoreIncreasingWorkers();
//		fixedWorkersIncreasingReputationScore();
		}
	
	//generate ARS for set of workers with random reputation score
		public static void workersWithRandomReputationScore(){
			List <Double> arr = new ArrayList<>();
			double num1=0.0, num2=0.0, num3=0.0;
			arr.add(0.7);
			arr.add(0.7);
			arr.add(0.7);
			double ars;
			for(int i=0; i<10; i++){
				num1 = Math.random();
				num2 = Math.random();
				num3 = Math.random();
				arr.add(num1);
				arr.add(num2);
				arr.add(num3);
				System.out.print(String.format("%.2f", calculateARS(prepareWorkers(arr), arr))+"\t");
				System.out.print(num1+"\t"+num2+"\t"+num3+"\n");
			}
		}
	
	// the following lines keeps on adding workers till required ARS is reached
		public static void fixedReputationScoreIncreasingWorkers(){
			List <Double> arr = new ArrayList<>();
			double ars = 0.0;
			while(ars < 0.9){
				arr.add(0.7);
				ars = calculateARS(prepareWorkers(arr), arr);
				System.out.println(ars+"");
//				System.out.println("Number of Workers : "+arr.size());
			}
		}
	
	// the following increases the reputation score of fixed number of workers till required ARS is reached
		public static void fixedWorkersIncreasingReputationScore(){
			List <Double> arr = new ArrayList<>();
			double ars = 0.0;
			double repScore = 0.1;
			while(ars < 0.9){
				arr = new ArrayList<>();
				arr.add(repScore);
				arr.add(repScore);
				arr.add(repScore);
				ars = calculateARS(prepareWorkers(arr), arr);
				System.out.println("current reputation score : " + repScore);
				System.out.println(ars+"");
				repScore = repScore + 0.1;
		}
	}
	
	
}










