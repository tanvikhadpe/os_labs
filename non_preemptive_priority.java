import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
class Process {
 String name;
 int burstTime;
 int arrivalTime;
 int priority;
public Process(String name, int burstTime, int arrivalTime, int priority) {
 this.name = name;
 this.burstTime = burstTime;
 this.arrivalTime = arrivalTime;
 this.priority = priority;
 }
}
class CPUScheduler {
public static void main(String[] args) {
 Scanner scanner = new Scanner(System.in);
 System.out.print("Enter the number of processes: ");
 int n = scanner.nextInt();
 List<Process> processes = new ArrayList<>();
 int[] waitingTime = new int[n];
 int[] turnaroundTime = new int[n];
 for (int i = 0; i < n; i++) {
 System.out.print("Enter process name: ");
 String name = scanner.next();
 System.out.print("Enter burst time for " + name + ": ");
 int burstTime = scanner.nextInt();
 System.out.print("Enter arrival time for " + name + ": ");
 int arrivalTime = scanner.nextInt();
 System.out.print("Enter priority for " + name + ": ");
 int priority = scanner.nextInt();
 processes.add(new Process(name, burstTime, arrivalTime, priority));
 }
 // Sort processes by arrival time
 processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
 int currentTime = 0;
 int completed = 0;
 Queue<Process> queue = new LinkedList<>();
 while (completed < n) {
 for (int i = 0; i < n; i++) {
 Process process = processes.get(i);
 if (process.arrivalTime <= currentTime) {
 queue.offer(process);
 }
 }
 if (!queue.isEmpty()) {
 Process selectedProcess = null;
 for (Process process : queue) {
 if (selectedProcess == null || process.priority < selectedProcess.priority) {
 selectedProcess = process;
 }
 }
 queue.remove(selectedProcess);
 int index = processes.indexOf(selectedProcess);
 turnaroundTime[index] = currentTime + selectedProcess.burstTime -
selectedProcess.arrivalTime;
 waitingTime[index] = turnaroundTime[index] - selectedProcess.burstTime;
 currentTime += selectedProcess.burstTime;
 completed++;
 } else {
 currentTime++;
 }
 }
 // Printing the Gantt chart
 System.out.println("Gantt Chart:");
 int prevTime = 0;
 for (Process process : processes) {
 System.out.print("| " + process.name + " ");
 prevTime += turnaroundTime[processes.indexOf(process)];
 for (int i = 0; i < prevTime - process.arrivalTime; i++) {
 System.out.print(" ");
 }
 }
 System.out.println("|");
 // Printing waiting time and turnaround time for each process
 System.out.println("Process\tWaiting Time\tTurnaround Time");
 for (int i = 0; i < n; i++) {
 Process process = processes.get(i);
 System.out.println(process.name + "\t" + waitingTime[i] + "\t\t" + turnaroundTime[i]);
 }
 // Calculate and print average waiting time and average turnaround time
 int totalWaitingTime = Arrays.stream(waitingTime).sum();
 int totalTurnaroundTime = Arrays.stream(turnaroundTime).sum();
 double avgWaitingTime = (double) totalWaitingTime / n;
 double avgTurnaroundTime = (double) totalTurnaroundTime / n;
 System.out.println("Average Waiting Time: " + avgWaitingTime);
 System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
 // Now schedule the same priority processes with Round-Robin scheduling
 int timeQuantum = 2;
 roundRobinScheduling(processes, waitingTime, turnaroundTime, timeQuantum);
 }
 public static void roundRobinScheduling(List<Process> processes, int[] waitingTime, int[] 
turnaroundTime, int timeQuantum) {
 int n = processes.size();
 int[] remainingTime = new int[n];
 int currentTime = 0;
 for (int i = 0; i < n; i++) {
 remainingTime[i] = processes.get(i).burstTime;
 }
 Queue<Process> queue = new LinkedBlockingQueue<>();
 int completed = 0;
 while (completed < n) {
 for (int i = 0; i < n; i++) {
 Process process = processes.get(i);
 if (process.arrivalTime <= currentTime && remainingTime[i] > 0) {
 queue.offer(process);
 }
 }
 if (!queue.isEmpty()) {
 Process currentProcess = queue.poll();
 if (remainingTime[processes.indexOf(currentProcess)] > timeQuantum) {
 currentTime += timeQuantum;
 remainingTime[processes.indexOf(currentProcess)] -= timeQuantum;
 queue.offer(currentProcess);
 } else {
 currentTime += remainingTime[processes.indexOf(currentProcess)];
 remainingTime[processes.indexOf(currentProcess)] = 0;
 completed++;
 int completionTime = currentTime;
 turnaroundTime[processes.indexOf(currentProcess)] = completionTime -
currentProcess.arrivalTime;
 waitingTime[processes.indexOf(currentProcess)] = 
turnaroundTime[processes.indexOf(currentProcess)] - currentProcess.burstTime;
 }
 } else {
 currentTime++;
 }
 }
 // Printing waiting time and turnaround time for each process after Round-Robin scheduling
 System.out.println("Round-Robin Scheduling Results:");
 System.out.println("Process\tWaiting Time\tTurnaround Time");
 for (int i = 0; i < n; i++) {
 Process process = processes.get(i);
 System.out.println(process.name + "\t" + waitingTime[i] + "\t\t" + turnaroundTime[i]);
 } 
 } 
 }
