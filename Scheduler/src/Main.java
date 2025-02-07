import java.util.*;
import java.util.ArrayList;
class Process{
    int pid;
    int waitingTime;
    int arrivalTime;
    int burstTime;
    int turnAroundTime;
    int completionTime = 0;
    int priority;
    int comesBackAfter;
    int remainingBurstTime;
    int totalBurstTime =0;
     int totalComesBackAfter =0;
    boolean porcesseIn = false;
    int remainingQuantum = 0;
    int ppriorty;

    Process(int pid, int arrivalTime, int burstTime, int comesBackAfter, int priority){
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.comesBackAfter = comesBackAfter;
         this.priority = priority;
        this.remainingBurstTime = burstTime;
        this.ppriorty = priority;
    }
}

public class Main {
    static int time = 200;
    static boolean processorIn = false;

    public static void main(String[] args) {
        Scanner  scan = new Scanner(System.in);
       int choice;

     while (true){
         System.out.println("1-FCFS Algorithm ");
         System.out.println("2-SJF Algorithm ");
         System.out.println("3-SRTF Algorithm ");
         System.out.println("4-Round Robin Algorithm ");
         System.out.println("5-Preemptive Priority");
         System.out.println("6-Non-preemptive Priority ");
         System.out.println("7-Exit ");
         System.out.print("Enter your choice : ");
         choice = scan.nextInt() ;
       ArrayList <Process>myProcesses = new ArrayList<Process>();
        myProcesses.add(new Process(1,0,10,2,3));
        myProcesses.add(new Process(2,1,8,4,2));
        myProcesses.add(new Process(3,3,14,6,3));
        myProcesses.add(new Process(4,4,7,8,1));
        myProcesses.add(new Process(5,6,5,3,0));
        myProcesses.add(new Process(6,7,4,6,1));
        myProcesses.add(new Process(7,8,6,9,2));

        switch (choice){
            case 1:
        FCFS(myProcesses);
        break;
            case 2:
        SJF(myProcesses);
        break;
            case 3:
        SRTF(myProcesses);
        break;
            case 4:
        roundRobin(myProcesses,5);
        break;
            case 5:
                preemPriority(myProcesses,5);
                break;
            case 6:
                noPreemPriority(myProcesses,5);
                break;
            case 7:
                return;

        }

    }


    }

