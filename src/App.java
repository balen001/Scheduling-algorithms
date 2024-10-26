import java.util.*;

//Author: Balen Ahmed Wasu
//Date: 19/5/2023
public class App {
    public static void main(String[] args) throws Exception {
       
        int NoTasks;

        System.out.print("Enter the number of tasks: ");
        Scanner scan = new Scanner(System.in);
        NoTasks =scan.nextInt();
        
      
        
        int[] arrTime = new int[NoTasks];
        int[] bursTime = new int[NoTasks];
        int[] priority = new int[NoTasks];
        int[] Tid = new int[NoTasks];


        

        for(int i = 0; i < NoTasks; i++){

            System.out.print("Enter the arrival time: ");
            arrTime[i] = scan.nextInt();
            System.out.print("Enter the burst time: ");
            bursTime[i] = scan.nextInt();
            System.out.print("Enter the priority: ");
            priority[i] = scan.nextInt();

            Tid[i] = i + 1;
        }
        


        sortByArrivalTime(Tid, arrTime, bursTime, priority); // sort by arrival time


        int[] waitingTime = new int[Tid.length];
        int[] completionTime = new int[Tid.length];
        int[] TurnArround = new int[Tid.length];

        double TAT = 0;
        double TWT = 0;
    
        completionTime[0] = bursTime[0]; 
        System.out.println("\nTask-" + Tid[0] + " started at: " + arrTime[0] + " | and completed at: " + completionTime[0]);
        TAT += TurnArround[0] = completionTime[0] - arrTime[0];
        TWT += waitingTime[0] = TurnArround[0] - bursTime[0];

        for(int i = 1 ; i < Tid.length; i++){

            if(arrTime[i] < completionTime[i-1]){
                completionTime[i] = completionTime[i-1] + bursTime[i];
                System.out.println("Task-" + Tid[i] + " started at: " + completionTime[i-1] + " | and completed at: " + completionTime[i]);

            }
            else{
                completionTime[i] = arrTime[i] + bursTime[i];
                System.out.println("Task-" + Tid[i] + " started at: " + arrTime[i] + " | and completed at: " + completionTime[i]);
            }

            TAT += TurnArround[i] = completionTime[i] - arrTime[i];
            TWT += waitingTime[i] = TurnArround[i] - bursTime[i];

            
        }

        System.out.println("\n\n------------------------------\nFor FCFS algorithm\n");
        System.out.println("Average Turnaround time = " + TAT / NoTasks);
        System.out.println("Average Waiting time = " + TWT / NoTasks);


        


       
        //----------------------------------------------------------------------------------------------------



        System.out.println("\n\n------------------------------\nFor Priority based algorithm\n");


        int[] tempBurs = new int[NoTasks];
        System.arraycopy(bursTime, 0, tempBurs, 0, tempBurs.length);

        int[] pri = new int[NoTasks];
        System.arraycopy(priority, 0, pri, 0, pri.length);

        int[] CT = new int[NoTasks]; //completion time
        int[] TT = new int[NoTasks]; //Turnaround time

        int totTime = 0;
        double totTurnArround = 0;
        double totWaitingTime = 0;





        ArrayList<Integer> finishedTasks = new ArrayList<Integer>();


       
        sortByPriority(pri); // sort the priority array


        



        boolean flag = true;
        int index = 0;
        int currHigh = 0;
        while(true){

            for(int j = 1; j < NoTasks && flag; j++){

                if(arrTime[index] + tempBurs[index] > arrTime[j] && flag){
                    if(priority[j] > priority[index] ){   // higher no. higher priority

                        totTime = totTime - ( arrTime[index] - arrTime[j] );
                        System.out.println("Task-" + Tid[index] + " Preempted at: " + totTime);
                        tempBurs[index] = tempBurs[index] + ( arrTime[index] - arrTime[j] );
                        index = j;
                        
                    }
                    

                    if(tempBurs[index] == 0){
                        CT[index] = totTime;
                        finishedTasks.add(index);
                        System.out.println("Task-" + Tid[index] + " Preempted at: " + totTime);
                    }

                    if(finishedTasks.size() == NoTasks){
                        System.out.println("Task-" + Tid[index] + " Preempted at: " + totTime);
                        break;
                        
                    }

                    

                }


                else if (pri[currHigh] == priority[index]){

                            totTime = totTime + tempBurs[index];
                            tempBurs[index] = 0;
                            CT[index] = totTime;
                            finishedTasks.add(index);
                            flag = false;
                            currHigh++;
                            System.out.println("Task-" + Tid[index] + " Preempted at: " + totTime);
                            break;
                            
                    }
                        

                }

            
                if(pri[currHigh] == priority[index] && currHigh < pri.length && !finishedTasks.contains(index)){

                    totTime = totTime + tempBurs[index];
                    tempBurs[index] = 0;
                    CT[index] = totTime;
                    finishedTasks.add(index);
                    System.out.println("Task-" + Tid[index] + " Preempted at: " + totTime);
                    flag = false;
                    currHigh++;
                    index = -1;


                    
                }

                index++;


                if(finishedTasks.size() == NoTasks){break;}



             






                
            }

            System.out.println();

            

            
            for(int p = 0 ; p < Tid.length ; p++){
                
                TT[p] = CT[p] - arrTime[p];
                totTurnArround += TT[p];

                waitingTime[p] = TT[p] - bursTime[p];
                totWaitingTime += waitingTime[p];
                
            }


            System.out.println("Average Turnaround time = " + totTurnArround/NoTasks);
            System.out.println("Average Waiting time = " + totWaitingTime/NoTasks);


            System.out.println("\n\n");

            
           
            

            scan.close();

        }











        


    

    

    public static void sortByArrivalTime(int[] Tid, int[] arrTime, int[] bursTime, int[] priority ){  

        for (int i = 0; i < Tid.length - 1; i++)  
        {  
            int index = i;  
            for (int j = i + 1; j < Tid.length; j++){  
                if (arrTime[j] < arrTime[index]){  
                    index = j;//searching for lowest index  
                } 
                if (arrTime[j] == arrTime[index]){
                    if(Tid[j] < Tid[index]){
                        index = j;
                    }
                    
                } 
            }  
            int shorterTime = arrTime[index];   
            int burst = bursTime[index];
            int pri = priority[index];
            int tid = Tid[index];

            arrTime[index] = arrTime[i];
            Tid[index] = Tid[i];
            bursTime[index] = bursTime[i];
            priority[index] = priority[i];

            arrTime[i] = shorterTime; 
            bursTime[i] = burst;  
            priority[i] = pri;  
            Tid[i] = tid;  

        }

    } 



    public static void sortByPriority(int[] priority ){  

        for (int i = 0; i < priority.length - 1; i++)  
        {  
            int index = i;  
            for (int j = i + 1; j < priority.length; j++){  
                if (priority[j] > priority[index]){  // highest no. = highest priority
                    index = j;//searching for lowest index  
                }  
            }  
           
            int pri = priority[index];
            priority[index] = priority[i];
            priority[i] = pri;  
             

        }

    }
}
