import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
/**
 * <Proj6.java>
 * <Preston Rottinghaus/ 02B Thursday 3:30>
 * <This program is designed to take the text file utput of a scantron machine
 * takes the number of questions and points for each question.
 * grades the studnets questions and gives the final score.
 * creates a result file with each students score.> 
 */
public class Proj6{
/**
 * takes all methods to read in file and grade and output file
 * 
 * @param args takes in input from the comand line
 * @throws IOException  incase file does not exist
 */
public static void main(String [] args) throws IOException{
    Scanner s = new Scanner(System.in);
   
    
    final int MAXCLASSSIZE = 50;
    final int MAXEXAMQUEST = 50;
    String [][]gradable = new String [MAXCLASSSIZE][MAXCLASSSIZE];
    String [] letGrad = new String[MAXCLASSSIZE];
    int userquest =0;
    int userpoint =0;
    int [][] answer = new int [MAXCLASSSIZE][MAXEXAMQUEST];
    int [] totcor =new int [MAXEXAMQUEST];
    String fileName = getFileInput(s);
   filler(MAXCLASSSIZE,gradable);
    
   
    System.out.print("Please enter the number of questions on the exam: ");
    userquest = s.nextInt();
    if(userquest >MAXEXAMQUEST && userquest<1){
        while(userquest > MAXEXAMQUEST && userquest<1){
            System.out.print("Question number exededs limit please enter number between 1-50: ");
            userquest = s.nextInt();
        }
    }
    
     System.out.print("Please enter the number of points per question: ");
    userpoint = s.nextInt();
    int[] gradeKey = new int[userquest];
    gradeKey = createKey(userquest);
   
int loop = readFile(fileName, gradable, answer);
gradeQuiz(gradeKey, answer,totcor,userquest);
getLetterGrade(letGrad, totcor,userquest);
displayResults(gradable,totcor, letGrad, userpoint,userquest);
getAvg(totcor,userquest);
saveResults(gradable, totcor, userquest, letGrad,loop);
}
    

/**
 * takes user input of file name and checks if file is valid
 * @param s take in name of the file with grades
 * @return fileName   file name to open file
 * 
 */
public static String getFileInput(Scanner s){
   boolean valid = false;
    String fileName = "";
while(valid == false){
    System.out.print("Enter the name of quiz file(i.e. Quizscores.txt): ");
    fileName = s.nextLine();
    File file = new File(fileName);
if(file.isFile()){
    valid = true;
}
else{
     System.out.println("Error! File does not exist.");
     valid = false;
}

}
return fileName;
}
/**
 * take user input of number of questions to be size of answer key array and let user enter correct answer
 * @param numQuestions number of questions on the exam 
 * @return int array answerkey the array holding answer key
 */
public static int [ ] createKey (int numQuestions){
   
    Scanner s = new Scanner(System.in);
    int[]key = new int[numQuestions];
    String userIn;
    int convert;
    System.out.println("Plese enter the answers for the following questions");
    System.out.println("where 'T' = true, 'F' = false, or A, B, C, D, E for multiple choice");
    for(int i =0; i< numQuestions; ++i){
        if(i>numQuestions){
            break;
        }
        System.out.print(i+1 + ")");
        userIn = s.nextLine();
        switch(userIn){
            case "A":
            case "a":
                convert = 1;
                key[i] = convert;
                break;
            case "B":
            case "b":
                convert = 2;
                key[i] = convert;
                break;
            case "C":
            case "c":
                convert = 3;
                key[i] = convert;
                break;
            case "D":
            case "d":
                convert =4;
                key[i] = convert;
                break;
            case "E":
            case "e":
                convert =5;
                key[i] = convert;
                break;
            case "T":
            case "t":
                convert = 1;
                key[i] = convert;
                break;
            case "F":
            case "f":
                convert = 2;
                key[i] = convert;
                break;
            default:
                System.out.println("Invalid entry, please enter 'T' = true, 'F' = false, or A, B, C, D, E for multiple choice");
                convert = 0;
                key[i] = convert;
                i--; // Decrement i to re-enter the correct answer for the current question
                break;
                 
    }
   
}
    s.close();
    /*/
    int[] key = {1,1,2,2,1,1,3,2,4,1,3,5,4,1,2};
    
*/
    return key;
}
/**
 * Reads in file and sorts into array for grading 
 * @param fileName name of the file
 * @param gradable array that file is read into
 * @param answer array of total answers broken down to individual element
 * @return number of times the loop iterates
 * @throws IOException if file name doesnt exist
 */
public static int readFile(String fileName, String [][]gradable,int [][] answer)throws IOException{
    Scanner InFile = new Scanner(new File(fileName));
    int i =0;
   while(InFile.hasNext()&& i<50){
    String current = InFile.nextLine();
    String [] parts = current.split(",");
if(parts.length == 2){
    for(int j =0; j<parts.length;j++){
         gradable[i][j]= parts[j];
         
   }
   for(int j = 0; j <= i; j++){
    String fulltest = gradable[j][1];
    for(int k =0;k< fulltest.length();k++){
        char q = fulltest.charAt(k);
        String c = String.valueOf(q);
        answer[j][k] = Integer.parseInt(c);
        
    }
   
    
  }
}
    
   i++;
   
   }
   InFile.close();
   
   //int len = gradable[0][1].length();
   
  /* 
  for(int j =0; j<i;j++){
  System.out.print(gradable[j][1] + ",");
  for(int k =0; k<len;k++){
  System.out.print(answer[k] + ","); 
  
  }
  }
*/
return i;
}



/**
 * compares the array of key and answers to see what score is
 * @param key is the answer key
 * @param answer is the answers the studnet put in
 * @param totcor is the total number of answers that student gor correct
 * @param numq number of question being graded
 */
public static void gradeQuiz(int[]key, int[][]answer, int [] totcor,int numq){
  
    for(int j =0; j< 50; j++){
        int totcorrect =0;
    for(int i =0 ; i< numq;i++){
       
        if(key[i] == answer[j][i]){
                ++totcorrect;
                
        }
    
    }
totcor[j] = (totcorrect);
}





}
/**
 * gets letter grade from correct # of answers
 * @param letGrad contains letter grade from number correct
 * @param numcor number of correct answers from student
 * @param numq number of questions being graded
 */
public static void getLetterGrade(String[] letGrad, int[] numcor,int numq){
 double gradeAsLetter =0;
 int L =totcorlength(numcor);
    for(int i = 0;i < L;i++){
   gradeAsLetter = (((double)numcor[i]/numq)*100);
        if(gradeAsLetter <=100 && gradeAsLetter >= 90){
            letGrad[i] ="A";
        }
        else if((gradeAsLetter <90 && gradeAsLetter >= 80)){
            letGrad[i] = "B";
        
        }         
        else if((gradeAsLetter <80 && gradeAsLetter > 70)){
            letGrad[i] = "C";
        }
        else if((gradeAsLetter <=70 && gradeAsLetter > 60)){
            letGrad[i] = "D";
        }
        else
            letGrad[i] = "F";
}

}
/**
 * takes in the information to be displayed for grader
 * @param gradable will give Wildcat ID number
 * @param totcor gives totalcorrect answers
 * @param letGrad gives the letterverion of totcor
 * @param userpoint used to clc total value of test
 * @param numQ gives the total number of correct answers
 */
public static void  displayResults(String [][]gradable,int[]totcor,String[] letGrad, int userpoint,int numQ){
    System.out.print("Student ID\t# correct\tScore\t% Correct\tGrade\n");
    int L =totcorlength(totcor);
    
for(int i = 0; i<L;i++){
    double per = ((double)totcor[i]/numQ) * 100;
    int points = totcor[i]*userpoint;
    System.out.printf("%s\t%d\t\t%d\t%.1f\t\t%s %n",gradable[i][0],totcor[i],points,per,letGrad[i]);
}
System.out.println();
int tempH = totcor[0];
int tempL = totcor[0];
for(int i = 1; i<L; i++){
    if(tempH < totcor[i]){
        tempH = totcor[i];
    }

    if(tempL > totcor[i]){
        tempL = totcor[i];
    }
}
System.out.println("High score: " + (tempH * userpoint ));
System.out.println("Low score: " + (tempL * userpoint ));


}

