package Flipzon;
import java.util.*;
import java.util.Scanner;
public class Flipzon_main {
    private ArrayList<Normal_Customer> Noraml_customers;
    private Admin admin;
    public Flipzon_main(){
        admin=new Admin();
        Noraml_customers=new ArrayList<>();
        this.Main_display();
    }

    public void asAdmin(){
        String default_username="Mayank Gupta";
        String default_psswd="2021065";

        System.out.println("Dear Admin,");
        System.out.println("Enter the username:");
        Scanner sc1=new Scanner(System.in);
        String name= sc1.nextLine();
        System.out.println("Enter your password:");
        Scanner sc2=new Scanner(System.in);
        String psswd=sc2.nextLine();
        String [] def_username=default_username.split(" ");
        int incorrect=0;
        if(name.contains(" ") && default_psswd.equals(psswd)){
            String [] Name=name.split(" ");

            if(Name.length!=def_username.length){
                System.out.println("Incorrect Username or Password1");
                incorrect=1;
            }
            else{
                for(int i=0;i<2;i++){
                    if(!Name[i].equals(def_username[i])){
                        System.out.println("Incorrect Username or Password2");
                        incorrect=1;
                        break;
                    }
                }
            }

        }
        else{
            System.out.println("Incorrect username or password3");
            incorrect=1;
        }
        if(incorrect==0){
            admin.setName(name);
            admin.setPsswd(psswd);
            while(true){
                System.out.println("\n");
                System.out.println("Welcome "+name+"!!!");
                System.out.println("Please choose any one of the following actions:");
                System.out.println("\t1) Add category");
                System.out.println("\t2) Delete category");
                System.out.println("\t3) Add Product");
                System.out.println("\t4) Delete Product");
                System.out.println("\t5) Set Discount on Product");
                System.out.println("\t6) Add giveaway deal");
                System.out.println("\t7) Back");
                Scanner sc5=new Scanner(System.in);
                int input=sc5.nextInt();
                if(input==1){
                    admin.Add_category();
                }
                if(input==2){
                    Scanner sc3=new Scanner(System.in);
                    Scanner sc6=new Scanner(System.in);
                    System.out.println("Enter the name of the category:");
                    String Name=sc3.nextLine();
                    System.out.println("Enter Category_ID:");
                    int Category_id=sc6.nextInt();
                    admin.delete_category(Name,Category_id);
                }
                if(input==3){
                    admin.Add_product();
                }
                if(input==4){
                    Scanner sc3=new Scanner(System.in);
                    System.out.println("Enter the category name:");
                    String namee=sc3.nextLine();
                    Scanner sc6=new Scanner(System.in);
                    System.out.println("Enter Product_id:");
                    float product_id=sc6.nextFloat();
                    admin.Delete_Product(namee,product_id);
                }

                if(input==5){
                    admin.set_discount();
                }
                if(input==6){
                    admin.give_deals();
                }
                if(input==7){
                    break;
                }
            }
        }
    }
    public void asCustomer(){
        while(true){
            System.out.println("1) Sign up");
            System.out.println("2) Log In");
            System.out.println("3) Back");
            Scanner sc= new Scanner(System.in);
            int input_num=sc.nextInt();
            if(input_num==1){
                Normal_Customer cus=new Normal_Customer();
                cus.Signup();
                this.Noraml_customers.add(cus);
            }
            else if(input_num==2){
                if(this.Noraml_customers.size()==0 ){
                    System.out.println("Please Sign in first");
                }
                Scanner sc1=new Scanner(System.in);
                System.out.println("Enter name:");
                String name_=sc1.nextLine();
                Scanner sc2=new Scanner(System.in);
                System.out.println("Enter password:");
                String psswd=sc2.nextLine();
                int found=0;
                Normal_Customer cus=null;
                if(this.Noraml_customers.size()!=0){
                    for(Normal_Customer cus_1:Noraml_customers){
                        if(psswd.equals(cus_1.get_password())){
                            String [] name_1=name_.split(" ");
                            String [] name_2=cus_1.getName().split(" ");
                            for(int i=0;i<2;i++){
                                if(name_1[i].equals(name_2[i])){
                                    found=1;
                                    cus=cus_1;
                                    break;
                                }
                            }
                        }
                    }
                }
                if(found==1){
                    while(true){
                        System.out.println("Welcome "+cus.getName()+"!!");
                        System.out.println("\t1)  browse products") ;
                        System.out.println("\t2)  browse deals");
                        System.out.println("\t3)  add a product to cart");
                        System.out.println("\t4)  add products in deal to cart");
                        System.out.println("\t5)  view coupons");
                        System.out.println("\t6)  check account balance");
                        System.out.println("\t7)  view cart");
                        System.out.println("\t8)  empty cart");
                        System.out.println("\t9)  checkout cart");
                        System.out.println("\t10) upgrade customer status");
                        System.out.println("\t11) Add amount to wallet");
                        System.out.println("\t12) back");
                        Scanner sc7=new Scanner(System.in);
                        int input1= sc7.nextInt();
                        if(input1==1){
                            cus.browse_product(admin);
                        }
                        if(input1==2){
                            if(admin.list_of_deals().size()==0){
                                System.out.println("Currently there is no deal available");
                            }
                            else {
                                System.out.println("Here is the deal!!!");
                                System.out.println("If you buy the following two products then the combined price is less than sum of their prices");
                                int i=1;
                                for (Product product : admin.list_of_deals()) {
                                    System.out.println(  i+")Name of the product:" + product.getname());
                                    System.out.println("Product ID:" + product.get_product_id());
                                    System.out.println("Price:" + product.get_price());
                                    product.printing_extra_details();
                                    System.out.println("\n");
                                    i++;
                                }
                                System.out.println("Combined price for Elite in Rs:"+admin.list_of_prices().get(0));
                                System.out.println("Combined price for Prime in Rs:"+admin.list_of_prices().get(1));
                                System.out.println("Combined price for Normal in Rs"+admin.list_of_prices().get(2));
                            }
                        }
                        if(input1==3){
                                cus.add_product(admin);
                        }
                        if(input1==4){
                                cus.add_deal_product(admin);
                        }
                        if(input1==5){
                            cus.view_coupons();

                        }
                        if(input1==6){
                            System.out.println("Current amount in Wallet:"+cus.get_amount_in_wallet());
                        }
                        if(input1==7){
                            cus.view_cart(admin);
                        }
                        if(input1==8){
                            cus.empty_cart();
                        }
                        if(input1==9){
                            cus.checkout_cart(admin);
                        }
                        if(input1==10){
                            System.out.println("Current Status:"+cus.getCurrent_status());
                            System.out.println("Choose New Status:");
                            Scanner sc11=new Scanner(System.in);
                            String updated_status=sc11.nextLine();
                            if(cus.getCurrent_status()=="NORMAL") {
                                if (updated_status.equals("ELITE")) {

                                    Normal_Customer cus1 = new Elite_Customer();
                                    cus1.Set_everything(cus.getName(), cus.get_password(), cus.get_amount_in_wallet());
                                    cus1.Updating_wallet();
                                    cus = cus1;

                                } else if (updated_status.equals("PRIME")) {

                                    Normal_Customer cus1 = new Prime_Customer();
                                    cus1.Set_everything(cus.getName(), cus.get_password(), cus.get_amount_in_wallet());
                                    cus1.Updating_wallet();
                                    cus = cus1;

                                }
                            }
                         else{
                             System.out.println("Such Status change is not possible");
                            }
                        }
                        if(input1==11){
                            System.out.println("Enter the amount you want to add in the wallet:");
                            Scanner sc10=new Scanner(System.in);
                            float amount=sc10.nextFloat();
                            cus.add_amount_to_wallet(amount);
                            System.out.println("Amount has been successfully added");

                        }
                        if(input1==12){
                            break;
                        }
                    }
                }
                else{
                    System.out.println("Incorrect Username or Password");
                }

            }
            else if(input_num==3){
                break;
            }
        }
    }

