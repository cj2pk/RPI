package com.example.taranair.rpi;

public class Lights {
	private int r;
	private int g;
	private int b;
	
	private int id = 1;
	private double i = 0.5;
	private boolean prop = true;
	
	private void onStart(){
		this.r = 0;
		this.g = 0;
		this.b = 255;
		
		 //String json = "{\"lights\": [{\"lightId\": 1, \"red\":0,\"green\":0,\"blue\":255, \"intensity\": 0.5}],\"propagate\": true}";
		 //return json;
	}

	private void onRoute(double distance, double maxLength){
		//determines percentage of how far user is from destination
		double difference = distance/maxLength;

		//determines algorithm for how lights change
		//scrappy code for now
		if(difference >= .6){
			this.r = 0;
			this.g = 0;
			this.b = 255;
		}
		else if(difference >= .3 && difference <= .6){
			this.r = 0;
			this.g = 255;
			this.b = 0;
		}
		else{
			this.r = 255;
			this.g = 0;
			this.b = 0;
		}
	}

	private void onComplete(){
		this.r = 255;
		this.g = 255;
		this.b = 255;
		
		//String json= "{\"lights\": [{\"lightId\": 1, \"red\":255,\"green\":255,\"blue\":255, \"intensity\": 0.5}],\"propagate\": true}";
		//return json;
	}
	
	public int getR(){
		return this.r;
	}
	
	public int getG(){
		return this.g;
	}
	
	public int getB(){
		return this.b;
	}
	
}
