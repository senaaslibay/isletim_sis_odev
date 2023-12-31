import java.util.*;
import java.io.*;

class Process {
    // Define attributes of a process
    int pid;
    int arrivalTime;
    int priority;
    int processTime;
    int memorySize;
    int memoryLocation;
    int printers;
    String status;
    int scanners;
    int modem;
    int cdDrives;

    // Additional attributes for tracking process state
    int remainingTime;
    boolean isRealTime;
    boolean isSuspended;

    // Constructor to initialize a process
    public Process(int pid, int arrivalTime, int priority, int processTime,
                   int memorySize, int printers, int scanners, int modem, int cdDrives) {
        // Initialize process attributes
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.processTime = processTime;
        this.memorySize = memorySize;
        this.printers = printers;
        this.scanners = scanners;
        this.modem = modem;
        this.cdDrives = cdDrives;

        // Initialize additional attributes
        this.remainingTime = processTime;
        this.isRealTime = (priority == 0);
        this.isSuspended = false;
    }
}

class MemoryBlock {
    // Define attributes of a memory block
    int startAddress;
    int size;
    int pid;

    // Constructor to initialize a memory block
    public MemoryBlock(int startAddress, int size, int pid) {
        this.startAddress = startAddress;
        this.size = size;
        this.pid = pid;
    }
}

class Dispatcher {
    // Define data structures for queues and memory
    Queue<Process> realTimeQueue;
    Queue<Process> userQueue1;
    Queue<Process> userQueue2;
    Queue<Process> userQueue3;

    List<MemoryBlock> memoryBlocks;

    // Resource constraints
    int availablePrinters;
    int availableScanners;
    int availableModems;
    int availableCdDrives;

    // Constructor to initialize the dispatcher
    public Dispatcher() {
        // Initialize queues and memory
        realTimeQueue = new LinkedList<>();
        userQueue1 = new LinkedList<>();
        userQueue2 = new LinkedList<>();
        userQueue3 = new LinkedList<>();
        memoryBlocks = new ArrayList<>();
        // Initialize memory with a single block representing the total available memory
        memoryBlocks.add(new MemoryBlock(0, 1024, -1));

        // Initialize resource constraints
        availablePrinters = 2;
        availableScanners = 1;
        availableModems = 1;
        availableCdDrives = 2;
    }

    // Method to run the dispatcher
    public void runDispatcher() {
        int currentTime = 0;

        System.out.println("Pid varış öncelik cpu MBytes prn scn modem cd status");
        System.out.println("============================================================================");


        while (!realTimeQueue.isEmpty() || !userQueue1.isEmpty() || !userQueue2.isEmpty() || !userQueue3.isEmpty()) {
            // Execute real-time processes
            executeRealTimeProcesses();

            // Execute user processes
            executeUserProcesses(currentTime);

            // Move processes from queues to execution based on priority
            moveProcessesToExecution();
            // Print the status of each process
            printProcessStatus();
            // Increment time
            currentTime++;
        }
    }

    private void printProcessStatus() {
        printProcessStatusFromQueue(realTimeQueue);
        printProcessStatusFromQueue(userQueue1);
        printProcessStatusFromQueue(userQueue2);
        printProcessStatusFromQueue(userQueue3);
    }

    private void printProcessStatusFromQueue(Queue<Process> queue) {
        for (Process process : queue) {
            System.out.println(process.pid + " " + process.arrivalTime + " " + process.priority + " " +
                    process.processTime + " " + process.memorySize + " " + process.printers + " " +
                    process.scanners + " " + process.modem + " " + process.cdDrives + " " + process.status);
        }
    }

    // Method to execute real-time processes
    private void executeRealTimeProcesses() {
        while (!realTimeQueue.isEmpty()) {
            Process process = realTimeQueue.poll();
            allocateMemory(process);
            executeProcess(process);
            deallocateResources(process);
        }
    }

    // Method to execute user processes
    private void executeUserProcesses(int currentTime) {
        Process userProcess = getUserProcessToExecute();
        if (userProcess != null) {
            allocateMemory(userProcess);
            executeProcess(userProcess);
            deallocateResources(userProcess);
        }
    }

