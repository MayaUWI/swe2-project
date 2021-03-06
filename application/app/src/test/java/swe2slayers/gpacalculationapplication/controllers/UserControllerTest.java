package swe2slayers.gpacalculationapplication.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import swe2slayers.gpacalculationapplication.models.Assignment;
import swe2slayers.gpacalculationapplication.models.Course;
import swe2slayers.gpacalculationapplication.models.Exam;
import swe2slayers.gpacalculationapplication.models.Semester;
import swe2slayers.gpacalculationapplication.models.User;
import swe2slayers.gpacalculationapplication.models.Year;
import swe2slayers.gpacalculationapplication.utils.Date;
import swe2slayers.gpacalculationapplication.utils.FirebaseDatabaseHelper;
import swe2slayers.gpacalculationapplication.utils.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static swe2slayers.gpacalculationapplication.controllers.UserController.getYearsForUser;

public class UserControllerTest {
    User user;
    Year yr;
    Semester s;
    Course c1,c2;
    Assignment a;
    Exam e;
    @Before
    public void before() {
        FirebaseDatabaseHelper.enableTestingMode();
        FirebaseDatabaseHelper.load(user, null);
        Date dateE = new Date(19, 12, 2019);

        user = new User("S9oThHsvlAX8OVSBA0Xp09mNKMr2", "test@test.com", "SWETest", "Cases", 816000111, "Computer Science", 3.8, 3.8);

        yr = new Year("Year 1", user.getUserId());
        yr.setYearId("123456");
        UserController.addYearForUser(user,yr,null);

        s = new Semester("Semester 1", yr.getYearId(), user.getUserId());
        s.setSemesterId("d324nk34iN3DNSD");
        UserController.addSemesterForUser(user,s,null);


        Course c1 =new Course("Math 2250","Industrial Statistics",s.getSemesterId(),user.getUserId(),3,2,85);
        c1.setCourseId("128102jkosj");
        Course c2 =new Course("Foun 1100","Caribbean Civ",s.getSemesterId(),user.getUserId(),3,1,50);
        c2.setCourseId("s239123sds");
        UserController.addCourseForUser(user,c1,null);
        UserController.addCourseForUser(user,c2,null);

        a = new Assignment("1289", "Assignment1", dateE, 100, 20, 100);
        a.setUserId(user.getUserId());
        UserController.addAssignmentForUser(user,a,null);

        e = new Exam("1293", "CW Exam 1", dateE, 50, 60, 84);
        e.setUserId(user.getUserId());
        UserController.addExamForUser(user,e,null);
    }

    @Test
    public void updateYearForUser() {
        assertFalse(yr.getUserId()!=user.getUserId());
        assertFalse(yr.getYearId()!="123456");
        yr.setYearId("56789");
        yr.setTitle("Year 3");
        UserController.updateYearForUser(user,yr,null);
        assertEquals("Year ID: ",yr.getYearId(),"56789");
        assertEquals("Year Title: ",yr.getTitle(),"Year 3");
        }

    @Test
    public void updateSemesterForUser() {
        assertFalse(s.getSemesterId()!="d324nk34iN3DNSD");
        assertFalse(s.getTitle()!="Semester 1");
        assertFalse(s.getUserId()!=user.getUserId());
        s.setSemesterId("298whe1282UJuw");
        s.setTitle("Semester 2");
        s.setYearId("56789");
        UserController.updateSemesterForUser(user,s,null);
        assertEquals("Semester ID: ",s.getSemesterId(),"298whe1282UJuw");
        assertEquals("Semester Title: ",s.getTitle(),"Semester 2");
        assertEquals("Year ID: ",s.getYearId(),"56789");

    }

    @Test
    public void updateCourseForUser() {
        Course c= new Course("Comp 3613","Software Engineering 2",s.getSemesterId(),user.getUserId(),3,3,85);
        c.setCourseId("1weqweqwe");
        UserController.addCourseForUser(user,c,null);
        c.setLevel(2);
        c.setCredits(1);
        c.setUserId(user.getUserId());
        c.setCode("Comp3603");
        c.setName("Human and Computer Interaction");
        UserController.updateCourseForUser(user,c,null);
        assertFalse(c.getLevel()!=2);
        assertFalse(c.getCredits()!=1);
        assertFalse(c.getCode()!="Comp3603");
        assertFalse(c.getName()!="Human and Computer Interaction");
        assertFalse(c.getUserId()!=user.getUserId());

    }

    @Test
    public void updateAssignmentForUser() {
        User u =user;
        assertFalse(a.getWeight()!=100);
        assertFalse(a.getTotal()!=100);
        assertFalse(a.getMark()!=20);
        a.setWeight(15);
        a.setTotal(100);
        a.setUserId(user.getUserId());
        a.setCourseId(c2.getCourseId());
        UserController.updateAssignmentForUser(u,a,null);
        assertFalse(a.getWeight()!=15);
        assertFalse(a.getTotal()!=100);
        assertFalse(a.getUserId()!=user.getUserId());

    }

    @Test
    public void updateExamForUser() {
        User u =user;
        assertFalse(e.getWeight()!=50);
        assertFalse(e.getTotal()!=84);
        assertFalse(e.getMark()!=60);
        e.setDuration(60);
        e.setCourseId(c1.getCourseId());
        e.setUserId(user.getUserId());
        e.setRoom("FST CSL 2");
        e.setNote("Ask Lecuturer about question 2 b");
        UserController.updateExamForUser(u,e,null);
        assertFalse(e.getRoom()!="FST CSL 2");
        assertFalse(e.getTitle()!="CW Exam 1");
        assertFalse(e.getNote()==null);
    }

    @Test
    public void calculateDegreeGPA() {
        User u =user;

        assertFalse(FirebaseDatabaseHelper.getGradingSchema()==null);
        System.out.println(UserController.calculateDegreeGPA(u));
        assertFalse(UserController.calculateDegreeGPA(u)==0.0);
        assertFalse(UserController.calculateDegreeGPA(u)>4.3);

    }

    @Test
    public void calculateCumulativeGPA() {
        User u =user;
        assertFalse(FirebaseDatabaseHelper.getGradingSchema()==null);
        System.out.println(UserController.calculateCumulativeGPA(u));
        assertFalse(UserController.calculateCumulativeGPA(u)==0.0);
        assertFalse(UserController.calculateCumulativeGPA(u)>4.3);

    }

    @After
    public void after() {
        // Disable Testing Mode
        swe2slayers.gpacalculationapplication.utils.FirebaseDatabaseHelper.disableTestingMode();
    }

}