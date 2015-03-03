package graphfx;

import java.util.ArrayList;

public class LVRK {

	double x = 10;
	double y = 10;
	double h = .01;
	double t = 0;

	double alpha = .9;
	double beta = .2;
	double delta = .3;
	double gamma = .8;
	
	double upperError = .000001;
	double lowerError = .00000001;
	double endT = 100;
	
	ArrayList<Double> xStore;
	ArrayList<Double> yStore;
	ArrayList<Double> tStore;

	public static void main(String args[]) {
		LVRK run = new LVRK();
		run.RKcalc();
		for(Double xOut : run.xStore){
                    System.out.println(xOut);
                }
	}
        
        public ArrayList<Double> getXarr(){
            return xStore;
        }
        public ArrayList<Double> getYarr(){
            return yStore;
        }
        public ArrayList<Double> getTarr(){
            return tStore;
        }

	private double delXcalc(double t, double y, double x) {
		double out = 0;
		out = alpha * x - beta * x * y;
		return out;
	}

	private double delYcalc(double t, double y, double x) {
		double out = 0;
		out = delta * x * y - gamma * y;
		return out;
	}

	public void RKcalc() {
		xStore = new ArrayList<Double>();
		yStore = new ArrayList<Double>();
		tStore = new ArrayList<Double>();
		long calcTime = System.nanoTime();
		while (t <= endT) {
                    /*
			System.out.println("t = " + t + " x = " + x + " y = " + y);
                    */
			double error = upperError * 2;
			xStore.add(x);
			yStore.add(y);
			tStore.add(t);
			while (!((error <= upperError) && (error >= lowerError))) {
                            /*
				System.out.println("NEW INNER LOOP:");
				System.out.println("error = " + error);
				System.out.println("VALUES HERE: h = " + h + " t = " + t + " x = " + x + " y = " + y);
                            */
				double st = t;
				double sx = x;
				double sy = y;
				//System.out.println("Pre calc temps: " + st + "\t" + sx + "\t" + sy);
				double k1x = h * delXcalc(st, sy, sx);
				double k1y = h * delYcalc(st, sy, sx);
				st = t + (1.0 / 4.0) * h;
				sx = x + (1.0 / 4.0) * k1x;
				sy = y + (1.0 / 4.0) * k1y;
				double k2x = h * delXcalc(st, sy, sx);
				double k2y = h * delYcalc(st, sy, sx);
				st = t + (3.0 / 8.0) * h;
				sx = x 
						+ (3.0 / 32.0) * k1x 
						+ (9.0 / 32.0) * k2x;
				sy = y 
						+ (3.0 / 32.0) * k1y 
						+ (9.0 / 32.0) * k2y;
				double k3x = h * delXcalc(st, sy, sx);
				double k3y = h * delYcalc(st, sy, sx);
				st = t + (12.0 / 13.0) * h;
				sx = x 
						+ (1932.0 / 2197.0) * k1x 
						- (7200.0 / 2197.0) * k2x
						+ (7296.0 / 2197.0) * k3x;
				sy = y 
						+ (1932.0 / 2197.0) * k1y 
						- (7200.0 / 2197.0) * k2y
						+ (7296.0 / 2197.0) * k3y;
				double k4x = h * delXcalc(st, sy, sx);
				double k4y = h * delYcalc(st, sy, sx);
				st = t + h;
				sy = y 
						+ (439.0 / 216.0) * k1y 
						- 8.0 * k2y 
						+ (3680.0 / 513.0) * k3y
						- (845.0 / 4104.0) * k4y;
				sx = x 
						+ (439.0 / 216.0) * k1x 
						- 8.0 * k2x 
						+ (3680.0 / 513.0) * k3x
						- (845.0 / 4104.0) * k4x;
				double k5x = h * delXcalc(st, sy, sx);
				double k5y = h * delYcalc(st, sy, sx);
				st = t + (h / 2.0);
				sx = x 
						- (8.0 / 27.0) * k1x 
						+ 2.0 * k2x 
						- (3544.0 / 2565.0) * k3x
						+ (1859.0 / 4104.0) * k4x 
						- (11.0 / 44.0) * k5x;
				sy = y
						- (8.0 / 27.0) * k1y 
						+ 2.0 * k2y 
						- (3544.0 / 2565.0) * k3y
						+ (1859.0 / 4104.0) * k4y 
						- (11.0 / 44.0) * k5y;
				double k6x = h * delXcalc(st, sy, sx);
				double k6y = h * delYcalc(st, sy, sx);
				/*
				System.out.println("k1x =" + k1x + "\nk1y =" + k1y + "\nk2x =" + k2x
						+ "\nk2y =" + k2y + "\nk3x =" + k3x + "\nk3y =" + k3y
						+ "\nk4x =" + k4x + "\nk4y =" + k4y + "\nk5x =" + k5x
						+ "\nk5y =" + k5y + "\nk6x =" + k6x + "\nk6y =" + k6y);
				*/
				double errory = 
					Math.abs(
							(1.0 / 360.0) * (k1y) 
							- (128.0 / 4275.0) * (k3y)
							- (2197.0 / 75240.0) * (k4y)
							+ (1.0 / 50.0) * (k5y) 
							+ (2.0 / 55.0) * (k6y)
						);
					
				double errorx =
						Math.abs(
									(1.0 / 360.0) * (k1x) 
									- (128.0 / 4275.0) * (k3x)
									- (2197.0 / 75240.0) * (k4x)
									+ (1.0 / 50.0) * (k5x) 
									+ (2.0 / 55.0) * (k6x)
								);
				error = Math.max(errorx, errory);
				
				//System.out.println("error = " + error);
				if (error > upperError) {
					h = h / 2;
					//System.out.println("Above upper error");
				} else if (error < lowerError) {
					h = h * 2;
					//System.out.println("Below upper error");
				} else if (((error <= upperError) && (error >= lowerError))) {
					x = x + (
								(16.0 / 135.0) * k1x 
								+ (6656.0 / 12825.0) * k3x
								+ (28561.0 / 56430.0) * k4x 
								- (9.0 / 50.0) * k5x 
								+ (2.0 / 55.0) * k6x
							);
					y = y + (
								(16.0 / 135.0) * k1y
								+ (6656.0 / 12825.0) * k3y
								+ (28561.0 / 56430.0) * k4y
								- (9.0 / 50.0) * k5y
								+ (2.0 / 55.0)* k6y
							);
					
					t = t + h;
					/*
					System.out.println("Final Values: t = " + t + " x = " + x + " y = " + y + 
							"\nTHESE valueS added to y: \nfor k1y: " + k1y + ","
							+ (16.0 / 135.0) * k1y + "\nfor k3y: "  + k3y + ","
							+ (6656.0 / 12825.0) * k3y + "\nfor k4y: " + k4y + ","
							+ (28561.0 / 56430.0) * k4y + "\nfor k5y: " + k5y + ","
							+ -(9.0 / 50.0) * k5y + "\nfor k6y: " + k6y + ","
							+ (2.0 / 55.0)*k6y);
					*/
					//System.out.println("Final Computation: t = " + t + " x = " + x + " y = " + y);
				}
				//System.out.println("END INNER LOOP\n");
				/*
				innercount++;
				if(innercount >= innerlim){
					break;
				}
				*/
				
			}
			//System.out.println("END Outer LOOP\n");
		}
		calcTime = System.nanoTime() - calcTime;
                /*
		System.out.println("TIME TO RUN: " + calcTime + "ns");
                		for(int i = 0; i<xStore.size();i++){
			System.out.println(xStore.get(i));
		}*/
		
	}
	public String printToMatlabMatrix(String var){
		if(var.equals("t")){
			return matLabFormat(tStore);
		}
		if(var.equals("x")){
			return matLabFormat(xStore);
		}
		if(var.equals("y")){
			return matLabFormat(yStore);
		}
		else return null;
	}
	private String matLabFormat(ArrayList<Double> in){
		String out;
		out = "[";
		for(int i = 0;i<in.size();i++){
			out = out + in.get(i);
			if(i==in.size()-1){
				out += "]";
			}
			else{
				out += " ";
			}
		}
		return out;
	}
}