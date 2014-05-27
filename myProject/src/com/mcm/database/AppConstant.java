package com.mcm.database;

public class AppConstant {
	
	   // Database details
	   public static String DB_NAME = "MCMMEMBER_DB";
	   public final String DB_VERSION = "1";

/*---------------------------------------TableName-----------------------------------------------------------------*/	   
	   public static String MEMBER_TABLE_NAME = "Member_Table";
	   public static String CLIENT_TABLE_NAME = "Client_Table";
	
	    // Column Name of MemberTableInfo
		public static String MCM_MEMBER_MEMEBER_ID = "member_ID";
		public static String MCM_MEMBER_CLIENT_ID = "client_ID";
		public static String MCM_MEMBER_FIRST_NAME = "first_name";
		public static String MCM_MEMBER_LAST_NAME = "sur_name";
		public static String MCM_MEMBER_EMAIL_ID = "email";
		public static String MCM_MEMBER_PASSWORD = "password";
		
		//Column name of Client Table Info
		
		public static String MCM_CLIENT_CLIENT_ID = "client_ID";
		public static String MCM_CLIENT_MEMBER_ID = "member_ID";
		public static String MCM_CLIENT_FIRST_NAME = "first_name";
		public static String MCM_CLIENT_LAST_NAME = "last_name";
		public static String MCM_CLIENT_CLIENT_CHURCH = "client_church";
		public static String MCM_CLIENT_CLIENT_EMAIL = "client_email";
		public static String MCM_CLIENT_CLIENT_PASSWORD = "client_password";
		
		//Field Constant to pass in intent
		public static final String CHECK_TABLE = "check_table";
}