    static void FCFS(ArrayList<Process> myProcesses) {
        System.out.println("FCFS Algorithm :");
        System.out.println("---------------------------------------------------------------------");
        ArrayList<Process> Processes = new ArrayList<Process>();
        Processes.addAll(myProcesses);
        ArrayList<Process> WaitingQueue = new ArrayList<Process>();
        ArrayList<Process> ReadyQueue = new ArrayList<Process>();
        Process executingProcess = null;
        int totalTurnAroundTime = 0;
        double avgWaitingTime;
        double avgTurnAroundTime;
        int totalWaitingTime = 0;
        int totalTime = 0;
        int index = 0;
        boolean processorIn = false;
        for(; totalTime <= time ; totalTime++){
            for (int i = 0 ; i < Processes.size(); i++){
                if(Processes.get(i).arrivalTime == totalTime){
                    ReadyQueue.add(Processes.remove(i));
                }
            }
            if(!processorIn && !ReadyQueue.isEmpty()){
                executingProcess =  ReadyQueue.get(index);
                processorIn = true;
            }
            for (int i = 0; i < WaitingQueue.size(); i++) {
                Process process = WaitingQueue.get(i);
                if (totalTime - process.completionTime >= process.comesBackAfter) {
                    WaitingQueue.get(i).totalComesBackAfter += WaitingQueue.get(i).comesBackAfter;
                    ReadyQueue.add(WaitingQueue.remove(i));
                }
            }

            if(processorIn){
                executingProcess.totalBurstTime++;
                executingProcess.remainingBurstTime--;

                if(executingProcess.remainingBurstTime == 0 ){

                    executingProcess.remainingBurstTime=executingProcess.burstTime;
                    if (totalTime == 200){executingProcess.completionTime = totalTime;}else {
                    executingProcess.completionTime = totalTime +1 ;}

                    processorIn = false;

                    WaitingQueue.add(executingProcess);

                    if(executingProcess.porcesseIn)
                        for (Process pp : Processes){
                            if(pp.pid ==  executingProcess.pid){
                                pp.completionTime = executingProcess.completionTime;
                                pp.totalBurstTime = executingProcess.totalBurstTime;
                            }
                        }
                    if (!executingProcess.porcesseIn){
                        Processes.add(executingProcess);
                        executingProcess.porcesseIn =true;
                    }

                    System.out.print("P" + executingProcess.pid +"|"+ executingProcess.totalBurstTime+"|"+ "-->" + executingProcess.completionTime + "--");

                    index++;
                    if(index == 7)
                        index = 0;
                }

            }
        }
       System.out.println("\n");

        for (Process p : Processes) {

            p.turnAroundTime = p.completionTime - p.arrivalTime;

            p.waitingTime= p.turnAroundTime - p.totalBurstTime  - p.totalComesBackAfter;


                System.out.println("P" + p.pid + "|"+  "\tTAT:" + p.turnAroundTime  +"|"+ "\tWT:" + p.waitingTime+"|");


            totalWaitingTime += p.waitingTime;
            totalTurnAroundTime += p.turnAroundTime;

        }

        avgWaitingTime = (double) totalWaitingTime /Processes.size();
        avgTurnAroundTime = (double) totalTurnAroundTime / Processes.size();

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnAroundTime);
    }



    static void SJF(ArrayList<Process> myProcesses){
        System.out.println("SJF Algorithm :");
        System.out.println("---------------------------------------------------------------------");
        ArrayList<Process> Processes =new ArrayList<Process>();
        Processes.addAll(myProcesses);
        ArrayList<Process> WaitingQueue = new ArrayList<Process>();
        ArrayList<Process> ReadyQueue = new ArrayList<Process>();
        Process executingProcess = null;
        int totalTurnAroundTime = 0;
        double avgWaitingTime;
        double avgTurnAroundTime;
        int totalWaitingTime = 0;
        int totalTime = 0;
        processorIn = false;

        for (;totalTime <= time;totalTime++){
            for (int i = 0 ; i < Processes.size(); i++){
                if(Processes.get(i).arrivalTime == totalTime){
                    ReadyQueue.add(Processes.remove(i));

                }
            }
            for (int i = 0; i < WaitingQueue.size(); i++) {
                Process process = WaitingQueue.get(i);
                if (totalTime - process.completionTime >= process.comesBackAfter) {
                    WaitingQueue.get(i).totalComesBackAfter += WaitingQueue.get(i).comesBackAfter;
                    ReadyQueue.add(WaitingQueue.remove(i));
                }
            }
            if(!processorIn && !ReadyQueue.isEmpty()){
                Collections.sort(ReadyQueue, new Comparator<Process>() {
                    public int compare(Process p1, Process p2) {
                        return p1.burstTime - p2.burstTime;
                    }
                });

                executingProcess =  ReadyQueue.remove(0);
                processorIn = true;
            }

            if(processorIn){
                executingProcess.totalBurstTime++;
                executingProcess.remainingBurstTime--;
                if(executingProcess.remainingBurstTime == 0){
                    executingProcess.remainingBurstTime=executingProcess.burstTime;
                    if (totalTime == 200){executingProcess.completionTime = totalTime;}else
                    {
                        executingProcess.completionTime = totalTime +1 ;}



                    processorIn = false;
                    WaitingQueue.add(executingProcess);
                    System.out.print("P" + executingProcess.pid +"|"+ executingProcess.totalBurstTime+"|"+ "-->" + executingProcess.completionTime + "--");
                    if(executingProcess.porcesseIn)
                        for (Process pp : Processes){
                            if(pp.pid ==  executingProcess.pid){
                                pp.completionTime = executingProcess.completionTime;
                                pp.totalBurstTime = executingProcess.totalBurstTime;
                            }
                        }
                    if (!executingProcess.porcesseIn){
                        Processes.add(executingProcess);
                        executingProcess.porcesseIn =true;
                    }
                }
            }
        }
        executingProcess.completionTime = totalTime -1;
        executingProcess.totalBurstTime--;
        System.out.print("P" + executingProcess.pid +"|"+ executingProcess.totalBurstTime+"|"+ "-->" + executingProcess.completionTime + "--");

        System.out.println("\n");


        for (Process p : Processes) {

            p.turnAroundTime = p.completionTime - p.arrivalTime;

            p.waitingTime= p.turnAroundTime - p.totalBurstTime  - p.totalComesBackAfter;
            if(p.waitingTime < 0)
                p.waitingTime = 0;


            System.out.println("\nP" + p.pid + "|"+  "\tTAT:" + p.turnAroundTime  +"|"+ "\tWT:" + p.waitingTime+"|");


            totalWaitingTime += p.waitingTime;
            totalTurnAroundTime += p.turnAroundTime;

        }
        avgTurnAroundTime = (double) totalTurnAroundTime /Processes.size();
        avgWaitingTime = (double) totalWaitingTime / Processes.size();
        System.out.println("\nAverage Waiting Time: " + avgWaitingTime );
        System.out.println("Average Turnaround Time: " + avgTurnAroundTime);
        System.out.println("---------------------------------------------------------------------");

    }





    static void SRTF(ArrayList<Process> myProcesses) {
        System.out.println("SRTF Algorithm :");
        System.out.println("---------------------------------------------------------------------");
        ArrayList<Process> Processes =new ArrayList<Process>();
        Processes.addAll(myProcesses);
        ArrayList<Process> WaitingQueue = new ArrayList<Process>();
        ArrayList<Process> ReadyQueue = new ArrayList<Process>();
        Process executingProcess = null;
        int totalTurnAroundTime = 0;
        double avgWaitingTime;
        double avgTurnAroundTime;
        int totalWaitingTime = 0;
        int totalTime = 0;
        processorIn = false;

        for(; totalTime <= time ; totalTime++){
            for (int i = 0 ; i < Processes.size(); i++){
                if(Processes.get(i).arrivalTime == totalTime){
                    ReadyQueue.add(Processes.remove(i));
                }
            }

            for (int i = 0; i < WaitingQueue.size(); i++) {
                Process process = WaitingQueue.get(i);
                if (totalTime - process.completionTime >= process.comesBackAfter) {
                    WaitingQueue.get(i).totalComesBackAfter += WaitingQueue.get(i).comesBackAfter;
                    ReadyQueue.add(WaitingQueue.remove(i));
                }
            }

            if(!processorIn && !ReadyQueue.isEmpty()){
                executingProcess = ReadyQueue.remove(0);
                processorIn = true;
            } else if (ReadyQueue.size() >0 ) {
                Collections.sort(ReadyQueue, new Comparator<Process>(){
                    public int compare(Process p1, Process p2){
                        return p1.remainingBurstTime - p2.remainingBurstTime;
                    }
                });
                if (ReadyQueue.get(0).remainingBurstTime < executingProcess.remainingBurstTime) {

                    System.out.print("P" + executingProcess.pid + "-->" + totalTime + "--");
                    ReadyQueue.add(executingProcess);

                    executingProcess = ReadyQueue.remove(0);
                }
            }
            if(processorIn){
                executingProcess.totalBurstTime++;
                executingProcess.remainingBurstTime--;
                if(executingProcess.remainingBurstTime == 0){
                    executingProcess.remainingBurstTime=executingProcess.burstTime;
                    if (totalTime == 200){executingProcess.completionTime = totalTime;}else {
                        executingProcess.completionTime = totalTime +1 ;}

                    WaitingQueue.add(executingProcess);
                    processorIn = false;

                    System.out.print("P" + executingProcess.pid +"|"+ executingProcess.totalBurstTime+"|"+ "-->" + executingProcess.completionTime + "--");
                    if(executingProcess.porcesseIn)
                        for (Process pp : Processes){
                            if(pp.pid ==  executingProcess.pid){
                                pp.completionTime = executingProcess.completionTime;
                                pp.totalBurstTime = executingProcess.totalBurstTime;
                            }
                        }
                    if (!executingProcess.porcesseIn){
                        Processes.add(executingProcess);
                        executingProcess.porcesseIn =true;
                    }

                    ReadyQueue.remove(executingProcess);
                }
            }


        }
        System.out.println("\n");



        for (Process p : Processes) {

            p.turnAroundTime = p.completionTime - p.arrivalTime;

            p.waitingTime= p.turnAroundTime - p.totalBurstTime  - p.totalComesBackAfter;
            if(p.waitingTime < 0) {
                p.waitingTime = 0;
            }


            System.out.println("\nP" + p.pid + "|"+  "\tTAT:" + p.turnAroundTime  +"|"+ "\tWT:" + p.waitingTime+"|");


            totalWaitingTime += p.waitingTime;
            totalTurnAroundTime += p.turnAroundTime;

        }
        avgWaitingTime = (double)totalWaitingTime / Processes.size();
        avgTurnAroundTime = (double)totalTurnAroundTime / Processes.size();

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnAroundTime);


    }



    static void roundRobin(ArrayList<Process> Processes, int quantum) {
        System.out.println("Round Robin  Algorithm with q = " + quantum + ": ");
        System.out.println("---------------------------------------------------------------------");
        ArrayList<Process> WaitingQueue = new ArrayList<Process>();
        ArrayList<Process> ReadyQueue = new ArrayList<Process>();
        Process executingProcess = null;
        int totalTurnAroundTime = 0;
        double avgWaitingTime;
        double avgTurnAroundTime;
        int totalWaitingTime = 0;
        int totalTime = 0;
        int remainingQuantum = quantum;

        processorIn = false;
        for(; totalTime <= time ; totalTime++){

            for (int i = 0 ; i < Processes.size(); i++){
                if(Processes.get(i).arrivalTime == totalTime){
                    ReadyQueue.add(Processes.remove(i));
                }
            }

            for (int i = 0; i < WaitingQueue.size(); i++) {
                Process process = WaitingQueue.get(i);
                if (totalTime - process.completionTime >= process.comesBackAfter) {
                    WaitingQueue.get(i).totalComesBackAfter += WaitingQueue.get(i).comesBackAfter;
                    ReadyQueue.add(WaitingQueue.remove(i));
                }

            }

            if(!processorIn && !ReadyQueue.isEmpty()){
                executingProcess =  ReadyQueue.remove(0);
                processorIn = true;
            }



            if(processorIn){
                executingProcess.remainingBurstTime--;
                executingProcess.totalBurstTime++;

                if (remainingQuantum == 0){
                    remainingQuantum = quantum;

                    if (executingProcess.remainingBurstTime == 0){
                        executingProcess.remainingBurstTime=executingProcess.burstTime;

                            executingProcess.completionTime = totalTime+1;
                        WaitingQueue.add(executingProcess);
                        processorIn = false;

                        System.out.print("P" + executingProcess.pid +"|"+ executingProcess.totalBurstTime+"|"+ "-->" + executingProcess.completionTime + "--");

                        ReadyQueue.remove(executingProcess);
                        if(executingProcess.porcesseIn)
                            for (Process pp : Processes){
                                if(pp.pid ==  executingProcess.pid){
                                    pp.completionTime = executingProcess.completionTime;
                                    pp.totalBurstTime = executingProcess.totalBurstTime;
                                }
                            }
                        if (!executingProcess.porcesseIn){
                            Processes.add(executingProcess);
                            executingProcess.porcesseIn =true;
                        }

                    }
                    else
                    {
                          ReadyQueue.add(executingProcess);


                        processorIn = false;

                        System.out.print("P" + executingProcess.pid + "-->" + (totalTime) +"-->");

                    }




                }


                else if(executingProcess.remainingBurstTime == 0 ){
                    remainingQuantum = quantum;
                    executingProcess.remainingBurstTime=executingProcess.burstTime;

                        executingProcess.completionTime = totalTime +1;
                    WaitingQueue.add(executingProcess);
                    processorIn = false;

                    System.out.print("P" + executingProcess.pid +"|"+ executingProcess.totalBurstTime+"|"+ "-->" + executingProcess.completionTime + "--");

                    ReadyQueue.remove(executingProcess);

                    if(executingProcess.porcesseIn)
                        for (Process pp : Processes){
                            if(pp.pid ==  executingProcess.pid){
                                pp.completionTime = executingProcess.completionTime;
                                pp.totalBurstTime = executingProcess.totalBurstTime;
                            }
                        }
                    if (!executingProcess.porcesseIn){
                        Processes.add(executingProcess);
                        executingProcess.porcesseIn =true;
                    }
                }



            }
            remainingQuantum--;
        }
        executingProcess.completionTime = totalTime -1;
        executingProcess.totalBurstTime--;
        System.out.print("P" + executingProcess.pid +"|"+ executingProcess.totalBurstTime+"|"+ "-->" + executingProcess.completionTime + "--");

        System.out.println("\n");

        for (Process p : Processes) {

            p.turnAroundTime = p.completionTime - p.arrivalTime;

            p.waitingTime= p.turnAroundTime - p.totalBurstTime  - p.totalComesBackAfter;
            if(p.waitingTime < 0) {
                p.waitingTime = 0;
            }

            System.out.println("\nP" + p.pid + "|"+  "\tTAT:" + p.turnAroundTime  +"|"+ "\tWT:" + p.waitingTime+"|");


            totalWaitingTime += p.waitingTime;
            totalTurnAroundTime += p.turnAroundTime;

        }
        avgTurnAroundTime = (double) totalTurnAroundTime / Processes.size();
        avgWaitingTime = (double) totalWaitingTime /Processes.size();
        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnAroundTime);
        System.out.println("---------------------------------------------------------------------");

    }

    static void preemPriority(ArrayList<Process> Processes, int quantum) {
        System.out.println("Preemptive Priority Algorithm with q = " + quantum + ": ");
        System.out.println("---------------------------------------------------------------------");
        ArrayList<Process> WaitingQueue = new ArrayList<Process>();
        ArrayList<Process> ReadyQueue = new ArrayList<Process>();
        Process executingProcess = null;
        int totalTurnAroundTime = 0;
        double avgWaitingTime;
        double avgTurnAroundTime;
        int totalWaitingTime = 0;
        int totalTime = 0;
        boolean processorIn = false;

        for(; totalTime <= time ; totalTime++){

            for (int i = 0 ; i < Processes.size(); i++){
                if(Processes.get(i).arrivalTime == totalTime){
                    ReadyQueue.add(Processes.remove(i));
                }
            }
            for (int i = 0; i < WaitingQueue.size(); i++) {
                Process process = WaitingQueue.get(i);
                if (totalTime - process.completionTime >= process.comesBackAfter) {
                    WaitingQueue.get(i).totalComesBackAfter += WaitingQueue.get(i).comesBackAfter;
                    ReadyQueue.add(WaitingQueue.remove(i));
                }
            }

            if(!processorIn && !ReadyQueue.isEmpty()){
                executingProcess = ReadyQueue.remove(0);
                processorIn = true;
            } else {
                Collections.sort(ReadyQueue, new Comparator<Process>(){
                    public int compare(Process p1, Process p2){
                        return p1.ppriorty - p2.ppriorty;
                    }
                });
                if (processorIn && !ReadyQueue.isEmpty() && ReadyQueue.get(0).ppriorty < executingProcess.ppriorty) {
                    System.out.print("P" + executingProcess.pid + "-->" + totalTime + "--");
                    ReadyQueue.add(executingProcess);
                    executingProcess = ReadyQueue.remove(0);
                }
            }

                for (Process p : ReadyQueue) {
                     p.remainingQuantum++;
                     if (p.remainingQuantum == quantum){
                         p.remainingQuantum = 0;
                         p.ppriorty--;
                     }
                }


            if(processorIn ){

                executingProcess.totalBurstTime++;
                executingProcess.remainingBurstTime--;

                if(executingProcess.remainingBurstTime == 0 ){
                    executingProcess.remainingBurstTime=executingProcess.burstTime;
                    executingProcess.ppriorty = executingProcess.priority;
                    if (totalTime == 200){executingProcess.completionTime = totalTime;}else {
                        executingProcess.completionTime = totalTime +1 ;}

                    WaitingQueue.add(executingProcess);
                    processorIn = false;

                    System.out.print("P" + executingProcess.pid +"|"+ executingProcess.totalBurstTime+"|"+ "-->" + executingProcess.completionTime + "--");

                    if(executingProcess.porcesseIn)
                        for (Process pp : Processes){
                            if(pp.pid ==  executingProcess.pid){
                                pp.completionTime = executingProcess.completionTime;
                                pp.totalBurstTime = executingProcess.totalBurstTime;
                            }
                        }
                    if (!executingProcess.porcesseIn){
                        Processes.add(executingProcess);
                        executingProcess.porcesseIn =true;
                    }
                }


            }



        }
        executingProcess.completionTime = totalTime -1;
        System.out.print("P" + executingProcess.pid +"|"+ executingProcess.totalBurstTime+"|"+ "-->" + executingProcess.completionTime + "--");
        System.out.println("\n");

        for (Process p : Processes) {

            p.turnAroundTime = p.completionTime - p.arrivalTime;

            p.waitingTime= p.turnAroundTime - p.totalBurstTime  - p.totalComesBackAfter;

            if(p.waitingTime < 0) {
                p.waitingTime = 0;
            }
            System.out.println("\nP" + p.pid + "|"+  "\tTAT:" + p.turnAroundTime  +"|"+ "\tWT:" + p.waitingTime+"|");


            totalWaitingTime += p.waitingTime;
            totalTurnAroundTime += p.turnAroundTime;

        }
        avgTurnAroundTime = (double) totalTurnAroundTime / Processes.size();
        avgWaitingTime = (double) totalWaitingTime / Processes.size();
        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnAroundTime);
        System.out.println("---------------------------------------------------------------------");

    }

    static void noPreemPriority(ArrayList<Process> Processes, int quantum) {
        System.out.println("Preemptive Priority Algorithm with q = " + quantum + ": ");
        System.out.println("---------------------------------------------------------------------");
        ArrayList<Process> WaitingQueue = new ArrayList<Process>();
        ArrayList<Process> ReadyQueue = new ArrayList<Process>();
        Process executingProcess = null;
        int totalTurnAroundTime = 0;
        double avgWaitingTime;
        double avgTurnAroundTime;
        int totalWaitingTime = 0;
        int totalTime = 0;
        boolean processorIn = false;

        for(; totalTime <= time ; totalTime++){

            for (int i = 0 ; i < Processes.size(); i++){
                if(Processes.get(i).arrivalTime == totalTime){
                    ReadyQueue.add(Processes.remove(i));
                }
            }
            for (int i = 0; i < WaitingQueue.size(); i++) {
                Process process = WaitingQueue.get(i);
                if (totalTime - process.completionTime >= process.comesBackAfter) {
                    WaitingQueue.get(i).totalComesBackAfter += WaitingQueue.get(i).comesBackAfter;
                    ReadyQueue.add(WaitingQueue.remove(i));
                }
            }

            if(!processorIn && !ReadyQueue.isEmpty()){
                executingProcess = ReadyQueue.remove(0);
                processorIn = true;
            } else {
                Collections.sort(ReadyQueue, new Comparator<Process>(){
                    public int compare(Process p1, Process p2){
                        return p1.ppriorty - p2.ppriorty;
                    }
                });

            }

            for (Process p : ReadyQueue) {
                p.remainingQuantum++;
                if (p.remainingQuantum == quantum){
                    p.remainingQuantum = 0;
                    p.ppriorty--;
                }
            }

            if(processorIn ){

                executingProcess.totalBurstTime++;
                executingProcess.remainingBurstTime--;

                if(executingProcess.remainingBurstTime == 0 ){
                    executingProcess.remainingBurstTime=executingProcess.burstTime;
                    executingProcess.ppriorty = executingProcess.priority;
                    if (totalTime == 200){executingProcess.completionTime = totalTime;}else {
                        executingProcess.completionTime = totalTime +1 ;}

                    WaitingQueue.add(executingProcess);
                    processorIn = false;

                    System.out.print("P" + executingProcess.pid +"|"+ executingProcess.totalBurstTime+"|"+ "-->" + executingProcess.completionTime + "--");

                    ReadyQueue.remove(executingProcess);
                    if(executingProcess.porcesseIn)
                        for (Process pp : Processes){
                            if(pp.pid ==  executingProcess.pid){
                                pp.completionTime = executingProcess.completionTime;
                                pp.totalBurstTime = executingProcess.totalBurstTime;
                            }
                        }
                    if (!executingProcess.porcesseIn){
                        Processes.add(executingProcess);
                        executingProcess.porcesseIn =true;
                    }
                }


            }


        }
        executingProcess.completionTime = totalTime-1;
        System.out.print("P" + executingProcess.pid +"|"+ executingProcess.totalBurstTime+"|"+ "-->" + executingProcess.completionTime + "--");
        System.out.println("\n");

        for (Process p : Processes) {

            p.turnAroundTime = p.completionTime - p.arrivalTime;

            p.waitingTime= p.turnAroundTime - p.totalBurstTime  - p.totalComesBackAfter;

            if(p.waitingTime < 0) {
                p.waitingTime = 0;
            }
            System.out.println("\nP" + p.pid + "|"+  "\tTAT:" + p.turnAroundTime  +"|"+ "\tWT:" + p.waitingTime+"|");


            totalWaitingTime += p.waitingTime;
            totalTurnAroundTime += p.turnAroundTime;

        }
        avgTurnAroundTime = (double) totalTurnAroundTime / Processes.size();
        avgWaitingTime = (double) totalWaitingTime / Processes.size();
        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnAroundTime);
        System.out.println("---------------------------------------------------------------------");

    }

}

