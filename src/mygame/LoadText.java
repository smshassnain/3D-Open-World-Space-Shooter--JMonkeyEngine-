


package mygame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

///done to make 
public class LoadText {
    
    public ArrayList <String> PlayerKillText1=new ArrayList();
    public ArrayList <String> PlayerDamageText1=new ArrayList();
    public ArrayList <String> PlayerSinGetText1=new ArrayList();
    
        public void InitText() throws FileNotFoundException, IOException {
                ///When eniemes are killed
       
        Scanner inputPKT=new Scanner (new File ("PlayerKillDialog.txt"));
        Scanner inputPDT=new Scanner (new File ("PlayerDamageDialog.txt"));
        Scanner inputPSGT=new Scanner (new File ("PlayerSinGetDialog.txt"));
   
        ///CLOSE INPUT STREAMS
        
        
        ///player kill text
        while (inputPKT.hasNextLine()){///while the next stream is not empty
                PlayerKillText1.add(inputPKT.nextLine());///add text from thing     
        }
        
        ///player geting hit text
        while (inputPDT.hasNextLine()){///while the next stream is not empty
            String s=inputPDT.nextLine();
                PlayerDamageText1.add(s);///add text from thing
                System.out.println (s);
        }
        
        ///player sin get text
        while (inputPSGT.hasNextLine()){///while the next stream is not empty
                PlayerSinGetText1.add(inputPSGT.nextLine());///add text from thing
        }
        inputPSGT.close();inputPKT.close();inputPDT.close();
    }

        
        
           /*
                PlayerKillText.add("PTSHHHH, NOTHIN PERSONELL KID.");
                PlayerKillText.add("REST IN PEICES.");
                PlayerKillText.add("I'LL TAKE YOU TO THE BANK DEMON...THE BLOOD BANK!!!!!");
                PlayerKillText.add("IT'S NOT LEGAL BUT AT LEAST IT SAVES TIME!!");
                PlayerKillText.add("AH, HAH, HAH, HAH, AH, HAHAHAHAHAH!");
                PlayerKillText.add("BEST [amount of sin cost per shot] SIN I'VE EVER SPENT!");
                PlayerKillText.add("KNEEL BEFORE ZOD!");
                PlayerKillText.add("YOUR LICENCE TO LIVE...HAS BEEN REVOKED.");
                PlayerKillText.add("JUSTICE!");
                PlayerKillText.add("LADIES AND GENTALMEN BOYS AND GIRLS...WELCOME TO HELL.");
                PlayerKillText.add("YOU KNOW WHAT RYHMES WITH DEMONS....? DIEING!!!!!!");
                PlayerKillText.add("AH, HAH, HAH, HAH, AH, HAHAHAHAHAH!");
                PlayerKillText.add("EAT [retreacted]!");
                PlayerKillText.add("YOU USED TO BE ALIVE LIKE ME. BUT THEN YOU TOOK AN LASER TO THE KNEE!");
                PlayerKillText.add("GO TO [retracted]!");
                PlayerKillText.add("YOUR MOTHER IS A [retracted][retracted] HIPPOPOSUMIUS [retracted] SOUP[retracted] IN A CASTLE FAR FAR AWAY WHERE NO ONE CAN HEAR YOU [retracted][retracted] ALAKAZAM!!");
                PlayerKillText.add("IT'S NOT LIKE I KILLED YOU OR ANYTHING... OH WAIT, AH, HAH, HAH ,HAH!!");
                PlayerKillText.add("COULD A MONKEY FIGHT A MAN?!");
                PlayerKillText.add("JUST WHO THE HELL DO YOU THINK I AM?!!");
                PlayerKillText.add("MY SHIP IS THE SHIP THAT WILL PEIRCE THE SEVEN!!");
                PlayerKillText.add("ARRRRRRRRRRRRRRRRRRRRHHHHHHHHHHHHHH!!");
                PlayerKillText.add("YOU PIG IN SPACESHIP CLOTHING!!!");
                PlayerKillText.add("RAGE IS ANGER, DEATH IS PERMENENT, VIOLENCE IS THE ANSWER!!");
                PlayerKillText.add("UNBELIVABLE JEFF!");
                PlayerKillText.add("ME- I'LL FIND A WAY.");
                PlayerKillText.add("WHAT A BEAUTIFUL DUWANG! CHEW!");
        */
        
        
        
        
    
    
    
    
}
