package admin.sales.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class testtest {

	public static void main(String args[]){
	String color = "1/2/3/4";
	String size = "a/b/cd";
	String[] colorarr = color.split("");
	String[] sizearr = size.split("");
	List colorList = new ArrayList();
	List sizeList = new ArrayList();
	int colorcnt = 0;
	int sizecnt = 0;
	String divi = "/";
	
	System.out.println(color.contains(divi));
	
	if(color.contains(divi)){
		
		for(int j=0;j<colorarr.length;j++){					
			if(divi.equals(colorarr[j])){colorcnt++;}
		}
		
		for(int k=0;k<colorcnt;k++){
			colorList = Arrays.asList(color.split(divi));
		}		
	}
	
	System.out.println("색상갯수 : "+colorcnt);
	for(int i=0;i<colorList.size();i++){
	System.out.println("colorList : "+colorList.get(i));
	}
	
	
	if(size.contains(divi)){
		
		for(int j=0;j<sizearr.length;j++){					
			if(divi.equals(sizearr[j])){sizecnt++;}
		}
		
		for(int k=0;k<sizecnt;k++){
			sizeList = Arrays.asList(size.split(divi));
		}
		
	}

	System.out.println("사이즈갯수 : "+sizecnt);
	for(int i=0;i<sizeList.size();i++){
	System.out.println("colorList : "+sizeList.get(i));
	}
	
	
	}
}
