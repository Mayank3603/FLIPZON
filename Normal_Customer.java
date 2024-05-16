package Flipzon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.lang.Math;

public class Normal_Customer {
    public String name;
    public String password;
    public float wallet=1000;
    private String current_status="NORMAL";
    private int lower_delivery=7;
    private  int upper_delivery=10;

    private float default_discount=0;
    private float delivery_percent =5;

    private float total_amount=0;

    private ArrayList<Integer> coupons;
    private HashMap<Product,ArrayList<Float>> cart;

    private float deal_combined_price;
    private int if_added_deal_cart=0;
    private int quantity_deal_product=0;
    private int used_product_discount=0;

    public void Set_everything(String name,String password,float wallet){
        this.name=name;
        this.password=password;
        this.wallet=wallet;

    }
    public float getWallet(){
        return this.wallet;
    }


    public void add_deal_product(Admin admin){
        System.out.println("Enter the pairs of Deal");
        Scanner sc=new Scanner(System.in);
        int number=sc.nextInt();
        if(admin.list_of_deals().get(0).getQuantity()>=number && admin.list_of_deals().get(1).getQuantity()>=number) {
            this.if_added_deal_cart = 1;
            this.quantity_deal_product = number;
            if (this.getCurrent_status() == "NORMAL") {
                this.deal_combined_price = admin.list_of_prices().get(2);
            } else if (this.getCurrent_status() == "ELITE") {
                this.deal_combined_price = admin.list_of_prices().get(0);
            } else if (this.getCurrent_status() == "PRIME") {
                this.deal_combined_price = admin.list_of_prices().get(1);
            }
        }
        else{
            System.out.println("The amount of quantity you are asking are in greater than we have!");
        }

    }
    public Normal_Customer(){
        coupons=new ArrayList<>();
        cart=new HashMap<>();

    }

    public Normal_Customer(String current_status,int lower_delivery,int upper_delivery,float default_discount,float delivery_percent){
        this.lower_delivery=lower_delivery;
        this.current_status=current_status;
        this.upper_delivery=upper_delivery;
        this.default_discount=default_discount;
        this.delivery_percent=delivery_percent;
        coupons=new ArrayList<>();
        cart=new HashMap<>();


    }
    public void add_product(Admin admin){

        System.out.println("Enter product ID");
        Scanner sc=new Scanner(System.in);
        float product_id=sc.nextFloat();
        Category category=null;
        int cat_of_product=(int)product_id;
        for(Category cat:admin.getCategories()){
            if(cat.get_category_id()==cat_of_product){;
                category=cat;
                break;
            }
        }
        Product product=null;
        for(int i=0;i<admin.getCat_product().get(category).size();i++){
            if(admin.getCat_product().get(category).get(i).get_product_id()==product_id){
                product=admin.getCat_product().get(category).get(i);
                break;
            }
        }

        System.out.println("Enter quantity:");
        int quantity=sc.nextInt();
        if(product.getQuantity()>=quantity){
            float product_discount=0;
            if(product.If_In_discount()==1){
                if(this.getCurrent_status()=="NORMAL"){
                    product_discount=(product.getDiscounts_on_a_product().get(2));
                }
                else if(this.getCurrent_status()=="ELITE"){
                    product_discount=(product.getDiscounts_on_a_product().get(0));
                }
                else if(this.getCurrent_status()=="PRIME"){
                    product_discount=(product.getDiscounts_on_a_product().get(1));
                }
            }
            ArrayList<Float> value =new ArrayList<>();
            float max=0;
            if(this.coupons.size()!=0){
                for(int i=0;i<this.coupons.size();i++){
                    if(this.coupons.get(i)>max){
                        max=this.coupons.get(i);
                    }
                }
            }
            if(max<=product_discount){
                max=product_discount;
                this.used_product_discount=1;
            }
            if(max<=this.default_discount){
                max=this.default_discount;
            }
            value.add((float)quantity);
            value.add(max);
            this.cart.put(product,value);
            System.out.println("Product added successfully");
        }
        else{
            System.out.println("Out of Stock");
        }

    }
    public float getTotal_amount(){
        return this.total_amount;
    }
    public void checkout_cart(Admin admin){
        float total_discount=0;
        float total_product_price=0;
        if(this.cart.size()!=0) {
            for (Product product : cart.keySet()) {

                total_discount += (cart.get(product).get(1) / 100) * product.get_price()*cart.get(product).get(0);
                total_product_price += product.get_price() * cart.get(product).get(0);

            }
        }
        if(this.if_added_deal_cart==1){
            total_product_price=this.deal_combined_price*this.quantity_deal_product;
            float max=0;
            if(this.coupons.size()!=0){
                for(int i=0;i<this.coupons.size();i++){
                    if(this.coupons.get(i)>max){
                        max=this.coupons.get(i);
                    }
                }
            }
            if(max<=this.default_discount){
                max=this.default_discount;
            }

        }
        this.total_amount+=total_product_price;
        this.total_amount-=total_discount;
        float delivery_charges= 100+(this.delivery_percent/100)*total_product_price;
        this.total_amount+=delivery_charges;

        if(this.total_amount<=this.get_amount_in_wallet()){
            System.out.println("Your order has been placed successfully!");
            System.out.println("\n");
            int i=0;
            if(this.cart.size()!=0) {
                for (Product product : cart.keySet()) {
                    System.out.println(i + ")Name of the product:" + product.getname());
                    System.out.println("Product ID:" + product.get_product_id());
                    System.out.println("Price:" + product.get_price() * cart.get(product).get(0));
                    System.out.println("Quantity:" + cart.get(product).get(0));
                    product.printing_extra_details();
                    product.Update_qunatity(cart.get(product).get(0));
                    System.out.println("\n");
                    i++;
                }
            }
            if(this.if_added_deal_cart==1){
                int j=0;
                for(Product product:admin.list_of_deals()){
                    System.out.println(j+")Name of the product:" +product.getname());
                    System.out.println("Product ID:"+product.get_product_id());
                    System.out.println("Price:"+product.get_price());
                    System.out.println("Quantity:"+this.quantity_deal_product);
                    product.Update_qunatity(this.quantity_deal_product);
                    product.printing_extra_details();
                    System.out.println("\n");
                    j++;
                }
                System.out.println("\n");

            }

            System.out.println("Delivery Charges:" +delivery_charges);
            System.out.println("Discount:"+total_discount);
            System.out.println("Total Amount:"+total_amount);
            System.out.println("Your order will be delivered within "+this.lower_delivery+"-"+this.upper_delivery+"days");
            this.wallet=this.wallet-this.total_amount;
            if(this.coupons.size()!=0){
                float max=0;
                int index=0;

                for( int l=0;l<this.coupons.size();l++){
                    if(max<this.coupons.get(l)){
                        max=this.coupons.get(l);
                        index=l;
                    }
                }
                if(max>this.default_discount && this.used_product_discount==0){
                    this.coupons.remove(index);
                }
            }

            if(this.total_amount>=5000) {
                int number_of_coupons=0;
                if (this.getCurrent_status() == "ELITE" ){
                    number_of_coupons=3;
                }
                if (this.getCurrent_status() == "PRIME") {
                    number_of_coupons=2;
                }
                    float min = 5.0f;
                    float max = 15.0f;
                    int k= 0;
                    while (k< number_of_coupons) {
                        float  a =(int)  (Math.random() * (max - min + 1)) + min;
                        this.coupons.add((int)a);
                        k++;
                    }
                    int j = 0;
                    System.out.println("Here is the list of coupons you have won");
                    while (j < number_of_coupons) {
                        System.out.println(j+1+")"+this.coupons.get(j)+"%");
                        j++;
                    }
            }
            this.empty_cart();
        }
        else{
            System.out.println(" Insufficient Balance!");
        }

    }
    public void view_cart(Admin admin){

        if(this.cart.size()!=0) {
            int i=0;
            for (Product product : cart.keySet()) {
                System.out.println(i + ")Name of the product:" + product.getname());
                System.out.println("Product ID:" + product.get_product_id());
                System.out.println("Price:" + product.get_price() * cart.get(product).get(0));
                System.out.println("Quantity:" + product.getQuantity());
                product.printing_extra_details();
                System.out.println("\n");
                i++;
            }
        }
        if(this.if_added_deal_cart==1){
            int j=0;
            for(Product product:admin.list_of_deals()){
                System.out.println(j+")Name of the product:" +product.getname());
                System.out.println("Product ID:"+product.get_product_id());
                System.out.println("Price:"+product.get_price());
                System.out.println("Quantity:"+this.quantity_deal_product);
                product.printing_extra_details();
                System.out.println("\n");
                j++;
            }
            System.out.println("\n");
            System.out.println("Combined price:"+this.deal_combined_price);
        }
    }
    public void empty_cart(){

        this.cart=new HashMap<>();
        this.if_added_deal_cart=0;
        this.quantity_deal_product=0;
        this.deal_combined_price=0;
        this.total_amount=0;
        this.used_product_discount=0;
    }

