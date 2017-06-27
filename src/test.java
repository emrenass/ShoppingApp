import com.shopping.controller.ManageProduct;

public class test {

	public static void main(String[] args) {
			ManageProduct mp = new ManageProduct();
			for(int i = 0; i<1000000; i++){
				mp.addProduct("Mouse", 45, 1200, "Hardware");
				System.out.println("Added: " + i);
			}
		}
	}
