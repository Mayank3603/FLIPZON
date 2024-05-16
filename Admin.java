package Flipzon;
import java.util.*;

import static java.lang.Integer.parseInt;

class Category{

    private int category_id;
    private String name;
    private ArrayList<Product> products;
    public Category(int input_category_id, String category_name){
            this.category_id=input_category_id;
            this.name=category_name;
            this.products=new ArrayList<>();
    }
    public int get_category_id(){
        return category_id;
    }
    public void add_products(Product product){
        this.products.add(product);
    }
    public ArrayList get_products(){
        return this.products;
    }
    public String get_name(){
        return this.name;
    }
    public void remove_product(int index){
        this.products.remove(index);
    }

}
class Product {
    private String name;
    private float product_id;
    private float price;
    private int discount=0;
    private String Extra_details = null;
    private int quantity;
    private ArrayList<Float> Discounts_on_a_product;
    public ArrayList<Float> getDiscounts_on_a_product(){
        return this.Discounts_on_a_product;
    }

    public Product(String name, float product_id, float price,int quantity) {
        this.name = name;
        this.product_id = product_id;
        this.quantity=quantity;
        this.price = price;
        this.Discounts_on_a_product=new ArrayList<>();
    }
    public int getQuantity(){
        return this.quantity;
    }

    public Product(String name, float product_id, float price,int quantity, String extra_details) {
        this.name = name;
        this.product_id = product_id;
        this.Extra_details = extra_details;
        this.quantity=quantity;
        this.price = price;
    }
    public void Update_qunatity(float value){
        this.quantity=this.quantity-(int)value;
    }
    public String getname() {
        return name;
    }

    public float get_product_id() {
        return this.product_id;
    }

