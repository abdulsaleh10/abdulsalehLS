# LMS Project
Learning Management System: Discussion Board GUI

HOW TO USE:

    In order to run this program, you will need to import all the code in the source folder into an IDE.

    Within the program, the choices are numbered, so enter the number of the corresponding choice that 
    you want.

    When commenting or replying to a discussion post, there will also be a number beside each of these 
    things, so that you can specify which discussion or reply you want to comment to.
    
Classes and its Functions

    Comment Class
    
        This class contains methods to add comments to discussion posts and 
        replies to comments. There is also a grade method where the teacher
        can assign a grade to the replies in the discussion. There are also 
        array lists that contains the replies and comments. The toString in
        this class shows the comments and replies to the user who is logged
        in, in this program.
        
    DiscussionBoard Class
  
        This class contains the main method where the all the code comes 
        together and is ran. Within this class, there are many methods 
        that sections off the numerous parts of the main method for 
        organizational purposes.
        
    DiscussionForum Class
    
        The discussion forum class is where each forum exists. Each of
        these forums contain a title, content, and comments to this 
        specific discussion forum. Basically, this forum is the format
        of each discussion post made by the teacher.
        
    Student Class
    
        The student class contains methods that are relevant to their 
        role. This also includes username and password methods for 
        creating and checking whether the account exists.
        
    Teacher Class
    
        This class is very similar to the student class. The only 
        difference is the roles that the teacher has access to;
        besides that, it is the same as the student class.
        
    P5Client Class
    
        This class contains the frontend code where the interface of
        the project takes place. This is what the user will see when the
        program is ran. These screens include the login screens where the
        user will enter the username and password information, which then
        will be sent to the P5Server class to see if the information exists
        if the user is choosing to login.
        
    P5Server Class
    
        This class is where all the backend code will be. This class is 
        responsible for storing username and password information, as well as
        checking if a certain account exists or not. If the account exists, this
        class will send an unique string such as "ahdjeb" as a code to let the 
        algorithm know that the account does exist. This will then allow the user
        to login and access their respective roles depending whether they are a 
        student or a teacher.
        
Testing 

    When testing our code, we ran through all the functionality of buttons to make
    sure that everythings works as planned. We also checked through possible way that
    could create errors and end our program by typing in certain things in text boxes 
    where possible. To make sure the GUI never ends unless the client exits out of it,
    the back button will be pressed on every possible chance to make sure that each of
    the buttons works as expected. The program should also not end when it fails to login,
    but instead should bring the user back to the login screen.
  
  Made by Abdul, Chris, Daniel, Dayoon.
