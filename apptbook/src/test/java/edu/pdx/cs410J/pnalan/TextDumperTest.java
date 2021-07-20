package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.ParserException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.*;
import java.security.InvalidParameterException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class TextDumperTest {

    @Test
    public void whenFilesWithExtensionOtherThantxtIsEntered() throws IOException {
        String owner = "Pooja";
            AppointmentBook appointmentBook = new AppointmentBook(owner);
            StringWriter sw = new StringWriter();
            FileWriter filewriter = new FileWriter(new File("life"));

            assertThrows(IllegalArgumentException.class, new Executable() {
                @Override
                public void execute() throws Throwable {
                    TextDumper dumper = new TextDumper(filewriter, sw, "life.pdf");
                }
            });

    }
    @Test
    void dumperDumpsAppointmentBookOwner() throws IOException{
        String owner = "Pooja";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        FileWriter filewriter = new FileWriter(new File("file1"));
        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(filewriter, sw,"file1");
        dumper.dump(appointmentBook);
        String text = sw.toString();
        assertThat(text,containsString(owner));
    }


   @Test
    public void fileNameWithExtensiontxtIsValid() throws IOException {
       String owner = "Pooja\n";
       AppointmentBook appointmentBook = new AppointmentBook(owner);
       FileWriter filewriter = new FileWriter(new File("filewithextension.txt"));
       StringWriter sw = new StringWriter();
       TextDumper dumper = new TextDumper(filewriter, sw,"filewithextension.txt");
       dumper.dump(appointmentBook);
       String text = sw.toString();
       assertThat(text,containsString(owner));
   }

    @Test
    @Disabled
    public void appointmentOwnerComparisonBetweenEnterValueandTextFile() throws IOException {
        String owner = "Pooja";
        String filename = "Folder/PeterFile.txt";
      //  InputStream resource = getClass().getResourceAsStream(filename);
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        StringWriter sw = new StringWriter();
     //   FileWriter filewriter = new FileWriter(new File(filename));
        FileWriter filewriter = null;
        assertThrows(InvalidParameterException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                TextDumper dumper = new TextDumper(filewriter, sw,filename);
                dumper.dump(appointmentBook);
            }
        });
    }
    @Test
    public void appointmentOwnerComparisonBetweenEnterValueandTextFileWhenOwnerisNull() throws IOException {
        String owner =  "";
        String filename = "nullfile.txt";
        InputStream resource = getClass().getResourceAsStream(filename);
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        StringWriter sw = new StringWriter();
        FileWriter filewriter = new FileWriter(new File(filename));

        assertThrows(InvalidParameterException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                TextDumper dumper = new TextDumper(filewriter, sw,filename);
                dumper.dump(appointmentBook);
            }
        });
    }
    @Test
    void appointmentBookOwnerCanBeDumpedWithRelativePath() throws IOException, ParserException{
        String filename = "Folder/file2";
        String owner = "Pooja";
        AppointmentBook appBook = new AppointmentBook(owner);
        StringWriter sw = new StringWriter();
        FileWriter filewriter = new FileWriter(new File(filename),true);
        TextDumper textDumper = new TextDumper(filewriter,sw,filename);
        textDumper.dump(appBook);
        String text = sw.toString();
        assertThat(text,containsString(owner));
    }
    @Test
    void appointmentBookOwnerCanBeDumpedWithRelativePathAndExtension() throws IOException, ParserException{
        String filename = "Folder/file1.txt";
        String owner = "Pooja";
        AppointmentBook appBook = new AppointmentBook(owner);
        StringWriter sw = new StringWriter();
        FileWriter filewriter = null;
        TextDumper textDumper = new TextDumper(filewriter,sw,filename);
        textDumper.dump(appBook);
        String text = sw.toString();
        assertThat(text,containsString(owner));

    }

    @Test
    void createsNewFileWhenItDoesnotExistWithRelativePath() throws IOException {
        String filename = "Folder/file5.txt";
        String owner = "Pooja";
        AppointmentBook appBook = new AppointmentBook(owner);
        StringWriter sw = new StringWriter();
        FileWriter filewriter = null;
        TextDumper textDumper = new TextDumper(filewriter,sw,filename);
        textDumper.dump(appBook);
        String text = sw.toString();
        assertThat(text,containsString(owner));
    }

    @Test
    void createsNewFileWhenItDoesnotExist() throws IOException {
        String filename = "NewFileCreation.txt";
        String owner = "Pooja";
        AppointmentBook appBook = new AppointmentBook(owner);
        StringWriter sw = new StringWriter();
        FileWriter filewriter = null;
        TextDumper textDumper = new TextDumper(filewriter,sw,filename);
        textDumper.dump(appBook);
        String text = sw.toString();
        assertThat(text,containsString(owner));
    }

    @Test
    public void whenOwnerNameIsNotEntered() throws IOException {
        String owner = "";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        StringWriter sw = new StringWriter();
        FileWriter filewriter = null;

        assertThrows(InvalidParameterException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                TextDumper dumper = new TextDumper(filewriter, sw, "Folder/PeterFile.txt");
                dumper.dump(appointmentBook);
            }
        });

    }
    @Test
    public void whenOwnerNameIsAbsentInAlreadyExistingFile() throws IOException {
        String owner = "Reese";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        StringWriter sw = new StringWriter();
        FileWriter filewriter = null;

        assertThrows(InvalidParameterException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                TextDumper dumper = new TextDumper(filewriter, sw, "Folder/OwnerLeese.txt");
                dumper.dump(appointmentBook);
            }
        });

    }

    @Test
    public void whenOwnerNameIsAbsentInInput() throws IOException {
        String owner = "";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        StringWriter sw = new StringWriter();
        FileWriter filewriter = null;

        assertThrows(InvalidParameterException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                TextDumper dumper = new TextDumper(filewriter, sw, "Folder/OwnerLeese.txt");
                dumper.dump(appointmentBook);
                //assertThat();
            }
        }, "Owner name not entered in the input");

    }

    @Test
    public void createNewFolderAndFileWhenNotExisting() throws IOException {
        String owner = "Leese";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        StringWriter sw = new StringWriter();
        FileWriter filewriter = null;
        TextDumper dumper = new TextDumper(filewriter, sw, "LeeseFolder/OwnerLeese.txt");
        dumper.dump(appointmentBook);


    }

}