    public String getCurrent_status(){
        return this.current_status;
    }
    public void setName(String name){
        this.name=name;

    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName(){
        return name;
    }
    public String get_password(){
        return password;
    }
    public void view_coupons(){
        if(this.coupons.size()==0){
            System.out.println("You have no coupons");

        }
        else{
            int j = 0;
            System.out.println("Here is the list of coupons you have won");
            while (j < this.coupons.size()) {
                System.out.println(j+1+")"+this.coupons.get(j));
                j++;
            }
        }
    }
    public void Signup(){
        System.out.println("Enter name");
        Scanner sc1=new Scanner(System.in);
        String name=sc1.nextLine();
        Scanner sc2=new Scanner(System.in);
        System.out.println("Enter password");
        String password=sc2.nextLine();
        this.setName(name);
        this.setPassword(password);
        System.out.println("customer successfully registered!!");
    }
    public void add_amount_to_wallet(float amount){
        this.wallet=this.wallet+amount;
    }
    public void browse_product(Admin admin){
        for(Category cat :admin.getCat_product().keySet() ){
            System.out.println(cat.get_category_id()+":"+ cat.get_name());
            for(int i=0;i<admin.getCat_product().get(cat).size();i++){
                System.out.println(i+1+")Name of the product:" +admin.getCat_product().get(cat).get(i).getname());
                System.out.println("Product ID:"+admin.getCat_product().get(cat).get(i).get_product_id());
                System.out.println("Price:"+admin.getCat_product().get(cat).get(i).get_price());
                System.out.println("Quantity:"+admin.getCat_product().get(cat).get(i).getQuantity());
                admin.getCat_product().get(cat).get(i).printing_extra_details();

                System.out.println("\n");
            }

        }
    }
    public float get_amount_in_wallet(){
        return this.wallet;
    }
    public void Updating_wallet(){
        this.wallet=this.wallet;
    }
}

class Prime_Customer extends Normal_Customer{

    private int add_coupons=2;

    public Prime_Customer(){
        super("PRIME",3,6,5,2);
    }

    @Override
    public void Updating_wallet(){
        this.wallet=this.wallet-200;
    }


}
class Elite_Customer extends Normal_Customer{
    int add_coupons=3;
    public Elite_Customer(){

        super("ELITE",1,2,10,0);
    }
    @Override
    public void Updating_wallet(){
        this.wallet=this.wallet-300;
    }
}