package com.example.taranair.rpi;

public class Lights {
    public int r;
    public int g;
    public int b;

    public int lightId = 1;
    public double intensity = 0.5;

    public void onStart(){
        this.r = 0;
        this.g = 0;
        this.b = 255;
    }

    public void lightsChangePos(){
        this.r = 0;
        this.g = 255;
        this.b = 0;
    }

    public void lightsChangeNeg(){
        this.r = 255;
        this.g = 0;
        this.b = 0;
    }

    public void onRoute(){
        this.r = 0;
        this.g = 0;
        this.b = 255;
    }

    public void halfwayPoint(){
        this.r = 255;
        this.g = 255;
        this.b = 0;
    }

    public void arrived(){
        this.r = 255;
        this.g = 255;
        this.b = 255;
    }
	
	/*
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
		else if(difference <= .3 && difference > 0.05){
			this.r = 255;
			this.g = 0;
			this.b = 0;
		}
		else{
			this.r = 255;
			this.g = 255;
			this.b = 255;
		}
	}*/

    public int getR(){
        return this.r;
    }

    public int getG(){
        return this.g;
    }

    public int getB(){
        return this.b;
    }

    public void setR(int num){
        this.r = num;
    }

    public void setG(int num){
        this.g = num;
    }

    public void setB(int num){
        this.b = num;
    }

}