 /**
  * fills entire grabable array with open spaces
  *@param MAXCLASSSIZE the largest possible class size
  *@param gradable all possible entries for grable test
  */
public static void filler(int MAXCLASSSIZE, String [][] gradable){
    for(int i = 0; i < MAXCLASSSIZE; i++){
        for(int j = 0; j< MAXCLASSSIZE; j++){
            gradable[i][j] = "";
        }
    }
}
/**
 * determines correct lenght of totcor array since it is set to max size of class
 * @param totcor array containg the correct number of questions for each student
 * @return number of students in the class
 */
public static int totcorlength(int[] totcor){
    for (int i = 0; i < totcor.length; i++) {
        if (totcor[i] == 0) {
            if (i + 1 < totcor.length && totcor[i + 1] == 0) {
                return i; 
            }
        }
    }
    return totcor.length; 
}
/**
 * 
 * @param gradable array read from the original file containing wildact id
 * @param totcor array of each students number of correct answers
 * @param numq number of questions graded
 * @param letGrad array holding each students letter grade
 * @param loop holds the number of studnets
 * @throws IOException incase the file already exists
 */
public static void saveResults(String[][]gradable,int[]totcor,int numq, String[] letGrad,int loop)throws IOException{
DecimalFormat printFormat;
printFormat  = new DecimalFormat("###.0");
PrintWriter outfile = new PrintWriter(new File("Results.txt"));
System.out.println("Results.txt file created...");
for(int i =0;i<loop;i++){
    double per = ((double)totcor[i]/numq) * 100;
    
    
    String prln = gradable[i][0] +","+ printFormat.format(per) +","+  letGrad[i];
   /* 
    System.out.println(gradable[i][0]);
    System.out.println(points);
    System.out.println(per);
    System.out.println(letGrad[i]);
   System.out.println(prln);
   */
    outfile.println(prln);
    
}
outfile.close();
}

/**
 * calculates average score of all tests given
 * @param totcor array of all correct answers for every studnet
 * @param userquest number of questions enterd by user
 */
public static void getAvg(int []totcor,int userquest){
    int L =totcorlength(totcor);
    int total =0;
    for(int i =0; i<L;i++){
        total += totcor[i];
    }
    double avg = ((double)total/(L *userquest)*100);
    System.out.printf("Average: %.1f%% %n", avg);
    System.out.println();
}
} 












