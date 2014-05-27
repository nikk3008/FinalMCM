package com.mcm;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;

public class MCMApp extends Application
	{
	//FOR learntopics:: 
	ArrayList< HashMap< String,String >  > memberList;
	int responseCodeFromServer =1000;
	public ArrayList< HashMap< String,String >  > getMemberList() 
	    {
	         return memberList;
	    }
	public int getResponseCodeFromServer() 
    {
         return responseCodeFromServer;
    }
	 public void setMemberList(ArrayList< HashMap< String,String >  > topicList) 
			    {
			       this.memberList = memberList;
			    }
	 
			 
	    @Override
	    public void onCreate() 
	    	{
	    	memberList = new  ArrayList< HashMap< String,String >  >();
	    	responseCodeFromServer =1000;
	    	}
	}//end MCMApp class.