    // Method to move processes from queues to execution based on priority
    private void moveProcessesToExecution() {
        if (!realTimeQueue.isEmpty()) {
            Process realTimeProcess = realTimeQueue.poll();
            allocateMemory(realTimeProcess);
            executeProcess(realTimeProcess);
            deallocateResources(realTimeProcess);
        } else if (!userQueue1.isEmpty()) {
            Process userProcess = userQueue1.poll();
            allocateMemory(userProcess);
            executeProcess(userProcess);
            deallocateResources(userProcess);
        } else if (!userQueue2.isEmpty()) {
            Process userProcess = userQueue2.poll();
            allocateMemory(userProcess);
            executeProcess(userProcess);
            deallocateResources(userProcess);
        } else if (!userQueue3.isEmpty()) {
            Process userProcess = userQueue3.poll();
            allocateMemory(userProcess);
            executeProcess(userProcess);
            deallocateResources(userProcess);
        }
    }

    // Method to execute a process
    private void executeProcess(Process process) {
        System.out.println("Executing process: " + process.pid + " at time " + process.arrivalTime);
        // Perform process execution logic here
        // Update process state, decrement remaining time, etc.
        if (--process.remainingTime == 0) {
            System.out.println("Process " + process.pid + " completed.");
        } else {
            System.out.println("Process " + process.pid + " is suspended.");
            process.isSuspended = true;
        }
    }

    // Method to allocate memory for a process
    private void allocateMemory(Process process) {
        // Implement memory allocation logic here
        // Update memoryBlocks list accordingly
        int requiredMemory = process.memorySize;

        // Check if there is a suitable memory block
        for (MemoryBlock block : memoryBlocks) {
            if (block.pid == -1 && block.size >= requiredMemory) {
                // Allocate memory to the process
                block.pid = process.pid;
                process.memorySize = block.size; // Process may get less memory than requested
                process.memoryLocation = block.startAddress;
                System.out.println("Allocated " + process.memorySize + " MB memory to process " + process.pid +
                        " starting from address " + process.memoryLocation);
                break;
            }
        }
    }

    // Method to deallocate resources after process execution
    private void deallocateResources(Process process) {
        // Implement resource deallocation logic here
        // Update availablePrinters, availableScanners, availableModems, availableCdDrives, etc.

        // Deallocate memory
        for (MemoryBlock block : memoryBlocks) {
            if (block.pid == process.pid) {
                block.pid = -1;
                System.out.println("Deallocated memory for process " + process.pid);
                break;
            }
        }

        // Update resource availability
        availablePrinters += process.printers;
        availableScanners += process.scanners;
        availableModems += process.modem;
        availableCdDrives += process.cdDrives;
    }

    // Method to get the next user process to execute based on priority and time
    private Process getUserProcessToExecute() {
        if (!userQueue1.isEmpty()) {
            return userQueue1.peek();
        } else if (!userQueue2.isEmpty()) {
            return userQueue2.peek();
        } else if (!userQueue3.isEmpty()) {
            return userQueue3.peek();
        }
        return null;
    }

    // Method to read processes from a file
    public void readProcessesFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNext()) {
                // Use try-catch to handle potential NumberFormatException
                try {
                    String[] tokens = scanner.nextLine().split(", ");
                    Process process = createProcessFromTokens(tokens);
                    if (process != null) {
                        // Add processes to appropriate queues
                        if (process.isRealTime) {
                            realTimeQueue.add(process);
                        } else {
                            switch (process.priority) {
                                case 1:
                                    userQueue1.add(process);
                                    break;
                                case 2:
                                    userQueue2.add(process);
                                    break;
                                case 3:
                                    userQueue3.add(process);
                                    break;
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println("HATA - Geçersiz formatlı bir satır bulundu - Satır atlandı");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to create a Process object from an array of tokens
    private Process createProcessFromTokens(String[] tokens) {
        // Use try-catch to handle potential NumberFormatException
        try {
            int arrivalTime = Integer.parseInt(tokens[0]);
            int priority = Integer.parseInt(tokens[1]);
            int processTime = Integer.parseInt(tokens[2]);
            int memorySize = Integer.parseInt(tokens[3]);
            int printers = Integer.parseInt(tokens[4]);
            int scanners = Integer.parseInt(tokens[5]);
            int modem = Integer.parseInt(tokens[6]);
            int cdDrives = Integer.parseInt(tokens[7]);
            return new Process(0, arrivalTime, priority, processTime, memorySize, printers, scanners, modem, cdDrives);
        } catch (NumberFormatException e) {
            System.err.println("HATA - Geçersiz formatlı bir satır bulundu - Satır atlandı");
        }
        return null;
    }

   
}
