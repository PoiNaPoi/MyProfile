// Name: Natakorn Hongharn
// Student ID: 6188028
// Section: 1

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleMovieSearchEngine implements BaseMovieSearchEngine {
	public Map<Integer, Movie> movies = new HashMap<>();
	
	@Override
	public Map<Integer, Movie> loadMovies(String movieFilename) {
		// YOUR CODE GOES HERE
		Map<Integer, Movie> movie = new HashMap<>();
		String b = null;
		String[] line = null;
		String[] type = null;
		String year = null;
		String title = null;
		boolean check = true;
		Matcher mat = null;
		Pattern pat = Pattern.compile("\\W[0-9][0-9][0-9][0-9]\\W");
		try {
			FileReader file = new FileReader(movieFilename);
			BufferedReader buf = new BufferedReader(file); 
			buf.readLine();
			while((b=buf.readLine())!=null) {
				check = true;
				line = b.split(",");
				if(line[0]!=null&&line[1]!=null&&line[2]!=null) {
					if(line.length==3) {
						title = line[1].substring(0,line[1].length()-7);
						mat = pat.matcher(line[1]);
						if(mat.find()) {
							year = mat.group();
							pat = Pattern.compile("[0-9][0-9][0-9][0-9]");
							mat = pat.matcher(year);
							if(mat.find()) {
								year = mat.group();
							}
						}
					}
					else {	
						title = line[1] +"," + line[2];
						title = title.substring(1,title.length()-7);
						mat = pat.matcher(line[2]);
						if(mat.find()) {
							year = mat.group();
							pat = Pattern.compile("[0-9][0-9][0-9][0-9]");
							mat = pat.matcher(year);
							if(mat.find()) {
								year = mat.group();
							}
						}
					}
					for(Integer k:movies.keySet()) {
						if(Integer.parseInt(line[0])!=movies.get(k).getID())
							check = false;
					}
					if(check)
						movie.put(Integer.parseInt(line[0]), new Movie(Integer.parseInt(line[0]),title.trim(),Integer.parseInt(year)));
					if(!line[line.length-1].equals(null)) {
						type = line[line.length-1].split("\\|");
						for(String x: type) {
							movie.get(Integer.parseInt(line[0])).addTag(x.trim());
						}
					}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return movie;
	}

	@Override
	public void loadRating(String ratingFilename) {
		// YOUR CODE GOES HERE
		String b = null;
		String[] line = null;
		try {
			FileReader file = new FileReader(ratingFilename);
			BufferedReader buf = new BufferedReader(file);
			buf.readLine();
			while((b=buf.readLine())!=null) {
				line = b.split(",");
				movies.get(Integer.parseInt(line[1])).addRating(new User(Integer.parseInt(line[0])), movies.get(Integer.parseInt(line[1])), Double.parseDouble(line[2]), Long.parseLong(line[3]));
//				movies.get(Integer.parseInt(line[1])).getMeanRating();
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadData(String movieFilename, String ratingFilename) {
		// YOUR CODE GOES HERE
		movies = loadMovies(movieFilename);
		loadRating(ratingFilename);
//------------------BONUS!!!!!----------------------------------------------------
		//Print DATA.txt for show list movies and ratings
		Map<Integer, Rating> ratings;
		int numRatings = 0;
		File f = new File("DATA.txt");
		try {
			FileWriter write = new FileWriter(f,false);
			for(Integer key: movies.keySet()){
				write.write(movies.get(key).toString()+"\n");
				ratings = movies.get(key).getRating();
				numRatings += ratings.size();
				for(Integer uid: ratings.keySet()){
					write.write("   " + ratings.get(uid).toString()+"\n");
				}
			}
			write.write("************************************\n");
			write.write("Total number of movies: " + movies.size()+"\n");
			write.write("Total number of ratings: " + numRatings+"\n");
			write.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//------------------BONUS!!!!!----------------------------------------------------
	}

	@Override
	public Map<Integer, Movie> getAllMovies() {
		// YOUR CODE GOES HERE
		return movies;
	}

	@Override
	public List<Movie> searchByTitle(String title, boolean exactMatch) {
		// YOUR CODE GOES HERE
		List<Movie> movie = new ArrayList<>();
		if(title!=null) {
			for(Integer key:movies.keySet()) {
				if(exactMatch==false&&movies.get(key).getTitle().toLowerCase().contains(title)) {
						movie.add(movies.get(key));
					}
				else if(exactMatch==true&&title.equals(movies.get(key).getTitle().toLowerCase())) {
					movie.add(movies.get(key));
				}
			}
		}
		else {
			for(Integer key:movies.keySet()) {
				movie.add(movies.get(key));
			}
		}
		return movie;
	}

	@Override
	public List<Movie> searchByTag(String tag) {
		// YOUR CODE GOES HERE
		List<Movie> movie = new ArrayList<>();
		Set<String> tagg = null;
		if(tag!=null) {
			for(Integer key:movies.keySet()) {
				tagg = movies.get(key).getTags();
				for(String x:tagg) {
					if(tag.equals(x)) {
						movie.add(movies.get(key));
					}
				}
			}
		}
		else {
			for(Integer key:movies.keySet()) {
				movie.add(movies.get(key));
			}
		}
		return movie;
	}

	@Override
	public List<Movie>searchByYear(int year) {
		// YOUR CODE GOES HERE
		List<Movie> movie = new ArrayList<>();
		if(year!=-1) {
			for(Integer key:movies.keySet()) {
				if(movies.get(key).getYear()==year) {
					movie.add(movies.get(key));
				}
			}
		}
		else {
			for(Integer key:movies.keySet()) {
				movie.add(movies.get(key));
			}
		}
		return movie;
	}

	@Override
	public List<Movie> advanceSearch(String title, String tag, int year) {
		// YOUR CODE GOES HERE
		List<Movie> movie = new ArrayList<>();
		Set<String> tagg = null;
		String title0 = null;
		int year0 = 0;
		for(Integer key:movies.keySet()) {
			tagg = movies.get(key).getTags();
			if(title==null) {
				title0 = movies.get(key).getTitle().toLowerCase();
			}
			else {
				title0 = title;
			}
			if(year==-1) {
				year0 = movies.get(key).getYear();
			}
			else {
				year0 = year;
			}
			for(String x:tagg) {
				if(tag.equals(x)&&movies.get(key).getTitle().toLowerCase().contains(title0)&&movies.get(key).getYear()==year0) {
					movie.add(movies.get(key));
				}
			}
		}
		return movie;
	}
	
	@Override
	public List<Movie> sortByTitle(List<Movie> unsortedMovies, boolean asc) {
		// YOUR CODE GOES HERE
		for(int x=0;x<unsortedMovies.size();x++) {
			for(int y=0;y<unsortedMovies.size();y++) {
				if(asc) {
					if(unsortedMovies.get(x).getTitle().compareTo(unsortedMovies.get(y).getTitle())<0) {
						Collections.swap(unsortedMovies, x, y);
					}
				}
				else {
					if(unsortedMovies.get(x).getTitle().compareTo(unsortedMovies.get(y).getTitle())>0) {
						Collections.swap(unsortedMovies, x, y);
					}
				}
			}
		}
		return unsortedMovies;
	}

	@Override
	public List<Movie> sortByRating(List<Movie> unsortedMovies, boolean asc) {
		// YOUR CODE GOES HERE
		List<Movie> sort = new ArrayList<>();
		double m = 0;
		int po = 0;
		if(!asc) {
			while(!unsortedMovies.isEmpty()) {
				m = 0;
				for(int x=0;x<unsortedMovies.size();x++) {
					if(unsortedMovies.get(x).getMeanRating()>m) {
						m = unsortedMovies.get(x).getMeanRating();
						po = x;
					}
				}
				sort.add(unsortedMovies.get(po));
				unsortedMovies.remove(po);
			}
		}
		else {
			while(!unsortedMovies.isEmpty()) {
				m = 10;
				for(int x=0;x<unsortedMovies.size();x++) {
					if(unsortedMovies.get(x).getMeanRating()<m) {
						m = unsortedMovies.get(x).getMeanRating();
						po = x;
					}
				}
				sort.add(unsortedMovies.get(po));
				unsortedMovies.remove(po);
			}
		}
		return sort;
	}
}
