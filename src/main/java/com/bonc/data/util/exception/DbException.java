package com.bonc.data.util.exception;

public class DbException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DbException(){
		super();
	}
	
	public DbException(String msg){
		super(msg);
	}

}