    public void Main_display() {
        while(true){
            System.out.println("WELCOME TO FLIPZON:");
            System.out.println("\t1) Enter as Admin ");
            System.out.println("\t2) Explore the Product Catalog ");
            System.out.println("\t3) Show available Deals");
            System.out.println("\t4) Enter as Customer ");
            System.out.println("\t5) Exit the Application");
            Scanner sc = new Scanner(System.in);
            int num1 = sc.nextInt();
            if (num1 == 1) {
                this.asAdmin();
            } else if (num1 == 2) {
                admin.printing_product_Catalog();

            } else if (num1 == 3) {
                if(admin.list_of_deals().size()==0){
                    System.out.println("Currently there is no deal available");
                }
                else {
                    System.out.println("Here is the deal!!!");
                    System.out.println("If you buy the following two products then the combined price is less than sum of their prices");
                    int i=1;
                    for (Product product : admin.list_of_deals()) {
                        System.out.println(  i+")Name of the product:" + product.getname());
                        System.out.println("Product ID:" + product.get_product_id());
                        System.out.println("Price:" + product.get_price());
                        product.printing_extra_details();
                        System.out.println("\n");
                        i++;
                    }
                    System.out.println("Combined price for Elite in Rs:"+admin.list_of_prices().get(0));
                    System.out.println("Combined price for Prime in Rs:"+admin.list_of_prices().get(1));
                    System.out.println("Combined price for Normal in Rs"+admin.list_of_prices().get(2));
                }
            } else if(num1==4){
                this.asCustomer();
            }

            else{
                this.exit_app();
                break;
            }
        }
    }
    public void exit_app(){
        System.out.println("Thanks For Using This Portal!!!");
    }
    public static void main (String[]args){
        Flipzon_main flipzon = new Flipzon_main();
    }
}

