
package com.rmsi.mast.studio.domain;


import java.util.ArrayList;
import java.util.List;


public class vertexLabel {
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	
	
	private int gid;
	
	private String x;
	
	private String y;
	

	

	
	public vertexLabel(int gid, String x, String y){
	      this.gid = gid;
	      this.x = x;
	      this.y = y;
	  }		
		
		public List<String> getMappedvertex(){
			
			ArrayList<String> tmp=new ArrayList<String>();
			
			tmp.add(0,String.valueOf(gid));
			tmp.add(1,x);
			tmp.add(2,y);
			
			
			return tmp; 
			
			
		}
	
	
}