import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
public class LibrarySystem {
    static class book{
        int isbn;
        String name;
        int count;
        double cost;
        int borrow=0;
        book(int isbn,String name,int count,double cost){
            this.isbn=isbn;
            this.name=name;
            this.count=count;
            this.cost=cost;
        }
    }
    static class borrowrecord{
          book bk;
          int extensions=0;
          LocalDate borrowdate;
          borrowrecord(book bk,LocalDate borrowdate){
            this.bk=bk;
            this.borrowdate=borrowdate;
          }
    }
      static class borrow{
        List<borrowrecord>borrow=new ArrayList<>();
        List<String>fine=new ArrayList<>();
        String email;
        String password;
        double deposit=1500;
        borrow(String email,String password){
            this.email=email;
            this.password=password;
        }
      }
 static Scanner sc=new Scanner(System.in);
 static String admin_email="admin@kpriet.ac.in";
 static String password="admin";
 static HashMap<Integer,book>books=new HashMap<>();
 static HashMap<String,borrow>borrowers=new HashMap<>();
 static DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
 static void seedbooks(){
         books.put(101,new book(101,"DSA",17,2450));
        books.put(102,new book(102,"JAVA",20,789));
        books.put(103,new book(103,"Aptitude",38,750));

 }
 public static void main(String[] args){
    seedbooks();
    borrowerlist();
    while(true){
           System.out.println("-----Library System-----");
           System.out.println("1.Admin Login");
           System.out.println("2.User login");
           System.out.println("3.Exit");
           int ch=sc.nextInt();
           sc.nextLine();
           switch(ch){
            case 1:
                adminLogin();
                break;
            case 2:
                userLogin();
                break;
            case 3:
                System.exit(0);
           }

    }
 }
 static void adminLogin(){
    System.out.println("Enter the admin email");
    String email=sc.next();
    System.out.println("Enter the admin password");
    String pass=sc.next();
    if(email.equals(admin_email)&&pass.equals(password)){
        adminmenu();

    }
    else{
        System.out.println("Invalid Login Credentials");
        return;
    }

    
 }
 static void adminmenu(){
     while(true){
        System.out.println("----Admin Menu---");
        System.out.println("1.Add Books");
        System.out.println("2.Modify Books");
        System.out.println("3.View Books");
        System.out.println("4.Add Borrower");
        System.out.println("5.Delete Book");
        System.out.println("6.Reports");
        System.out.println("7.Exit");
        int c=sc.nextInt();
        sc.nextLine();
        switch(c){
            case 1:
                System.out.println("Enter the ISBN:");
                int isb=sc.nextInt();
                sc.nextLine();
                System.out.println("Enter Book Name:");
                String name=sc.nextLine();
                System.out.println("Enter the quantity");
                int q=sc.nextInt();
                System.out.println("Enter the cost");
                double cost=sc.nextDouble();
                books.put(isb,new book(isb,name,q,cost));
                System.out.println("Book Added Successfully");
                break;
            case 2:
                System.out.println("Enter the ISBN:");
                int i=sc.nextInt();
                if(books.containsKey(i)){
                    System.out.println("New quantity:");
                    books.get(i).count=sc.nextInt();}
                    break;
            case 3:
                System.out.println("Books Available:");
                for(book b:books.values()){
                    System.out.println("ISBN No:"+b.isbn+" Name:"+b.name+" Count:"+b.count+" Cost:"+b.cost);
                }
                break;
            case 4:
                System.out.println("Enter the borrower email");
                String bemail=sc.nextLine();
                System.out.println("Enter the borrower password");
                String bpass=sc.nextLine();
                borrowers.put(bemail,new borrow(bemail, bpass));
                System.out.println("Added Successfully");
                break;
            case 5:
                System.out.println("Enter the ISBN of the book");
                int disbn=sc.nextInt();
                books.remove(disbn);
                System.out.println("Deleted Successfully");
                break;
            case 6:
                reports();
                break;
            case 7:
                System.exit(0);


        }

     }
 }
 static void borrowerlist(){
    borrowers.put("23ec059",new borrow("23ec059","lak@2508"));
    borrowers.put("23ec039",new borrow("23ec039","harsh@2108"));
    borrowers.put("23ad015",new borrow("23ad015","divs"));
    borrowers.put("23it042",new borrow("23it042","mendali"));
    }
    static void userLogin(){
        System.out.println("Enter your username:");
        String uemail=sc.next();
        System.out.println("Enter the password:");
        String password=sc.next();
        borrow b=borrowers.get(uemail);
        if(b!=null&&b.password.equals(password)){
            usermenu(b);
        }
        else{
            System.out.println("Enter valid Credentials");
        }
    }
    static void usermenu(borrow b){
        while(true){
        System.out.println("-----User Menu-----");
        System.out.println("1.View books");
        System.out.println("2.Borrow book");
        System.out.println("3.View borrowed book");
        System.out.println("4.Return book");
        System.out.println("5.Exit");
        int c=sc.nextInt();
        switch(c){
            case 1:
                System.out.println("Books Available:");
                for(book bk:books.values()){
                    System.out.println(bk.isbn+" "+bk.name+" "+bk.count);
                }
                break;
            case 2:
                if(b.borrow.size()==3){
                System.out.println("Maximum 3 books only allowed");
                break;
               }
               if(b.deposit<500){
                System.out.println("Insufficient Deposit");
                break;
               }
               System.out.println("Enter ISBN:");
               int isbn=sc.nextInt();
               if(!books.containsKey(isbn)){
                System.out.println("Book not found");
                break;
               }
               book bk=books.get(isbn);
               if(bk.count==0){
                System.out.println("Out of Stock");
                break;
               }
               boolean already=false;
               for(borrowrecord br:b.borrow){
                if(br.bk.isbn==isbn){
                      already=true;
                      break;
                }
               }
               if(already){
                System.out.println("Same book cannot be borrowed twice");
                break;
               }
               System.out.println("Enter borrow date (DD/MM/YYYY):");
               String bd=sc.next();
               LocalDate bdate=LocalDate.parse(bd,formatter);
               b.borrow.add(new borrowrecord(bk, bdate));
               bk.count--;
               bk.borrow++;
               System.out.println("Borrowed Successfully");
               break;

            case 3:
               for(borrowrecord br:b.borrow){
                System.out.println(br.bk.name+"-Borrowed day: "+br.borrowdate);
               }
                break;
            case 4:
                for(borrowrecord br : b.borrow){
                System.out.println(br.bk.isbn);
                      }
                System.out.println("Enter ISBN: ");
                int rid=sc.nextInt();
                Iterator<borrowrecord>it=b.borrow.iterator();
                boolean bo=false;
                while(it.hasNext()){
                    borrowrecord br=it.next();
                    if(br.bk.isbn==rid){
                        System.out.println("Return date (DD/MM/YYYY): ");
                        String rutdate=sc.next();
                        LocalDate rdate=LocalDate.parse(rutdate,formatter);
                        long days=ChronoUnit.DAYS.between(br.borrowdate,rdate);
                        double fine=0;
                        if(days>15){
                            fine=(days-15)*2;
                            double max=br.bk.cost*0.8;
                            if(fine>max)
                                fine=max;
                        }
                        b.deposit-=fine;
                        if(fine>0){
                            b.fine.add("Fine: "+fine);
                        }
                        br.bk.count++;
                        it.remove();
                        System.out.println("Returned with fine: "+fine);
                        bo=true;
                        break;
                    }
                }
                if(!bo){
                    System.out.println("You have not borrowed this book");

                }
                break;
            case 5:
                System.exit(0);

        }

    }
}
static void reports(){
          System.out.println("Low stock books:");
          for(book b:books.values()){
            if(b.count<5){
                System.out.println(b.name);
            }
          }
          System.out.println("Never borrowed: ");
          for(book b:books.values()){
            if(b.borrow==0){
                 System.out.println(b.name);
            }
          }
          System.out.println("Highly borrowed:");
          for(book b : books.values()){
            if(b.borrow>5){
                System.out.println(b.name);
            }
          }
}

}