    public float get_price() {
        return this.price;
    }
    public void Make_discount(){
        this.discount=1;
        System.out.println("Enter discount for Elite, Prime and Normal customers respetively (in percent)");
        Scanner sc=new Scanner(System.in);
        String discounts=sc.nextLine();
        String [] discounts_percentage=discounts.split(" ");
        this.Discounts_on_a_product.add(Float.parseFloat(discounts_percentage[0]));
        this.Discounts_on_a_product.add(Float.parseFloat(discounts_percentage[1]));
        this.Discounts_on_a_product.add(Float.parseFloat(discounts_percentage[2]));
        
    }
    public int If_In_discount(){
        return this.discount;
    }
    public void printing_extra_details() {
        if (this.Extra_details != null) {
            String[] extra_details = this.Extra_details.split(" ");
            for (String s : extra_details) {
                System.out.println(s);
            }
        }
    }
}
public class Admin {
    private String name;
    private String psswd;
    private ArrayList<Category> categories;
    private HashMap<Category,ArrayList<Product>> cat_product;
    private ArrayList<Product> deal_on_products;
    private ArrayList<Integer> combined_price;
    public ArrayList<Product> list_of_deals(){
        return deal_on_products;

    }
    public ArrayList<Integer> list_of_prices(){
        return combined_price;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setPsswd(String password){
        this.psswd=password;
    }

    public  Admin(){
        this.cat_product=new HashMap<>();
        this.categories=new ArrayList<>();
        this.deal_on_products=new ArrayList<>();
        this.combined_price=new ArrayList<>();
    }

    public String getname(){
        return name;
    }

    public void Add_category(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter category ID");
        int input_category_id=sc.nextInt();

        if(categories.size()==0){
            Scanner sc4=new Scanner(System.in);
            System.out.println("Enter category name1:");
            String category_name= sc4.nextLine();
            Category cat1=new Category(input_category_id,category_name);
            categories.add(cat1);
        }
        else{
            int found=0;
            for(Category cat:categories){
                if(cat.get_category_id()==input_category_id){
                    found=1;
                    System.out.println("Dear Admin, the category ID is already used!! Please set a different and unique category ID");
                    break;
                }
            }
            if(found==0){
                Scanner sc4=new Scanner(System.in);
                System.out.println("Enter category name2:");
                String category_name= sc4.nextLine();
                Category cat1=new Category(input_category_id,category_name);
                categories.add(cat1);
            }
        }
    }
    public void Add_product(){
        Scanner sc1=new Scanner(System.in);
        Scanner sc2=new Scanner(System.in);
        System.out.println("Enter the following details of the product:");
        System.out.println("Enter Category ID");
        int cat_of_product= sc2.nextInt();
        System.out.println("Enter name:");
        String name=sc1.nextLine();
        System.out.println("Enter product ID:");
        float product_id=sc2.nextFloat();
        System.out.println("Enter Price in Rs:");
        float price =sc2.nextFloat();
        System.out.println("Enter the quantity of product:");
        int quantity=sc2.nextInt();
        System.out.println("Do you want to add more information about the product(y/n)");
        String more_info=sc2.next();
        int found=0;
        Category category=null;
        for(Category cat:categories){
            if(cat.get_category_id()==cat_of_product){
                found=1;
                category=cat;
                break;
            }
        }
        if(found==1) {
            Product product=null;
            if (more_info.equals("y")) {
                System.out.println("If so, Enter the details in space separated form:");
                Scanner sc3 = new Scanner(System.in);
                String extra_details = sc3.nextLine();
                product = new Product(name, product_id, price, quantity,extra_details);
            } else {
                product = new Product(name, product_id, price,quantity);

            }
            category.add_products(product);
            cat_product.put(category,category.get_products());
        }
        else{
            System.out.println("Dear Admin , You have not added the corresponding Category!Kindly add the corresponding Category first");
        }
    }
    public void delete_category(String name,int id){
        int found=0;

        Category cmp=null;
        for(Category cat:categories){
            if(cat.get_category_id()==id && cat.get_name().equals(name)){
                cmp=cat;
                found=1;
                break;
            }
        }
        if(found==1){
            cat_product.remove(cmp);
            System.out.println("Successfully deleted the Category!!!");
        }
        else{
            System.out.println("Sorry! No such Category found");
        }
    }
    public void Delete_Product(String cat_name,float product_id){
        Category CAT=null;
        int found=0;
        String [] cat_namee=cat_name.split(" ");
        for(Category cat:categories){
            String [] catt=cat.get_name().split(" ");
            if(catt.length!=cat_namee.length){
                continue;
            }
            for(int i=0;i<catt.length;i++){
                if(catt[i].equals(cat_namee[i])){
                    found=1;
                    CAT=cat;
                    break;
                }
            }
        }
        if(found==0){
            System.out.println("No Category found with name :"+cat_name);
        }
        else {
            int index = 0;
            for (int i = 0; i < cat_product.get(CAT).size(); i++) {
                if (cat_product.get(CAT).get(i).get_product_id() == product_id) {

                    index = i;
                    break;
                }
            }
            cat_product.get(CAT).remove(index);
            if (cat_product.get(CAT).size() == 0) {
                System.out.println("There is no products left in " + CAT.get_name() + "!!");
                System.out.println("So, We are deleting the this Category");
                this.delete_category(CAT.get_name(), CAT.get_category_id());

            }
        }

    }
    public void give_deals(){
        System.out.println("Dear Admin give the Product IDs you want to combine and giveaway a deal for");
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter product ID_1:");
        float product_id_1=sc.nextFloat();
        System.out.println("Enter product ID_2:");
        float product_id_2=sc.nextFloat();
        System.out.println("Enter combined price in RS:");
        Scanner sc2=new Scanner(System.in);
        String combined_price=sc2.nextLine();
        String [] combine=combined_price.split(" ");
        this.combined_price.add(parseInt(combine[0]));
        this.combined_price.add(parseInt(combine[1]));
        this.combined_price.add(parseInt(combine[2]));

        int found1=0;
        Category category1=null;
        int cat_of_product_1=(int)product_id_1;
        int found2=0;
        Category category2=null;
        int cat_of_product_2=(int)product_id_2;
        for(Category cat:categories){
            if(cat.get_category_id()==cat_of_product_1){
                found1=1;
                category1=cat;
                break;
            }
        }
        for(Category cat:categories){
            if(cat.get_category_id()==cat_of_product_2){
                found2=1;
                category2=cat;
                break;
            }
        }
        Product product1=null;
        for(int i=0;i<cat_product.get(category1).size();i++){
            if(cat_product.get(category1).get(i).get_product_id()==product_id_1){
                product1=cat_product.get(category1).get(i);
                break;
            }
        }
        Product product2=null;
        for(int i=0;i<cat_product.get(category2).size();i++){
            if(cat_product.get(category2).get(i).get_product_id()==product_id_2){
                product2=cat_product.get(category2).get(i);
                break;
            }
        }
        this.deal_on_products.add(product1);
        this.deal_on_products.add(product2);

    }
    public void set_discount(){
        System.out.println("Dear Admin give the Product ID you want to add discount for");
        System.out.println("Enter the Product ID:");
        Scanner sc=new Scanner(System.in);
        float product_id=sc.nextFloat();
        Category category=null;
        int cat_of_product=(int)product_id;
        for(Category cat:categories){
            if(cat.get_category_id()==cat_of_product){;
                category=cat;
                break;
            }
        }
        Product product=null;
        for(int i=0;i<cat_product.get(category).size();i++){
            if(cat_product.get(category).get(i).get_product_id()==product_id){
                product=cat_product.get(category).get(i);
                break;
            }
        }
        product.Make_discount();
    }
    public HashMap<Category,ArrayList<Product>> getCat_product(){
        return this.cat_product;
    }
    public ArrayList<Category> getCategories(){
        return this.categories;
    }
    public void printing_product_Catalog(){
            for(Category cat :cat_product.keySet() ){
                System.out.println(cat.get_category_id()+":"+ cat.get_name());
                for(int i=0;i<cat_product.get(cat).size();i++){
                    System.out.println(i+1+")Name of the product:" +cat_product.get(cat).get(i).getname());
                    System.out.println("Product ID:"+cat_product.get(cat).get(i).get_product_id());
                    System.out.println("Price:"+cat_product.get(cat).get(i).get_price());
                    System.out.println("Quantity:"+cat_product.get(cat).get(i).getQuantity());
                    cat_product.get(cat).get(i).printing_extra_details();
                    System.out.println("\n");
                }
            }
    }
}
