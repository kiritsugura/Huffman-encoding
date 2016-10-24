
import java.util.Scanner;

public class Tester {
    public static void main(String[] args){
        Scanner s=new Scanner(System.in);
        while(true){
            System.out.println("Select an Option:");
            System.out.println("a.Encode");
            System.out.println("b.Decode");
            System.out.println("c.Exit");
            String fileName=s.nextLine();
            if(fileName.equals("a")){
                System.out.println("Enter the Text File name(without the extension)");
                String file=s.nextLine();
                Encoding en=new Encoding(file);
                System.out.println("File Encoded");
            }else if(fileName.equals("b")){
                System.out.println("Enter the Dat File name(without the extension)");
                String file=s.nextLine();
                Decoding d=new Decoding(file);
                System.out.println("File Decoded");
            }else if(fileName.equals("c")){
                System.out.println("Exiting");
                break;
            }else{
                System.out.println("Please try again.");
            }
            System.out.println("\n\n\n\n");
        }
    }
    
}
