//import java.util.Scanner;  
public class Subordinate {
    
    public static int abs (int a){
        if (a >= 0)
        return a;
        else
        return -a;
    }
    
    public static double abs (double a){
        if (a >= 0)
        return a;
        else
        return -a;
    }

    public static int factorial(int f){
        int i,fct=1; 
        for(i=1;i<=f;i++){    
            fct=fct*i;    
        }    
        return fct;
    }

    public static double sqrt(double x){
        int i = 1;  
        while(true)   
        {  
        //for perfect square numbers  
        if(i*i == x)  
        return i;  
        //for not perfect square numbers  
        else if(i*i > x)   
        //returns the value calculated by the method decimalSqrt()  
        return decimalSqrt(x,i-1,i);  
        i++;  
        }  
        }  
    // recursive method to find the square root of a number up to n decimal places    
    private static double decimalSqrt(double number, double i, double j){  
        // using Newton's method to find a value close to the real value
        double midvalue = (i+j)/2;  
        double square = midvalue * midvalue;  
        //compares the midvalue with square. Accuracy is set to 10 decimal places.   
        if(square==number||abs(square-number)<0.0000000001)   
        return midvalue;   
        //if the square root belongs to second half  
        else if(square>number)  
        return decimalSqrt(number, i, midvalue);  
        //if the square root belongs to first half  
        else  
        return decimalSqrt(number, midvalue, j);  
        }  
    
    // same principle with decimalSqrt function using Newton's method
    public static double pow(double base, double exp){
        double temp = 0;
        //covers prior to decimal point using the property x^y = x^(y/2)^2
        if(exp >= 1){
        temp = pow(base, exp/2);
        return temp * temp;
        }
        //now deal with the fractional part    
        else{
        double low = 0;
        double high = 1.0;
        double sqr = sqrt(base);
        double acc = sqr;
        double mid = high / 2;
        
        // Accuracy is set to 10 decimal points
        while(abs(mid - exp) > 0.0000000001){
            sqr = sqrt(sqr);

            if (mid <= exp){
                low = mid;
                acc *= sqr;
            }
            else{
                high = mid;
                acc *= (1/sqr);
            }
            mid = (low + high) / 2;
        }
        return acc;
    }
    }

    // nth root of x = x^(1/n)
    public double nthroot(double n, double x) 
    {
        // if x is negative, returns error message
        if(x < 0) 
        {
            System.err.println("Negative!");
            return -1;
        }
        if(x == 0) 
            return 0;
        double x1 = x;
        double x2 = x / n;  
        double x3 = x1 - x2;
        if (x3<0)
        x3 = x3*-1;
        while (x3 > 0.0000000001) 
        {
            x1 = x2;
            x2 = ((n - 1.0) * x2 + x / pow(x2, n - 1.0)) / n;
        }
        return x2;
    }

/*    
public static void main(String[] args)   
{  
double base = 0, exp=0;    
Scanner sc = new Scanner(System.in);  
System.out.print("Enter a number: ");   
base = sc.nextDouble();
exp = sc.nextDouble();    
double result = pow(base, exp);  
System.out.println(result);  
}   
*/
}
