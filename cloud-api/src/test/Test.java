
public class Test {

    public static void main(String[] args) {

        String url = "lb://cloud-product:7654/product/dict/type/list";
        System.out.println(url.substring(url.indexOf("7654") + "7654".length()));
    }
}
