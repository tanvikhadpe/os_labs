import java.util.Scanner;
public class Example1 {

    public static void main(String[] args) {
    
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of processes: "); //Entering the number of processes
               
        int n = sc.nextInt();
        int bt[] = new int[20]; //Initializing an array for storing Burst Time 

        System.out.println("\nEnter the Burst Time for each process.");       
        for (int i = 0; i < n; i++) {
            System.out.print("\nFor Process " + (i + 1) + ":");
            bt[i] = sc.nextInt();       
        }
        
        avg_wt_tt(n, bt); //Invoking the function to calculate the average waiting and turnaround times.
    }
    
    //function to calculate Waiting Time for each process
    private static void waiting_time(int n, int[] bt, int[] wt) {       
        wt[0] = 0;
        // calculating waiting time

        for (int i = 1; i < n; i++) {           
            wt[i] = bt[i - 1] + wt[i - 1];       
        }
    }

    //function to calculate Turn Around Time
    private static void turnaround_time(int n, int[] bt, int[] wt, int[] tt) {       
        for (int i = 0; i < n; i++) {           
            tt[i] = bt[i] + wt[i]; //Calculating turn around time
        }   
    }

	//function to calculate average waiting time and average turn around time.   
    private static void avg_wt_tt(int n, int[] bt) {       
        int wt[] = new int[n];       
        int tt[] = new int[n];
        
        // Invoking the function for waiting time
        waiting_time(n, bt, wt);
        
        // Invoking the function for turn around time
        turnaround_time(n, bt, wt, tt);

        //Displaying the headings
        System.out.println("\nProcesses ||" + " Burst Time ||" + " Arrival Time ||" + " Waiting Time ||" + " Turn-Around Time ");

        float awt = 0;
        float att = 0;

        for (int i = 0; i < n; i++) {           
            awt = awt + wt[i]; //Calculating the total waiting time for all processes
            att = att + tt[i]; //Calculating the total turn around time for all processes

            //Displaying all the details
            System.out.println(i + 1 + "\t  ||\t" + bt[i] + "\t||\t" + "\t||\t" + wt[i] + "\t||\t " + tt[i]);       
        }
               
        awt = awt / n; //Calculating average waiting time
        att = att / n; //Calculating average turn around time

        System.out.println("\nAverage waiting time = " + awt);       
        System.out.println("\nAverage turn around time = " + att);   
    }
}
