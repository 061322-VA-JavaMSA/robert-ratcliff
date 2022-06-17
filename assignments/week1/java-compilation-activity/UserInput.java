
public class UserInput{

    /*
        main takes user input from args[] and prints either:
            a random double
                or
            a String based on an integer provided by the user.

        @params String[] args
     */
    public static void main(String[] args){

        //In case of empty array
        if(args.length == 0){
            System.out.println("No argument giving");
        }
        //Prints random double if only one input
        else if (args.length == 1) {
            double rand = Math.random();
            System.out.println(rand);
        }
        /*Prints 2nd item in args based on first element.
          ASSUMES first element is an integer and second element is a word.
        */
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