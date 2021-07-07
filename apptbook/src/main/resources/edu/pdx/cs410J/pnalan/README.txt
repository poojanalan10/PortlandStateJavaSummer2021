README Name: Pooja Nalan Project : apptbook This project adds an appointment to an appointment book belonging to a particular owner taking the necessary details about the appointment which includes owner name, purpose of the appointment, start date and time and end date and time.
The argument list is as follows:
<owner> <description> <appointment begin date> <appointment begin time> <appointment end date> <appointment end time>
[options] may appear in any order and the options are
    -print
    -README
When -README option is entered either as the first or second argument along with other arguments or just that -README option alone, the program will display the contents of the README.txt file in which the input command line arguments are mentioned in its order of usage.
When -print option is selected, te program checks if all the arguments necessary for an appointment is given and once entered, the arguments appointment date and time are validated and a new appointment is created and added to the appointment book. Also, the appointment
details of the owner are printed.
When no arguments are entered/ argument list is empty or just a -print is given without any options, if any of the required options are missing, when the validation of the entered arguments fails, then the program will print the usage of the command line arguments by
printing appropriate error message.
