import mpi.MPI;
import mpi.MPIException;
import java.util.ArrayList;

public class MainClass {

    public static void main(String args[]) {

        // User desired Ip address and count
        String ipAddress = "192.168.1.90";
        String count = "10";
        
        GetIp a = new GetIp();
        ArrayList<String> ip = new ArrayList<>();
        try {
             ip = a.GetIpAdd();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        // Initilize MPI
        MPI.Init(args);
        int size;
        int rank;

        try {
            size = MPI.COMM_WORLD.Size();
            rank = MPI.COMM_WORLD.Rank();
        } catch (MPIException e) {
            e.printStackTrace();
            MPI.Finalize();
            return;
        }

        int numProcesses = MPI.COMM_WORLD.Size();

        // Calculate the number of IPs per process
        int numIPs = ip.size();
        int ipsPerProcess = (int) Math.ceil((double) numIPs / numProcesses);

        // Calculate the starting and ending indices for the current process
        int startIdx = rank * ipsPerProcess;
        int endIdx = Math.min(startIdx + ipsPerProcess, numIPs);

        // Check if the current process has IPs to ping
        if (startIdx < numIPs) {
            for (int i = startIdx; i < endIdx; i++) {
                String line = ip.get(i);
                String pathh = "/home/kali/hping.sh";
                String arg = pathh + " " + count + " " + line + " " + ipAddress;
                Ping p = new Ping(arg);
                p.execute();
            }
        }

        // Finalize MPI
        try {
            MPI.Finalize();
        } catch (MPIException e) {
            e.printStackTrace();
        }

    }

}
