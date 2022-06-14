
public class UserInput{

    public static void main(String[] args){

        if(args.length == 0){
            System.out.println("No argument giving");
        }
        else if (args.length == 1) {
            double rand = Math.random();
            System.out.println(rand);
        }
        else{
            String s = args[0];
            int i = Integer.parseInt(s);
            while(i > 0) {
                System.out.print(args[1]);
                i--;
            }
        }


    }
}