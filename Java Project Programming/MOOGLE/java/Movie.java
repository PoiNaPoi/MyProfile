// Name: Natakorn Hongharn
// Student ID: 6188028
// Section: 1

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Movie {
	private int mid;
	private String title;
	private int year;
	private Set<String> tags;
	private Map<Integer, Rating> ratings;	//mapping userID -> rating
	private Double avgRating;
	//additional
	Integer num = 0;
	
	public Movie(int _mid, String _title, int _year){
		// YOUR CODE GOES HERE
		mid = _mid;
		title = _title;
		year = _year;
		ratings = new HashMap<>();
		tags = new HashSet<>();
		avgRating = -1.0;
	}
	
	public int getID() {
		// YOUR CODE GOES HERE
		return mid;
	}
	
	public String getTitle(){
		// YOUR CODE GOES HERE
		return title;
	}
	
	public Set<String> getTags() {
		// YOUR CODE GOES HERE
		Set<String> x = new HashSet<>();
		for(String i:tags) {
			x.add(i.toString());
		}
		return tags;
	}
	
	public void addTag(String tag){
		// YOUR CODE GOES HERE
		tags.add(tag);
	}
	
	public int getYear(){
		// YOUR CODE GOES HERE
		return year;
	}
	
	public String toString()
	{
		return "[mid: "+mid+":"+title+" ("+year+") "+tags+"] -> avg rating: " + avgRating + ", #ratings: " + num;
	}
	
	public double calMeanRating(){
		// YOUR CODE GOES HERE
		double mean = 0;
		double num = 0;
		double avg = 0;
		if(ratings.isEmpty())
			return -1;
		else {
			for(Integer i:ratings.keySet()) {
				mean += ratings.get(i).rating;
				num++;
			}
			avg = mean/num;
			return avg;
		}
	}
	
	public Double getMeanRating(){
		// YOUR CODE GOES HERE
		if(calMeanRating()>0)
			return calMeanRating();
		else
			return -1.0;
	}
	
	public void addRating(User user, Movie movie, double rating, long timestamp) {
		// YOUR CODE GOES HERE
		boolean swit = true;
		if(user!=null&&movie!=null&&rating!=0&&timestamp!=0) {
			for(Integer i:ratings.keySet()) {
				if(ratings.get(i).u.uid==user.uid) {
					swit = false;
					if(ratings.get(i).timestamp<timestamp) {
						ratings.remove(i);
						ratings.put(i, new Rating(user, movie, rating, timestamp));
						num--;
					}
				}
			}
			if(swit)
				ratings.put(num, new Rating(user, movie, rating, timestamp));
			num++;
		}
		avgRating = getMeanRating();
	}
	
	public Map<Integer, Rating> getRating(){
		// YOUR CODE GOES HERE
		return ratings;
	}
	
}
