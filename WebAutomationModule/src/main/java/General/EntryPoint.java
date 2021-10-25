package General;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class EntryPoint {
    public static void main(String[] args) throws GeneralSecurityException, MessagingException, IOException {
        String mainText = "<h3> Hello , </h3> <p> <br /> A password recovery has been initiated for yo=\n" +
                "ur account in Qualis Capital . If you did not initiate password recovery re=\n" +
                "quest, please ignore this email.  <br />  Your  Confirmation Code  is  7666=\n" +
                "82<br /> New password should be minimun 8 characters, require numbers,speci=\n" +
                "al character,uppercase letters,lowercase letters. <br /><br /> Regards, <br=\n" +
                " /> Qualis Capital</p><img src=3D\"data:image/png;base64,iVBORw0KGgoAAAANSUh=\n" +
                "EUgAAARAAAAFWCAMAAACW1TJWAAABmFBMVEUAAAApJxgQDw8YFhUYGBcYFhUYFhUYFRUYFhW2ml=\n" +
                "cYFhWzmlQZFhSzmlQXFRQZFhUVFBMYFRWzmlSzmlQYFhUWFRQXFhUYFhWymVQZFhWymVSymlSym=\n";
        String substring = "Your  Confirmation Code  is  ";

        System.out.println(Helper.FindSubstringInAString(mainText, substring, 's'));
    }
}
