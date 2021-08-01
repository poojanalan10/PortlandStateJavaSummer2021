package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
/**
 * This class represents a <code>TextParser</code>
 */


public class TextParser implements AppointmentBookParser<AppointmentBook> {
    /**
     * The reader object
     */
    private final Reader reader;
    /**
     * The buffered reader object
     */
    private static BufferedReader bufferedreader;

    /**
     * Text parser constructor
     * @param reader
     * @throws IOException
     */
    public TextParser(Reader reader) throws IOException {

        this.reader = reader;


    }

    /**
     * Parses the appointment
     * @return book
     *          the appointment book
     * @throws ParserException
     * @throws ArrayIndexOutOfBoundsException
     */
    @Override
    public AppointmentBook parse() throws ParserException, ArrayIndexOutOfBoundsException {
        AppointmentBook book = null;

        BufferedReader bufferedreader = new BufferedReader(this.reader);
        try {
            if (!bufferedreader.ready()) {
                // throw new ParserException("Missing owner");
                throw new IOException("Buffered Reader not ready!");
            } else {
                String owner = bufferedreader.readLine();
                if (owner == null) {
                    throw new ParserException("Missing owner");
                }
                book = new AppointmentBook(owner);
                for (String line = bufferedreader.readLine(); line != null; line = bufferedreader.readLine()) {
                    String[] args = line.split(",");
                    if (args.length != 3) {
                        throw new ArrayIndexOutOfBoundsException("The given appointment does not have description, start date and time,  end date and time in the right format!");
                    }
                    String description = args[0];
                    String startTimeString = args[1];
                    String endTimeString = args[2];
                    book.addAppointment(new Appointment(description, startTimeString, endTimeString));

                }
                return book;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return book;
    }
}


